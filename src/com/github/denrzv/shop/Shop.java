package com.github.denrzv.shop;

import java.util.Collection;

public interface Shop {
    void setShopName(String shopName);
    void addProduct(Product product);

    void removeProduct(Product product);
    void updateProduct(Product product);
    Collection<Product> getProducts();
    boolean isShopOpen();
    void runShop();
    void closeShop();
    Cart getCart();
}
