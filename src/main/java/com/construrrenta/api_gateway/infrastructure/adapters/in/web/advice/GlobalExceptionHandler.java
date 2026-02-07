package com.construrrenta.api_gateway.infrastructure.adapters.in.web.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainException(DomainException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("error", "Conflicto de Reglas de Negocio");
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Error interno del servidor: " + ex.getMessage());
        response.put("error", "Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        
        // Mensaje amigable para el usuario
        response.put("message", "No se puede eliminar este registro porque tiene historial asociado (Reservas, Pagos, etc).");
        response.put("error", "Conflicto de Integridad");
        
        // Devolvemos 409 CONFLICT
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}