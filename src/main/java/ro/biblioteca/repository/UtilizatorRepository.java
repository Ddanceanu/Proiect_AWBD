package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Utilizator;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
}