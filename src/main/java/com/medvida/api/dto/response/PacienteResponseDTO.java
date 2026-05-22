package com.medvida.api.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class PacienteResponseDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;

    public PacienteResponseDTO(Long id, String nome, String cpf, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    @JsonPropertyOrder({
            "id",
            "nome",
            "cpf",
            "email"
    })

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }
}