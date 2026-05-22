package com.medvida.api.repository;

import com.medvida.api.entity.Prontuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {
    
    @Query("SELECT p FROM Prontuario p WHERE p.paciente.id = :pacienteId")
    Optional<Prontuario> findByPacienteId(@Param("pacienteId") Long pacienteId);
}
