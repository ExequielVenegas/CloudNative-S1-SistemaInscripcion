package com.duoc.SistemaInscripcion.controller;

import com.duoc.SistemaInscripcion.model.Curso;
import com.duoc.SistemaInscripcion.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listarCursos() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @PostMapping
    public ResponseEntity<Curso> agregarCurso(@RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.agregarCurso(curso));
    }
}