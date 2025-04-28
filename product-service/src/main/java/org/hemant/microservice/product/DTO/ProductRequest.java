package org.hemant.microservice.product.DTO;


import java.math.BigDecimal;

public record ProductRequest(String id, String name, BigDecimal price, String description) {}
