package com.demo.jspdemo.controller;

import com.demo.jspdemo.dao.DAOAdmin;
import com.demo.jspdemo.dao.DAOCustomer;
import com.demo.jspdemo.entity.Admin;
import com.demo.jspdemo.entity.Customer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "login", value = "/login")
public class ControllerLogIn extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null || "login".equals(action)) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if ("register".equals(action)) {
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else if ("admin".equals(action)) {
            request.getSession().invalidate();
            request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
        } else if ("logout".equals(action)) {
            request.getSession().invalidate();
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("logout".equals(request.getParameter("action"))) {
            request.getSession().invalidate();
            request.getRequestDispatcher("LoginJSP/Login.jsp").forward(request, response);
        }
        if ("login".equals(request.getParameter("action"))) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            DAOCustomer daoCustomer = new DAOCustomer();
            Customer customer = daoCustomer.logIn(username, password);
            if (customer == null) {
                request.setAttribute("error", "Wrong username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            request.getSession().setAttribute("customer", customer);
            System.out.println("customer: " + customer);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        if ("admin".equals(request.getParameter("action"))) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            DAOAdmin daoAdmin = new DAOAdmin();
            Admin admin = daoAdmin.adminLogin(username, password);
            if (admin == null) {
                request.setAttribute("error", "Wrong username or password");
                request.getRequestDispatcher("adminLogin.jsp").forward(request, response);
            }
            request.getSession().setAttribute("admin", admin);
            System.out.println("admin: " + admin);
            response.sendRedirect("admin?go=");
        }
        if ("register".equals(request.getParameter("action"))) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            DAOCustomer daoCustomer = new DAOCustomer();
            Customer customer = new Customer(name, username, password, address, phone, 1);
            daoCustomer.addCustomer(customer);
            request.getSession().setAttribute("customer", customer);
            System.out.println("customer: " + customer);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
