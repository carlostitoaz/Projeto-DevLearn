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
        if(isClass(newClass.getTitle())){
            throw new IllegalArgumentException("Aula já existe");
        }

        try {
            String sql = "INSERT INTO aula (id_curso, titulo, descricao, url_video) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, newClass.getId_course());
            ps.setString(2, newClass.getTitle());
            ps.setString(3, newClass.getDescription());
            ps.setString(4, newClass.getUrl_video());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir " + e.getMessage());
        }
    }

    public boolean isClass(String title){
        try{
            String sql = "SELECT * FROM aula WHERE titulo = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.err.println("Erro ao verificaar existencia da aula "+e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<Class> listClass() {
        ArrayList<Class> listClass = new ArrayList<>();
        try {
            String sql = "SELECT * FROM aula";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Class newClass = new Class();
                newClass.setId_class(rs.getInt("id_aula"));
                newClass.setId_course(rs.getInt("id_curso"));
                newClass.setTitle(rs.getString("titulo"));
                newClass.setDescription(rs.getString("descricao"));
                newClass.setUrl_video(rs.getString("url_video"));
                newClass.setPublished(rs.getBoolean("publicado"));
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
            String sql = "SELECT * FROM aula WHERE id_aula = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Class newClass = new Class();
                newClass.setId_class(rs.getInt("id_aula"));
                newClass.setId_course(rs.getInt("id_curso"));
                newClass.setTitle(rs.getString("titulo"));
                newClass.setDescription(rs.getString("descricao"));
                newClass.setUrl_video(rs.getString("url_video"));
                newClass.setPublished(rs.getBoolean("publicado"));
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
            String sql = "UPDATE aula SET id_curso = ?, titulo = ?, descricao = ?, url_video = ?, publicado = ? WHERE id_aula = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, newClass.getId_course());
            ps.setString(2, newClass.getTitle());
            ps.setString(3, newClass.getDescription());
            ps.setString(4, newClass.getUrl_video());
            ps.setBoolean(5, newClass.getPublished());
            ps.setInt(6, newClass.getId_class());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar classe " + e.getMessage());
        }
    }

    @Override
    public void deleteClass(int id) {
        try {
            String sql = "DELETE FROM aula WHERE id_aula = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar classe " + e.getMessage());
        }
    }
}
