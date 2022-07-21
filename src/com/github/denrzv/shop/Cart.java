package com.github.denrzv.shop;

public interface Cart {
    void addProduct(Product product, int quantity);
    void removeProduct(Product product, int quantity);
    void submitOrder();
}
