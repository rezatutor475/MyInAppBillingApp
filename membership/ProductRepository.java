package com.myinappbilling.repository;

import com.myinappbilling.model.ProductDetailsModel;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class ProductRepository {

    private final Map<String, ProductDetailsModel> productDatabase;

    public ProductRepository() {
        this.productDatabase = new HashMap<>();
    }

    // Adds a new product to the repository or updates the existing one
    public void addProduct(ProductDetailsModel product) {
        if (product == null || product.getSku() == null || product.getSku().isEmpty()) {
            throw new IllegalArgumentException("Product or SKU cannot be null or empty.");
        }
        productDatabase.put(product.getSku(), product);
    }

    // Retrieves a product by its SKU
    public Optional<ProductDetailsModel> getProductBySku(String sku) {
        if (sku == null || sku.isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be null or empty.");
        }
        return Optional.ofNullable(productDatabase.get(sku));
    }

    // Retrieves all products in the repository
    public List<ProductDetailsModel> getAllProducts() {
        return new ArrayList<>(productDatabase.values());
    }

    // Updates an existing product in the repository
    public void updateProduct(ProductDetailsModel product) {
        if (product == null || product.getSku() == null || product.getSku().isEmpty()) {
            throw new IllegalArgumentException("Product or SKU cannot be null or empty.");
        }

        if (!productDatabase.containsKey(product.getSku())) {
            throw new IllegalArgumentException("Product with SKU " + product.getSku() + " does not exist.");
        }

        productDatabase.put(product.getSku(), product);
    }

    // Deletes a product by its SKU
    public boolean deleteProduct(String sku) {
        if (sku == null || sku.isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be null or empty.");
        }

        return productDatabase.remove(sku) != null;
    }

    // Checks if a product exists in the repository
    public boolean productExists(String sku) {
        return sku != null && productDatabase.containsKey(sku);
    }

    // Retrieves products by their subscription type (subscription or non-subscription)
    public List<ProductDetailsModel> getProductsBySubscriptionType(boolean isSubscription) {
        List<ProductDetailsModel> result = new ArrayList<>();
        for (ProductDetailsModel product : productDatabase.values()) {
            if (product.isSubscription() == isSubscription) {
                result.add(product);
            }
        }
        return result;
    }

    // Clears all products in the repository
    public void clearAllProducts() {
        productDatabase.clear();
    }

    // Retrieves products that fall within a specific price range
    public List<ProductDetailsModel> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<ProductDetailsModel> result = new ArrayList<>();
        for (ProductDetailsModel product : productDatabase.values()) {
            try {
                double productPrice = Double.parseDouble(product.getPrice().replace("$", ""));
                if (productPrice >= minPrice && productPrice <= maxPrice) {
                    result.add(product);
                }
            } catch (NumberFormatException e) {
                // Handle invalid price format in the product data
                System.err.println("Invalid price format for SKU " + product.getSku());
            }
        }
        return result;
    }

    // Retrieves products that contain a specific feature in their features string
    public List<ProductDetailsModel> getProductsByFeature(String featureKeyword) {
        List<ProductDetailsModel> result = new ArrayList<>();
        for (ProductDetailsModel product : productDatabase.values()) {
            if (product.getPlanFeatures() != null && product.getPlanFeatures().toLowerCase().contains(featureKeyword.toLowerCase())) {
                result.add(product);
            }
        }
        return result;
    }

    // Returns the total number of products in the repository
    public int getProductCount() {
        return productDatabase.size();
    }

    // Updates the price of a product by its SKU
    public void updateProductPrice(String sku, String newPrice) {
        if (sku == null || sku.isEmpty() || newPrice == null || newPrice.isEmpty()) {
            throw new IllegalArgumentException("SKU and new price cannot be null or empty.");
        }

        ProductDetailsModel product = productDatabase.get(sku);
        if (product == null) {
            throw new IllegalArgumentException("Product with SKU " + sku + " does not exist.");
        }

        product.setPrice(newPrice);
        productDatabase.put(sku, product);
    }

    // Adds multiple products at once
    public void bulkAddProducts(List<ProductDetailsModel> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list cannot be null or empty.");
        }

        for (ProductDetailsModel product : products) {
            addProduct(product);
        }
    }

    // Marks a product as disabled (inactive) without deleting it
    public void disableProduct(String sku) {
        if (sku == null || sku.isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be null or empty.");
        }

        ProductDetailsModel product = productDatabase.get(sku);
        if (product == null) {
            throw new IllegalArgumentException("Product with SKU " + sku + " does not exist.");
        }

        product.setActive(false); // Mark as inactive
        productDatabase.put(sku, product);
    }
}
