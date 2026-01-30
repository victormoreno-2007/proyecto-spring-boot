package com.construrrenta.api_gateway.application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.ManageBookingUseaCase;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class BookingService implements ManageBookingUseaCase{

    private final BookingRepositoryPort bookingRepositoryPort;
    private final ToolRepositoryPort toolRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;

    @Override
    @Transactional
    public Booking createBooking(UUID userId, UUID toolId, LocalDateTime startDate, LocalDateTime endDate) {
    // 1. Validar que el USUARIO existe (Usando el puerto de Víctor)
        // Solo verificamos existencia, no necesitamos el objeto completo si el repo tiene existsById
        // Pero por seguridad hexagonal, solemos traerlo o usar un método específico.
        if (userRepositoryPort.findByEmail(userId.toString()).isEmpty() && 
            userRepositoryPort.findById(userId).isEmpty()) { 
             // Ajusta según los métodos que Víctor haya puesto en UserRepositoryPort
             // Asumiré findById para este ejemplo:
             throw new DomainException("El usuario no existe");
        }
        // 2. Validar que la HERRAMIENTA existe (Usando el puerto de Laura)
        Tool tool = toolRepositoryPort.findById(toolId)
                .orElseThrow(() -> new DomainException("La herramienta no existe"));

        // 3. Lógica de Negocio: ¿La herramienta está en condiciones de ser rentada?
        if (tool.getStatus() == ToolStatus.MAINTENANCE || tool.getStatus() == ToolStatus.DELETED) {
            throw new DomainException("La herramienta no está disponible para renta (Mantenimiento o Eliminada)");
        }
        // 4. Lógica de Negocio (EL CEREBRO): VALIDACIÓN DE FECHAS CRUZADAS
        // "No puedo rentar si ya está ocupada esos días"
        List<Booking> conflicts = bookingRepositoryPort.findConflictingBookings(toolId, startDate, endDate);
        if (!conflicts.isEmpty()) {
            throw new DomainException("La herramienta ya está reservada en las fechas seleccionadas");
        }

        // 5. Crear la Reserva (Aquí el Modelo Booking calcula el precio automáticamente)
        // Tu modelo Booking.create ya valida que startDate < endDate
        Booking newBooking = Booking.create(userId, tool, startDate, endDate);

        // 6. Guardar usando el puerto
        return bookingRepositoryPort.save(newBooking);

    }
    @Override
    public Booking getBooking(UUID id) {
        return bookingRepositoryPort.findbyId(id)
                .orElseThrow(() -> new DomainException("Reserva no encontrada"));
    }

}


