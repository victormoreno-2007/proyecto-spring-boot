package com.construrrenta.api_gateway.infrastructure.adapters.out.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "damage_reports")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DamageReportEntity {
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "repair_cost", precision = 10, scale = 2)
    private BigDecimal repairCost;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "is_repaired", nullable = false)
    private boolean isRepaired;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
