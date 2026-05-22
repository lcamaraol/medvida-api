package com.medvida.api.controller;

import com.medvida.api.dto.request.PacienteRequestDTO;
import com.medvida.api.dto.response.PacienteResponseDTO;
import com.medvida.api.exception.ResourceNotFoundException; 
import com.medvida.api.service.PacienteService;
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

@Tag(name = "Pacientes", description = "Endpoints para gerenciamento de pacientes.")
@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @Operation(summary = "Listar todos os pacientes", description = "Retorna uma lista contendo todos os pacientes cadastrados no sistema.")
    @ApiResponse(responseCode = "200", description = "Lista de pacientes retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<PacienteResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @Operation(summary = "Buscar paciente por ID", description = "Retorna os detalhes de um paciente específico com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> buscarPorId(
            @Parameter(description = "ID do paciente", example = "1") @PathVariable Long id) 
            throws ResourceNotFoundException {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Cadastrar novo paciente", description = "Salva um novo paciente no sistema se o CPF for único e retorna o recurso criado com sua URI.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados (ex: CPF já cadastrado)")
    })
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> criar(@Valid @RequestBody PacienteRequestDTO dto) {
        PacienteResponseDTO pacienteResponse = service.criar(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pacienteResponse.getId()) 
                .toUri();

        return ResponseEntity.created(uri).body(pacienteResponse);
    }

    @Operation(summary = "Atualizar um paciente", description = "Atualiza completamente as informações de um paciente existente com base no ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados informados inválidos"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> atualizar(
            @Parameter(description = "ID do paciente a ser atualizado", example = "1") @PathVariable Long id,
            @Valid @RequestBody PacienteRequestDTO dto) 
            throws ResourceNotFoundException {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir um paciente", description = "Remove o registro de um paciente do sistema através do ID, exigindo um motivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado para exclusão")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(
            @Parameter(description = "ID do paciente a ser excluído", example = "1") @PathVariable Long id,
            @Parameter(description = "Motivo da exclusão", example = "Solicitação do paciente") @RequestParam String comentario) 
            throws ResourceNotFoundException {
        
        String nomePaciente = service.deletar(id, comentario);
        return ResponseEntity.ok(Map.of(
                "mensagem", "Paciente '" + nomePaciente + "' excluído com sucesso!",
                "motivo_cancelamento", comentario));
    }
}