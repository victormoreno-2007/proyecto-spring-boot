package com.construrrenta.api_gateway.domain.model.tool;

import java.math.BigDecimal;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;

public class Tool {
    private  UUID id;  
    private  String name;
    private  String description;
    private  BigDecimal pricePerDay; 
    private  String imageUrl;
    private  ToolStatus status;
    private  UUID providerId;
    private Integer stock;

    public Tool() {}
    
    public static Tool create(String name, String description, BigDecimal pricePerDay, String imageUrl, UUID providerId, Integer stock){
        if (pricePerDay == null || pricePerDay.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("El precio debe ser mayor a 0");
        }   
        if (providerId == null) {
            throw new DomainException("La herramienta debe tener un proveedor asignado");
        }
        if (stock == null || stock < 0) {
            throw new DomainException("El stock no puede ser negativo");
        }
        Tool tool = new Tool();
        tool.id = UUID.randomUUID();
        tool.name = name;
        tool.description = description;
        tool.pricePerDay = pricePerDay;
        tool.imageUrl = imageUrl;
        tool.providerId = providerId;
        tool.stock = stock;
        tool.status = ToolStatus.AVAILABLE;
        return tool;
        
        }
    public static Tool reconstruct(UUID id, String name, String description, BigDecimal pricePerDay, String imageUrl, ToolStatus status, UUID providerId, Integer stock) {
        Tool tool = new Tool();
        tool.id = id;
        tool.name = name;
        tool.description = description;
        tool.pricePerDay = pricePerDay;
        tool.imageUrl = imageUrl;
        tool.status = status;
        tool.providerId = providerId;
        tool.stock = stock;
        return tool;
    }

    public UUID getId() {return id;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public BigDecimal getPricePerDay() {return pricePerDay;}

    public String getImageUrl() {return imageUrl;}

    public ToolStatus getStatus() {return status;}

    public UUID getProviderId() {return providerId;}

    public Integer getStock() { return stock; }

}
