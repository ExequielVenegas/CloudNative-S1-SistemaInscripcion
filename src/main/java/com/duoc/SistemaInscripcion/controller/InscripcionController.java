package com.duoc.SistemaInscripcion.controller;

import com.duoc.SistemaInscripcion.dto.InscripcionRequestDTO;
import com.duoc.SistemaInscripcion.dto.InscripcionResponseDTO;
import com.duoc.SistemaInscripcion.service.InscripcionService;
import com.duoc.SistemaInscripcion.service.ResumenService;
import com.duoc.SistemaInscripcion.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final ResumenService resumenService;
    private final S3Service s3Service;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> inscribir(
            @RequestBody InscripcionRequestDTO request) {
        return ResponseEntity.ok(inscripcionService.inscribir(request));
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(inscripcionService.obtenerTodas());
    }

    @GetMapping("/{id}/resumen/descargar")
    public ResponseEntity<byte[]> descargarResumenLocal(@PathVariable Long id) {
        byte[] contenido = resumenService.generarResumen(id);
        String nombreArchivo = "resumen_inscripcion_" + id + ".txt";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(contenido);
    }

    @PostMapping("/{id}/resumen/subir")
    public ResponseEntity<String> subirResumenS3(@PathVariable Long id) {
        byte[] contenido = resumenService.generarResumen(id);
        String carpeta = String.valueOf(id);
        String nombreArchivo = "resumen_inscripcion_" + id + ".txt";

        s3Service.subirArchivo(carpeta, nombreArchivo, contenido);

        return ResponseEntity.ok("Resumen subido exitosamente a S3 en la carpeta: " + carpeta);
    }

    @GetMapping("/{id}/resumen/listar")
    public ResponseEntity<List<String>> listarArchivosS3(@PathVariable Long id) {
        String carpeta = String.valueOf(id);
        List<String> archivos = s3Service.listarArchivos(carpeta);

        return ResponseEntity.ok(archivos);
    }

    @GetMapping("/{id}/resumen/s3")
    public ResponseEntity<byte[]> descargarDesdeS3(@PathVariable Long id) {
        String carpeta = String.valueOf(id);
        String nombreArchivo = "resumen_inscripcion_" + id + ".txt";
        byte[] contenido = s3Service.descargarArchivo(carpeta, nombreArchivo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombreArchivo + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(contenido);
    }

    @PutMapping("/{id}/resumen")
    public ResponseEntity<String> modificarEnS3(
            @PathVariable Long id,
            @RequestBody String nuevoContenido) {
        String carpeta = String.valueOf(id);
        String nombreArchivo = "resumen_inscripcion_" + id + ".txt";

        s3Service.reemplazarArchivo(carpeta, nombreArchivo,
                nuevoContenido.getBytes(java.nio.charset.StandardCharsets.UTF_8));

        return ResponseEntity.ok("Archivo modificado exitosamente en S3.");
    }

    @DeleteMapping("/{id}/resumen")
    public ResponseEntity<String> eliminarDeS3(@PathVariable Long id) {
        String carpeta = String.valueOf(id);
        String nombreArchivo = "resumen_inscripcion_" + id + ".txt";

        s3Service.eliminarArchivo(carpeta, nombreArchivo);

        return ResponseEntity.ok("Archivo eliminado exitosamente de S3.");
    }

    @GetMapping("/resumen/listar")
    public ResponseEntity<List<String>> listarTodosLosArchivos() {
        return ResponseEntity.ok(s3Service.listarTodosLosArchivos());
    }
}