package com.medvida.api.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class MedicoRequestDTO {

    @Schema(description = "Nome completo do médico", example = "Dr. Carlos Eduardo")
    @NotBlank(message = "O nome do médico é obrigatório.")
    private String nome;

    @Schema(description = "CRM do médico com estado", example = "123456/RJ")
    @NotBlank(message = "O CRM é obrigatório.")
    @Pattern(regexp = "\\d{4,6}/[A-Z]{2}", message = "O CRM deve seguir o formato 123456/RJ.")
    private String crm;

    @Schema(description = "Lista de IDs das especialidades vinculadas ao médico. Pode conter um ou mais IDs.", type = "array", example = "[1, 2, 3]")
    @NotEmpty(message = "O médico deve ter pelo menos uma especialidade.")
    private List<Long> especialidadesIds;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public List<Long> getEspecialidadesIds() {
        return especialidadesIds;
    }

    public void setEspecialidadesIds(List<Long> especialidadesIds) {
        this.especialidadesIds = especialidadesIds;
    }
}