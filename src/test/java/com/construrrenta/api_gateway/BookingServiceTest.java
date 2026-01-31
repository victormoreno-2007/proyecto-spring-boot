package com.construrrenta.api_gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.construrrenta.api_gateway.application.services.BookingService;
import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.model.booking.BookingStatus;
import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.DamageReportRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepositoryPort bookingRepositoryPort;
    @Mock
    private ToolRepositoryPort toolRepositoryPort;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private DamageReportRepositoryPort damageReportRepositoryPort;

    @InjectMocks
    private BookingService bookingService;

    private UUID userId;
    private UUID toolId;
    private UUID bookingId;
    private Tool mockTool;
    private Booking mockBooking;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        toolId = UUID.randomUUID();
        bookingId = UUID.randomUUID();

        // Preparamos una herramienta de prueba
        mockTool = Tool.reconstruct(
            toolId, "Taladro", "Potente", new BigDecimal("50.00"), 
            "img.jpg", ToolStatus.AVAILABLE, UUID.randomUUID()
        );
        
        // Preparamos una reserva de prueba
        mockBooking = Booking.reconstruct(
            bookingId, userId, toolId, null, 
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3), 
            new BigDecimal("100.00"), BookingStatus.CONFIRMED
        );
    }

    @Test
    void createBooking_UsuarioNoExiste_LanzaExcepcion() {
        // Simular que el repositorio de usuarios devuelve vacío
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> 
            bookingService.createBooking(userId, toolId, LocalDateTime.now(), LocalDateTime.now().plusDays(2))
        );
    }

    @Test
    void createBooking_Exitoso_GuardaReserva() {
        // Simulamos que todo está bien
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(mock(User.class)));
        when(toolRepositoryPort.findById(toolId)).thenReturn(Optional.of(mockTool));
        when(bookingRepositoryPort.findConflictingBookings(any(), any(), any())).thenReturn(Collections.emptyList());
        when(bookingRepositoryPort.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Booking result = bookingService.createBooking(
            userId, toolId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2)
        );

        assertNotNull(result);
        assertEquals(BookingStatus.PENDING, result.getStatus());
        verify(bookingRepositoryPort).save(any(Booking.class)); // Verifica que se llamó a guardar
    }

    @Test
    void registerReturn_ConDanios_GuardaReporte() {
        // Simulamos buscar la reserva
        when(bookingRepositoryPort.findById(bookingId)).thenReturn(Optional.of(mockBooking));

        // Ejecutamos la devolución con daños
        bookingService.registerReturn(bookingId, true, "Broca rota", new BigDecimal("20.00"));

        // VERIFICACIONES (Aquí está la magia)
        // 1. Verificamos que la reserva cambió a COMPLETADA
        assertEquals(BookingStatus.COMPLETED, mockBooking.getStatus());
        
        // 2. Verificamos que se guardó la reserva actualizada
        verify(bookingRepositoryPort).save(mockBooking);
        
        // 3. Verificamos que se llamó al repositorio de daños para guardar el reporte
        verify(damageReportRepositoryPort).save(any(DamageReport.class));
    }
    @Test
    void getAllDamageReports_DebeRetornarLista() {
        // 1. ARRANGE (Preparar)
        // Simulamos que el repositorio devuelve una lista con 1 reporte
        DamageReport reporteSimulado = DamageReport.create("Motor quemado", new BigDecimal("50.00"), UUID.randomUUID());
        when(damageReportRepositoryPort.findAll()).thenReturn(List.of(reporteSimulado));

        // 2. ACT (Actuar)
        var listaResultante = bookingService.getAllDamageReports();

        // 3. ASSERT (Verificar)
        assertNotNull(listaResultante);
        assertFalse(listaResultante.isEmpty());
        assertEquals(1, listaResultante.size());
        assertEquals("Motor quemado", listaResultante.get(0).getDescription());
        
        // Verificamos que "El Cerebro" le pidió los datos al puerto
        verify(damageReportRepositoryPort).findAll(); 
    }
}