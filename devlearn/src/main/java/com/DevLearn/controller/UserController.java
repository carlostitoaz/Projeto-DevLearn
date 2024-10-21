package com.DevLearn.controller;

import com.DevLearn.dao.UserDAO;
import com.DevLearn.service.UserService;
import com.DevLearn.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/users")
public class UserController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService(new UserDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String userIdParam = request.getParameter("id");
        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            User user = userService.findUserById(userId);
            if (user != null) {
                out.print(userToJson(user));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Usuário não encontrado\"}");
            }
        } else {
            List<User> users = userService.listUser();
            out.print(usersToJson(users));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String idUserTypeParam = request.getParameter("type");

        if (name == null || email == null || password == null || idUserTypeParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Todos os campos (name, email, password, type) são obrigatórios\"}");
            out.flush();
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setId_userType(Integer.parseInt(idUserTypeParam));

        try {
            userService.addUser(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Usuário criado com sucesso\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Erro ao criar usuário: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String userIdParam = request.getParameter("id");
            if (userIdParam == null || userIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Parâmetro 'id' é obrigatório\"}");
                out.flush();
                return;
            }

            int userId = Integer.parseInt(userIdParam);
            User user = userService.findUserById(userId);

            if (user == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                out.print("{\"error\": \"Usuário não encontrado\"}");
                out.flush();
                return;
            }

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String idUserTypeParam = request.getParameter("type");
            String inactiveParam = request.getParameter("inactive");

            if (name == null || email == null || password == null || idUserTypeParam == null || inactiveParam == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Todos os campos (name, email, password, type, inactive) são obrigatórios\"}");
                out.flush();
                return;
            }

            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setId_userType(Integer.parseInt(idUserTypeParam));
            user.setInactive(Boolean.parseBoolean(inactiveParam));

            userService.updateUser(user);

            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Usuário atualizado com sucesso\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Parâmetro inválido: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Erro ao atualizar o usuário: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int userId = Integer.parseInt(request.getParameter("id"));
        User user = userService.findUserById(userId);

        if (user != null) {
            userService.deleteUser(userId);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Usuário deletado com sucesso\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Usuário não encontrado\"}");
        }
        out.flush();
    }

    private String userToJson(User user) {
        return String.format("{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"type\":%d,\"registration_date\":\"%s\",\"inactive\":%b}",
                user.getId_user(), user.getName(), user.getEmail(), user.getId_userType(), user.getRegistrationDate(), user.isInactive());
    }

    private String usersToJson(List<User> users) {
        StringBuilder json = new StringBuilder("[");
        json.append(users.stream().map(this::userToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }
}
