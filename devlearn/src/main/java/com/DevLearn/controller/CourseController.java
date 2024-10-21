package com.DevLearn.controller;

import com.DevLearn.dao.CourseDAO;
import com.DevLearn.dao.UserDAO;
import com.DevLearn.model.Course;
import com.DevLearn.model.User;
import com.DevLearn.service.CourseService;
import com.DevLearn.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/courses")
public class CourseController extends HttpServlet {
    private CourseService courseService;

    @Override
    public void init() throws ServletException{
        super.init();
        courseService = new CourseService(new CourseDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String courseIdParam = request.getParameter("id");
        if(courseIdParam != null){
            int courseId = Integer.parseInt(courseIdParam);
            Course course = courseService.findCourseById(courseId);
            if(course != null){
                out.print(courseToJson(course));
                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Curso não encotrado\"}");
            }
        }else{
            List<Course> courses = courseService.listCourse();
            out.print(coursesToJson(courses));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String id_user = request.getParameter("id_user");

        if(title == null || description == null || id_user == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Todos os campos (title, description, id_user) são obrigatórios\"}");
            out.flush();
            return;
        }

        Course course = new Course();
        course.setTitle(title);
        course.setDescription(description);
        course.setId_user(Integer.parseInt(id_user));

        UserService userService = new UserService(new UserDAO());
        User user = userService.findUserById(course.getId_user());

        if (user.getId_userType() != 1) {  // Verifica se o tipo de usuário é professor
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 Forbidden
            out.print("{\"error\": \"Apenas professores (tipo de usuário 1) podem criar cursos\"}");
            out.flush();
            return;
        }

        try{
            courseService.addCourse(course);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Curso criado com sucesso\"}");
        }catch (IllegalArgumentException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Erro ao criar curso: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try{
            String courseIdParam = request.getParameter("id");
            if(courseIdParam == null || courseIdParam.isEmpty()){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Parâmetro 'id' é obrigatório\"}");
                out.flush();
                return;
            }

            int courseId = Integer.parseInt(courseIdParam);
            Course course = courseService.findCourseById(courseId);

            if(course == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Curso não encontrado\"}");
                out.flush();
                return;
            }

            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String id_user = request.getParameter("id_user");
            String published = request.getParameter("published");
            String inactive = request.getParameter("inactive");

            if(title == null || description == null || id_user == null || published == null || inactive == null){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Todos os campos (title, description, id_user, publiched, inactive) são obrigatórios\"}");
                out.flush();
                return;
            }

            UserService userService = new UserService(new UserDAO());
            User user = userService.findUserById(Integer.parseInt(id_user));

            if (user.getId_userType() != 1) {  // Verifica se o tipo de usuário é professor
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 Forbidden
                out.print("{\"error\": \"id_user não é professor\"}");
                out.flush();
                return;
            }

            course.setId_course(courseId);
            course.setTitle(title);
            course.setDescription(description);
            course.setPublished(Boolean.parseBoolean(published));
            course.setInactive(Boolean.parseBoolean(inactive));
            course.setId_user(Integer.parseInt(id_user));

            courseService.updateCourse(course);

            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Curso atualizado com sucesso\"}");
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Parâmetro inválido: " + e.getMessage() + "\"}");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
                out.print("{\"error\": \"Erro ao atualizar o curso: " + e.getMessage() + "\"}");
            } finally {
                out.flush();
            }
        }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int courseId = Integer.parseInt(request.getParameter("id"));
        Course course = courseService.findCourseById(courseId);

        if(course != null){
            courseService.deleteCourse(courseId);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Curso deletado com sucesso\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Curso não encontrado\"}");
        }
        out.flush();
    }

    private String courseToJson(Course course) {
        return String.format("{\"id\":%d,\"title\":\"%s\",\"description\":\"%s\",\"id_user\":%d,\"creation_date\":\"%s\",\"published\":%b,\"inactive\":%b}",
                course.getId_course(), course.getTitle(), course.getDescription(), course.getId_user(),course.getCreation_date(), course.isPublished(), course.isInactive());
    }

    private String coursesToJson(List<Course> course) {
        StringBuilder json = new StringBuilder("[");
        json.append(course.stream().map(this::courseToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }
}















