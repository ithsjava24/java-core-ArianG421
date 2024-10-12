package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRecord(UUID uuid, String name, Category category, java.math.BigDecimal price) {
    public ProductRecord {
        if (uuid == null) {
            throw new IllegalArgumentException("Product ID can't be null.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        if (price == null) {
            price = BigDecimal.ZERO;
        }
    }
}