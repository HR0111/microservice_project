package org.hemant.microservices.orders.service;

import lombok.RequiredArgsConstructor;
import org.hemant.microservices.orders.DTO.OrderRequest;
import org.hemant.microservices.orders.client.InventoryClient;
import org.hemant.microservices.orders.model.Order;
import org.hemant.microservices.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

      boolean isProductInStock =  inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

      if(isProductInStock){
          Order order = new Order();
          order.setOrderNumber(UUID.randomUUID().toString());
          order.setSkuCode(orderRequest.skuCode());
          order.setPrice(orderRequest.price());
          order.setQuantity(orderRequest.quantity());
          orderRepository.save(order);
      }else{
          throw new RuntimeException("Product is not in stock " + orderRequest.skuCode());
      }
    }

}
