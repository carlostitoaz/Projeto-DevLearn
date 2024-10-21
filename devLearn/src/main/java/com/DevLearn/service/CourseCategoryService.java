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

    public CourseCategory findCourseCategoryById(int id_course, int id_category){
        return courseCategoryDAO.findCourseCategoryById(id_course, id_category);
    }

    public void deleteCourseCategory(int id_course, int id_category){
        courseCategoryDAO.deleteCourseCategory(id_course, id_category);
    }
}