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
import com.construrrenta.api_gateway.domain.model.payment.Payment;
import com.construrrenta.api_gateway.domain.model.payment.PaymentMethod;
import com.construrrenta.api_gateway.domain.model.payment.PaymentStatus;
import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.DamageReportRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.PaymentRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock private BookingRepositoryPort bookingRepositoryPort;
    @Mock private ToolRepositoryPort toolRepositoryPort;
    @Mock private UserRepositoryPort userRepositoryPort;
    @Mock private DamageReportRepositoryPort damageReportRepositoryPort;
    @Mock private PaymentRepositoryPort paymentRepositoryPort; // <--- FALTABA ESTO PARA TUS TESTS

    @InjectMocks
    private BookingService bookingService;

    private UUID userId, toolId, bookingId;
    private Tool mockTool;
    private Booking mockBooking;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        toolId = UUID.randomUUID();
        bookingId = UUID.randomUUID();

        mockTool = Tool.reconstruct(toolId, "Taladro", "Potente", new BigDecimal("50.00"), 
            "img.jpg", ToolStatus.AVAILABLE, UUID.randomUUID(), 1); // <--- STOCK EN 1 PARA EL TEST DE FALLO
        
        mockBooking = Booking.reconstruct(bookingId, userId, mockTool, null, 
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(3), 
            new BigDecimal("100.00"), BookingStatus.CONFIRMED);
    }

    // --- TUS TESTS ORIGINALES ---
    @Test
    void createBooking_UsuarioNoExiste_LanzaExcepcion() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());
        assertThrows(DomainException.class, () -> 
            bookingService.createBooking(userId, toolId, LocalDateTime.now(), LocalDateTime.now().plusDays(2))
        );
    }

    @Test
    void createBooking_Exitoso_GuardaReserva() {
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(mock(User.class)));
        when(toolRepositoryPort.findById(toolId)).thenReturn(Optional.of(mockTool));
        when(bookingRepositoryPort.findConflictingBookings(any(), any(), any())).thenReturn(Collections.emptyList());
        when(bookingRepositoryPort.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        Booking result = bookingService.createBooking(userId, toolId, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
        assertNotNull(result);
        assertEquals(BookingStatus.PENDING, result.getStatus());
    }

    @Test
    void registerReturn_ConDanios_GuardaReporte() {
        when(bookingRepositoryPort.findById(bookingId)).thenReturn(Optional.of(mockBooking));
        bookingService.registerReturn(bookingId, true, "Broca rota", new BigDecimal("20.00"));
        assertEquals(BookingStatus.COMPLETED, mockBooking.getStatus());
        verify(damageReportRepositoryPort).save(any(DamageReport.class));
    }

    @Test
    void getAllDamageReports_DebeRetornarLista() {
        when(damageReportRepositoryPort.findAll()).thenReturn(List.of(new DamageReport()));
        var lista = bookingService.getAllDamageReports();
        assertFalse(lista.isEmpty());
    }

    // --- TUS NUEVOS TESTS (CLIENTE) ---

    @Test
    void confirmBookingPayment_DebeRegistrarPagoYConfirmarReserva() {
        Booking reservaPendiente = Booking.reconstruct(bookingId, userId, mockTool, null, LocalDateTime.now(), LocalDateTime.now().plusDays(2), new BigDecimal("100.00"), BookingStatus.PENDING);
        
        when(bookingRepositoryPort.findById(bookingId)).thenReturn(Optional.of(reservaPendiente));
        when(paymentRepositoryPort.save(any(Payment.class))).thenAnswer(i -> {
            Payment p = i.getArgument(0);
            return Payment.reconstruct(UUID.randomUUID(), p.getAmount(), p.getPaymentDate(), p.getMethod(), p.getStatus(), p.getBookingId());
        });

        bookingService.confirmBookingPayment(bookingId, "PAY-12345");

        assertEquals(BookingStatus.CONFIRMED, reservaPendiente.getStatus());
        assertNotNull(reservaPendiente.getPaymentId());
        verify(paymentRepositoryPort).save(any(Payment.class));
    }

    @Test
    void reportArrivalDamage_DebeCancelarReservaYReembolsar() {
        UUID paymentId = UUID.randomUUID();
        Booking reservaConfirmada = Booking.reconstruct(bookingId, userId, mockTool, paymentId, LocalDateTime.now(), LocalDateTime.now().plusDays(2), new BigDecimal("100.00"), BookingStatus.CONFIRMED);
        Payment pagoExistente = Payment.reconstruct(paymentId, new BigDecimal("100.00"), LocalDateTime.now(), PaymentMethod.CREDIT_CARD, PaymentStatus.COMPLETED, bookingId);

        when(bookingRepositoryPort.findById(bookingId)).thenReturn(Optional.of(reservaConfirmada));
        when(paymentRepositoryPort.findById(paymentId)).thenReturn(Optional.of(pagoExistente));

        bookingService.reportArrivalDamage(bookingId, "Llegó rota");

        assertEquals(BookingStatus.CANCELLED, reservaConfirmada.getStatus());
        assertEquals(PaymentStatus.REFUNDED, pagoExistente.getStatus());
        verify(damageReportRepositoryPort).save(any(DamageReport.class));
    }

    @Test
    void createBooking_SiNoHayStock_LanzaExcepcion() {
        List<Booking> conflictos = List.of(mock(Booking.class)); // 1 conflicto
        
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(mock(User.class)));
        when(toolRepositoryPort.findById(toolId)).thenReturn(Optional.of(mockTool)); // mockTool tiene stock 1
        when(bookingRepositoryPort.findConflictingBookings(any(), any(), any())).thenReturn(conflictos);

        assertThrows(DomainException.class, () -> 
            bookingService.createBooking(userId, toolId, LocalDateTime.now(), LocalDateTime.now().plusDays(2))
        );
    }
}