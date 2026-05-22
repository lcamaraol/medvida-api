package com.medvida.api.config;

import com.medvida.api.entity.*;
import com.medvida.api.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            EspecialidadeRepository especialidadeRepository,
            MedicoRepository medicoRepository,
            PacienteRepository pacienteRepository,
            ProntuarioRepository prontuarioRepository,
            ConsultaRepository consultaRepository) {

        return args -> {
            if (especialidadeRepository.count() == 0) {


                Especialidade cardio = new Especialidade();
                cardio.setNome("Cardiologia");
                especialidadeRepository.save(cardio);

                Especialidade pedia = new Especialidade();
                pedia.setNome("Pediatria");
                especialidadeRepository.save(pedia);

                Especialidade derma = new Especialidade();
                derma.setNome("Dermatologia");
                especialidadeRepository.save(derma);

                Medico m1 = new Medico();
                m1.setNome("Dr. Gregory House");
                m1.setCrm("CRM-12345");
                m1.setEspecialidades(List.of(cardio, pedia));
                medicoRepository.save(m1);

                Medico m2 = new Medico();
                m2.setNome("Dra. Meredith Grey");
                m2.setCrm("CRM-67890");
                m2.setEspecialidades(List.of(derma));
                medicoRepository.save(m2);

                Medico m3 = new Medico();
                m3.setNome("Dr. Stephen Strange");
                m3.setCrm("CRM-11223");
                m3.setEspecialidades(List.of(cardio));
                medicoRepository.save(m3);

                Paciente p1 = new Paciente();
                p1.setNome("Alice Silva");
                p1.setCpf("12345678901");
                p1.setEmail("alice@email.com");
                pacienteRepository.save(p1);

                Paciente p2 = new Paciente();
                p2.setNome("Bob Souza");
                p2.setCpf("98765432109");
                p2.setEmail("bob@email.com");
                pacienteRepository.save(p2);

                Prontuario pro1 = new Prontuario();
                pro1.setPaciente(p1);
                pro1.setDataCriacao(LocalDate.now());
                pro1.setDescricao("[22/05/2026] - Paciente com histórico de asma.");
                prontuarioRepository.save(pro1);

                Prontuario pro2 = new Prontuario();
                pro2.setPaciente(p2);
                pro2.setDataCriacao(LocalDate.now());
                pro2.setDescricao("[22/05/2026] - Paciente saudável, exames em dia.");
                prontuarioRepository.save(pro2);

                Consulta c1 = new Consulta();
                c1.setDataHora(LocalDateTime.now().plusDays(2));
                c1.setStatus("AGENDADA");
                c1.setDescricao("Consulta de rotina");
                c1.setPaciente(p1);
                c1.setMedico(m1);
                consultaRepository.save(c1);

                Consulta c2 = new Consulta();
                c2.setDataHora(LocalDateTime.now().plusDays(5));
                c2.setStatus("AGENDADA");
                c2.setDescricao("Retorno dermatológico");
                c2.setPaciente(p2);
                c2.setMedico(m2);
                consultaRepository.save(c2);

                System.out.println("Dados iniciais carregados com sucesso!");
            }
        };
    }
}