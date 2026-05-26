package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Carte;

public interface CarteRepository extends JpaRepository<Carte, Long> {
}