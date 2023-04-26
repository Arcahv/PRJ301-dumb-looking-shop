package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.Cart;
import com.demo.jspdemo.entity.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class DAOCart extends DBConnect {
    public Vector<Cart> getCartVector(String cid) {
        String sql = "select * from Cart where cid = ?";
        Vector<Cart> v = new Vector<>();
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, cid);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCid(rs.getString("cid"));
                cart.setPid(rs.getString("pid"));
                cart.setBuyQuantity(rs.getInt("quantity"));
                v.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return v;
    }

    public int updateCart(Cart cart) {
        String sql = "update Cart set quantity = ? where cid = ? and pid = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, cart.getBuyQuantity());
            pre.setString(2, cart.getCid());
            pre.setString(3, cart.getPid());
            return pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public int addNewCart(Cart cart) {
        String sql = "insert into Cart values(?, ?, ?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, cart.getCid());
            pre.setString(2, cart.getPid());
            pre.setInt(3, cart.getBuyQuantity());
            return pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cart ifProductInCartExisted(Cart cart) {
        //Checks for product already in cart, when user add the same product to cart, it will update the quantity instead of creating new entry in cart
        String sql = "select * from Cart where cid = ? and pid = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, cart.getCid());
            pre.setString(2, cart.getPid());
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Cart cart1 = new Cart();
                cart1.setCid(rs.getString("cid"));
                cart1.setPid(rs.getString("pid"));
                cart1.setBuyQuantity(rs.getInt("quantity"));
                return cart1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void removeAllItem(Customer user) {
        String sql = "delete from Cart where cid = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getCid());
            pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeItem(Customer user, String pid) {
        String sql = "delete from Cart where cid = ? and pid = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, user.getCid());
            pre.setString(2, pid);
            pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
