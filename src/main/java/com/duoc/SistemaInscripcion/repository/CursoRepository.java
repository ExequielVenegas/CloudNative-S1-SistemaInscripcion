package com.duoc.SistemaInscripcion.repository;

import com.duoc.SistemaInscripcion.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}