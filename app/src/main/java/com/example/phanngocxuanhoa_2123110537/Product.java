package com.example.phanngocxuanhoa_2123110537;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private int id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    private double rating;

    // Default constructor
    public Product() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.price = 0.0;
        this.image = "";
        this.category = "";
        this.rating = 0.0;
    }

    // Full constructor
    public Product(int id, String title, String description, double price, String image, String category, double rating) {
        this.id = id;
        this.title = title != null ? title : "";
        this.description = description != null ? description : "";
        this.price = price;
        this.image = image != null ? image : "";
        this.category = category != null ? category : "";
        this.rating = rating;
    }

    // Basic constructor
    public Product(int id, String title, String description, double price) {
        this(id, title, description, price, "", "", 0.0);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }

    public double getRating() {
        return rating;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
    }

    public void setDescription(String description) {
        this.description = description != null ? description : "";
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image != null ? image : "";
    }

    public void setCategory(String category) {
        this.category = category != null ? category : "";
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    // Helper methods
    public String getFormattedPrice() {
        try {
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            return formatter.format(price) + " đ";
        } catch (Exception e) {
            return price + " đ";
        }
    }

    public String getFormattedRating() {
        return String.format(Locale.US, "%.1f", rating);
    }

    public boolean hasImage() {
        return image != null && !image.trim().isEmpty();
    }

    public boolean isValidProduct() {
        return id > 0 && title != null && !title.trim().isEmpty() && price >= 0;
    }

    // toString method
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", category='" + category + '\'' +
                ", rating=" + rating +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}