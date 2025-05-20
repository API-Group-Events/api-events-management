package com.eventos.controllers;

import java.util.List;
import java.util.Optional;

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

import com.eventos.dto.ParticipanteDTO;
import com.eventos.services.ParticipanteService;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping
    public ResponseEntity<List<ParticipanteDTO>> listar() {
        List<ParticipanteDTO> participantes = participanteService.buscarTodos();
        return ResponseEntity.ok(participantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> buscarPorId(@PathVariable Long id) {
    Optional<ParticipanteDTO> participanteOpt = participanteService.buscarPorId(id);
    
    if (participanteOpt.isPresent()) {
        return ResponseEntity.ok(participanteOpt.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @PostMapping
    public ResponseEntity<ParticipanteDTO> salvar(@RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO participante = participanteService.inserir(participanteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(participante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteDTO> atualizar(@PathVariable Long id, @RequestBody ParticipanteDTO participanteDTO) {
        ParticipanteDTO participante = participanteService.atualizar(id, participanteDTO);
        return ResponseEntity.ok(participante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        participanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
