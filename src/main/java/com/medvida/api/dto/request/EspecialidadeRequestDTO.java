package com.medvida.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EspecialidadeRequestDTO {

    @Schema(description = "Nome da especialidade médica", example = "Cardiologia")
    @NotBlank(message = "O nome da especialidade não pode ficar em branco.")
    @Size(min = 3, max = 100, message = "O nome da especialidade deve ter entre 3 e 100 caracteres.")
    private String nome;

    public EspecialidadeRequestDTO() {
    }

    public EspecialidadeRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}