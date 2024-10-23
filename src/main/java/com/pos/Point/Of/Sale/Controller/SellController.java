package com.pos.Point.Of.Sale.Controller;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Sell;
import com.pos.Point.Of.Sale.Service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sell")
public class SellController {
    @Autowired private SellService service;

    @GetMapping
    public ResponseEntity<CustomResponse> getAll() {
        CustomResponse response = service.getAll();
        return switch (response.getStatus()) {
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            case ABSENT -> new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @GetMapping("/get")
    public ResponseEntity<CustomResponse> getSell(@RequestParam Long id) {
        CustomResponse response = service.getSell(id);
        return switch (response.getStatus()) {
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PostMapping("/new")
    public ResponseEntity<CustomResponse> addNew(@RequestBody Sell sell) {
        CustomResponse response = service.addSell(sell);
        return switch (response.getStatus()) {
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.CREATED);
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateSell(@RequestParam Long id, @RequestBody Sell newData) {
        CustomResponse response = service.updateSell(id, newData);
        return switch (response.getStatus()) {
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponse> delete(@RequestParam Long id) {
        CustomResponse response = service.deleteSell(id);
        return switch (response.getStatus()) {
            case SUCCESS -> new ResponseEntity<>(response, HttpStatus.OK);
            case ABSENT -> new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            case REJECTED -> new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            default -> new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        };
    }
}
