package com.DevLearn.controller;

import com.DevLearn.dao.UserDAO;
import com.DevLearn.model.User;
import com.DevLearn.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class UserValidationController extends HttpServlet {
    private UserService userService = new UserService(new UserDAO());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Email e senha são obrigatórios\"}");
            out.flush();
            return;
        }

        User user = userService.validation(email, password);

        if (user != null) {
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            out.print(userToJson(user));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            out.print("{\"error\": \"Credenciais inválidas\"}");
        }

        out.flush();
    }

    private String userToJson(User user) {
        return "{\"id\": " + user.getId_user() + ", \"name\": \"" + user.getName() + "\", \"email\": \"" + user.getEmail() + "\", \"inactive\": " + user.isInactive() +"}";
    }
}
