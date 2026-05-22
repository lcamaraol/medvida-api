package com.medvida.api.service;

import com.medvida.api.dto.request.ProntuarioRequestDTO;
import com.medvida.api.dto.response.ProntuarioResponseDTO;
import com.medvida.api.entity.Paciente;
import com.medvida.api.entity.Prontuario;
import com.medvida.api.exception.ResourceNotFoundException;
import com.medvida.api.repository.PacienteRepository;
import com.medvida.api.repository.ProntuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ProntuarioService {

    @Autowired
    private ProntuarioRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public static ProntuarioResponseDTO toResponse(Prontuario p) {
        return new ProntuarioResponseDTO(p.getId(), p.getPaciente().getNome(), p.getDescricao());
    }

    public ProntuarioResponseDTO buscarPorPacienteId(Long pacienteId) {
        Prontuario p = repository.findByPacienteId(pacienteId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Prontuário clínico não encontrado para este paciente."));
        return toResponse(p);
    }

    public ProntuarioResponseDTO criarOuAtualizar(ProntuarioRequestDTO request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com o ID: " + request.getPacienteId()));

        Prontuario p = repository.findByPacienteId(request.getPacienteId()).orElseGet(() -> {
            Prontuario novo = new Prontuario();
            novo.setPaciente(paciente);
            novo.setDataCriacao(LocalDate.now());
            return novo;
        });

        String dataFormatada = "[" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "]";
        String novaEvolucao = dataFormatada + " - " + request.getDescricao();

        if (p.getDescricao() == null || p.getDescricao().isBlank()) {
            p.setDescricao(novaEvolucao);
        } else {
            p.setDescricao(p.getDescricao() + " | " + novaEvolucao);
        }

        return toResponse(repository.save(p));
    }

    public String deletar(Long id, String comentario) {
        Prontuario p = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Prontuário não encontrado para exclusão com o ID: " + id));
        String nomePaciente = p.getPaciente().getNome();
        repository.delete(p);
        return nomePaciente;
    }
}