package com.DevLearn.dao;

import com.DevLearn.model.Category;
import com.DevLearn.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO implements ICategoryDAO{
    private DataSource dataSource;

    public CategoryDAO() {
        this.dataSource = new DataSource();
    }

    @Override
    public void addCategory(Category category) {
        if(isCategory(category.getName())){
            throw new IllegalArgumentException("Categoria já existe");
        }

        try{
            String sql = "INSERT INTO categoria (nome) VALUES (?)";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erro ao inserir categoria"+e.getMessage());
        }
    }

    public boolean isCategory(String name){
        try{
            String sql = "SELECT * FROM categoria WHERE nome = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }catch (SQLException e){
            System.err.println("Erro ao verificar existencia da categoria "+e.getMessage());
        }
        return false;
    }

    @Override
    public ArrayList<Category> listCategory() {
        ArrayList<Category> listCatagory = new ArrayList<>();

        try{
            String sql = "SELECT * FROM categoria";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Category category = new Category();
                category.setId_category(rs.getInt("id_categoria"));
                category.setName(rs.getString("nome"));
                listCatagory.add(category);
            }
            ps.close();
        }catch (SQLException e){
            System.err.println("Erro ao listar categorias "+e.getMessage());
        }
        return listCatagory;
    }

    @Override
    public Category findCategoryById(int id) {
        try{
            String sql = "SELECT * FROM categoria WHERE id_categoria = ?";
            PreparedStatement ps =  dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Category category = new Category();
                category.setId_category(rs.getInt("id_categoria"));
                category.setName(rs.getString("nome"));
                return category;
            }
        }catch (SQLException e){
            System.err.println("Erro ao buscar categoria por id "+e.getMessage());
        }
        return null;
    }

    @Override
    public void updateCategory(Category category) {
        try{
            String sql = "UPDATE categoria SET nome = ? WHERE id_categoria = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId_category());
            ps.executeUpdate();
        }catch (SQLException e){
            System.err.println("Erro ao atualizar categoria "+e.getMessage());
        }
    }

    @Override
    public void deleteCategory(int id) {
        try{
            String sql = "DELETE FROM categoria WHERE id_categoria = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erro ao deletar categoria "+e.getMessage());
        }
    }
}
