package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Categorie;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.CategorieRepository;

import java.util.List;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public Categorie findById(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria cu id-ul " + id + " nu a fost gasita."));
    }

    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie update(Long id, Categorie categorieActualizata) {
        Categorie categorieExistenta = findById(id);

        categorieExistenta.setNume(categorieActualizata.getNume());
        categorieExistenta.setDescriere(categorieActualizata.getDescriere());

        return categorieRepository.save(categorieExistenta);
    }

    public void deleteById(Long id) {
        Categorie categorie = findById(id);
        categorieRepository.delete(categorie);
    }
}