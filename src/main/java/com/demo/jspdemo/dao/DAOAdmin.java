package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.Admin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOAdmin extends DBConnect {

    public static void main(String[] args) {
        Vector<Admin> vector = new DAOAdmin().getAdminVector("select * from admin");
        for (Admin admin : vector) {
            System.out.println(admin.getAdmin());
        }
    }

    public Admin adminLogin(String admin_name, String password) {
        String sql = "select * from admin where admin=? and password=?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, admin_name);
            pre.setString(2, password);
            //A `PreparedStatement` is an object that represents a precompiled SQL statement in Java. It extends the `Statement` interface and can be used to efficiently execute a SQL statement multiple times with different parameters¹.
            //When you create a `PreparedStatement`, you provide a SQL statement with placeholders for parameters. These placeholders are represented by question marks (`?`) in the SQL statement. You can then set the values for these parameters using the appropriate setter methods (e.g., `setInt`, `setString`, etc.) before executing the statement. This allows you to reuse the same `PreparedStatement` object to execute the same SQL statement multiple times with different parameter values¹.
            //Using a `PreparedStatement` can improve performance and security compared to using a regular `Statement` object. Because the SQL statement is precompiled, it can be executed more efficiently. Additionally, using a `PreparedStatement` can help prevent SQL injection attacks because it automatically handles escaping special characters in parameter values¹.
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString(2), rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int addAdmin(Admin admin) {
        int n = 0;
        //String sql = "INSERT INTO [dbo].[admin]\n" + "           ([admin]\n" + "           ,[password])\n" + "     VALUES(?,?)";
        String sql = "Insert into admin(admin,password) values(?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, admin.getAdmin());
            pre.setString(2, admin.getPassword());
            n = pre.executeUpdate();
            //A `PreparedStatement` is an object that represents a precompiled SQL statement in Java. It extends the `Statement` interface and can be used to efficiently execute a SQL statement multiple times with different parameters¹.
            //When you create a `PreparedStatement`, you provide a SQL statement with placeholders for parameters. These placeholders are represented by question marks (`?`) in the SQL statement. You can then set the values for these parameters using the appropriate setter methods (e.g., `setInt`, `setString`, etc.) before executing the statement. This allows you to reuse the same `PreparedStatement` object to execute the same SQL statement multiple times with different parameter values¹.
            //Using a `PreparedStatement` can improve performance and security compared to using a regular `Statement` object. Because the SQL statement is precompiled, it can be executed more efficiently. Additionally, using a `PreparedStatement` can help prevent SQL injection attacks because it automatically handles escaping special characters in parameter values¹.
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int UpdateAdmin(Admin admin) {
        int n = 0;
        String sql = "UPDATE [dbo].[admin]\n" + "   SET [password] = ?\n" + " WHERE [admin] = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, admin.getPassword());
            preparedStatement.setString(2, admin.getAdmin());
            n = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<Admin> getAdminVector(String sql) {
        Vector<Admin> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                String admin = rs.getString("admin");
                String password = rs.getString("password");
                Admin Admin = new Admin(admin, password);
                vector.add(Admin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int removeAdmin(String admin) {
        int n = 0;
        String sql = "Delete from admin where admin = '" + admin + "'";
        Statement state;
        try {
            state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
