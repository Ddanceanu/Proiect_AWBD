package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Imprumut;

public interface ImprumutRepository extends JpaRepository<Imprumut, Long> {
}