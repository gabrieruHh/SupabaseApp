package com.gabriell.supabaseapp.models;

public class ProductModel {

    private String id;
    private String name;
    private double price;
    private String created_at;

    public ProductModel() {
    }

    public ProductModel(String id, String name, double price, String created_at) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return name + " - R$ " + price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
