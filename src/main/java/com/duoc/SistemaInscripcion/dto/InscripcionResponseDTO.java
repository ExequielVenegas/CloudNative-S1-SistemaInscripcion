package com.duoc.SistemaInscripcion.dto;

import com.duoc.SistemaInscripcion.model.Curso;
import com.duoc.SistemaInscripcion.model.Estudiante;
import lombok.Data;
import java.util.List;

@Data
public class InscripcionResponseDTO {
    private Long id;
    private Estudiante estudiante;
    private List<Curso> cursos;
    private Double totalPagar;
}