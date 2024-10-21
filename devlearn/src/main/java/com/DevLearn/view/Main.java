package com.DevLearn.view;

import com.DevLearn.controller.UserController;
import com.DevLearn.dao.UserDAO;
import com.DevLearn.model.User;
import com.DevLearn.service.UserService;
import com.DevLearn.util.DataSource;

import java.util.ArrayList;
import java.util.List;

public class Main{
    public static void main(String[] args) {
        // DataSource dataSource = new DataSource();

        UserDAO usuarioDao = new UserDAO();

//        User usuario = new User("Joao de Botas", "joaobotas@gmail.com", "123", 1);

        ArrayList<User> listaUser = usuarioDao.listUser();

        UserService us = new UserService(usuarioDao);
        List<User> listaUser2 = us.listUser();
//
//        System.out.println(listaUser.get(0).getNome());
//        System.out.println(listaUser.get(0));

        System.out.println("Lista de usuarios: " + usuarioDao.listUser());
        System.out.println(listaUser2);
//        System.out.println("Autenticar usuario: " + usuarioDao.autenticarUsuario("joaobotas@gmail.com", "123"));
//        System.out.println("Obter usuario por email: "+usuarioDao.getUsuarioPorEmail("joaobotas@gmaill.com"));

        //System.out.println("Existe usuario: "+usuarioDao.usuarioExiste("joaobotas@gmail.com"));
        //usuarioDao.criarUsuario(usuario);

        // UserController userController = new UserController();
        //userController.testeUser();

        //dataSource.closeDataSource();
    }
}


