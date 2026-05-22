package com.medvida.api.service;

import com.medvida.api.dto.request.MedicoRequestDTO;
import com.medvida.api.dto.response.MedicoResponseDTO;
import com.medvida.api.entity.Medico;
import com.medvida.api.entity.Especialidade;
import com.medvida.api.exception.DuplicateEntryException;
import com.medvida.api.exception.ResourceNotFoundException;
import com.medvida.api.repository.MedicoRepository;
import com.medvida.api.repository.EspecialidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    public static MedicoResponseDTO toResponse(Medico m) {
        String nomeEspecialidades = (m.getEspecialidades() != null && !m.getEspecialidades().isEmpty())
                ? m.getEspecialidades().stream().map(Especialidade::getNome).collect(Collectors.joining(", "))
                : "Sem Especialidade";

        return new MedicoResponseDTO(m.getId(), m.getNome(), m.getCrm(), nomeEspecialidades);
    }

    public List<MedicoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(MedicoService::toResponse).toList();
    }

    public MedicoResponseDTO buscarPorId(Long id) {
        Medico m = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com o ID: " + id));
        return toResponse(m);
    }

    public MedicoResponseDTO criar(MedicoRequestDTO request) {
        if (repository.existsByCrm(request.getCrm())) {
            throw new DuplicateEntryException("Já existe um médico cadastrado com este CRM.");
        }

        Medico m = new Medico();
        m.setNome(request.getNome());
        m.setCrm(request.getCrm());

        if (request.getEspecialidadesIds() != null) {
            for (Long idEsp : request.getEspecialidadesIds().stream().distinct().toList()) {
                Especialidade esp = especialidadeRepository.findById(idEsp)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Especialidade não encontrada com o ID: " + idEsp));
                m.getEspecialidades().add(esp);
            }
        }
        return toResponse(repository.save(m));
    }

    public MedicoResponseDTO atualizar(Long id, MedicoRequestDTO request) {
        Medico m = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com o ID: " + id));

        m.setNome(request.getNome());
        m.setCrm(request.getCrm());

        m.getEspecialidades().clear();

        if (request.getEspecialidadesIds() != null) {
            for (Long idEsp : request.getEspecialidadesIds().stream().distinct().toList()) {
                Especialidade esp = especialidadeRepository.findById(idEsp)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Especialidade não encontrada com o ID: " + idEsp));
                m.getEspecialidades().add(esp);
            }
        }
        return toResponse(repository.save(m));
    }

    public String deletar(Long id, String comentario) {
        Medico m = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Médico não encontrado para exclusão com o ID: " + id));
        String nomeMedico = m.getNome();
        repository.delete(m);
        return nomeMedico;
    }
}