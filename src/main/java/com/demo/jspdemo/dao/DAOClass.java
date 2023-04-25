package com.demo.jspdemo.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOClass extends DBConnect {
    public int updateClasNameBySID(String SID_name, String newClassName) throws SQLException {
        String sql = "UPDATE [dbo].[Class]\n" + "   SET [className] = ?\n" + " WHERE [classID] = ?" + " OR [className] LIKE ?";
        PreparedStatement pre = conn.prepareStatement(sql);
        pre.setString(1, newClassName);
        pre.setString(2, SID_name);
        pre.setString(3, SID_name);
        return pre.executeUpdate();
    }
}
