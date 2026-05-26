package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Rol;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.RolRepository;

import java.util.List;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> findAll() {
        return rolRepository.findAll();
    }

    public Rol findById(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rolul cu id-ul " + id + " nu a fost gasit."));
    }

    public Rol save(Rol rol) {
        return rolRepository.save(rol);
    }

    public Rol update(Long id, Rol rolActualizat) {
        Rol rolExistent = findById(id);

        rolExistent.setNume(rolActualizat.getNume());

        return rolRepository.save(rolExistent);
    }

    public void deleteById(Long id) {
        Rol rol = findById(id);
        rolRepository.delete(rol);
    }
}