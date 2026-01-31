package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.booking;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity;

@Repository
public interface JpaBookingRepository extends JpaRepository<BookingEntity, UUID> {
}