package ro.biblioteca.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.entity.Utilizator;
import ro.biblioteca.repository.UtilizatorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UtilizatorServiceTest {

    @Mock
    private UtilizatorRepository utilizatorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UtilizatorService utilizatorService;

    private Utilizator utilizator;

    @BeforeEach
    void setUp() {
        utilizator = new Utilizator();
        utilizator.setUsername("user1");
        utilizator.setParola("plainPass");
        utilizator.setEmail("user1@example.com");
        utilizator.setActiv(true);
        utilizator.setCititor(new Cititor("Nume", "Prenume", "email@domain.local", "0720000000", java.time.LocalDate.now(), "STANDARD"));
    }

    @Test
    void save_shouldEncodePasswordAndSaveUser() {
        when(passwordEncoder.encode("plainPass")).thenReturn("encodedPass");
        when(utilizatorRepository.save(any(Utilizator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Utilizator saved = utilizatorService.save(utilizator);

        assertThat(saved.getParola()).isEqualTo("encodedPass");
        verify(utilizatorRepository, times(1)).save(utilizator);
    }

    @Test
    void update_shouldPreservePasswordWhenBlank() {
        Utilizator existing = new Utilizator();
        existing.setUsername("user1");
        existing.setParola("oldEncoded");
        existing.setEmail("user1@example.com");
        existing.setActiv(true);
        existing.setCititor(utilizator.getCititor());

        when(utilizatorRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(utilizatorRepository.save(any(Utilizator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Utilizator updated = new Utilizator();
        updated.setUsername("user1Updated");
        updated.setParola("");
        updated.setEmail("updated@example.com");
        updated.setActiv(false);
        updated.setCititor(utilizator.getCititor());

        Utilizator result = utilizatorService.update(1L, updated);

        assertThat(result.getUsername()).isEqualTo("user1Updated");
        assertThat(result.getParola()).isEqualTo("oldEncoded");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
        assertThat(result.getActiv()).isFalse();
        verify(utilizatorRepository).save(existing);
    }
}
