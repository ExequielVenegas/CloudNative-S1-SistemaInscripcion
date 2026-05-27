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

        Estudiante estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        List<Curso> cursos = cursoRepository.findAllById(request.getCursoIds());

        double total = cursos.stream()
                .mapToDouble(Curso::getCosto)
                .sum();

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCursos(cursos);
        inscripcion.setTotalPagar(total);
        inscripcionRepository.save(inscripcion);

        InscripcionResponseDTO response = new InscripcionResponseDTO();
        response.setId(inscripcion.getId());
        response.setEstudiante(estudiante);
        response.setCursos(cursos);
        response.setTotalPagar(total);
        return response;
    }

    public List<InscripcionResponseDTO> obtenerTodas() {
        return inscripcionRepository.findAll().stream()
                .map(inscripcion -> {
                    InscripcionResponseDTO response = new InscripcionResponseDTO();
                    response.setId(inscripcion.getId());
                    response.setEstudiante(inscripcion.getEstudiante());
                    response.setCursos(inscripcion.getCursos());
                    response.setTotalPagar(inscripcion.getTotalPagar());
                    return response;
                })
                .collect(java.util.stream.Collectors.toList());
    }
}