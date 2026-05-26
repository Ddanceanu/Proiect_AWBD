package ro.biblioteca.service;

import org.springframework.stereotype.Service;
import ro.biblioteca.entity.Carte;
import ro.biblioteca.exception.ResourceNotFoundException;
import ro.biblioteca.repository.CarteRepository;

import java.util.List;

@Service
public class CarteService {

    private final CarteRepository carteRepository;

    public CarteService(CarteRepository carteRepository) {
        this.carteRepository = carteRepository;
    }

    public List<Carte> findAll() {
        return carteRepository.findAll();
    }

    public Carte findById(Long id) {
        return carteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cartea cu id-ul " + id + " nu a fost gasita."));
    }

    public Carte save(Carte carte) {
        return carteRepository.save(carte);
    }

    public Carte update(Long id, Carte carteActualizata) {
        Carte carteExistenta = findById(id);

        carteExistenta.setTitlu(carteActualizata.getTitlu());
        carteExistenta.setIsbn(carteActualizata.getIsbn());
        carteExistenta.setAnPublicare(carteActualizata.getAnPublicare());
        carteExistenta.setNumarPagini(carteActualizata.getNumarPagini());
        carteExistenta.setDisponibila(carteActualizata.getDisponibila());
        carteExistenta.setAutor(carteActualizata.getAutor());
        carteExistenta.setEditura(carteActualizata.getEditura());
        carteExistenta.setCategorii(carteActualizata.getCategorii());

        return carteRepository.save(carteExistenta);
    }

    public void deleteById(Long id) {
        Carte carte = findById(id);
        carteRepository.delete(carte);
    }
}