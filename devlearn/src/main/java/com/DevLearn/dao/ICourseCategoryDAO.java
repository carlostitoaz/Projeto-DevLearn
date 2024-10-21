package com.DevLearn.dao;

import com.DevLearn.model.Category;
import com.DevLearn.model.Course;
import com.DevLearn.model.CourseCategory;

import java.util.ArrayList;

public interface ICourseCategoryDAO {
    void addCourseCategory(CourseCategory courseCategory);
    ArrayList<CourseCategory> listCourseCategory();
    CourseCategory findCourseCategoryById(int id);
    void updateCourseCategory(CourseCategory courseCategory);
    void deleteCourseCategory(int id);
}
