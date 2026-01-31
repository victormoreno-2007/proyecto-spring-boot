package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity;

public interface JpaBookingRepository extends JpaRepository<BookingEntity, UUID>{
    List<BookingEntity> findByUserId(UUID userId);

    @Query("SELECT b FROM BookingEntity b WHERE b.tool.id = :toolId " +
           "AND b.status <> 'CANCELLED' " +
           "AND b.startDate <= :endDate " +
           "AND b.endDate >= :startDate")
    List<BookingEntity> findConflictingBookings(
        @Param("toolId") UUID toolId, 
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );
}
