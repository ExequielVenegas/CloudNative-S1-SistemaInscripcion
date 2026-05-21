package com.duoc.SistemaInscripcion.dto;

import lombok.Data;
import java.util.List;

@Data
public class InscripcionRequestDTO {
    private Long estudianteId;
    private List<Long> cursoIds;
}