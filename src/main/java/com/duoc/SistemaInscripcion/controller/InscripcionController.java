package com.duoc.SistemaInscripcion.controller;

import com.duoc.SistemaInscripcion.dto.InscripcionRequestDTO;
import com.duoc.SistemaInscripcion.dto.InscripcionResponseDTO;
import com.duoc.SistemaInscripcion.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> inscribir(
            @RequestBody InscripcionRequestDTO request) {
        return ResponseEntity.ok(inscripcionService.inscribir(request));
    }
}