package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Utilizator;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.UtilizatorRepository;

import java.util.List;

@Service
public class UtilizatorService {

    private final UtilizatorRepository utilizatorRepository;

    public UtilizatorService(UtilizatorRepository utilizatorRepository) {
        this.utilizatorRepository = utilizatorRepository;
    }

    public List<Utilizator> findAll() {
        return utilizatorRepository.findAll();
    }

    public Utilizator findById(Long id) {
        return utilizatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizatorul cu id-ul " + id + " nu a fost gasit."));
    }

    public Utilizator save(Utilizator utilizator) {
        return utilizatorRepository.save(utilizator);
    }

    public Utilizator update(Long id, Utilizator utilizatorActualizat) {
        Utilizator utilizatorExistent = findById(id);

        utilizatorExistent.setUsername(utilizatorActualizat.getUsername());
        utilizatorExistent.setParola(utilizatorActualizat.getParola());
        utilizatorExistent.setActiv(utilizatorActualizat.getActiv());
        utilizatorExistent.setCititor(utilizatorActualizat.getCititor());
        utilizatorExistent.setRoluri(utilizatorActualizat.getRoluri());

        return utilizatorRepository.save(utilizatorExistent);
    }

    public void deleteById(Long id) {
        Utilizator utilizator = findById(id);
        utilizatorRepository.delete(utilizator);
    }
}