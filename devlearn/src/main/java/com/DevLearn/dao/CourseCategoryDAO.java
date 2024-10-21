package com.DevLearn.dao;

import com.DevLearn.model.Category;
import com.DevLearn.model.CourseCategory;
import com.DevLearn.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseCategoryDAO implements ICourseCategoryDAO{
    private DataSource dataSource;

    public CourseCategoryDAO(){
        this.dataSource = new DataSource();
    }

    @Override
    public void addCourseCategory(CourseCategory courseCategory) {
        if(isCourseCategory(courseCategory)){
            throw new IllegalArgumentException("curso_categoria já existe");
        }

        try {
            String sql = "INSERT INTO curso_categoria (id_curso, id_categoria) VALUES (?,?)";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, courseCategory.getId_course());
            ps.setInt(2, courseCategory.getId_category());
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("Erro ao inserir curso_categoria "+e.getMessage());
        }
    }

    public boolean isCourseCategory(CourseCategory courseCategory){
        try{
            String sql = "SELECT * FROM curso_categoria WHERE id_curso = ? AND id_categoria = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, courseCategory.getId_course());
            ps.setInt(2,courseCategory.getId_category());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.err.println("Erro ao verificar existencia do curso_categoria");
        }
        return false;
    }

    @Override
    public ArrayList<CourseCategory> listCourseCategory() {
        ArrayList<CourseCategory> listCourseCategory = new ArrayList<>();

        try{
            String sql = "SELECT cc.*, c.titulo as nome_curso, cat.nome as nome_categoria FROM curso_categoria cc LEFT JOIN curso c on cc.id_curso = c.id_curso LEFT JOIN categoria cat on cc.id_categoria = cat.id_categoria;";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                CourseCategory courseCategory = new CourseCategory();
                courseCategory.setId_course(rs.getInt("id_curso"));
                courseCategory.setId_category(rs.getInt("id_categoria"));
                courseCategory.setCourse_name(rs.getString("nome_curso"));
                courseCategory.setCategory_name(rs.getString("nome_categoria"));
                listCourseCategory.add(courseCategory);
            }
            ps.close();
        }catch (SQLException e){
            System.err.println("Erro ao listar curso_categoria "+e.getMessage());
        }
        return listCourseCategory;
    }

    @Override
    public CourseCategory findCourseCategoryById(int id) {
        try{
            String sql = "SELECT cc.*, c.titulo as nome_curso, cat.nome as nome_categoria FROM curso_categoria cc LEFT JOIN curso c on cc.id_curso = c.id_curso LEFT JOIN categoria cat on cc.id_categoria = cat.id_categoria WHERE id_curso_categoria = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                CourseCategory courseCategory = new CourseCategory();
                courseCategory.setId_course(rs.getInt("id_curso"));
                courseCategory.setCourse_name("nome_curso");
                courseCategory.setId_category(rs.getInt("id_categoria"));
                courseCategory.setCategory_name("nome_categoria");
                return courseCategory;
            }
        }catch (SQLException e){
            System.err.println("Erro ao buscar curso_categoria por id "+e.getMessage());
        }
        return null;
    }

    public void updateCourseCategory(CourseCategory courseCategory){
        try{
            String sql =  "UPDATE curso_categoria SET id_course = ?, id_categoria = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, courseCategory.getId_course());
            ps.setInt(2, courseCategory.getId_category());
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("Erro ao atualizar curso_categoria "+e.getMessage());
        }
    }

    @Override
    public void deleteCourseCategory(int id) {
        try{
            String sql = "DELETE FROM curso_categoria WHERE id_curso_categoria = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("Erro ao deletar curso_categoria "+e.getMessage());
        }
    }
}
