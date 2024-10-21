package com.DevLearn.controller;

import com.DevLearn.dao.CategoryDAO;
import com.DevLearn.model.Category;
import com.DevLearn.model.Course;
import com.DevLearn.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/categories")
public class CategoryController extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException{
        super.init();
        categoryService = new CategoryService(new CategoryDAO());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String categoryIdParam = request.getParameter("id");
        if(categoryIdParam != null){
            int categoryId = Integer.parseInt(categoryIdParam);
            Category category = categoryService.findCategoryById(categoryId);
            if(category != null){
                out.print(categoryToJson(category));
                response.setStatus(HttpServletResponse.SC_OK);
            }else{
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Categoria não encotrado\"}");
            }
        }else{
            List<Category> categories = categoryService.listCategory();
            out.print(categoriesToJson(categories));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");

        if(name == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\": \"O campo (name) é obrigatório\"}");
            out.flush();
            return;
        }

        Category category = new Category();
        category.setName(name);

        try{
            categoryService.addCategory(category);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print("{\"message\": \"Categoria criada com sucesso\"}");
        }catch (IllegalArgumentException e){
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            out.print("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\": \"Erro ao criar categoria: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try{
            String categoryIdParam = request.getParameter("id");
            if(categoryIdParam == null || categoryIdParam.isEmpty()){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\": \"Parâmetro 'id' é obrigatório\"}");
                out.flush();
                return;
            }
            int categoryId = Integer.parseInt(categoryIdParam);
            Category category = categoryService.findCategoryById(categoryId);

            if(category == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\": \"Categoria não encontrado\"}");
                out.flush();
                return;
            }

            String name = request.getParameter("name");

            if(name == null){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"O campo (name) é obrigatório\"}");
                out.flush();
                return;
            }

            category.setName(name);

            categoryService.updateCategory(category);

            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Categoria atualizada com sucesso\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            out.print("{\"error\": \"Parâmetro inválido: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Erro ao atualizar a categoria: " + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int categoryId = Integer.parseInt(request.getParameter("id"));
        Category category = categoryService.findCategoryById(categoryId);

        if(category != null){
            categoryService.deleteCategory(categoryId);
            response.setStatus(HttpServletResponse.SC_OK);
            out.print("{\"message\": \"Categoria deletada com sucesso\"}");
        }else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Categoria não encontrada\"}");
        }
        out.flush();
    }

    private String categoryToJson(Category category) {
        return String.format("{\"id\":%d,\"name\":\"%s\"}",
                category.getId_category(), category.getName());
    }

    private String categoriesToJson(List<Category> category) {
        StringBuilder json = new StringBuilder("[");
        json.append(category.stream().map(this::categoryToJson).collect(Collectors.joining(",")));
        json.append("]");
        return json.toString();
    }
}


