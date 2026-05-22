package com.medvida.api.controller;

import com.medvida.api.dto.request.ProntuarioRequestDTO;
import com.medvida.api.dto.response.ProntuarioResponseDTO;
import com.medvida.api.service.ProntuarioService;
import io.swagger.v3.oas.annotations.*;
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
import java.util.Map;

@RestController
@RequestMapping("/api/prontuarios")
@Tag(name = "Prontuários", description = "Endpoints para gerenciamento do prontuário dos pacientes.")
public class ProntuarioController {

    @Autowired
    private ProntuarioService service;

    @Operation(summary = "Buscar prontuário pelo ID do Paciente", description = "Retorna os dados do prontuário vinculado a um paciente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prontuário ou Paciente não encontrado")
    })
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<ProntuarioResponseDTO> buscarPorPaciente(
            @Parameter(description = "ID do paciente", example = "1") @PathVariable Long pacienteId) {
        return ResponseEntity.ok(service.buscarPorPacienteId(pacienteId));
    }

    @Operation(summary = "Registrar ou atualizar histórico médico no prontuário", description = "Cria um novo prontuário ou atualiza um existente e retorna a URI de acesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prontuário salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos")
    })
    @PostMapping
    public ResponseEntity<ProntuarioResponseDTO> salvar(@Valid @RequestBody ProntuarioRequestDTO dto) {
        ProntuarioResponseDTO responseDTO = service.criarOuAtualizar(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Excluir um prontuário", description = "Remove o histórico médico de um paciente informando um motivo obrigatório.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do prontuário a ser excluído", example = "1") @PathVariable Long id,
            @Parameter(description = "Motivo da exclusão", example = "Registro duplicado") @RequestParam String comentario) {
        
        String nomePaciente = service.deletar(id, comentario);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Prontuário do paciente '" + nomePaciente + "' excluído com sucesso!",
                "motivo_cancelamento", comentario));
    }
}