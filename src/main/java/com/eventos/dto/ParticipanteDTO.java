package com.eventos.dto;

import com.eventos.models.Participante;

public class ParticipanteDTO {

    private Long id;
    private String nome;
    private String email;
    private String telefone;
    
    public ParticipanteDTO() {}

    public ParticipanteDTO(Long id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
  
    // Metodo para converter Entity para DTO
    public static ParticipanteDTO fromEntity(Participante participante) {
        return new ParticipanteDTO(
            participante.getId(),
            participante.getNome(),
            participante.getEmail(),
            participante.getTelefone()
        );
    }
    
    // Metodo para converter DTO para Entity
    public Participante toEntity() {
        Participante participante = new Participante();
        participante.setId(this.id);
        participante.setNome(this.nome);
        participante.setEmail(this.email);
        participante.setTelefone(this.telefone);
        return participante;
    }

    // Getters e Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }

    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    public String getEmail() { 
        return email; 
    }
    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getTelefone() { 
        return telefone; 
    }
    public void setTelefone(String telefone) { 
        this.telefone = telefone; 
    }
}
