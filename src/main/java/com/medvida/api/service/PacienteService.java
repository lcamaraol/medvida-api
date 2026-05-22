package com.medvida.api.service;

import com.medvida.api.dto.request.PacienteRequestDTO;
import com.medvida.api.dto.response.PacienteResponseDTO;
import com.medvida.api.entity.Paciente;
import com.medvida.api.exception.DuplicateEntryException;
import com.medvida.api.exception.ResourceNotFoundException;
import com.medvida.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    public static PacienteResponseDTO toResponse(Paciente p) {
        return new PacienteResponseDTO(p.getId(), p.getNome(), p.getCpf(), p.getEmail());
    }

    public List<PacienteResponseDTO> listarTodos() {
        return repository.findAll().stream().map(PacienteService::toResponse).toList();
    }

    public PacienteResponseDTO buscarPorId(Long id) {
        Paciente p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com o ID: " + id));
        return toResponse(p);
    }

    public PacienteResponseDTO criar(PacienteRequestDTO request) {
        if (repository.existsByCpf(request.getCpf())) {
            throw new DuplicateEntryException("Já existe um paciente cadastrado com este CPF.");
        }
        Paciente p = new Paciente();
        p.setNome(request.getNome());
        p.setCpf(request.getCpf());
        p.setEmail(request.getEmail());
        return toResponse(repository.save(p));
    }

    public PacienteResponseDTO atualizar(Long id, PacienteRequestDTO request) {
        Paciente p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado para atualização."));
        p.setNome(request.getNome());
        p.setCpf(request.getCpf());
        p.setEmail(request.getEmail());
        return toResponse(repository.save(p));
    }

    public String deletar(Long id, String comentario) {
        Paciente p = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado para exclusão com o ID: " + id));
        String nomePaciente = p.getNome();
        repository.delete(p);
        return nomePaciente;
    }
}