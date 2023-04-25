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

@WebServlet(name = "cart", value = "/cart")
public class ControllerCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("customer") == null) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        String action = request.getParameter("action");
        if ("add-cart".equals(action)) {
            String pid = request.getParameter("pid");
            Customer customer = (Customer)request.getSession().getAttribute("customer");
            DAOCart daoCart = new DAOCart();
            Cart cart = new Cart(pid, customer.getCid(), 1);
            Cart existed = daoCart.isExistCart(cart);
            if (existed == null) System.out.println("Added: " + daoCart.addNewCart(cart) + " " + cart);
            else {
                existed.setBuyQuantity(existed.getBuyQuantity() + 1);
                System.out.println("Updated: " + daoCart.updateCart(existed) + " " + existed);
            }
        }
        if ("delete".equals(action)) {
            String pid = request.getParameter("pid");
            DAOCart daoCart = new DAOCart();
            daoCart.removeItem(((Customer)request.getSession().getAttribute("customer")), pid);
        }

        Vector<Cart> cartItems = new DAOCart().getCartVector(((Customer)request.getSession().getAttribute("customer")).getCid());
        request.setAttribute("carts", cartItems);
        request.getRequestDispatcher("showCart.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            DAOCart daoCart = new DAOCart();
            Vector<Cart> cartVector = daoCart.getCartVector(((Customer)request.getSession().getAttribute("customer")).getCid());
            for (Cart cart : cartVector) {

                cart.setBuyQuantity(Integer.parseInt(request.getParameter("quantity[" + cart.getPid() + "]")));
                daoCart.updateCart(cart);
            }
        }
        if ("checkout".equals(action)) {
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            String notes = request.getParameter("notes");
            DAOCart daoCart = new DAOCart();
            String bid = Utils.randomString30();
            DAOProduct daoProduct = new DAOProduct();
            Vector<Cart> cartVector = daoCart.getCartVector(((Customer)request.getSession().getAttribute("customer")).getCid());
            double totalMoney = 0;
            for (Cart cart : cartVector) {
                totalMoney += daoProduct.getProduct(cart.getPid()).getPrice() * cart.getBuyQuantity();
            }

            DAOBill daoBill = new DAOBill();
            DAOBillDetail daoBillDetail = new DAOBillDetail();
            Bill bill = new Bill(bid, Utils.getCurrentDate(), address, phone, notes, totalMoney, 0, ((Customer)request.getSession().getAttribute("customer")).getCid());
            daoBill.addBill(bill);

            for (Cart cart : cartVector) {
                Product product = daoProduct.getProduct(cart.getPid());
                product.setQuantity(daoProduct.getProduct(cart.getPid()).getQuantity() - cart.getBuyQuantity());
                daoProduct.updateProduct(product);
                BillDetail billDetail = new BillDetail(bid, cart.getPid(), cart.getBuyQuantity(), product.getPrice(), product.getPrice() * cart.getBuyQuantity());
                daoBillDetail.addBillDetail(billDetail);
            }
            daoCart.removeAllItem((Customer)request.getSession().getAttribute("customer"));

        }

        Vector<Cart> cartItems = new DAOCart().getCartVector(((Customer)request.getSession().getAttribute("customer")).getCid());
        request.setAttribute("carts", cartItems);
        request.getRequestDispatcher("showCart.jsp").forward(request, response);
    }
}
