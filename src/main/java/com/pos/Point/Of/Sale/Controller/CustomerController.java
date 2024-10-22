package com.pos.Point.Of.Sale.Controller;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Customer;
import com.pos.Point.Of.Sale.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired private CustomerService service;

    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        CustomResponse response = service.getAll();
        return switch (response.getStatus()) {
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PostMapping("/new")
    public ResponseEntity<CustomResponse> addNew(@RequestBody Customer customer) {
        CustomResponse response = service.addCustomer(customer);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.CREATED);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @GetMapping("/get")
    public ResponseEntity<CustomResponse> getCustomer(@RequestParam String phone) {
        CustomResponse response = service.getCustomer(phone);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateCustomer(@RequestBody Customer newCustomerData, @RequestParam String oldPhone) {
        CustomResponse response = service.updateCustomer(newCustomerData, oldPhone);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponse> deleteCustomer(@RequestParam String phone) {
        CustomResponse response = service.deleteCustomer(phone);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
