package com.DevLearn.dao;

import com.DevLearn.model.User;
import com.DevLearn.util.DataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO implements IUserDAO {
    private DataSource dataSource;

    public UserDAO() {
        this.dataSource = new DataSource();
    }

    @Override
    public void addUser(User user) {
        if (getUsuarioPorEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Usuário já cadastrado");
        }

        try {
            String sql = "INSERT INTO usuario (nome, email, senha, id_tipo_usuario) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId_userType());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao inserir " + e.getMessage());
        }
    }

    @Override
    public ArrayList<User> listUser() {
        ArrayList<User> listUser = new ArrayList<>();
        try {
            String sql = "SELECT * FROM usuario";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId_user(rs.getInt("id_usuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("senha"));
                user.setId_userType(rs.getInt("id_tipo_usuario"));
                user.setRegistrationDate(rs.getDate("data_cadastro"));
                user.setInactive(rs.getBoolean("inativo"));
                listUser.add(user);
            }
            ps.close();
        } catch (SQLException e) {
            System.err.println("Erro ao listar " + e.getMessage());
        }
        return listUser;
    }

    @Override
    public User findUserById(int id) {
        try {
            String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId_user(rs.getInt("id_usuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("senha"));
                user.setId_userType(rs.getInt("id_tipo_usuario"));
                user.setRegistrationDate(rs.getDate("data_cadastro"));
                user.setInactive(rs.getBoolean("inativo"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário " + e.getMessage());
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        try {
            String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ?, id_tipo_usuario = ?, inativo = ? WHERE id_usuario = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId_userType());
            ps.setBoolean(5, user.isInactive());
            ps.setInt(6, user.getId_user());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) {
        try {
            String sql = "DELETE FROM usuario WHERE id_usuario = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar usuário " + e.getMessage());
        }
    }

    public User getUsuarioPorEmail(String email) {
        try {
            String sql = "SELECT * FROM usuario WHERE email = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId_user(rs.getInt("id_usuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("senha"));
                user.setId_userType(rs.getInt("id_tipo_usuario"));
                user.setRegistrationDate(rs.getDate("data_cadastro"));
                user.setInactive(rs.getBoolean("inativo"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuário por email " + e.getMessage());
        }
        return null;
    }

    public User validation(String email, String password){
        try {
            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
            PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId_user(rs.getInt("id_usuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setId_userType(rs.getInt("id_tipo_usuario"));
                user.setRegistrationDate(rs.getDate("data_cadastro"));
                user.setInactive(rs.getBoolean("inativo"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao validar usuário" + e.getMessage());
        }
        return null;
    }
}
