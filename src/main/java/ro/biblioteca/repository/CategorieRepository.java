package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}