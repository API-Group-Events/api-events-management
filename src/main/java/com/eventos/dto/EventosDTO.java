package com.eventos.dto;

import com.eventos.models.Eventos;

public class EventosDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String data;
    private String local;
    private Integer vagas;

    public EventosDTO() {}

    public EventosDTO(Long id, String nome, String descricao, String data, String local, Integer vagas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.vagas = vagas;
    }
    
    // Metodo para converter Entity para DTO
    public static EventosDTO fromEntity(Eventos eventos) {
        return eventos == null ? null : new EventosDTO(
            eventos.getId(),
            eventos.getNome(),
            eventos.getDescricao(),
            eventos.getData(),
            eventos.getLocal(),
            eventos.getVagas()
        );
    }
    
    // Metodo para converter DTO para Entity
    public Eventos toEntity() {
        Eventos eventos = new Eventos();
        eventos.setId(this.id);
        eventos.setNome(this.nome);
        eventos.setDescricao(this.descricao);
        eventos.setData(this.data);
        eventos.setLocal(this.local);
        eventos.setVagas(this.vagas);
        return eventos;
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

    public String getDescricao() { 
        return descricao; 
    }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }

    public String getData() { 
        return data; 
    }
    public void setData(String data) { 
        this.data = data; 
    }

    public String getLocal() {
        return local; 
    }
    public void setLocal(String local) { 
        this.local = local; 
    }

    public Integer getVagas() { 
        return vagas; 
    }
    public void setVagas(Integer vagas) { 
        this.vagas = vagas; 
    }
}
