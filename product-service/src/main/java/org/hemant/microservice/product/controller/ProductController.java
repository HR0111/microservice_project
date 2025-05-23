package org.hemant.microservice.product.controller;


import lombok.RequiredArgsConstructor;
import org.hemant.microservice.product.DTO.ProductRequest;
import org.hemant.microservice.product.DTO.ProductResponse;
import org.hemant.microservice.product.model.Product;
import org.hemant.microservice.product.repository.ProductRepository;
import org.hemant.microservice.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
       return productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

}
