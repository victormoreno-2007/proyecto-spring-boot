package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import com.construrrenta.api_gateway.domain.model.payment.Payment;
import com.construrrenta.api_gateway.domain.ports.out.PaymentRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.security.model.SecurityUser;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/payments") 
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepositoryPort paymentRepositoryPort;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        return ResponseEntity.ok(paymentRepositoryPort.findAll());
    }
    @GetMapping("/my-payments")
    public ResponseEntity<List<Payment>> getMyPayments(@AuthenticationPrincipal SecurityUser user) {
        return ResponseEntity.ok(paymentRepositoryPort.findByUserId(user.getId()));
    }
    
}