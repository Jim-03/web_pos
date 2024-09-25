package com.pos.Point.Of.Sale.Controller;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Category;
import com.pos.Point.Of.Sale.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired private CategoryService service;

    @GetMapping
    public ResponseEntity<CustomResponse> findAll() {
        CustomResponse response = service.getAll();
        return switch (response.getStatus()) {
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @GetMapping("/get")
    public ResponseEntity<CustomResponse> findCategory(@RequestParam String name){
        CustomResponse response = service.getCategory(name);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PostMapping("/new")
    public ResponseEntity<CustomResponse> addCategory(@RequestBody Category category) {
        CustomResponse response = service.create(category);
        return switch(response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case CREATED -> new ResponseEntity<>(response, HttpStatus.CREATED);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> update(@RequestBody Category updatedData) {
        CustomResponse response = service.updateCategory(updatedData);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponse> delete(@RequestParam String name) {
        CustomResponse response = service.deleteCategory(name);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> listCategories() {
        CustomResponse response = service.getAll();
        if (response.getCategories() != null) {
            List<String> categories = new ArrayList<>();
            for (Category category : response.getCategories()) {
                categories.add(category.getName());
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}