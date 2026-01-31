package com.construrrenta.api_gateway.application.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.DamageReportRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.ManageBookingUseCase;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService implements ManageBookingUseCase {

    private final BookingRepositoryPort bookingRepositoryPort;
    private final ToolRepositoryPort toolRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final DamageReportRepositoryPort damageReportRepositoryPort;

    @Override
    @Transactional
    public Booking createBooking(UUID userId, UUID toolId, LocalDateTime startDate, LocalDateTime endDate) {
        // CORRECCIÓN: Usamos findById directamente. Un UUID convertido a String no es un email válido.
        if (userRepositoryPort.findById(userId).isEmpty()) {
            throw new DomainException("El usuario no existe");
        }

        Tool tool = toolRepositoryPort.findById(toolId)
                .orElseThrow(() -> new DomainException("La herramienta no existe"));

        if (tool.getStatus() == ToolStatus.MAINTENANCE || tool.getStatus() == ToolStatus.DELETED) {
            throw new DomainException("La herramienta no está disponible para renta (Mantenimiento o Eliminada)");
        }

        List<Booking> conflicts = bookingRepositoryPort.findConflictingBookings(toolId, startDate, endDate);
        if (!conflicts.isEmpty()) {
            throw new DomainException("La herramienta ya está reservada en las fechas seleccionadas");
        }

        Booking newBooking = Booking.create(userId, tool, startDate, endDate);
        return bookingRepositoryPort.save(newBooking);
    }

    @Override
    public Booking getBooking(UUID id) {
        return bookingRepositoryPort.findById(id)
                .orElseThrow(() -> new DomainException("Reserva no encontrada"));
    }

    // --- MÉTODOS QUE FALTABAN PARA CUMPLIR EL CONTRATO ---

    @Override
    public List<Booking> getBookingsByUser(UUID userId) {
        return bookingRepositoryPort.findByUserId(userId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepositoryPort.findAll();
    }

    @Override
    @Transactional
    public void cancelBooking(UUID bookingId) {
        Booking booking = getBooking(bookingId);
        booking.cancel(); // Asegúrate de tener este método en tu modelo Booking
        bookingRepositoryPort.save(booking);
    }

    @Override
    @Transactional
    public void completeBooking(UUID bookingId) {
        // Este método es útil si quieres finalizar sin reportar daños explícitamente
        Booking booking = getBooking(bookingId);
        booking.complete();
        bookingRepositoryPort.save(booking);
    }

    // -----------------------------------------------------

    @Override
    @Transactional
    public void confirmBookingPayment(UUID bookingId, UUID paymentId) {
        Booking booking = getBooking(bookingId);
        booking.confirmPayment(paymentId);
        bookingRepositoryPort.save(booking);
    }

    @Override
    @Transactional
    public void registerReturn(UUID bookingId, boolean withDamage, String damageDescription, BigDecimal repairCost) {
        Booking booking = getBooking(bookingId);
        
        // Finalizamos la reserva
        booking.complete(); 

        if (withDamage) {
            DamageReport report = DamageReport.create(damageDescription, repairCost, bookingId);
            damageReportRepositoryPort.save(report);
            
            // Opcional: Cambiar estado de herramienta a MANTENIMIENTO si lo deseas
            // Tool tool = toolRepositoryPort.findById(booking.getToolId()).orElseThrow();
            // tool.setStatus(ToolStatus.MAINTENANCE);
            // toolRepositoryPort.save(tool);
        }

        bookingRepositoryPort.save(booking);
    }
}