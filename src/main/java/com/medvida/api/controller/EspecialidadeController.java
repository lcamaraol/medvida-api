package com.medvida.api.controller;

import com.medvida.api.dto.request.EspecialidadeRequestDTO;
import com.medvida.api.dto.response.EspecialidadeResponseDTO;
import com.medvida.api.service.EspecialidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades", description = "Endpoints para gerenciamento de especialidades médicas.")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService service;

    @Operation(summary = "Listar todas as especialidades", description = "Retorna uma lista de todas as especialidades cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EspecialidadeResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Buscar especialidade por ID", description = "Retorna uma especialidade específica pelo seu identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> buscarPorId(
            @Parameter(description = "ID da especialidade", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar uma nova especialidade", description = "Cria uma nova especialidade no sistema e retorna sua URI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialidade cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou em branco")
    })
    @PostMapping
    public ResponseEntity<EspecialidadeResponseDTO> criar(@Valid @RequestBody EspecialidadeRequestDTO dto) {
        EspecialidadeResponseDTO responseDTO = service.criar(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Atualizar o cadastro de uma especialidade", description = "Atualiza os dados de uma especialidade baseada no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EspecialidadeResponseDTO> atualizar(
            @Parameter(description = "ID da especialidade", example = "1") @PathVariable Long id,
            @Valid @RequestBody EspecialidadeRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir uma especialidade", description = "Remove permanentemente a especialidade informando o motivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID da especialidade a ser excluída", example = "1") @PathVariable Long id,
            @Parameter(description = "Motivo da exclusão", example = "Erro de cadastro") @RequestParam String comentario) {
        
        String nomeEsp = service.deletar(id, comentario);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Especialidade '" + nomeEsp + "' excluída com sucesso!",
                "motivo_cancelamento", comentario));
    }
}