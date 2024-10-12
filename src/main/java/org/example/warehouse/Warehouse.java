package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    // En karta för att lagra unika instanser av Warehouse med namn som nycklar
    private static final Map<String, Warehouse> instances = new HashMap<>();
    private final String name;
    private final Map<UUID, ProductRecord> products;
    private final List<ProductRecord> changedProducts;

    // Standardkonstruktor för att skapa ett lager namngett "Warehouse"
    private Warehouse() {
        this.name = "Warehouse";
        this.products = new LinkedHashMap<>();
        this.changedProducts = new LinkedList<>();
    }

    // Överlagrad metod för att skapa ett lagret med ett specifikt namn
    private Warehouse(String name) {
        this.name = name;
        this.products = new LinkedHashMap<>();
        this.changedProducts = new LinkedList<>();
    }

    // Hämta instansen av standard Warehouse
    public static Warehouse getInstance() {
        return new Warehouse();
    }

    // Hämta instansen av ett Warehouse med ett specifikt namn
    public static Warehouse getInstance(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Warehouse name can't be null or empty");
        }
        return instances.computeIfAbsent(name, Warehouse::new);
    }

    // Hämta Warehouse namn
    public String getName() {
        return name;
    }

    // Kontrollera om Warehouse är tomt
    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Returnera en oföränderlig lista av produkter
    public List<ProductRecord> getProducts() {
        return List.copyOf(products.values());
    }

    // Lägga till en produkt i Warehouse
    public ProductRecord addProduct(UUID uuid, String name, Category category, BigDecimal price) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }

        // Kontrollera om produktens UUID redan finns
        if (products.containsKey(uuid)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        // Generera ett nytt UUID om uuid är null
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        // Skapa en ny produkt och lägg till den i Warehouse
        ProductRecord newProduct = new ProductRecord(uuid, name, category, price);
        products.put(uuid, newProduct);
        return newProduct;
    }

    // Hämta en produkt baserat på dess ID
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return Optional.ofNullable(products.get(uuid));
    }

    // Uppdatera priset på en befintlig produkt
    public void updateProductPrice(UUID uuid, BigDecimal newPrice) {
        // Hämta den befintliga produkten
        ProductRecord existingProduct = products.get(uuid);

        // Kontrollera om produkten existerar
        if (existingProduct == null) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        // Skapa en ny produkt med det uppdaterade priset och lägg till den i listan över ändrade produkter
        ProductRecord updatedProduct = new ProductRecord(existingProduct.uuid(), existingProduct.name(), existingProduct.category(), newPrice);
        changedProducts.add(existingProduct);
        products.put(uuid, updatedProduct);
    }

    // Gruppera produkter efter kategori
    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return Collections.unmodifiableMap(
                products.values().stream()
                        .collect(Collectors.groupingBy(ProductRecord::category)));
    }

    // Hämta listan över ändrade produkter
    public List<ProductRecord> getChangedProducts() {
        return Collections.unmodifiableList(changedProducts);
    }

    // Hämta produkter baserat på deras kategori
    public List<ProductRecord> getProductsBy(Category category) {
        return Collections.unmodifiableList(
                products.values().stream()
                        .filter(product -> product.category().equals(category))
                        .collect(Collectors.toList()));
    }
}
