package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.Category;
import com.demo.jspdemo.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOCategory extends DBConnect {

    public static void main(String[] args) {
        DAOCategory dao = new DAOCategory();
        int n = dao.removeCategoryWithCascade("1");
        if (n > 0) {
            System.out.println("removed");
        }
    }

    public int addCategory(Category category) {
        int n = 0;
        String sql = "INSERT INTO Category(cateName, status) VALUES (?,?)";
        //String sql = "INSERT INTO [dbo].[Category]\n" + "           ([cateName]\n" + "           ,[status])\n" + "     VALUES (?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, category.getCateName());
            pre.setInt(2, category.getStatus());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int UpdateCategory(Category category) {
        int n = 0;
        String sql = "UPDATE [dbo].[Category]\n" + "   SET [cateName] = ?\n" + "      ,[status] = ?\n" + " WHERE [cateId] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, category.getCateName());
            pre.setInt(2, category.getStatus());
            pre.setInt(3, category.getCateId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Category getCategory(int id) {
        Vector<Category> vector = this.getAllCategory("select * from Category where cateId = '" + id + "'");
        if (vector.size() > 0) return vector.get(0);
        return null;
    }

    public void displayAll() {
        String sql = "select * from Category";
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                /* dataType varName = rs.getDataType("fieldName|index"); */
                String name = rs.getString("cateName");
                int status = rs.getInt("status");
                int cateid = rs.getInt("cateId");
                Category cat = new Category(cateid, name, status);
                System.out.println(cat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Vector<Category> getAllCategory(String sql) {
        Vector<Category> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                String name = rs.getString("cateName");
                int status = rs.getInt("status");
                int cateId = rs.getInt("cateId");
                Category cat = new Category(cateId, name, status);
                vector.add(cat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int removeCategoryWithCascade(String id) {
        int n = 0;
        String sql = "DELETE FROM Category WHERE cateId = ?";
        //String sql = "DELETE FROM [dbo].[Category]\n" + "      WHERE cateId = ?";
        DAOProduct dao = new DAOProduct();
        Vector<Product> vector = dao.getAllProduct("select * from Product where cateId = " + id);
        for (Product product : vector) {
            n += dao.removeProductWithCascade(product.getPid());
        }
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, id);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOCategory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
