package com.example.Restaurante_ms.api.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrearCocineroRequest {
    private Map<String, Object> proyecto;
    private Map<String, Object> facultad;
    private CocineroDTO cocinero;
}


