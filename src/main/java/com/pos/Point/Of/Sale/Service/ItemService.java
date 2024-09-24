package com.pos.Point.Of.Sale.Service;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Item;
import com.pos.Point.Of.Sale.Repository.ItemRepository;
import com.pos.Point.Of.Sale.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository repository;

    /**
     * Adds a new item to the database
     * @param item the item's data
     * @return a custom response depending on the outcome
     */
    @Transactional
    public CustomResponse addItem(Item item) {
        // Check if item is empty
        if (item == null) {
            return new CustomResponse(Status.REJECTED, "The item data can't be empty");
        }
        try {
            // Check if item exists
            if (repository.findByName(item.getName()) != null) {
                return new CustomResponse(Status.REJECTED, "An item with the same name already exists!");
            }
            repository.save(item);
            return new CustomResponse(Status.CREATED, "The item was successfully added");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while adding item: " + e.getMessage());
        }
    }

    /**
     * Retrieves an item's data
     * @param name the name of the item
     * @return a custom response depending on the outcome
     */
    public CustomResponse getItem(String name) {
        // Check if name was found
        if (name == null) {
            return new CustomResponse(Status.REJECTED, "Please enter the item's name!");
        }
        try {
            Item item = repository.findByName(name);
            // Check if item was found
            if (item == null) {
                return new CustomResponse(Status.ABSENT, "The item wasn't found");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "Item successfully found");
            response.setItem(item);
            return response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while fetching item: " + e.getMessage());
        }
    }

    /**
     * Updates an existing item's data
     * @param newItem the new data
     * @return a custom response depending on the outcome
     */
    @Transactional
    public CustomResponse updateItem(Item newItem) {
        // Check if new data is empty
        if (newItem == null) {
            return new CustomResponse(Status.REJECTED, "Can't update to empty data");
        }
        // Attempt updating the item
        try{
            // Fetch the old data
            Item oldItem = repository.findByName(newItem.getName());
            // Check if the item exists
            if (oldItem == null) {
                return new CustomResponse(Status.ABSENT, "The specified item doesn't exist");
            }
            // Update the item
            oldItem.setBuyingPrice(newItem.getBuyingPrice());
            oldItem.setQuantity(newItem.getQuantity());
            oldItem.setSellingPrice(newItem.getSellingPrice());
            repository.save(oldItem);
            return new CustomResponse(Status.SUCCESS, "Item was successfully updated");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while updating the item: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing item from the database
     * @param itemName the item's name
     * @return a custom response depending on the outcome
     */
    @Transactional
    public CustomResponse deleteItem(String itemName) {
        // Check if name was provided
        if (itemName == null || itemName.trim().isEmpty()) {
            return new CustomResponse(Status.REJECTED, "Please enter the item's name");
        }
        try {
            // Fetch the item's data
            Item itemToDelete = repository.findByName(itemName);
            // CHeck if item exists
            if (itemToDelete == null) {
                return new CustomResponse(Status.ABSENT, "The specified item doesn't exist");
            }
            repository.delete(itemToDelete);
            return new CustomResponse(Status.SUCCESS, "Item was successfully deleted");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while deleting the item: " + e.getMessage());
        }
    }
}