package com.sivtcev.expensetracker.controller;

import com.sivtcev.expensetracker.domain.Category;
import com.sivtcev.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String getAllCategories(HttpServletRequest request) {
        long userId = (long) request.getAttribute("userId");
        return "Authenticated. UserId: " + userId;
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request,
                                                    @PathVariable("categoryId") long categoryId) {
        long userId = (long) request.getAttribute("userId");
        Category category = categoryService.fetchCategoryById(userId, categoryId);
        return ResponseEntity.ok(category);
    }

    @PostMapping("")
    public ResponseEntity<Category> addCategory(HttpServletRequest request,
                                                @RequestBody Map<String, Object> categoryMap) {
        long userId = (long) request.getAttribute("userId");
        String title = (String) categoryMap.get("title");
        String description = (String) categoryMap.get("description");
        Category category = categoryService.addCategory(userId, title, description);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
                                                               @PathVariable("categoryId") long categoryId,
                                                               @RequestBody Category category) {
        long user_id = (long) request.getAttribute("userId");
        categoryService.updateCategory(user_id, categoryId, category);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Boolean>> deleteCategory(HttpServletRequest request,
                                                               @PathVariable("categoryId") long categoryId){
        long userId = (long) request.getAttribute("userId");
        categoryService.removeCategoryWithAllTransactions(userId, categoryId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return ResponseEntity.ok(map);
    }
}
