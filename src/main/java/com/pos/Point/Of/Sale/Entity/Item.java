package com.pos.Point.Of.Sale.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false, unique = true) private String name;
    @Column(name = "stock", nullable = false) private int quantity;
    @Column(name = "buying_price", nullable = false) private double buyingPrice;
    @Column(name = "selling_price", nullable = false) private double sellingPrice;

    public Item() {}

    public Item(String name, int quantity, double buyingPrice, double sellingPrice) {
        this.name = name;
        this.quantity = quantity;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
