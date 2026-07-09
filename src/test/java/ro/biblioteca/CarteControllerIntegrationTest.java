package ro.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.biblioteca.repository.AutorRepository;
import ro.biblioteca.repository.CarteRepository;
import ro.biblioteca.repository.EdituraRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
class CarteControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private EdituraRepository edituraRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveCarte_withSelectedAuthorAndPublisher_persistsBook() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        Long autorId = autorRepository.findAll().get(0).getId();
        Long edituraId = edituraRepository.findAll().get(0).getId();
        String isbn = "ISBN-" + UUID.randomUUID();

        mockMvc.perform(post("/carti/salvare")
                        .with(csrf())
                        .param("titlu", "Test")
                        .param("isbn", isbn)
                        .param("anPublicare", "2003")
                        .param("numarPagini", "123")
                        .param("disponibila", "true")
                        .param("autorId", autorId.toString())
                        .param("edituraId", edituraId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carti"));

        assertThat(carteRepository.findAll())
                .anySatisfy(carte -> {
                    assertThat(carte.getTitlu()).isEqualTo("Test");
                    assertThat(carte.getIsbn()).isEqualTo(isbn);
                    assertThat(carte.getAutor().getId()).isEqualTo(autorId);
                    assertThat(carte.getEditura().getId()).isEqualTo(edituraId);
                });
    }
}
