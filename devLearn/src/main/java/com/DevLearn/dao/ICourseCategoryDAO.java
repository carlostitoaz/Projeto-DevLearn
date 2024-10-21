package com.DevLearn.dao;

import com.DevLearn.model.Category;
import com.DevLearn.model.Course;
import com.DevLearn.model.CourseCategory;

import java.util.ArrayList;

public interface ICourseCategoryDAO {
    void addCourseCategory(CourseCategory courseCategory);
    ArrayList<CourseCategory> listCourseCategory();
    CourseCategory findCourseCategoryById(int id_course, int id_category);
    void deleteCourseCategory(int id_course, int id_category);
}
