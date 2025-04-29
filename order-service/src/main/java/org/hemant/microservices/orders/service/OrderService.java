package org.hemant.microservices.orders.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hemant.microservices.orders.DTO.OrderRequest;
import org.hemant.microservices.orders.client.InventoryClient;
import org.hemant.microservices.orders.event.OrderPlacedEvent;
import org.hemant.microservices.orders.model.Order;
import org.hemant.microservices.orders.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

      boolean isProductInStock =  inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

      if(isProductInStock){
          Order order = new Order();
          order.setOrderNumber(UUID.randomUUID().toString());
          order.setSkuCode(orderRequest.skuCode());
          order.setPrice(orderRequest.price());
          order.setQuantity(orderRequest.quantity());
          orderRepository.save(order);


          OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(
                  order.getOrderNumber(),
                  orderRequest.userDetails().email()  // Get email from userDetails
          );
        log.info("Start - Kafka message bhej raha hai OrderPlacedEvent: " + orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
          log.info("End - Kafka message Aagaya  hai OrderPlacedEvent: " + orderPlacedEvent);

      }else{
          throw new RuntimeException("Product is not in stock " + orderRequest.skuCode());
      }
    }

}
