package com.medvida.api.dto.response;

public class ProntuarioResponseDTO {
    private Long id;
    private String pacienteNome;
    private String descricao;

    public ProntuarioResponseDTO(Long id, String pacienteNome, String descricao) {
        this.id = id;
        this.pacienteNome = pacienteNome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}