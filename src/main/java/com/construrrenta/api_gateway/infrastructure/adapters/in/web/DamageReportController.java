package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.domain.ports.out.ManageBookingUseCase;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/damage-reports")
@RequiredArgsConstructor

public class DamageReportController {
    
    private final ManageBookingUseCase manageBookingUseCase;

    @GetMapping
    public ResponseEntity<List<DamageReport>> getAllDamageReports() {
        return ResponseEntity.ok(manageBookingUseCase.getAllDamageReports());
    }
}
