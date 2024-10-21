package com.DevLearn.controller;

import com.DevLearn.dao.CategoryDAO;
import com.DevLearn.dao.CourseCategoryDAO;
import com.DevLearn.dao.CourseDAO;
import com.DevLearn.model.Category;
import com.DevLearn.model.Course;
import com.DevLearn.model.CourseCategory;
import com.DevLearn.model.User;
import com.DevLearn.service.CategoryService;
import com.DevLearn.service.CourseCategoryService;
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

@WebServlet("/categories/courses")
public class CourseCategoryController extends HttpServlet {
    private CourseCategoryService courseCategoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        courseCategoryService = new CourseCategoryService(new CourseCategoryDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String courseCategoryIdParam = request.getParameter("id");
        if(courseCategoryIdParam != null){
            int courseCategoryId = Integer.parseInt(courseCategoryIdParam);
            CourseCategory courseCategory = courseCategoryService.findCourseCategoryById(courseCategoryId);
            if(courseCategory != null){
                out.print(courseCategoryToJson(courseCategory));
                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"curso_categoria não encotrado\"}");
            }
        }else{
            List<CourseCategory> courseCategories = courseCategoryService.listCourseCategory();
            out.print(courseCategoriesToJson(courseCategories));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String courseId = request.getParameter("id_course");
        String categoryId = request.getParameter("id_category");

        if(courseId == null || categoryId == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"Os campos (id_course, id_category) são obrigatórios\"}");
            out.flush();
            return;
        }

        CourseCategory courseCategory = new CourseCategory();
        courseCategory.setId_course(Integer.parseInt(courseId));
        courseCategory.setId_category(Integer.parseInt(categoryId));

        try{
            courseCategoryService.addCourseCategory(courseCategory);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"curso_categoria criado com sucesso\"}");
        }catch (IllegalArgumentException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Erro ao criar curso_categoria: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            String courseCategoryIdParam = request.getParameter("id");
            String courseidParam = request.getParameter("id_course");
            String categoryidParam = request.getParameter("id_category");
            if (courseCategoryIdParam == null || courseCategoryIdParam.isEmpty() || courseidParam == null || courseidParam.isEmpty() || categoryidParam == null || categoryidParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Todos os campos (id, id_course, id_category) são obrigatórios\"}");
                out.flush();
                return;
            }

            int courseCategoryId = Integer.parseInt(courseCategoryIdParam);
            CourseCategory courseCategory = courseCategoryService.findCourseCategoryById(courseCategoryId);

            if (courseCategory == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                out.print("{\"error\": \"curso_categoria não encontrado\"}");
                out.flush();
                return;
            }

            Course course = new Course();
            CourseService courseService = new CourseService(new CourseDAO());
            course = courseService.findCourseById(courseCategory.getId_course());

            Category category = new Category();
            CategoryService categoryService = new CategoryService(new CategoryDAO());
            category = categoryService.findCategoryById(courseCategory.getId_category());

            if(course == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                out.print("{\"error\": \"curso não encontrado\"}");
                out.flush();
                return;
            } else if (category == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
                out.print("{\"error\": \"categoria não encontrada\"}");
                out.flush();
                return;
            }

            int courseid = Integer.parseInt(courseidParam);
            int categoryid = Integer.parseInt(categoryidParam);

            courseCategory.setId_course(courseid);
            courseCategory.setId_category(categoryid);

            courseCategoryService.updateCourseCategory(courseCategory);

            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"curso_categoria atualizado com sucesso\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Parâmetro inválido: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Erro ao atualizar o curso_categoria: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int courseCategoryId = Integer.parseInt(request.getParameter("id"));

        CourseCategory courseCategory = courseCategoryService.findCourseCategoryById(courseCategoryId);

        if(courseCategory != null){
            courseCategoryService.deleteCourseCategory(courseCategory.getId_courseCategory());
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"courso_categoria deletado com sucesso\"}");
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"courso_categoria não encontrado\"}");
        }
        out.flush();
    }

    private String courseCategoryToJson(CourseCategory courseCategory) {
        return String.format("{\"id_course\":%d, \"course\":\"%s\", \"id_category\":%d, \"category\":\"%s\"}",
                courseCategory.getId_course(), courseCategory.getCourse_name(),courseCategory.getId_category(), courseCategory.getCategory_name());
    }

    private String courseCategoriesToJson(List<CourseCategory> courseCategory) {
        StringBuilder json = new StringBuilder("[");
        json.append(courseCategory.stream().map(this::courseCategoryToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }
}