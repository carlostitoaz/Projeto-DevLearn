package com.DevLearn.controller;

import com.DevLearn.dao.CategoryDAO;
import com.DevLearn.dao.CourseCategoryDAO;
import com.DevLearn.model.Category;
import com.DevLearn.model.CourseCategory;
import com.DevLearn.service.CategoryService;
import com.DevLearn.service.CourseCategoryService;
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

        String courseIdParam = request.getParameter("id_course");
        String categoryIdParam = request.getParameter("id_category");
        if(courseIdParam != null && categoryIdParam != null){
            int courseId = Integer.parseInt(courseIdParam);
            int categoryId = Integer.parseInt(categoryIdParam);
            CourseCategory courseCategory = courseCategoryService.findCourseCategoryById(courseId, categoryId);
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
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int courseId = Integer.parseInt(request.getParameter("id_course"));
        int categoryId = Integer.parseInt(request.getParameter("id_category"));

        CourseCategory courseCategory = courseCategoryService.findCourseCategoryById(courseId, categoryId);

        if(courseCategory != null){
            courseCategoryService.deleteCourseCategory(courseCategory.getId_course(), courseCategory.getId_category());
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"courso_categoria deletado com sucesso\"}");
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"courso_categoria não encontrado\"}");
        }
        out.flush();
    }

    private String courseCategoryToJson(CourseCategory courseCategory) {
        return String.format("{\"id_course\":%d,\"id_category\":%d}",
                courseCategory.getId_course(), courseCategory.getId_category());
    }

    private String courseCategoriesToJson(List<CourseCategory> courseCategory) {
        StringBuilder json = new StringBuilder("[");
        json.append(courseCategory.stream().map(this::courseCategoryToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }








}
