package com.medvida.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProntuarioRequestDTO {

    @Schema(description = "ID do Paciente dono do prontuário", example = "1")
    @NotNull(message = "O ID do paciente é obrigatório para o prontuário.")
    private Long pacienteId;

    @Schema(description = "Descrição do atendimento ou evolução clínica", example = "Paciente relatou dores de cabeça constantes na consulta.")
    @NotBlank(message = "A descrição não pode ficar em branco.")
    private String descricao;

    public Long getPacienteId() { 
        return pacienteId; 
    }
    
    public void setPacienteId(Long pacienteId) { 
        this.pacienteId = pacienteId; 
    }
    
    public String getDescricao() { 
        return descricao; 
    }
    
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }
}