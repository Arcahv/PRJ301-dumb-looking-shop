package com.demo.jspdemo.controller;

import com.demo.jspdemo.dao.DAOCategory;
import com.demo.jspdemo.dao.DAOProduct;
import com.demo.jspdemo.entity.Category;
import com.demo.jspdemo.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Vector;

@WebServlet(name = "home", value = "/home")
public class ControllerHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "home";

        DAOCategory daoCategory = new DAOCategory();
        Vector<Category> vec = daoCategory.getAllCategory("Select * from category where status = 1");
        request.setAttribute("vec", vec);
        if ("search".equals(action)) {
            DAOProduct daoProduct = new DAOProduct();
            String sql;
            if (request.getParameter("search") == null) sql = "Select * from product where status = 1";
            else
                sql = "Select * from product where status = 1 and pname like '%" + request.getParameter("search") + "%'";
            Vector<Product> productVector = daoProduct.getAllProduct(sql);
            request.setAttribute("productVector", productVector);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        if ("home".equals(action)) {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        if ("category".equals(action)) {
            DAOProduct daoProduct = new DAOProduct();
            Vector<Product> productVector = daoProduct.getAllProduct("Select * from product where status = 1 and cateId = " + request.getParameter("cateId"));
            request.setAttribute("productVector", productVector);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
