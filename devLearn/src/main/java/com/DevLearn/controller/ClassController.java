/*
package com.DevLearn.controller;

import com.DevLearn.dao.ClassDAO;
import com.DevLearn.service.ClassService;
import com.DevLearn.model.Class;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/classes")
public class ClassController extends HttpServlet {
    private ClassService classService;

    @Override
    public void init() throws ServletException {
        super.init();
        classService = new ClassService(new ClassDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String classIdParam = request.getParameter("id");
        if (classIdParam != null) {
            int classId = Integer.parseInt(classIdParam);
            Class vclass = classService.findClassById(classId);
            if (vclass != null) {
                out.print(classToJson(vclass));
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Aula não encontrada\"}");
            }
        } else {
            List<Class> classes = classService.listClass();
            out.print(classesToJson(classes));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String durationParam = request.getParameter("duration");
        String idCourseParam = request.getParameter("id_course");
        String url = request.getParameter("url");

        if (name == null || durationParam == null || idCourseParam == null || url == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Todos os campos (name, duration, id_course, url) são obrigatórios\"}");
            out.flush();
            return;
        }

        Class vclass = new Class();
        vclass.setName(name);
        vclass.setDuration(Integer.parseInt(durationParam));
        vclass.setId_course(Integer.parseInt(idCourseParam));
        vclass.setUrl(url);

        try {
            classService.addClass(vclass);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Aula criada com sucesso\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Erro ao criar aula: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String classIdParam = request.getParameter("id");
            if (classIdParam == null || classIdParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Parâmetro 'id' é obrigatório\"}");
                out.flush();
                return;
            }

            int classId = Integer.parseInt(classIdParam);
            Class vclass = classService.findClassById(classId);

            if (vclass == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                out.print("{\"error\": \"Aula não encontrada\"}");
                out.flush();
                return;
            }

            String name = request.getParameter("name");
            String durationParam = request.getParameter("duration");
            String idCourseParam = request.getParameter("id_course");
            String url = request.getParameter("url");

            if (name == null || durationParam == null || idCourseParam == null || url == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Todos os campos (name, duration, id_course, url) são obrigatórios\"}");
                out.flush();
                return;
            }

            vclass.setName(name);
            vclass.setDuration(Integer.parseInt(durationParam));
            vclass.setId_course(Integer.parseInt(idCourseParam));
            vclass.setUrl(url);

            classService.updateClass(vclass);

            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Aula atualizada com sucesso\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Parâmetro inválido: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Erro ao atualizar a aula: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int classId = Integer.parseInt(request.getParameter("id"));
        Class vclass = classService.findClassById(classId);

        if (vclass != null) {
            classService.deleteClass(classId);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Aula deletada com sucesso\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Aula não encontrada\"}");
        }
        out.flush();
    }

    private String classToJson(Class vclass) {
        return String.format("{\"id_class\":%d,\"name\":\"%s\",\"duration\":%d,\"id_course\":%d,\"url\":\"%s\"}",
                vclass.getId_class(), vclass.getName(), vclass.getDuration(), vclass.getId_course(), vclass.getUrl());
    }

    private String classesToJson(List<Class> classes) {
        StringBuilder json = new StringBuilder("[");
        json.append(classes.stream().map(this::classToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }
}
*/
