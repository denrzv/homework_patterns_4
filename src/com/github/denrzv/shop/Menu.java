package com.github.denrzv.shop;


public interface Menu {
    void addItem(String label, Menu nextMenu);
    void removeItem(String label);
}
