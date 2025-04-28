package org.hemant.microservices.orders.DTO;


import java.math.BigDecimal;

public record OrderRequest(Long id, String orderNumber , String skuCode ,
                           BigDecimal price , Integer quantity ){}
