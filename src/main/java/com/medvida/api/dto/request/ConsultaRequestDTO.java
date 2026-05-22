package com.medvida.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ConsultaRequestDTO {

    @Schema(description = "ID do Paciente")
    @NotNull(message = "O ID do paciente é obrigatório.")
    private Long pacienteId;

    @Schema(description = "ID do Médico")
    @NotNull(message = "O ID do médico é obrigatório.")
    private Long medicoId;

    @Schema(description = "Data e Hora da consulta")
    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "O agendamento deve ser para uma data futura.")
    private LocalDateTime dataHora;

    @Schema(description = "Descrição do atendimento clínico.")
    private String descricao;

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}