package com.medvida.api.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ConsultaResponseDTO {
    private Long id;
    private String pacienteNome;
    private String medicoNome;
    private String status;
    private String descricao;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm - dd/MM/yyyy")
    private LocalDateTime dataHora;

    public ConsultaResponseDTO(Long id, LocalDateTime dataHora, String pacienteNome, String medicoNome, String status,
            String descricao) {
        this.id = id;
        this.dataHora = dataHora;
        this.pacienteNome = pacienteNome;
        this.medicoNome = medicoNome;
        this.status = status;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getPacienteNome() {
        return pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getMedicoNome() {
        return medicoNome;
    }

    public void setMedicoNome(String medicoNome) {
        this.medicoNome = medicoNome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}