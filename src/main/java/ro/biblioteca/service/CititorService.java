package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Cititor;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.CititorRepository;

import java.util.List;

@Service
public class CititorService {

    private final CititorRepository cititorRepository;

    public CititorService(CititorRepository cititorRepository) {
        this.cititorRepository = cititorRepository;
    }

    public List<Cititor> findAll() {
        return cititorRepository.findAll();
    }

    public Cititor findById(Long id) {
        return cititorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cititorul cu id-ul " + id + " nu a fost gasit."));
    }

    public Cititor save(Cititor cititor) {
        return cititorRepository.save(cititor);
    }

    public Cititor update(Long id, Cititor cititorActualizat) {
        Cititor cititorExistent = findById(id);

        cititorExistent.setNume(cititorActualizat.getNume());
        cititorExistent.setPrenume(cititorActualizat.getPrenume());
        cititorExistent.setEmail(cititorActualizat.getEmail());
        cititorExistent.setTelefon(cititorActualizat.getTelefon());
        cititorExistent.setDataInscriere(cititorActualizat.getDataInscriere());
        cititorExistent.setTipCititor(cititorActualizat.getTipCititor());

        return cititorRepository.save(cititorExistent);
    }

    public void deleteById(Long id) {
        Cititor cititor = findById(id);
        cititorRepository.delete(cititor);
    }
}