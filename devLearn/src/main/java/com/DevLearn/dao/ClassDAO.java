package com.DevLearn.dao;

import com.DevLearn.model.Class;
import com.DevLearn.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClassDAO implements IClassDAO {
    private DataSource dataSource;

    public ClassDAO() {
        this.dataSource = new DataSource();
    }

    @Override
    public void addClass(Class newClass) {
        try {
            String sql = "INSERT INTO class (name, duration, id_course, url) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, newClass.getName());
            ps.setInt(2, newClass.getDuration());
            ps.setInt(3, newClass.getId_course());
            ps.setString(4, newClass.getUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Class> listClass() {
        ArrayList<Class> listClass = new ArrayList<>();
        try {
            String sql = "SELECT * FROM class";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Class newClass = new Class();
                newClass.setId_class(rs.getInt("id_class"));
                newClass.setName(rs.getString("name"));
                newClass.setDuration(rs.getInt("duration"));
                newClass.setId_course(rs.getInt("id_course"));
                newClass.setUrl(rs.getString("url"));
                listClass.add(newClass);
            }
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erro ao listar " + e.getMessage());
        }
        return listClass;
    }

    @Override
    public Class findClassById(int id) {
        try {
            String sql = "SELECT * FROM class WHERE id_class = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Class newClass = new Class();
                newClass.setId_class(rs.getInt("id_class"));
                newClass.setName(rs.getString("name"));
                newClass.setDuration(rs.getInt("duration"));
                newClass.setId_course(rs.getInt("id_course"));
                newClass.setUrl(rs.getString("url"));
                return newClass;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar classe " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateClass(Class newClass) {
        try {
            String sql = "UPDATE class SET name = ?, duration = ?, id_course = ?, url = ? WHERE id_class = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, newClass.getName());
            ps.setInt(2, newClass.getDuration());
            ps.setInt(3, newClass.getId_course());
            ps.setString(4, newClass.getUrl());
            ps.setInt(5, newClass.getId_class());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar classe " + e.getMessage());
        }
    }

    @Override
    public void deleteClass(int id) {
        try {
            String sql = "DELETE FROM class WHERE id_class = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar classe " + e.getMessage());
        }
    }
}
