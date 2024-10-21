package com.DevLearn.service;

import com.DevLearn.dao.ICourseCategoryDAO;
import com.DevLearn.model.Course;
import com.DevLearn.model.CourseCategory;

import java.util.List;

public class CourseCategoryService {
    ICourseCategoryDAO courseCategoryDAO;

    public CourseCategoryService(ICourseCategoryDAO courseCategoryDAO){
        this.courseCategoryDAO = courseCategoryDAO;
    }

    public void addCourseCategory(CourseCategory courseCategory){
        courseCategoryDAO.addCourseCategory(courseCategory);
    }

    public List<CourseCategory> listCourseCategory(){
        return courseCategoryDAO.listCourseCategory();
    }

    public void updateCourseCategory(CourseCategory courseCategory){
        courseCategoryDAO.updateCourseCategory(courseCategory);
    }

    public CourseCategory findCourseCategoryById(int id){
        return courseCategoryDAO.findCourseCategoryById(id);
    }

    public void deleteCourseCategory(int id){
        courseCategoryDAO.deleteCourseCategory(id);
    }
}