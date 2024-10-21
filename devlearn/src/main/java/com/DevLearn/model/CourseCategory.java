package com.DevLearn.model;

public class CourseCategory {
    private int id_courseCategory;
    private int id_course;
    private int id_category;
    private String course_name;
    private String category_name;

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getId_courseCategory() {
        return id_courseCategory;
    }

    public void setId_courseCategory(int id_courseCategory) {
        this.id_courseCategory = id_courseCategory;
    }
}
