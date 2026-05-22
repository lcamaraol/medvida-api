package com.medvida.api.service;

import com.medvida.api.dto.request.ConsultaRequestDTO;
import com.medvida.api.dto.response.ConsultaResponseDTO;
import com.medvida.api.entity.Consulta;
import com.medvida.api.entity.Paciente;
import com.medvida.api.entity.Medico;
import com.medvida.api.exception.ResourceNotFoundException;
import com.medvida.api.repository.ConsultaRepository;
import com.medvida.api.repository.PacienteRepository;
import com.medvida.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    public static ConsultaResponseDTO toConsultaResponse(Consulta c) {
        String nomePaciente = c.getPaciente() != null ? c.getPaciente().getNome() : "Não informado";
        String nomeMedico = c.getMedico() != null ? c.getMedico().getNome() : "Não informado";

        return new ConsultaResponseDTO(
                c.getId(),
                c.getDataHora(),
                nomePaciente,
                nomeMedico,
                c.getStatus(),
                c.getDescricao()
        );
    }

    public List<ConsultaResponseDTO> listarTodas() {
        List<Consulta> consultas = repository.findAll();

        return consultas.stream().map(ConsultaService::toConsultaResponse).toList();
    }

    public ConsultaResponseDTO agendar(ConsultaRequestDTO request) {
        Paciente paciente = pacienteRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com o ID: " + request.getPacienteId()));

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico não encontrado com o ID: " + request.getMedicoId()));

        Consulta c = new Consulta();
        c.setDataHora(request.getDataHora());
        c.setStatus("AGENDADA");
        c.setDescricao(request.getDescricao());
        c.setPaciente(paciente);
        c.setMedico(medico);

        return toConsultaResponse(repository.save(c));
    }

    public ConsultaResponseDTO cancelar(Long id, String comentario) {
        Consulta c = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta não encontrada para cancelamento com o ID: " + id));

        c.setStatus("CANCELADA");

        String descricaoAtual = c.getDescricao() != null ? c.getDescricao() : "";
        c.setDescricao(descricaoAtual + " [Motivo do Cancelamento: " + comentario + "]");

        return toConsultaResponse(repository.save(c));
    }
}