/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.demo.jspdemo.controller;

import com.demo.jspdemo.dao.DAOBill;
import com.demo.jspdemo.dao.DAOBillDetail;
import com.demo.jspdemo.dao.DAOCustomer;
import com.demo.jspdemo.dao.DAOProduct;
import com.demo.jspdemo.entity.Bill;
import com.demo.jspdemo.entity.BillDetail;
import com.demo.jspdemo.entity.Customer;
import com.demo.jspdemo.entity.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Vector;

/**
 * @author Supersanta
 */
@WebServlet(name = "admin", urlPatterns = {"/admin"})
public class ControllerAdmin extends HttpServlet {
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login?action=admin");
        }
        String action = request.getParameter("action");
        if ("view-bill-detail".equals(action)) {
            String bid = request.getParameter("bid");
            Vector<BillDetail> billDetails = new DAOBillDetail().getAllBillDetail("select * from BillDetail where bid = '" + bid + "'");
            request.setAttribute("billDetails", billDetails);
            request.setAttribute("bid", bid);
            Bill bill = new DAOBill().getAllBill("select * from bill where bid = '" + bid + "'").get(0);
            request.setAttribute("bill", bill);
            request.getRequestDispatcher("billDetail.jsp").forward(request, response);
        }
        if ("search".equals(action)) {
            String search = request.getParameter("search");
            String sql = search != null ? "select * from product where pname like '%" + search + "%' or description like '%" + search + "%' or price like '%" + search + "%' or quantity like '%" + search + "%' or cateId like '%" + search + "%' or status like '%" + search + "%' or image like '%" + search + "%'" : "select * from product";
            DAOProduct daoProduct = new DAOProduct();
            Vector<Product> products = daoProduct.getAllProduct(sql);
            request.setAttribute("products", products);
        }
        if ("search-customer".equals(action)) {
            String search = request.getParameter("search");
            String sql;
            if (search.equals("")) sql = "select * from customer";
            else
                sql = "select * from customer where username like '%" + search + "%' or password like '%" + search + "%' or address like '%" + search + "%' or cid like '%" + search + "%' or phone like '%" + search + "%' or status like '%" + search + "%'";
            DAOCustomer daoCustomer = new DAOCustomer();
            Vector<Customer> customers = daoCustomer.getAllCustomer(sql);
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("customerManage.jsp").forward(request, response);
        }
        if ("search-bill".equals(action)) {
            String search = request.getParameter("search");
            String sql;
            if (search.equals("")) sql = "select * from bill";
            else
                sql = "select * from bill where cid like '%" + search + "%' or bid like '%" + search + "%' or dateCreate like '%" + search + "%' or status like '%" + search + "%'";

            DAOBill daoBill = new DAOBill();
            Vector<Bill> bills = daoBill.getAllBill(sql);
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("billManage.jsp").forward(request, response);
        }
        if ("category".equals(action)) {
            DAOProduct daoProduct = new DAOProduct();
            Vector<Product> products = daoProduct.getAllProduct("select * from product where cateId = '" + request.getParameter("cateId") + "'");
            request.setAttribute("products", products);
        }
        if ("customer-manage".equals(action)) {
            DAOCustomer daoCustomer = new DAOCustomer();
            Vector<Customer> customers = daoCustomer.getAllCustomer("select * from customer");
            request.setAttribute("customers", customers);
            request.getRequestDispatcher("customerManage.jsp").forward(request, response);
        }
        if ("product-manage".equals(action)) {
            DAOProduct daoProduct = new DAOProduct();
            Vector<Product> products = daoProduct.getAllProduct("select * from product");
            request.setAttribute("products", products);
            request.getRequestDispatcher("addProduct.jsp").forward(request, response);
        }
        if ("bill-manage".equals(action)) {
            DAOBill daoBill = new DAOBill();
            Vector<Bill> bills = daoBill.getAllBill("select * from bill");
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("billManage.jsp").forward(request, response);
        }
        if ("add-product".equals(action)) {
            request.getRequestDispatcher("addProduct.jsp").forward(request, response);
        }
        if ("delete-product".equals(action)) {
            handleDelete(request, response);
        }
        if ("update-product".equals(action)) {
            String pid = request.getParameter("pid");
            Product product = new DAOProduct().getProduct(pid);
            request.setAttribute("product", product);
            request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
        }
        if ("add-customer".equals(action)) {
            request.getRequestDispatcher("addCustomer.jsp").forward(request, response);
        }
        if ("delete-customer".equals(action)) {
            String cid = request.getParameter("cid");
            DAOCustomer daoCustomer = new DAOCustomer();
            daoCustomer.removeCustomerWithCascade(cid);
        }
        if ("update-customer".equals(action)) {
            String cid = request.getParameter("cid");
            Vector<Customer> customers = new DAOCustomer().getAllCustomer("select * from customer where cid = '" + cid + "'");
            request.setAttribute("customer", customers.get(0));
            request.getRequestDispatcher("updateCustomer.jsp").forward(request, response);
        }
        // check if request is already commited
        if (!response.isCommitted()) request.getRequestDispatcher("adminIndex.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("login?action=admin");
        }
        String action = request.getParameter("action");
        if ("add-product".equals(action)) {
            String pid = Utils.randomString30();
            String pname = request.getParameter("pname");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            String image = request.getParameter("image");
            String description = request.getParameter("description");
            int status = Integer.parseInt(request.getParameter("status"));
            int cateID = Integer.parseInt(request.getParameter("cateID"));
            Product product = new Product(pid, pname, quantity, price, image, description, status, cateID);
            DAOProduct daoProduct = new DAOProduct();
            daoProduct.addProductByPre(product);

        }
        if ("delete-product".equals(action)) {
            handleDelete(request, response);
        }
        if ("update-product".equals(action)) {
            String pid = request.getParameter("pid");
            String pname = request.getParameter("pname");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            double price = Double.parseDouble(request.getParameter("price"));
            String image = request.getParameter("image");
            String description = request.getParameter("description");
            int status = Integer.parseInt(request.getParameter("status"));
            int cateID = Integer.parseInt(request.getParameter("cateID"));
            Product product = new Product(pid, pname, quantity, price, image, description, status, cateID);
            DAOProduct daoProduct = new DAOProduct();
            daoProduct.updateProduct(product);
        }
        if ("update-customer".equals(action)) {
            String cid = request.getParameter("cid");
            String cname = request.getParameter("cname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String address = request.getParameter("address");
            String phone = request.getParameter("phone");
            int status = Integer.parseInt(request.getParameter("status"));
            Customer customer = new Customer(cid, cname, username, password, address, phone, status);
            DAOCustomer daoCustomer = new DAOCustomer();
            daoCustomer.updateCustomer(customer);
            response.sendRedirect("admin?action=customer-manage");
        }
        if ("update-bill-status".equals(action)) {
            String bid = request.getParameter("bid");
            String status = request.getParameter("status");
            DAOBill daoBill = new DAOBill();
            daoBill.updateBillStatus(bid, status);
            response.sendRedirect("admin?action=view-bill-detail&bid=" + bid);
        }
        //check if request is already commited
        if (!response.isCommitted()) request.getRequestDispatcher("adminIndex.jsp").forward(request, response);
    }

    public void handleDelete(HttpServletRequest request, HttpServletResponse response) {
        String pid = request.getParameter("pid");
        DAOProduct daoProduct = new DAOProduct();
        daoProduct.removeProductWithCascade(pid);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
