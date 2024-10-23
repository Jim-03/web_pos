package com.pos.Point.Of.Sale.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "sells")
public class Sell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_of_sell", nullable = false) private LocalDate dateOfSale;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", nullable = false) private Customer customer;
    @ManyToMany
    @JoinTable(
            name = "sell_items",
            joinColumns = @JoinColumn(name = "sell_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    ) private List<Item> items;
    @Column(name = "total_amount") private Double totalAmount;
    @Column(name = "amount_paid") private Double amountPaid;
    @Column(name = "change_due") private Double change;
    @Column(name = "unpaid_debt") private Double unpaid;

    public Sell() {}

    public Sell(LocalDate dateOfSale, Customer customer, List<Item> items, Double totalAmount, Double amountPaid, Double change, Double unpaid) {
        this.dateOfSale = dateOfSale;
        this.customer = customer;
        this.items = items;
        this.totalAmount = totalAmount;
        this.amountPaid = amountPaid;
        this.change = change;
        this.unpaid = unpaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDate dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Double getUnpaid() {
        return unpaid;
    }

    public void setUnpaid(Double unpaid) {
        this.unpaid = unpaid;
    }
}
