package com.eventos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import com.eventos.dto.EventosDTO;
import com.eventos.services.EventosService;

@RestController
@RequestMapping("/eventos")
public class EventosControllers {
    
    @Autowired
    private EventosService eventosService;
    
    @GetMapping
    public ResponseEntity<List<EventosDTO>> listar() {
        return ResponseEntity.ok(eventosService.buscarTodos());
    }
    
    @GetMapping("/{id}")
public ResponseEntity<EventosDTO> buscarPorId(@PathVariable Long id) {
    return eventosService.buscarPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
}

    
    @PostMapping
    public ResponseEntity<EventosDTO> salvar(@RequestBody EventosDTO eventosDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventosService.salvar(eventosDTO));

    }
    
    @PostMapping("/{eventoId}/participantes")
public ResponseEntity<?> adicionarParticipante(@PathVariable Long eventoId, @RequestBody Map<String, Long> payload) {
    Long participanteId = payload.get("participanteId");
    
    boolean inscrito = eventosService.inscreverParticipante(eventoId, participanteId);
    
    if (inscrito) {
        return ResponseEntity.ok().body(Map.of(
            "success", true,
            "message", "Participante inscrito com sucesso"
        ));
    } else {
        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "message", "Não foi possível inscrever o participante. Verifique se há vagas disponíveis."
        ));
    }
}

@PutMapping("/{id}")
public ResponseEntity<EventosDTO> atualizar(@PathVariable Long id, @RequestBody EventosDTO eventosDTO) {
    return eventosService.atualizar(id, eventosDTO)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventosService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
