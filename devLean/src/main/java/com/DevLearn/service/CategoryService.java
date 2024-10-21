package com.DevLearn.service;

import com.DevLearn.dao.ICategoryDAO;
import com.DevLearn.model.Category;

import java.util.List;

public class CategoryService{
    ICategoryDAO categoryDAO;

    public CategoryService(ICategoryDAO categoryDAO){
        this.categoryDAO = categoryDAO;
    }

    public void addCategory(Category category){
        categoryDAO.addCategory(category);
    }

    public List<Category> listCategory(){
        return categoryDAO.listCategory();
    }

    public Category findCategoryById(int id){
        return categoryDAO.findCategoryById(id);
    }

    public void updateCategory(Category category){
        categoryDAO.updateCategory(category);
    }

    public void deleteCategory(int id){
        categoryDAO.deleteCategory(id);
    }
}