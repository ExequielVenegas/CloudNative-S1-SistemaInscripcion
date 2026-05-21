package com.duoc.SistemaInscripcion.repository;

import com.duoc.SistemaInscripcion.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}