package com.gabriell.supabaseapp.models;

public class ProductModel {

    private String id;
    private String nome;
    private double preco;
    private String created_at;

    public ProductModel() {
    }

    public ProductModel(String id, String nome, double preco, String created_at) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return nome;
    }

    public void setName(String name) {
        this.nome = name;
    }

    public double getPrice() {
        return preco;
    }

    public void setPrice(double price) {
        this.preco = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
