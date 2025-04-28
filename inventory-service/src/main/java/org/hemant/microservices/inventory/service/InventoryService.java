package org.hemant.microservices.inventory.service;

import lombok.RequiredArgsConstructor;
import org.hemant.microservices.inventory.model.Inventory;
import org.hemant.microservices.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode , Integer quantity){
      return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode , quantity);
    }
}
