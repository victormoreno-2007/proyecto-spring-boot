package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.model.booking.BookingStatus;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/reports")
@RequiredArgsConstructor
public class AdminReportController {

    private final UserRepositoryPort userRepositoryPort;
    private final BookingRepositoryPort bookingRepositoryPort;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardStats> getDashboardStats() {
        long totalUsers = userRepositoryPort.findAll().size();

        List<Booking> allBookings = bookingRepositoryPort.findAll();
        
        long activeBookings = allBookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.PENDING)
                .count();

        BigDecimal totalRevenue = bookingRepositoryPort.findAll().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED || b.getStatus() == BookingStatus.COMPLETED)
                .map(Booking::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(DashboardStats.builder()
                .totalUsers(totalUsers)
                .activeBookings(activeBookings)
                .totalRevenue(totalRevenue)
                .build());
    }
    @GetMapping("/top-tools")
    public ResponseEntity<List<com.construrrenta.api_gateway.domain.model.tool.Tool>> getTopTools() {
        return ResponseEntity.ok(bookingRepositoryPort.findTopTools());
    }

    @GetMapping("/top-users")
    public ResponseEntity<List<com.construrrenta.api_gateway.domain.model.user.User>> getTopUsers() {
        return ResponseEntity.ok(bookingRepositoryPort.findTopUsers());
    }

    // DTO simple para enviar la respuesta
    @Data
    @Builder
    public static class DashboardStats {
        private long totalUsers;
        private long activeBookings;
        private BigDecimal totalRevenue;
    }
}