package org.example.warehouse;

import java.util.*;

// Klass som representerar en kategori av produkter
public class Category {
    private final String name; // Namnet på kategorin
    private static final Map<String, Category> categories = new HashMap<>(); // Karta för att lagra unika kategorier

    // Privat konstruktor för att skapa en kategori
    private Category(String name) {
        this.name = name;
    }

    // Statisk metod för att skapa eller hämta en kategori med ett specifikt namn
    public static Category of(String name) throws IllegalArgumentException {
        // Kontrollera att kategori-namnet inte är null eller tomt
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        // Gör första bokstaven stor och resten små
        String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        // Returnera en befintlig kategori eller skapa en ny om den inte finns
        return categories.computeIfAbsent(capitalized, Category::new);
    }

    // Metod för att hämta namnet på kategorin
    public String getName() {
        return name;
    }
}