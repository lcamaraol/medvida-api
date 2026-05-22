package com.medvida.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ConsultaRequestDTO {

    @Schema(description = "ID do paciente", example = "1")
    @NotNull(message = "O ID do paciente é obrigatório.")
    private Long pacienteId;

    @Schema(description = "ID do médico", example = "1")
    @NotNull(message = "O ID do médico é obrigatório.")
    private Long medicoId;

    @Schema(description = "Data e hora da consulta", example = "14:30 - 22/05/2026")
    @NotNull(message = "A data e hora são obrigatórias.")
    @Future(message = "O agendamento deve ser para uma data futura.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm - dd/MM/yyyy")
    private LocalDateTime dataHora;

    @Schema(description = "Breve descrição da consulta", example = "Consulta de rotina para check-up")
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