package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

// En record som representerar en produkt med unika identifierare, namn, kategori och pris
public record ProductRecord(UUID uuid, String name, Category category, java.math.BigDecimal price) {
    // Kompakt Konstruktor för att validera produktens egenskaper
    public ProductRecord {
        // Kontrollera att produktens ID inte är null
        if (uuid == null) {
            throw new IllegalArgumentException("Product ID can't be null.");
        }
        // Kontrollera att produktens namn inte är null eller tomt
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }
        // Kontrollera att kategorin inte är null
        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }
        // Om priset är null, sätt det till noll
        if (price == null) {
            price = BigDecimal.ZERO;
        }
    }
}