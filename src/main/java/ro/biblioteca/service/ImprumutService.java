package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Imprumut;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.ImprumutRepository;

import java.util.List;

@Service
public class ImprumutService {

    private final ImprumutRepository imprumutRepository;

    public ImprumutService(ImprumutRepository imprumutRepository) {
        this.imprumutRepository = imprumutRepository;
    }

    public List<Imprumut> findAll() {
        return imprumutRepository.findAll();
    }

    public Imprumut findById(Long id) {
        return imprumutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imprumutul cu id-ul " + id + " nu a fost gasit."));
    }

    public Imprumut save(Imprumut imprumut) {
        return imprumutRepository.save(imprumut);
    }

    public Imprumut update(Long id, Imprumut imprumutActualizat) {
        Imprumut imprumutExistent = findById(id);

        imprumutExistent.setDataImprumut(imprumutActualizat.getDataImprumut());
        imprumutExistent.setDataScadenta(imprumutActualizat.getDataScadenta());
        imprumutExistent.setDataReturnare(imprumutActualizat.getDataReturnare());
        imprumutExistent.setStatus(imprumutActualizat.getStatus());
        imprumutExistent.setObservatii(imprumutActualizat.getObservatii());
        imprumutExistent.setCititor(imprumutActualizat.getCititor());
        imprumutExistent.setCarte(imprumutActualizat.getCarte());

        return imprumutRepository.save(imprumutExistent);
    }

    public void deleteById(Long id) {
        Imprumut imprumut = findById(id);
        imprumutRepository.delete(imprumut);
    }
}