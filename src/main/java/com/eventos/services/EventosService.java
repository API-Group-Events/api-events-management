package com.eventos.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventos.dto.EventosDTO;
import com.eventos.dto.ParticipanteDTO;
import com.eventos.models.Eventos;
import com.eventos.models.Participante;
import com.eventos.repository.EventosRepository;
import com.eventos.repository.ParticipanteRepository;

@Service
public class EventosService {
    
    @Autowired
    private EventosRepository eventosRepository;
    
    @Autowired
    private ParticipanteRepository participanteRepository;
    
    // Mapa para controlar participantes inscritos em eventos (eventoId -> lista de participanteId)
    private Map<Long, List<Long>> inscricoesMap = new HashMap<>();

    // Buscar todos os eventos
    public List<EventosDTO> buscarTodos() {
        List<Eventos> eventos = eventosRepository.findAll();
        return eventos.stream().map(EventosDTO::fromEntity).collect(Collectors.toList());
    }
    
    // Buscar por id do evento
    public Optional<EventosDTO> buscarPorId(Long id) {
        Optional<Eventos> eventos = eventosRepository.findById(id);
        return eventos.map(EventosDTO::fromEntity);
    }
    
    // Inserir um novo evento
    public EventosDTO inserir(EventosDTO eventosDTO) {
        Eventos eventos = eventosDTO.toEntity();
        Eventos savedEvento = eventosRepository.save(eventos);
        return EventosDTO.fromEntity(savedEvento);
    }
    
    // Atualizar um evento existente
    public EventosDTO atualizar(Long id, EventosDTO eventosDTO) {
        Optional<Eventos> eventosOpt = eventosRepository.findById(id);
        if (eventosOpt.isPresent()) {
            Eventos evento = eventosOpt.get();
            evento.setNome(eventosDTO.getNome());
            evento.setDescricao(eventosDTO.getDescricao());
            evento.setData(eventosDTO.getData());
            evento.setLocal(eventosDTO.getLocal());
            evento.setVagas(eventosDTO.getVagas());
            Eventos savedEvento = eventosRepository.save(evento);
            return EventosDTO.fromEntity(savedEvento);
        }
        return null;
    }
    
    // Remover um evento pelo id
    public void deletar(Long id) {
        eventosRepository.deleteById(id);
        // Remover inscrições do evento também
        inscricoesMap.remove(id);
    }
    
    // Verificar disponibilidade de vagas em um evento
    public boolean verificarDisponibilidadeVagas(Long eventoId) {
        Optional<Eventos> eventoOpt = eventosRepository.findById(eventoId);
        if (!eventoOpt.isPresent()) {
            throw new RuntimeException("Evento não encontrado");
        }
        
        Eventos evento = eventoOpt.get();
        Integer vagasDisponiveis = evento.getVagas();
        
        // Verificar quantos participantes já estão inscritos
        List<Long> inscritos = inscricoesMap.get(eventoId);
        int ocupadas = (inscritos != null) ? inscritos.size() : 0;
        
        return ocupadas < vagasDisponiveis;
    }
    
    // Obter número de vagas disponíveis
    public int getVagasDisponiveis(Long eventoId) {
        Optional<Eventos> eventoOpt = eventosRepository.findById(eventoId);
        if (!eventoOpt.isPresent()) {
            throw new RuntimeException("Evento não encontrado");
        }
        
        Eventos evento = eventoOpt.get();
        Integer totalVagas = evento.getVagas();
        
        // Verificar quantos participantes já estão inscritos
        List<Long> inscritos = inscricoesMap.get(eventoId);
        int ocupadas = (inscritos != null) ? inscritos.size() : 0;
        
        return totalVagas - ocupadas;
    }
    
    // Inscrever participante no evento (com verificação de vagas)
    public boolean inscreverParticipante(Long eventoId, Long participanteId) {
        // Verificar se há vagas disponíveis
        if (!verificarDisponibilidadeVagas(eventoId)) {
            return false; // Não há vagas disponíveis
        }
        
        // Verificar se o evento existe
        Optional<Eventos> eventoOpt = eventosRepository.findById(eventoId);
        if (!eventoOpt.isPresent()) {
            throw new RuntimeException("Evento não encontrado");
        }
        
        // Verificar se o participante existe
        Optional<Participante> participanteOpt = participanteRepository.findById(participanteId);
        if (!participanteOpt.isPresent()) {
            throw new RuntimeException("Participante não encontrado");
        }
        
        // Verificar se o participante já está inscrito
        List<Long> inscritos = inscricoesMap.computeIfAbsent(eventoId, k -> new java.util.ArrayList<>());
        
        if (inscritos.contains(participanteId)) {
            return true; // Participante já inscrito
        }
        
        // Inscrever participante
        inscritos.add(participanteId);
        return true;
    }
    
    // Cancelar inscrição de participante
    public boolean cancelarInscricao(Long eventoId, Long participanteId) {
        List<Long> inscritos = inscricoesMap.get(eventoId);
        if (inscritos != null) {
            return inscritos.remove(participanteId);
        }
        return false;
    }
    
    // Listar participantes de um evento
    public List<ParticipanteDTO> listarParticipantesDoEvento(Long eventoId) {
        List<Long> participantesIds = inscricoesMap.getOrDefault(eventoId, new java.util.ArrayList<>());
        
        return participantesIds.stream()
                .map(participanteRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(participante -> {
                    ParticipanteDTO dto = new ParticipanteDTO();
                    dto.setId(participante.getId());
                    dto.setNome(participante.getNome());
                    dto.setEmail(participante.getEmail());
                    dto.setTelefone(participante.getTelefone());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
