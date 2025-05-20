package com.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.eventos.models.Eventos;

public interface EventosRepository extends JpaRepository<Eventos, Long> {

    
}
