package cl.kibernumacademy.service;

import cl.kibernumacademy.model.Product;

import java.util.*;

public class ProductManager {
    private final Map<Integer, Product> productMap = new HashMap<>();
    private int nextId = 1;

    public Product addProduct(String name, String description, double price) {
        Product p = new Product(nextId++, name, description, price);
        productMap.put(p.getId(), p);
        return p;
    }

    public boolean updateProduct(int id, String newName, String newDescription, double newPrice) {
        Product p = productMap.get(id);
        if (p != null) {
            p.setName(newName);
            p.setDescription(newDescription);
            p.setPrice(newPrice);
            return true;
        }
        return false;
    }

    public boolean deleteProduct(int id) {
        return productMap.remove(id) != null;
    }

    public Optional<Product> getProduct(int id) {
        return Optional.ofNullable(productMap.get(id));
    }

    public List<Product> getAll() {
        return new ArrayList<>(productMap.values());
    }

    public void clearAll() {
        productMap.clear();
    }
}
