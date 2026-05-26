package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Editura;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.EdituraRepository;

import java.util.List;

@Service
public class EdituraService {

    private final EdituraRepository edituraRepository;

    public EdituraService(EdituraRepository edituraRepository) {
        this.edituraRepository = edituraRepository;
    }

    public List<Editura> findAll() {
        return edituraRepository.findAll();
    }

    public Editura findById(Long id) {
        return edituraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Editura cu id-ul " + id + " nu a fost gasita."));
    }

    public Editura save(Editura editura) {
        return edituraRepository.save(editura);
    }

    public Editura update(Long id, Editura edituraActualizata) {
        Editura edituraExistenta = findById(id);

        edituraExistenta.setNume(edituraActualizata.getNume());
        edituraExistenta.setOras(edituraActualizata.getOras());
        edituraExistenta.setEmailContact(edituraActualizata.getEmailContact());

        return edituraRepository.save(edituraExistenta);
    }

    public void deleteById(Long id) {
        Editura editura = findById(id);
        edituraRepository.delete(editura);
    }
}