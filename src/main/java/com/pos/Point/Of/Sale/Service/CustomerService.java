package com.pos.Point.Of.Sale.Service;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Customer;
import com.pos.Point.Of.Sale.Repository.CustomerRepository;
import com.pos.Point.Of.Sale.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    private Boolean isPresent(String phone) {
        return repository.existsByPhone(phone);
    }

    @Transactional
    public CustomResponse addCustomer(Customer customer) {
        // Check if the customer's data is empty
        if (customer == null) {
            return new CustomResponse(Status.REJECTED, "Please enter the customer's details");
        }

        try {
            if (isPresent(customer.getPhone()) == null) {
                return new CustomResponse(Status.ERROR, "An error occurred while checking for customer");
            } else if (isPresent(customer.getPhone())) {
                return updateCustomer(customer, customer.getPhone());
            }
            repository.save(customer);
            return new CustomResponse(Status.SUCCESS, "The customer was successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return new CustomResponse(Status.ERROR, "An error occurred while adding patient: " + e.getMessage());
        }
    }

    @Transactional
    public CustomResponse updateCustomer(Customer customer, String oldPhone) {
        // Check if customer's data is empty
        if (customer == null) {
            return new CustomResponse(Status.REJECTED, "Please enter valid customer details");
        } else if (oldPhone == null || oldPhone.trim().isEmpty()) {
            return new CustomResponse(Status.ERROR, "The customer's previous phone number is required to update");
        }
        try {
            // Fetch customer's details
            Customer oldCustomerDetails = repository.findByPhone(oldPhone);
            // Check if the customer exists
            if (oldCustomerDetails == null) {
                return new CustomResponse(Status.ABSENT, "The specified customer doesn't exist");
            }
            oldCustomerDetails.setName(customer.getName());
            oldCustomerDetails.setPhone(customer.getPhone());
            oldCustomerDetails.setDebtToReturn(customer.getDebtToReturn());
            oldCustomerDetails.setUnpaidDebt(customer.getUnpaidDebt());
            repository.save(oldCustomerDetails);
            return new CustomResponse(Status.SUCCESS, "The customer's details successfully updated");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while updating customer's details: " + e.getMessage());
        }
    }

    public CustomResponse getCustomer(String phone) {
        // Check if the phone number is provided
        if (phone == null || phone.trim().isEmpty()) {
            return new CustomResponse(Status.REJECTED, "The phone number wasn't provided");
        }
        try {
            Customer customer = repository.findByPhone(phone);
            // Check if customer exists
            if (customer == null) {
                return new CustomResponse(Status.ABSENT, "The specified customer wasn't found");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "Customer's data found");
            response.setCustomer(customer);
            return response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while fetching customer's data: " + e.getMessage());
        }
    }

    public CustomResponse getAll() {
        try {
            List<Customer> customers = repository.findAll();
            // Check if list is empty
            if (customers.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "No customers are saved at the moment");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "Customers' data found");
            response.setCustomerList(customers);
            return response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while fetching customers data: " + e.getMessage());
        }
    }

    @Transactional
    public CustomResponse deleteCustomer(String phone) {
        // Check if phone number was provided
        if (phone == null || phone.trim().isEmpty()) {
            return new CustomResponse(Status.REJECTED, "Please enter the customer's phone number");
        }
        try {
            Customer customer = repository.findByPhone(phone);
            // Check if customer exists
            if (customer == null) {
                return new CustomResponse(Status.ABSENT, "The specified customer doesn't exist");
            }
            repository.delete(customer);
            return new CustomResponse(Status.SUCCESS, "The customer's data was successfully deleted");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while deleting customer's data: " + e.getMessage());
        }
    }
}
