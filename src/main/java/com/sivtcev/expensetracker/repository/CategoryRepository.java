package com.sivtcev.expensetracker.repository;

import com.sivtcev.expensetracker.domain.Category;
import com.sivtcev.expensetracker.exception.EtBadRequestException;
import com.sivtcev.expensetracker.exception.EtResourceNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(long userId) throws EtResourceNotFoundException;

    Category findById(long userId, long categoryId) throws EtResourceNotFoundException;

    long create(long userId, String title, String description) throws EtBadRequestException;

    void update(long userId, long categoryId, Category category) throws EtBadRequestException;

    void removeById(long userId, long categoryId);
}
