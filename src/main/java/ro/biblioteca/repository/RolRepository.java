package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
}