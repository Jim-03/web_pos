package com.pos.Point.Of.Sale.Service;

import com.pos.Point.Of.Sale.CustomResponse;
import com.pos.Point.Of.Sale.Entity.Category;
import com.pos.Point.Of.Sale.Repository.CategoryRepository;
import com.pos.Point.Of.Sale.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired private CategoryRepository repository;

    /**
     * Creates a new category
     * @param category the category's data
     * @return a custom response depending on the outcome
     */
    @Transactional
    public CustomResponse create(Category category) {
        // Check if the data is provided
        if (category == null) {
            return new CustomResponse(Status.REJECTED, "Please provide the category's details");
        }
        try {
            // Check if the category already exists
            if (repository.findByName(category.getName()) != null) {
                return new CustomResponse(Status.REJECTED, "A category with the provided details already exists");
            }
            repository.save(category);
            return new CustomResponse(Status.CREATED, "The category was successfully added");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while adding the category: " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all categories
     * @return a custom response depending on the outcome
     */
    public CustomResponse getAll() {
        try {
            List<Category> categories = repository.findAll();
            // Check if any category exists
            if (categories.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "No category is saved");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "Categories found");
            response.setCategories(categories);
            return response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while fetching the categories: " + e.getMessage());
        }
    }
    /**
     * Retrieves a category's data
     * @param categoryName the category's name
     * @return a custom response depending on the outcome
     */
    public CustomResponse getCategory(String categoryName) {
        // Check if the name is empty
        if (categoryName == null) {
            return new CustomResponse(Status.REJECTED, "Please provide the category's name!");
        }
        try {
            // Fetch the category's data
            Category category = repository.findByName(categoryName);
            // Check if category exists
            if (category == null) {
                return new CustomResponse(Status.ABSENT, "The specified category doesn't exist");
            }
            CustomResponse response = new CustomResponse(Status.SUCCESS, "Category's data found");
            response.setCategory(category);
            return response;
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while getting data: " + e.getMessage());
        }
    }

    /**
     * Updates an existing category's data
     * @param newData the new category's data
     * @return a custom response depending on the outcome
     */
    @Transactional
    public CustomResponse updateCategory(Category newData) {
        // Check if the new data is provided
        if (newData == null) {
            return new CustomResponse(Status.REJECTED, "Can't update to empty data");
        }
        try {
            // Fetch old data
            Optional<Category> oldData = repository.findById(newData.getId());
            // Check if the category exists
            if (oldData.isEmpty()) {
                return new CustomResponse(Status.ABSENT, "The specified category doesn't exist");
            }
            // Update the data
            oldData.get().setName(newData.getName());
            oldData.get().setDescription(newData.getDescription());
            repository.save(oldData.get());
            return new CustomResponse(Status.SUCCESS, "The category was successfully updated");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while updating the category: " + e.getMessage());
        }
    }

    /**
     * Deletes an existing category's data
     * @param categoryName the category's name
     * @return a custom response depending on the outcome
     */
    @Transactional
    public CustomResponse deleteCategory(String categoryName) {
        // Check if the name was provided
        if (categoryName == null || categoryName.trim().isEmpty()) {
            return new CustomResponse(Status.REJECTED, "Please provide the category's name");
        }
        try {
            // Fetch the category
            Category categoryToDelete = repository.findByName(categoryName);
            // Check if category exists
            if (categoryToDelete == null) {
                return new CustomResponse(Status.ABSENT, "The specified category doesn't exist");
            }
            repository.delete(categoryToDelete);
            return new CustomResponse(Status.SUCCESS, "The category was successfully deleted");
        } catch (Exception e) {
            return new CustomResponse(Status.ERROR, "An error occurred while deleting the category: " + e.getMessage());
        }
    }
}
