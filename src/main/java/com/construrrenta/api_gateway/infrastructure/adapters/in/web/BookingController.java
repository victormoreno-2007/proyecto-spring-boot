package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.ports.out.ManageBookingUseCase;
import com.construrrenta.api_gateway.infrastructure.security.model.SecurityUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final ManageBookingUseCase manageBookingUseCase;

    // 1. CREAR RESERVA
    @PostMapping
    public ResponseEntity<Booking> createBooking(
        @AuthenticationPrincipal SecurityUser user,
        @RequestBody BookingRequest request
    ) {
        Booking booking = manageBookingUseCase.createBooking(
            user.getId(), 
            request.getToolId(),
            request.getStartDate(),
            request.getEndDate()
        );
        return ResponseEntity.ok(booking);
    }

    // 2. OBTENER UNA RESERVA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(manageBookingUseCase.getBooking(id));
    }

    // 3. OBTENER RESERVAS DE UN USUARIO (Historial del Cliente)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(manageBookingUseCase.getBookingsByUser(userId));
    }

    // 4. OBTENER TODAS LAS RESERVAS (Panel del Admin)
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(manageBookingUseCase.getAllBookings());
    }

    // 5. CANCELAR RESERVA
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable UUID id) {
        manageBookingUseCase.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    // 6. CONFIRMAR PAGO
    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<Void> confirmPayment(@PathVariable UUID id, @RequestBody PaymentConfirmationRequest request) {
        manageBookingUseCase.confirmBookingPayment(id, request.getPaymentId());
        return ResponseEntity.ok().build();
    }

    // 7. FINALIZAR / DEVOLVER HERRAMIENTA
    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnTool(@PathVariable UUID id, @RequestBody ReturnRequest request) {
        manageBookingUseCase.registerReturn(
            id,
            request.isWithDamage(),
            request.getDamageDescription(),
            request.getRepairCost()
        );
        return ResponseEntity.ok().build();
    }

    // 8. HISTORIAL SEGURO (Cliente)
    @GetMapping("/my-history")
    public ResponseEntity<List<Booking>> getMyHistory(@AuthenticationPrincipal UUID userId) {
        return ResponseEntity.ok(manageBookingUseCase.getBookingsByUser(userId));
    }
    
    // OBTENER RESERVAS DE UN PROVEEDOR (Gestión de Alquileres)
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Booking>> getProviderBookings(@PathVariable UUID providerId) {
        return ResponseEntity.ok(manageBookingUseCase.getBookingsByProvider(providerId));
    }

    // 10. REPORTAR DAÑO AL RECIBIR (Cliente)
    @PostMapping("/{id}/report-arrival-issue")
    public ResponseEntity<Void> reportArrivalIssue(@PathVariable UUID id, @RequestBody ReturnRequest request) {
        manageBookingUseCase.reportArrivalDamage(
            id,
            request.getDamageDescription()
        );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approveBooking(@PathVariable UUID id) {
        manageBookingUseCase.approveBooking(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Void> rejectBooking(@PathVariable UUID id) {
        manageBookingUseCase.rejectBooking(id);
        return ResponseEntity.ok().build();
    }


    public static class BookingRequest {
        private UUID userId;
        private UUID toolId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public UUID getToolId() { return toolId; }
        public void setToolId(UUID toolId) { this.toolId = toolId; }
        public LocalDateTime getStartDate() { return startDate; }
        public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
        public LocalDateTime getEndDate() { return endDate; }
        public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    }

    public static class PaymentConfirmationRequest {
        private String paymentId;
        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    }

    public static class ReturnRequest {
        private boolean withDamage;
        private String damageDescription;
        private BigDecimal repairCost;

        public boolean isWithDamage() { return withDamage; }
        public void setWithDamage(boolean withDamage) { this.withDamage = withDamage; }
        public String getDamageDescription() { return damageDescription; }
        public void setDamageDescription(String damageDescription) { this.damageDescription = damageDescription; }
        public BigDecimal getRepairCost() { return repairCost; }
        public void setRepairCost(BigDecimal repairCost) { this.repairCost = repairCost; }
    }
}