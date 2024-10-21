package com.DevLearn.controller;

import com.DevLearn.dao.ClassDAO;
import com.DevLearn.dao.CourseDAO;
import com.DevLearn.model.Course;
import com.DevLearn.service.ClassService;
import com.DevLearn.model.Class;

import com.DevLearn.service.CourseService;
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

        String idCourseParam = request.getParameter("id_course");
        String title = request.getParameter("title");
        String descriptionParam = request.getParameter("description");
        String url = request.getParameter("url");

        if (idCourseParam == null || title == null || descriptionParam == null ||  url == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Todos os campos (id_course, title, description, url) são obrigatórios\"}");
            out.flush();
            return;
        }

        Class vclass = new Class();
        vclass.setId_course(Integer.parseInt(idCourseParam));
        vclass.setTitle(title);
        vclass.setDescription(descriptionParam);
        vclass.setUrl_video(url);

        Course course = new Course();
        CourseService courseService = new CourseService(new CourseDAO());
        course = courseService.findCourseById(vclass.getId_course());
        if(course == null){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 Forbidden
            out.print("{\"error\": \"id_curso não encontrado\"}");
            out.flush();
            return;
        }

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

            String idCourseParam = request.getParameter("id_course");
            String title = request.getParameter("title");
            String descriptionParam = request.getParameter("description");
            String url = request.getParameter("url");
            String published = request.getParameter("published");

            if (idCourseParam == null || title == null || descriptionParam == null ||  url == null || published == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Todos os campos (id, id_course, title, description, url, published) são obrigatórios\"}");
                out.flush();
                return;
            }

            vclass.setId_class(classId);
            vclass.setId_course(Integer.parseInt(idCourseParam));
            vclass.setTitle(title);
            vclass.setDescription(descriptionParam);
            vclass.setUrl_video(url);
            vclass.setPublished(Boolean.parseBoolean(published));

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
        return String.format("{\"id\":%d,\"id_course\":%d,\"title\":\"%s\",\"description\":\"%s\",\"url\":\"%s\"}",
                vclass.getId_class(), vclass.getId_course(), vclass.getTitle(), vclass.getDescription(), vclass.getUrl_video());
    }

    private String classesToJson(List<Class> classes) {
        StringBuilder json = new StringBuilder("[");
        json.append(classes.stream().map(this::classToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }
}
