package com.medvida.api.service;

import com.medvida.api.dto.request.EspecialidadeRequestDTO;
import com.medvida.api.dto.response.EspecialidadeResponseDTO;
import com.medvida.api.entity.Especialidade;
import com.medvida.api.exception.ResourceNotFoundException;
import com.medvida.api.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadeService {

    @Autowired
    private EspecialidadeRepository repository;

    public static EspecialidadeResponseDTO toResponse(Especialidade e) {
        return new EspecialidadeResponseDTO(e.getId(), e.getNome());
    }

    public List<EspecialidadeResponseDTO> listarTodas() {
        return repository.findAll().stream().map(EspecialidadeService::toResponse).toList();
    }

    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade esp = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada com o ID: " + id));
        return toResponse(esp);
    }

    public EspecialidadeResponseDTO criar(EspecialidadeRequestDTO request) {
        Especialidade esp = new Especialidade();
        esp.setNome(request.getNome());
        return toResponse(repository.save(esp));
    }

    public EspecialidadeResponseDTO atualizar(Long id, EspecialidadeRequestDTO request) {
        Especialidade esp = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada para atualização com o ID: " + id));
        esp.setNome(request.getNome());
        return toResponse(repository.save(esp));
    }

    public String deletar(Long id, String comentario) {
        Especialidade esp = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidade não encontrada para exclusão com o ID: " + id));
        String nomeEspecialidade = esp.getNome();
        repository.delete(esp);
        return nomeEspecialidade;
    }
}