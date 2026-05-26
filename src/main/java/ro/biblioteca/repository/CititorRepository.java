package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Cititor;

public interface CititorRepository extends JpaRepository<Cititor, Long> {
}