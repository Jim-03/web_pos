package com.pos.Point.Of.Sale;

import com.pos.Point.Of.Sale.Entity.Category;
import com.pos.Point.Of.Sale.Entity.Item;

import java.util.List;

public class CustomResponse {
    private Status status;
    private String message;
    private Item item;
    private Category category;
    private List<Category> categories;

    public CustomResponse(Status status, String message) {
        this.status = status;
        this.message = message;
        this.item = null;
        this.category = null;
        this.categories = null;
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
}
