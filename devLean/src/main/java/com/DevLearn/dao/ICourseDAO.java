package com.DevLearn.dao;

import com.DevLearn.model.Course;

import java.util.ArrayList;

public interface ICourseDAO {
    void addCourse(Course course);
    ArrayList<Course> listCourse();
    Course findCourseById(int id);
    void updateCourse(Course course);
    void deleteCourse(int id);
}
