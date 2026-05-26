package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Editura;

public interface EdituraRepository extends JpaRepository<Editura, Long> {
}