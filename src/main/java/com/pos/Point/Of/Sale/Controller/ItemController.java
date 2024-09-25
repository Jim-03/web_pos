package com.pos.Point.Of.Sale.Controller;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Category;
import com.pos.Point.Of.Sale.Entity.Item;
import com.pos.Point.Of.Sale.Service.CategoryService;
import com.pos.Point.Of.Sale.Service.ItemService;
import com.pos.Point.Of.Sale.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired private ItemService service;
    @Autowired private CategoryService categoryService;

    @PostMapping("/submit")
    public ResponseEntity<CustomResponse> add(@RequestBody Item item, @RequestParam String categoryName) {
        CustomResponse category = categoryService.getCategory(categoryName);
        if (category.getCategory() == null) {
            return new ResponseEntity<>(category, HttpStatus.NOT_FOUND);
        }
        item.setCategory(category.getCategory());
        CustomResponse response = service.addItem(item);
        return switch (response.getStatus()) {
            case CREATED -> new ResponseEntity<>(response, HttpStatus.CREATED);
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @GetMapping("/get")
    public ResponseEntity<CustomResponse> getItem(@RequestParam String itemName) {
        CustomResponse response = service.getItem(itemName);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateItem(@RequestBody Item item) {
        CustomResponse response = service.updateItem(item);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponse> deleteItem(@RequestParam String itemName) {
        CustomResponse response = service.deleteItem(itemName);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
