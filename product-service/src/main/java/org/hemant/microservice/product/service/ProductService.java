package org.hemant.microservice.product.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.microservice.product.DTO.ProductRequest;
import org.hemant.microservice.product.DTO.ProductResponse;
import org.hemant.microservice.product.model.Product;
import org.hemant.microservice.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = Product.builder()
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .build();

        productRepository.save(product);
        log.info("Product Created Successfully");

        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }


    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream().map(product -> new ProductResponse(product.getId() , product.getName(), product.getDescription(), product.getPrice()))
                .toList();
    }



}
