package com.sivtcev.expensetracker.service;

import com.sivtcev.expensetracker.domain.Category;
import com.sivtcev.expensetracker.exception.EtBadRequestException;
import com.sivtcev.expensetracker.exception.EtResourceNotFoundException;
import com.sivtcev.expensetracker.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories(long userId) {
        return categoryRepository.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(long userId, long categoryId) throws EtResourceNotFoundException {
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public Category addCategory(long userId, String title, String description) throws EtBadRequestException {
        long categoryId = categoryRepository.create(userId, title, description);
        return categoryRepository.findById(userId, categoryId);
    }

    @Override
    public void updateCategory(long userId, long categoryId, Category category) throws EtBadRequestException {
        categoryRepository.update(userId, categoryId, category);
    }

    @Override
    public void removeCategoryWithAllTransactions(long userId, long categoryId) throws EtResourceNotFoundException {
        this.fetchCategoryById(userId, categoryId);
        categoryRepository.removeById(userId, categoryId);
    }
}
