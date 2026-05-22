package com.medvida.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class EspecialidadeResponseDTO {

    @Schema(description = "ID da especialidade no banco de dados: ", example = "1")
    private Long id;

    @Schema(description = "Nome da especialidade médica", example = "Cardiologia.")
    private String nome;

    public EspecialidadeResponseDTO() {
    }

    public EspecialidadeResponseDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

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
}