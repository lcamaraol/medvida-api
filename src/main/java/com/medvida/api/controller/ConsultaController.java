package com.medvida.api.controller;

import com.medvida.api.dto.request.ConsultaRequestDTO;
import com.medvida.api.dto.response.ConsultaResponseDTO;
import com.medvida.api.service.ConsultaService;
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

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Endpoints para agendamento e fluxo de atendimento.")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @Operation(summary = "Visualizar agenda completa de consultas", description = "Retorna uma lista contendo todos os agendamentos do sistema.")
    @ApiResponse(responseCode = "200", description = "Agenda retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @Operation(summary = "Agendar uma nova consulta/atendimento", description = "Salva um novo agendamento no sistema e retorna a URI de acesso.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consulta agendada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Paciente ou Médico não encontrado")
    })
    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@Valid @RequestBody ConsultaRequestDTO dto) {
        ConsultaResponseDTO responseDTO = service.agendar(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Cancelar um agendamento existente", description = "Cancela uma consulta informando obrigatoriamente o motivo do cancelamento.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada")
    })
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseDTO> cancelar(
            @Parameter(description = "ID da consulta", example = "1") @PathVariable Long id,
            @Parameter(description = "Motivo do cancelamento", example = "Paciente desmarcou por imprevisto") @RequestParam String comentario) {

        ConsultaResponseDTO consultaCancelada = service.cancelar(id, comentario);
        return ResponseEntity.ok(consultaCancelada);
    }
}