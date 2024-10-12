package org.example.warehouse;

import java.util.*;

public class Category {
    private final String name;
    private static final Map<String, Category> categories = new HashMap<>();

    private Category(String name) {
        this.name = name;
    }
    public static Category of(String name) throws IllegalArgumentException {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        return categories.computeIfAbsent(capitalized, Category::new);
    }
    public String getName() {
        return name;
    }
}
