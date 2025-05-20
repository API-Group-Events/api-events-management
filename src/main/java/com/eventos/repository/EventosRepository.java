package com.eventos.repository;

import com.eventos.models.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {
 
    @Query("SELECT e FROM Eventos e WHERE e.id = :id")
    Optional<Eventos> findById(Long id);
}