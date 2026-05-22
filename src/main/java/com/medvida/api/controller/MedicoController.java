package com.medvida.api.controller;

import com.medvida.api.dto.request.MedicoRequestDTO;
import com.medvida.api.dto.response.MedicoResponseDTO;
import com.medvida.api.service.MedicoService;
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
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Endpoints para gerenciamento de médicos.")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Operation(summary = "Listar todos os médicos", description = "Retorna uma lista contendo todos os médicos cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de médicos retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<MedicoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar médico por ID", description = "Retorna os detalhes de um médico específico com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> buscarPorId(
            @Parameter(description = "ID do médico", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar novo médico", description = "Salva um novo médico no sistema e retorna o recurso criado acompanhado de sua URI de acesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Médico cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados (ex: CRM já cadastrado)")
    })
    @PostMapping
    public ResponseEntity<MedicoResponseDTO> criar(@Valid @RequestBody MedicoRequestDTO dto) {
        MedicoResponseDTO responseDTO = service.criar(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Atualizar um médico", description = "Atualiza os dados de um médico, incluindo suas especialidades.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico ou Especialidade não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MedicoResponseDTO> atualizar(
            @Parameter(description = "ID do médico", example = "1") @PathVariable Long id,
            @Valid @RequestBody MedicoRequestDTO dto) {

        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir um médico", description = "Remove o registro de um médico do sistema, exigindo um motivo obrigatório.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Médico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do médico a ser excluído", example = "1") @PathVariable Long id,
            @Parameter(description = "Motivo da exclusão", example = "Desligamento da clínica") @RequestParam String comentario) {

        String nomeMedico = service.deletar(id, comentario);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Médico '" + nomeMedico + "' excluído com sucesso!",
                "motivo_cancelamento", comentario));
    }
}