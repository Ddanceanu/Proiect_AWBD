package ro.biblioteca.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.biblioteca.entity.Utilizator;

import java.util.Optional;

public interface UtilizatorRepository extends JpaRepository<Utilizator, Long> {
    @EntityGraph(attributePaths = "roluri")
    Optional<Utilizator> findByUsername(String username);

    boolean existsByCititor_Id(Long cititorId);

    boolean existsByCititor_IdAndIdNot(Long cititorId, Long utilizatorId);
}