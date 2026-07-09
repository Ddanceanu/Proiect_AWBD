package ro.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.repository.CititorRepository;
import ro.biblioteca.repository.RolRepository;
import ro.biblioteca.repository.UtilizatorRepository;

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
class UtilizatorControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CititorRepository cititorRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UtilizatorRepository utilizatorRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    void saveUtilizator_withSelectedReader_persistsUser() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
        String suffix = UUID.randomUUID().toString();
        Cititor cititor = cititorRepository.save(new Cititor(
                "Popescu",
                "Test",
                "cititor-" + suffix + "@example.com",
                "0712345678",
                LocalDate.now(),
                "STANDARD"));
        Long rolId = rolRepository.findByNume("USER").orElseThrow().getId();
        String username = "user-" + suffix;

        mockMvc.perform(post("/utilizatori/salvare")
                        .with(csrf())
                        .param("username", username)
                        .param("parola", "pass123")
                        .param("email", "user-" + suffix + "@example.com")
                        .param("activ", "true")
                        .param("cititorId", cititor.getId().toString())
                        .param("rolIds", rolId.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/utilizatori"));

        var utilizator = utilizatorRepository.findByUsername(username).orElseThrow();
        assertThat(utilizator.getCititor().getId()).isEqualTo(cititor.getId());
        assertThat(utilizator.getRoluri()).anySatisfy(rol -> assertThat(rol.getNume()).isEqualTo("USER"));
        assertThat(utilizator.getParola()).isNotEqualTo("pass123");
    }
}
