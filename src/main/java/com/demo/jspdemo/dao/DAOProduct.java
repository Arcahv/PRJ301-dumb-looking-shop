package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOProduct extends DBConnect {
    public static void main(String[] args) {
        DAOProduct dao = new DAOProduct();
        dao.getAllProduct("PD001");
    }

    public int addProductByPre(Product product) {
        int n = 0;
        String sql = "";
        sql = "INSERT INTO Product(pid, pname, quantity, price, image, description, status, cateID) VALUES (?,?,?,?,?,?,?,?)";
        //String sql = "INSERT INTO [dbo].[Product]" + "([pid]" + ",[pname]" + ",[quantity]" + ",[price]" + ",[image]" + ",[description]" + ",[status]" + ",[cateID])" + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, product.getPid());
            pre.setString(2, product.getPname());
            pre.setInt(3, product.getQuantity());
            pre.setDouble(4, product.getPrice());
            pre.setString(5, product.getImage());
            pre.setString(6, product.getDescription());
            pre.setInt(7, product.getStatus());
            pre.setInt(8, product.getCateID());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int updateProduct(Product product) {
        int n = 0;
        String sql = "UPDATE Product SET pname = ?, quantity = ?, price = ?, image = ?, description = ?, status = ?, cateID = ? WHERE pid = ?";
        //String sql = "UPDATE [dbo].[Product]" + "   SET [pname] = ?" + "      ,[quantity] = ?" + "      ,[price] = ?" + "      ,[image] = ?" + "      ,[description] = ?" + "      ,[status] = ?" + "      ,[cateID] = ?" + " WHERE [pid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, product.getPname());
            pre.setInt(2, product.getQuantity());
            pre.setDouble(3, product.getPrice());
            pre.setString(4, product.getImage());
            pre.setString(5, product.getDescription());
            pre.setInt(6, product.getStatus());
            pre.setInt(7, product.getCateID());
            pre.setString(8, product.getPid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public void displayAll() {
        String sql = "select * from Customer";

        try {

            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Product pro = getProduct(rs);
                System.out.println(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Product getProduct(ResultSet rs) throws SQLException {
        String pID = rs.getString("pid");
        String pName = rs.getString("pname");
        int quantity = rs.getInt("quantity");
        double price = rs.getDouble("price");
        String image = rs.getString("image");
        String description = rs.getString("description");
        int status = rs.getInt("status");
        int cateId = rs.getInt("cateId");
        return new Product(pID, pName, quantity, price, image, description, status, cateId);
    }

    public Product getProduct(String pid) {
        String sql = "SELECT * FROM [dbo].[Product] WHERE [pid] = ?";
        Product pro;
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, pid);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                pro = getProduct(rs);
                return pro;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Vector<Product> getAllProduct(String sql) {
        Vector<Product> vector = new Vector<Product>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                Product pro = getProduct(rs);
                vector.add(pro);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int removeProductWithCascade(String id) {
        int n = 0;
        String sql2 = "DELETE FROM [dbo].[Product] WHERE [pid] = ?";
        String sql1 = "DELETE FROM [dbo].[BillDetail] WHERE [pid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql1);
            PreparedStatement pre2 = conn.prepareStatement(sql2);
            pre.setString(1, id);
            pre2.setString(1, id);
            n = pre.executeUpdate();
            n += pre2.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOProduct.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
