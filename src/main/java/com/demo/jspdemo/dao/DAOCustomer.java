package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.Bill;
import com.demo.jspdemo.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOCustomer extends DBConnect {


    public static void main(String[] args) {
        Customer customer = new Customer("C005", "Daniel Kim", "dkim", "password1234", "654 Pine Ln, Anytown USA", "555-555-5559", 0);
        DAOCustomer dao = new DAOCustomer();
        System.out.println("added: " + dao.updateCustomer(customer));
    }

    public Customer logIn(String username, String password) {
        Customer cus;
        String sql = "select * from Customer where username=? and password=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, username);
            pre.setString(2, password);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                cus = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
                return cus;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int addCustomer(Customer customer) {
        //CREATE TABLE Customer
        //(
        //    cid varchar(32) PRIMARY KEY,
        //    cname nvarchar(50) NOT NULL,
        //    username varchar(30) UNIQUE,
        //    password varchar(32) NOT NULL,
        //    address nvarchar(max),
        //    phone varchar(20),
        //    status int,
        //)
        int n = 0;
        String sql = "INSERT INTO [Customer]" + "           ([cid]" + "           ,[cname]" + "           ,[username]" + "           ,[password]" + "           ,[address]" + "           ,[phone]" + "           ,[status])" + "     VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);

            pre.setString(1, customer.getCid());
            pre.setString(2, customer.getCname());
            pre.setString(3, customer.getUsername());
            pre.setString(4, customer.getPassword());
            pre.setString(5, customer.getAddress());
            pre.setString(6, customer.getPhone());
            pre.setInt(7, customer.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateCustomer(Customer customer) {
        int n = 0;
        String sql = "UPDATE [dbo].[Customer]" + "   SET [cname] = ?" + "      ,[username] = ?" + "      ,[password] = ?" + "      ,[address] = ?" + "      ,[status] = ?" + "      ,[phone] = ?" + " WHERE [cid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, customer.getCname());
            pre.setString(2, customer.getUsername());
            pre.setString(3, customer.getPassword());
            pre.setString(4, customer.getAddress());
            pre.setInt(5, customer.getStatus());
            pre.setString(6, customer.getPhone());
            pre.setString(7, customer.getCid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void displayAll() {
        String sql = "select * from Customer";
        try {

            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Customer cus = getCus(rs);
                System.out.println(cus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Customer getCus(ResultSet rs) throws SQLException {
        String cid = rs.getString("cid");
        String name = rs.getString("cname");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String address = rs.getString("address");
        String phone = rs.getString("phone");
        int status = rs.getInt("status");
        return new Customer(cid, name, username, password, address, phone, status);
    }

    public Vector<Customer> getAllCustomer(String sql) {
        Vector<Customer> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                Customer cus = getCus(rs);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public boolean login(String user, String pass) {
        String sql = "select * from Customer where username=? and  password = ? and status=1";
        PreparedStatement pre;
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, user);
            pre.setString(2, pass);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int removeCustomerWithCascade(String id) {
        int n = 0;
        DAOBill daoBill = new DAOBill();
        Vector<Bill> bills = daoBill.getAllBill("select * from Bill where cid = '" + id + "'");
        for (Bill bill : bills) {
            n += daoBill.removeBillWithCascade(bill.getBid());
        }
        String sql = "delete from Customer where cid = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            n += pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }


}
