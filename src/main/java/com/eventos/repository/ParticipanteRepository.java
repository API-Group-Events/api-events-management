package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventos.models.Participante;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    
}
