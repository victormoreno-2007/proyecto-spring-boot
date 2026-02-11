package com.construrrenta.api_gateway.infrastructure.adapters.in.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevolucionDTO {
    private String estado;
    private String descripcionDano;
    private Double costoReparacion;
}