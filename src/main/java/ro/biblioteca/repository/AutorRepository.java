package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}

// pentru a exista metode precum findAll(), findById(), etc