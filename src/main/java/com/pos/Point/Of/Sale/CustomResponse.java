package com.pos.Point.Of.Sale;

import com.pos.Point.Of.Sale.Entity.Category;
import com.pos.Point.Of.Sale.Entity.Customer;
import com.pos.Point.Of.Sale.Entity.Item;
import com.pos.Point.Of.Sale.Entity.Sell;

import java.util.List;

public class CustomResponse {
    private Status status;
    private String message;
    private Item item;
    private Category category;
    private List<Category> categories;
    private List<Item> items;
    private Customer customer;
    private List<Customer> customerList;
    private Sell sell;
    private List<Sell> sellList;

    public CustomResponse(Status status, String message) {
        this.status = status;
        this.message = message;
        this.item = null;
        this.category = null;
        this.categories = null;
        this.items = null;
        this.customer = null;
        this.customerList = null;
        this.sell = null;
        this.sellList = null;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Sell getSell() {
        return sell;
    }

    public List<Sell> getSellList() {
        return sellList;
    }

    public void setSellList(List<Sell> sellList) {
        this.sellList = sellList;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }
}
