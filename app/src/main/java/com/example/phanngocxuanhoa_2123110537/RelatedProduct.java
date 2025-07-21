package com.example.phanngocxuanhoa_2123110537;

import java.io.Serializable;

public class RelatedProduct implements Serializable {
    private String name;
    private String price;
    private int imageResId;

    public RelatedProduct(String name, String price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
