package com.DevLearn.dao;

import com.DevLearn.model.Category;

import java.util.ArrayList;

public interface ICategoryDAO {
    void addCategory(Category category);
    ArrayList<Category> listCategory();
    Category findCategoryById(int id);
    void updateCategory(Category category);
    void deleteCategory(int id);
}
