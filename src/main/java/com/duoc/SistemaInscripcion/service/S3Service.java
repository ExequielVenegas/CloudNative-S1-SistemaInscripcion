package com.duoc.SistemaInscripcion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public void subirArchivo(String carpeta, String nombreArchivo, byte[] contenido) {
        String clave = carpeta + "/" + nombreArchivo;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(clave)
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(contenido));
    }

    public List<String> listarArchivos(String carpeta) {
        String prefijo = carpeta + "/";

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(prefijo)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }


    public byte[] descargarArchivo(String carpeta, String nombreArchivo) {
        String clave = carpeta + "/" + nombreArchivo;

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(clave)
                .build();

        return s3Client.getObjectAsBytes(request).asByteArray();
    }

    public void reemplazarArchivo(String carpeta, String nombreArchivo, byte[] nuevoContenido) {
        subirArchivo(carpeta, nombreArchivo, nuevoContenido);
    }

    public void eliminarArchivo(String carpeta, String nombreArchivo) {
        String clave = carpeta + "/" + nombreArchivo;

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(clave)
                .build();

        s3Client.deleteObject(request);
    }

    public List<String> listarTodosLosArchivos() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(S3Object::key)
                .collect(Collectors.toList());
    }
}