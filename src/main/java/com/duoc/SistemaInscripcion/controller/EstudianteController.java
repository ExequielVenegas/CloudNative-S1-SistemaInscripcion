package com.duoc.SistemaInscripcion.controller;

import com.duoc.SistemaInscripcion.model.Estudiante;
import com.duoc.SistemaInscripcion.repository.EstudianteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
public class EstudianteController {

    private final EstudianteRepository estudianteRepository;

    @GetMapping
    public ResponseEntity<List<Estudiante>> listar() {
        return ResponseEntity.ok(estudianteRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Estudiante> agregar(@RequestBody Estudiante estudiante) {
        return ResponseEntity.ok(estudianteRepository.save(estudiante));
    }
}