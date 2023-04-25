package com.demo.jspdemo.controller;

import com.demo.jspdemo.dao.DAOBill;
import com.demo.jspdemo.dao.DAOBillDetail;
import com.demo.jspdemo.dao.DAOCart;
import com.demo.jspdemo.dao.DAOProduct;
import com.demo.jspdemo.entity.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Vector;
@WebServlet(name = "bill", urlPatterns = {"/bill"})
public class ControllerBill extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("customer") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        String action = request.getParameter("action");
        if ("view-bill".equals(action)) {
            DAOBill daoBill = new DAOBill();
            Vector<Bill> bills = daoBill.getAllBill("select * from bill where cid = '" + ((Customer)request.getSession().getAttribute("customer")).getCid() + "'");
            request.setAttribute("bills", bills);
            request.getRequestDispatcher("showBill.jsp").forward(request, response);
        }
        if ("view-bill-detail".equals(action)) {
            String bid = request.getParameter("bid");
            Vector<BillDetail> billDetails = new DAOBillDetail().getAllBillDetail("select * from BillDetail where bid = '" + bid + "'");
            request.setAttribute("billDetails", billDetails);
            request.setAttribute("bid", bid);
            Bill bill = new DAOBill().getAllBill("select * from bill where bid = '" + bid + "'").get(0);
            request.setAttribute("bill", bill);
            request.getRequestDispatcher("showBillDetail.jsp").forward(request, response);

        }
        if ("view-bill-status".equals(action)) {
            String bid = request.getParameter("bid");
            String status = request.getParameter("status");
            DAOBill daoBill = new DAOBill();
            daoBill.updateBillStatus(bid, status);
            response.sendRedirect("admin?action=view-bill-detail&bid=" + bid);
        }
    }
}
