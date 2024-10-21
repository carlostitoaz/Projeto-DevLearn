package com.DevLearn.dao;

import com.DevLearn.model.Class;

import java.util.ArrayList;

public interface IClassDAO {
    void addClass(Class vclass);
    ArrayList<Class> listClass();
    Class findClassById(int id);
    void updateClass(Class vclass);
    void deleteClass(int id);
}
