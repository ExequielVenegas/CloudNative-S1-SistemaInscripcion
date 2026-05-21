package com.duoc.SistemaInscripcion.repository;

import com.duoc.SistemaInscripcion.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}