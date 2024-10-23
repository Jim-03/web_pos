package com.pos.Point.Of.Sale.Service;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Customer;
import com.pos.Point.Of.Sale.Entity.Sell;
import com.pos.Point.Of.Sale.Repository.SellRepository;
import com.pos.Point.Of.Sale.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellService {
    @Autowired private SellRepository repository;
    @Autowired private CustomerService service = new CustomerService();

    /**
     * Adds a new sell record to the database
     * @param newSell the sell data
     * @return a custom response
     */
    @Transactional
    public CustomResponse addSell(Sell newSell) {
        // Check if the sell data is provided
        if (newSell == null) {
            return new CustomResponse(Status.REJECTED, "The new sell is empty");
        }
        try {
            // Check if the customer provided already exists
            CustomResponse response = service.getCustomer(newSell.getCustomer().getPhone());
            if (response.getCustomer() != null) {
                newSell.setCustomer(response.getCustomer());
            } else {
                service.addCustomer(newSell.getCustomer());
            }
            repository.save(newSell);
            return new CustomResponse(Status.CREATED, "The sell has successfully been made");
        } catch (Exception e) {
            e.printStackTrace();
            return new CustomResponse(Status.ERROR, "An error occurred while saving the sell: " + e.getMessage());
        }
    }

    public CustomResponse getSell(Long id) {
        // Check if reference number was provided
        if (id == null || id == 0) {
            return new CustomResponse(Status.REJECTED, "Please provide the reference number!");
        }
        try {
            Optional<Sell> sell = repository.findById(id);
            // Check if sell exists
            if (sell.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "The sell with the provided id doesn't exist");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "The sell's data was found");
            response.setSell(sell.get());
            return response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while fetching sell data: " + e.getMessage());
        }
    }

    /**
     * updates an existing sell's data
     * @param id the id of the sell
     * @param newSellData the new data
     * @return a custom response
     */
    @Transactional
    public CustomResponse updateSell (Long id, Sell newSellData) {
        // Check if id is provided
        if (id == null || id == 0) {
            return new CustomResponse(Status.REJECTED, "Please provide the sell's id for identification");
        } else if (newSellData == null) {
            return new CustomResponse(Status.REJECTED, "Please provide the new data");
        }
        try {
            // Fetch previous info
            Optional<Sell> oldSellData = repository.findById(id);
            // Check if the data exists
            if (oldSellData.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "No sell has been found with the provided reference");
            }
            // Update the data
            oldSellData.get().setCustomer(newSellData.getCustomer());
            oldSellData.get().setChange(newSellData.getChange());
            oldSellData.get().setItems(newSellData.getItems());
            oldSellData.get().setAmountPaid(newSellData.getAmountPaid());
            oldSellData.get().setTotalAmount(newSellData.getTotalAmount());
            oldSellData.get().setUnpaid(newSellData.getUnpaid());
            oldSellData.get().setDateOfSale(newSellData.getDateOfSale());
            repository.save(oldSellData.get());
            return new CustomResponse(Status.SUCCESS, "The data was successfully updated");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while updating the sell data: " + e.getMessage());
        }
    }

    /**
     *Deletes an existing sell from the database
     * @param id the sell's unique identifier
     * @return a custom response
     */
    @Transactional
    public CustomResponse deleteSell(Long id) {
        // Check if the id was provided
        if (id == null || id == 0) {
            return new CustomResponse(Status.REJECTED, "Please provide the id of the sell to delete");
        }
        try {
            // Check if the sell exists
            Optional<Sell> sell = repository.findById(id);
            if (sell.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "No sell with the provided id has been found");
            }
            // Delete the sell
            repository.delete(sell.get());
            return new CustomResponse(Status.SUCCESS, "The sell was successfully deleted");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while deleting the sell: "+ e.getMessage());
        }
    }

    /**
     * Retrieves a list of all sells made
     * @return a custom response
     */
    public CustomResponse getAll() {
        try {
            List<Sell> sellList = repository.findAll();
            // Check if list is empty
            if (sellList.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "There are no sells present");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "Sells list found");
            response.setSellList(sellList);
            return  response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while fetching the list: " + e.getMessage());
        }
    }
}
