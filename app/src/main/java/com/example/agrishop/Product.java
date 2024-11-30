package com.example.agrishop;

public class Product {
    private String id;  // Added ID field to uniquely identify each product
    private String name;
    private int price;
    private String description;
    private String imageUrl;

    public Product() {
        // Default constructor required for Firebase
    }

    // Updated constructor to accept id
    public Product(String id, String name, int price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;  // Getter for id
    }

    public void setId(String id) {
        this.id = id;  // Setter for id
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
