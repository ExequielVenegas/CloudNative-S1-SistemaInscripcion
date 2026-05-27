package com.duoc.SistemaInscripcion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/")
public class InicioController {

    @GetMapping
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of(
                "mensaje", "Bienvenido a la Plataforma Educativa",
                "version", "1.1.2",
                "status", "online"
        ));
    }
}
