package com.example.phanngocxuanhoa_2123110537;

import java.text.NumberFormat;
import java.util.Locale;

public class CartItem {
    private String name;
    private String size;
    private String price;  // Thay đổi từ int sang String để tương thích
    private int quantity;
    private int imageResId;
    private String productId;  // Thêm productId để quản lý

    // Constructor chính (tương thích với CartManager)
    public CartItem(String name, String size, int quantity, String price, int imageResId) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.imageResId = imageResId;
        this.productId = name + "_" + size;
    }

    // Constructor cũ để tương thích
    public CartItem(String name, String size, int price, int quantity, int imageResId) {
        this.name = name;
        this.size = size;
        this.quantity = quantity;
        this.price = formatPrice(price);
        this.imageResId = imageResId;
        this.productId = name + "_" + size;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getProductName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getProductId() {
        return productId;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setSize(String size) {
        this.size = size;
        this.productId = name + "_" + size; // Cập nhật productId
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = formatPrice(price);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // Chuyển đổi price từ String sang int
    public int getPriceAsInt() {
        try {
            // Loại bỏ định dạng tiền tệ và chuyển sang số
            String cleanPrice = price.replaceAll("[^0-9]", "");
            return Integer.parseInt(cleanPrice);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Tính tổng tiền (trả về int)
    public int getTotalPrice() {
        return getPriceAsInt() * quantity;
    }

    // Tính tổng tiền (trả về double để tương thích với CartManager)
    public double getTotalPriceAsDouble() {
        return (double) getTotalPrice();
    }

    // Format tổng tiền thành string
    public String getFormattedTotalPrice() {
        int totalPrice = getTotalPrice();
        return formatPrice(totalPrice);
    }

    // Format giá từ int sang string với định dạng tiền tệ VN
    private String formatPrice(int price) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(price) + "đ";
    }

    // Format giá từ double sang string
    public static String formatPrice(double price) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format((int) price) + "đ";
    }

    // Override equals và hashCode để so sánh CartItem
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        CartItem cartItem = (CartItem) obj;
        return productId != null ? productId.equals(cartItem.productId) : cartItem.productId == null;
    }

    @Override
    public int hashCode() {
        return productId != null ? productId.hashCode() : 0;
    }

    // Override toString để debug
    @Override
    public String toString() {
        return "CartItem{" +
                "name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", productId='" + productId + '\'' +
                '}';
    }
}