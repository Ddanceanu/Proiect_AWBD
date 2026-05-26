package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Autor;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.AutorRepository;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Autor findById(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autorul cu id-ul " + id + " nu a fost gasit."));
    }

    public Autor save(Autor autor) {
        return autorRepository.save(autor);
    }

    public Autor update(Long id, Autor autorActualizat) {
        Autor autorExistent = findById(id);

        autorExistent.setNume(autorActualizat.getNume());
        autorExistent.setPrenume(autorActualizat.getPrenume());
        autorExistent.setNationalitate(autorActualizat.getNationalitate());
        autorExistent.setAnNastere(autorActualizat.getAnNastere());

        return autorRepository.save(autorExistent);
    }

    public void deleteById(Long id) {
        Autor autor = findById(id);
        autorRepository.delete(autor);
    }
}