package com.duoc.SistemaInscripcion.service;

import com.duoc.SistemaInscripcion.dto.InscripcionRequestDTO;
import com.duoc.SistemaInscripcion.dto.InscripcionResponseDTO;
import com.duoc.SistemaInscripcion.model.Curso;
import com.duoc.SistemaInscripcion.model.Estudiante;
import com.duoc.SistemaInscripcion.model.Inscripcion;
import com.duoc.SistemaInscripcion.repository.CursoRepository;
import com.duoc.SistemaInscripcion.repository.EstudianteRepository;
import com.duoc.SistemaInscripcion.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;

    public InscripcionResponseDTO inscribir(InscripcionRequestDTO request) {

        // Busca el estudiante
        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Busca los cursos
        List<Curso> cursos = cursoRepository.findAllById(request.getCursoIds());

        // Calcula el total
        double total = cursos.stream()
                .mapToDouble(Curso::getCosto)
                .sum();

        // Guarda la inscripción
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCursos(cursos);
        inscripcion.setTotalPagar(total);
        inscripcionRepository.save(inscripcion);

        // Arma la respuesta
        InscripcionResponseDTO response = new InscripcionResponseDTO();
        response.setEstudiante(estudiante);
        response.setCursos(cursos);
        response.setTotalPagar(total);
        return response;
    }
}