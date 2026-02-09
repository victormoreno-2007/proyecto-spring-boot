package com.construrrenta.api_gateway.domain.model.damage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;

public class DamageReport {
    private UUID id;
    private String description;
    private BigDecimal repairCost;
    private LocalDateTime reportDate;
    private boolean isRepaired;
    private UUID bookingId;

    public DamageReport() {}

    public static DamageReport create(String description, BigDecimal repairCost, UUID bookingId) {
        if (description == null || description.isEmpty()) {
            throw new DomainException("La descripción del daño es obligatoria");
        }
        if (repairCost != null && repairCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("El costo de reparación no puede ser negativo");
        }

        DamageReport report = new DamageReport();
        report.id = UUID.randomUUID();
        report.description = description;
        report.repairCost = repairCost; 
        report.reportDate = LocalDateTime.now();
        report.isRepaired = false;
        report.bookingId = bookingId;
        return report;
    }

    public static DamageReport reconstruct(UUID id, String description, BigDecimal repairCost, 
                                         LocalDateTime reportDate, boolean isRepaired, UUID bookingId) {
        DamageReport report = new DamageReport();
        report.id = id;
        report.description = description;
        report.repairCost = repairCost;
        report.reportDate = reportDate;
        report.isRepaired = isRepaired;
        report.bookingId = bookingId;
        return report;
    }

   
    public UUID getId() { return id; }
    public String getDescription() { return description; }
    public BigDecimal getRepairCost() { return repairCost; }
    public LocalDateTime getReportDate() { return reportDate; }
    public boolean isRepaired() { return isRepaired; }
    public UUID getBookingId() { return bookingId; }

    public void markAsRepaired() {
        this.isRepaired = true;
    }
}
