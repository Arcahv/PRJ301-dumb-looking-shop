package com.demo.jspdemo.dao;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {
    public Connection conn = null;

    public DBConnect() {
        this("jdbc:sqlserver://localhost:1433;databaseName=SE1704_NEW;encrypt=false", "prj_login", "123456");
    }

    public DBConnect(String url, String userName, String pass) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(url, userName, pass);
            System.out.println("Connected");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getData(String sql) {
        ResultSet rs = null;
        try {
            Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = state.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    //ArrayList and Vector are both implementations of the List interface in Java. They both use dynamically resizable arrays for their internal data structure. The main difference between them is that Vector is synchronized (thread-safe), while ArrayList is not. This means that only one thread can call methods on a Vector at a time, which can result in a slight overhead in acquiring the lock. If you use an ArrayList, multiple threads can work on it at the same time.
    //Thread safety is important for building web servers because web servers often handle multiple requests simultaneously. This means that multiple threads may be accessing and modifying shared data structures at the same time. If these data structures are not thread-safe, it can result in race conditions, data corruption, and other issues.
    //Using a thread-safe data structure like Vector can help prevent these issues by ensuring that only one thread can access the data structure at a time. This can help ensure that the data remains consistent and correct even when accessed by multiple threads simultaneously.
}
