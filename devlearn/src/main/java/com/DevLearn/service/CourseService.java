package com.DevLearn.service;

import com.DevLearn.dao.CourseDAO;
import com.DevLearn.dao.ICourseDAO;
import com.DevLearn.model.Course;

import java.util.List;

public class CourseService {
    ICourseDAO courseDAO;

    public CourseService(ICourseDAO courseDAO){
        this.courseDAO = courseDAO;
    }

    public void addCourse(Course course){
        courseDAO.addCourse(course);
    }

    public List<Course> listCourse(){
        return courseDAO.listCourse();
    }

    public Course findCourseById(int id){
        return courseDAO.findCourseById(id);
    }

    public void updateCourse(Course course){
        courseDAO.updateCourse(course);
    }

    public void deleteCourse(int id){
        courseDAO.deleteCourse(id);
    }
}
