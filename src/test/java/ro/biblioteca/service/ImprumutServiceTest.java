package ro.biblioteca.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.biblioteca.entity.Carte;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.entity.Imprumut;
import ro.biblioteca.repository.ImprumutRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImprumutServiceTest {

    @Mock
    private ImprumutRepository imprumutRepository;

    @InjectMocks
    private ImprumutService imprumutService;

    private Imprumut imprumut;

    @BeforeEach
    void setUp() {
        imprumut = new Imprumut();
        imprumut.setDataImprumut(LocalDate.now());
        imprumut.setDataScadenta(LocalDate.now().plusDays(14));
        imprumut.setStatus("Împrumutat");
        imprumut.setObservatii("Fără observații");
        imprumut.setCititor(new Cititor("Nume", "Prenume", "email@domain.local", "0720000000", LocalDate.now(), "STANDARD"));
        imprumut.setCarte(new Carte());
    }

    @Test
    void save_shouldPersistLoan() {
        when(imprumutRepository.save(any(Imprumut.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Imprumut saved = imprumutService.save(imprumut);

        assertThat(saved).isSameAs(imprumut);
        verify(imprumutRepository, times(1)).save(imprumut);
    }

    @Test
    void update_shouldModifyExistingLoanFields() {
        Imprumut existing = new Imprumut();
        existing.setId(1L);
        existing.setDataImprumut(LocalDate.now().minusDays(1));
        existing.setDataScadenta(LocalDate.now().plusDays(7));
        existing.setStatus("Împrumutat");
        existing.setObservatii("Inițial");
        existing.setCititor(imprumut.getCititor());
        existing.setCarte(imprumut.getCarte());

        when(imprumutRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(imprumutRepository.save(any(Imprumut.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Imprumut actualizat = new Imprumut();
        actualizat.setDataImprumut(LocalDate.now());
        actualizat.setDataScadenta(LocalDate.now().plusDays(21));
        actualizat.setDataReturnare(LocalDate.now().plusDays(20));
        actualizat.setStatus("Returnat");
        actualizat.setObservatii("Returnat la timp");
        actualizat.setCititor(imprumut.getCititor());
        actualizat.setCarte(imprumut.getCarte());

        Imprumut result = imprumutService.update(1L, actualizat);

        assertThat(result.getStatus()).isEqualTo("Returnat");
        assertThat(result.getDataReturnare()).isEqualTo(LocalDate.now().plusDays(20));
        assertThat(result.getObservatii()).isEqualTo("Returnat la timp");
        verify(imprumutRepository).save(existing);
    }
}
