package com.eventos.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.dto.ParticipanteDTO;
import com.eventos.models.Participante;
import com.eventos.repository.ParticipanteRepository;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;
    

    // GET - Buscar todos
    public List<ParticipanteDTO> buscarTodos() {
        List<Participante> participantes = participanteRepository.findAll();
        return participantes.stream().map(ParticipanteDTO::fromEntity).collect(Collectors.toList());
    }

    // GET - Buscar por ID
    public Optional<ParticipanteDTO> buscarPorId(Long id) {
        Optional<Participante> participante = participanteRepository.findById(id);
        return participante.map(ParticipanteDTO::fromEntity);
    }

    // POST - Inserir
    public ParticipanteDTO inserir(ParticipanteDTO participanteDTO) {
        Participante participante = participanteDTO.toEntity();
        Participante savedParticipante = participanteRepository.save(participante);
        return ParticipanteDTO.fromEntity(savedParticipante);
    }

    // PUT - Atualizar
    public ParticipanteDTO atualizar(Long id, ParticipanteDTO participanteDTO) {
        Optional<Participante> participanteOpt = participanteRepository.findById(id);
        if (participanteOpt.isPresent()) {
            Participante participante = participanteOpt.get();
            participante.setNome(participanteDTO.getNome());
            participante.setEmail(participanteDTO.getEmail());
            participante.setTelefone(participanteDTO.getTelefone());
            Participante savedParticipante = participanteRepository.save(participante);
            return ParticipanteDTO.fromEntity(savedParticipante);
        }
        return null;
    }

    // DELETE - Remover
    public void deletar(Long id) {
        participanteRepository.deleteById(id);
    }

}