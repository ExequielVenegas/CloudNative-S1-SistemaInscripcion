package com.duoc.SistemaInscripcion.service;

import com.duoc.SistemaInscripcion.model.Curso;
import com.duoc.SistemaInscripcion.model.Inscripcion;
import com.duoc.SistemaInscripcion.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumenService {

    private final InscripcionRepository inscripcionRepository;

    public byte[] generarResumen(Long inscripcionId) {

        Inscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada con id: " + inscripcionId));

        StringBuilder sb = new StringBuilder();
        sb.append("=== RESUMEN DE INSCRIPCIÓN #").append(inscripcion.getId()).append(" ===\n\n");
        sb.append("Estudiante: ").append(inscripcion.getEstudiante().getNombre()).append("\n");
        sb.append("Email:      ").append(inscripcion.getEstudiante().getEmail()).append("\n\n");
        sb.append("Cursos inscritos:\n");

        for (Curso curso : inscripcion.getCursos()) {
            sb.append("  - ").append(curso.getNombre())
                    .append(" (Instructor: ").append(curso.getInstructor()).append(")")
                    .append(" | $").append(curso.getCosto()).append("\n");
        }

        sb.append("\nTotal a pagar: $").append(inscripcion.getTotalPagar()).append("\n");

        return sb.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }
}