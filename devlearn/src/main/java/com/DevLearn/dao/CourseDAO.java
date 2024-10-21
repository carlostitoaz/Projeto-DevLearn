package com.DevLearn.dao;

import com.DevLearn.model.Course;
import com.DevLearn.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO implements ICourseDAO{
    private DataSource dataSource;

    public CourseDAO(){
        this.dataSource = new DataSource();
    }

    @Override
    public void addCourse(Course course){
        if(isCourse(course.getTitle())){
            throw new IllegalArgumentException("Curso já existe");
        }

        try{
            String sql = "INSERT INTO curso (titulo, descricao, id_usuario) VALUES (?,?,?)";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getId_user());
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erro ao inserir curso "+e.getMessage());
        }
    }

    public boolean isCourse(String titulo){
        try {
            String sql = "SELECT * FROM curso WHERE titulo = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.err.println("Erro ao verificar existencia do curso "+e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<Course> listCourse(){
        ArrayList<Course> listCourse = new ArrayList<>();

        try{
            String sql = "SELECT * FROM curso";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                Course course = new Course();
                course.setId_course(rs.getInt("id_curso"));
                course.setTitle(rs.getString("titulo"));
                course.setDescription(rs.getString("descricao"));
                course.setId_user(rs.getInt("id_usuario"));
                course.setCreation_date(rs.getDate("data_criacao"));
                course.setPublished(rs.getBoolean("publicado"));
                course.setInactive(rs.getBoolean("inativo"));
                listCourse.add(course);
            }
            ps.close();
        }catch(SQLException e){
            System.err.println("Erro ao listar cursos "+e.getMessage());
        }
        return listCourse;
    }

    @Override
    public Course findCourseById(int id){
        try{
            String sql = "SELECT * FROM curso WHERE id_curso = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Course course = new Course();
                course.setId_course(rs.getInt("id_curso"));
                course.setTitle(rs.getString("titulo"));
                course.setDescription(rs.getString("descricao"));
                course.setId_user(rs.getInt("id_usuario"));
                course.setCreation_date(rs.getDate("data_criacao"));
                course.setPublished(rs.getBoolean("publicado"));
                course.setInactive(rs.getBoolean("inativo"));
                return course;
            }
        }catch (SQLException e){
            System.err.println("Erro ao burcar curso por id "+e.getMessage());
        }
        return null;
    }

    @Override
    public void updateCourse(Course course){
        try{
            String sql = "UPDATE curso SET titulo = ?, descricao = ?, id_usuario = ?, publicado = ?, inativo = ? WHERE id_curso = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setInt(3, course.getId_user());
            ps.setBoolean(4, course.isPublished());
            ps.setBoolean(5, course.isInactive());
            ps.setInt(6, course.getId_course());
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erro ao atuializar curso "+e.getMessage());
        }
    }

    @Override
    public void deleteCourse(int id){
        try{
            String sql = "DELETE FROM curso WHERE id_curso = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("Erro ao deletar curso "+e.getMessage());
        }
    }
}
