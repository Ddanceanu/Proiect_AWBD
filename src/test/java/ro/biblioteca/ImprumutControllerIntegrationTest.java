package ro.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.biblioteca.entity.Carte;
import ro.biblioteca.repository.AutorRepository;
import ro.biblioteca.repository.CarteRepository;
import ro.biblioteca.repository.CititorRepository;
import ro.biblioteca.repository.EdituraRepository;
import ro.biblioteca.repository.ImprumutRepository;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
class ImprumutControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EdituraRepository edituraRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private CititorRepository cititorRepository;

    @Autowired
    private ImprumutRepository imprumutRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveImprumut_withSelectedReaderAndBook_persistsLoan() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        Carte carte = carteRepository.save(new Carte(
                "Carte imprumut test",
                "ISBN-IMPRUMUT-" + UUID.randomUUID(),
                2024,
                180,
                true,
                autorRepository.findAll().get(0),
                edituraRepository.findAll().get(0)));
        Long cititorId = cititorRepository.findAll().get(0).getId();
        LocalDate dataImprumut = LocalDate.of(2026, 7, 9);
        LocalDate dataScadenta = dataImprumut.plusDays(14);

        mockMvc.perform(post("/imprumuturi/salvare")
                        .with(csrf())
                        .param("cititorId", cititorId.toString())
                        .param("carteId", carte.getId().toString())
                        .param("dataImprumut", dataImprumut.toString())
                        .param("dataScadenta", dataScadenta.toString())
                        .param("status", "Imprumutat")
                        .param("observatii", "Test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/imprumuturi"));

        assertThat(imprumutRepository.findAll())
                .anySatisfy(imprumut -> {
                    assertThat(imprumut.getCititor().getId()).isEqualTo(cititorId);
                    assertThat(imprumut.getCarte().getId()).isEqualTo(carte.getId());
                    assertThat(imprumut.getStatus()).isEqualTo("Imprumutat");
                });
    }
}
