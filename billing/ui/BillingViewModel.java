package com.myinappbilling.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myinappbilling.model.ProductDetailsModel;
import com.myinappbilling.repository.ProductRepository;

import java.util.List;

/**
 * BillingViewModel acts as a communication center between the UI and the ProductRepository.
 * It handles loading product details and exposing them via LiveData to the UI.
 */
public class BillingViewModel extends ViewModel {

    private final ProductRepository productRepository;
    private final MutableLiveData<List<ProductDetailsModel>> productList;
    private final MutableLiveData<ProductDetailsModel> selectedProduct;
    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<String> errorMessage;
    private final MutableLiveData<Boolean> isProductSelected;

    public BillingViewModel() {
        productRepository = new ProductRepository();
        productList = new MutableLiveData<>();
        selectedProduct = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
        errorMessage = new MutableLiveData<>(null);
        isProductSelected = new MutableLiveData<>(false);
    }

    public LiveData<List<ProductDetailsModel>> getProductList() {
        return productList;
    }

    public LiveData<ProductDetailsModel> getSelectedProduct() {
        return selectedProduct;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsProductSelected() {
        return isProductSelected;
    }

    /**
     * Loads all products from the repository and updates the LiveData.
     */
    public void loadProducts() {
        isLoading.setValue(true);
        try {
            List<ProductDetailsModel> products = productRepository.getAllProducts();
            productList.setValue(products);
        } catch (Exception e) {
            errorMessage.setValue("Failed to load products: " + e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    /**
     * Sets the selected product, usually when the user taps on a product in the UI.
     *
     * @param product The selected product.
     */
    public void selectProduct(ProductDetailsModel product) {
        selectedProduct.setValue(product);
        isProductSelected.setValue(product != null);
    }

    /**
     * Clears the error message LiveData.
     */
    public void clearError() {
        errorMessage.setValue(null);
    }

    /**
     * Refreshes the product list.
     */
    public void refreshProductList() {
        loadProducts();
    }

    /**
     * Deletes a product by SKU and updates LiveData.
     *
     * @param sku The SKU of the product to delete.
     */
    public void deleteProduct(String sku) {
        if (sku == null || sku.isEmpty()) {
            errorMessage.setValue("Invalid SKU provided for deletion.");
            return;
        }
        boolean deleted = productRepository.deleteProduct(sku);
        if (deleted) {
            loadProducts();
        } else {
            errorMessage.setValue("Product with SKU " + sku + " not found.");
        }
    }

    /**
     * Adds or updates a product and refreshes the product list.
     *
     * @param product The product to add or update.
     */
    public void addOrUpdateProduct(ProductDetailsModel product) {
        try {
            productRepository.addProduct(product);
            loadProducts();
        } catch (IllegalArgumentException e) {
            errorMessage.setValue("Error adding product: " + e.getMessage());
        }
    }
} 
