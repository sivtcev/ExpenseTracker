package com.sivtcev.expensetracker.service;

import com.sivtcev.expensetracker.domain.Category;
import com.sivtcev.expensetracker.exception.EtBadRequestException;
import com.sivtcev.expensetracker.exception.EtResourceNotFoundException;

import java.util.List;

public interface CategoryService {

    List<Category> fetchAllCategories(long userId);

    Category fetchCategoryById(long userId, long categoryId) throws EtResourceNotFoundException;

    Category addCategory(long userId, String title, String description) throws EtBadRequestException;

    void updateCategory(long userId, long categoryId, Category category) throws EtBadRequestException;

    void removeCategoryWithAllTransactions(long userId, long categoryId) throws EtResourceNotFoundException;

}
