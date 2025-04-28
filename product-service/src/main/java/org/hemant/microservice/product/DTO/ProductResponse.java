package org.hemant.microservice.product.DTO;

import java.math.BigDecimal;

public record ProductResponse(String id , String name , String description , BigDecimal price) {

}
