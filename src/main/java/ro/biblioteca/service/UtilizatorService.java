package ro.biblioteca.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Utilizator;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.UtilizatorRepository;

import java.util.List;

@Service
public class UtilizatorService {

    private final UtilizatorRepository utilizatorRepository;
    private final PasswordEncoder passwordEncoder;

    public UtilizatorService(UtilizatorRepository utilizatorRepository,
                             PasswordEncoder passwordEncoder) {
        this.utilizatorRepository = utilizatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Utilizator> findAll() {
        return utilizatorRepository.findAll();
    }

    public Utilizator findById(Long id) {
        return utilizatorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilizatorul cu id-ul " + id + " nu a fost gasit."));
    }

    public Utilizator save(Utilizator utilizator) {
        utilizator.setParola(passwordEncoder.encode(utilizator.getParola()));
        return utilizatorRepository.save(utilizator);
    }

    public Utilizator update(Long id, Utilizator utilizatorActualizat) {
        Utilizator utilizatorExistent = findById(id);

        utilizatorExistent.setUsername(utilizatorActualizat.getUsername());
        if (utilizatorActualizat.getParola() != null && !utilizatorActualizat.getParola().isBlank()) {
            utilizatorExistent.setParola(passwordEncoder.encode(utilizatorActualizat.getParola()));
        }
        utilizatorExistent.setEmail(utilizatorActualizat.getEmail());
        utilizatorExistent.setActiv(utilizatorActualizat.getActiv());
        utilizatorExistent.setCititor(utilizatorActualizat.getCititor());
        utilizatorExistent.setRoluri(utilizatorActualizat.getRoluri());

        return utilizatorRepository.save(utilizatorExistent);
    }

    public void deleteById(Long id) {
        Utilizator utilizator = findById(id);
        utilizatorRepository.delete(utilizator);
    }

    public boolean isCititorAssigned(Long cititorId) {
        return utilizatorRepository.existsByCititor_Id(cititorId);
    }

    public boolean isCititorAssignedToAnotherUser(Long cititorId, Long utilizatorId) {
        return utilizatorRepository.existsByCititor_IdAndIdNot(cititorId, utilizatorId);
    }
}