package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.BillDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBillDetail extends DBConnect {

    public static void main(String[] args) {
        DAOBillDetail dao = new DAOBillDetail();
        int n = dao.addBillDetail(new BillDetail("B01", "P01", 2, 10.5, 20.1));
        if (n > 0) {
            System.out.println("Inserted");
        }
    }

    public double getTotalMoney(String bid) {
        double total = 0;
        String sql = "SELECT SUM(subtotal) as total FROM [dbo].[BillDetail] WHERE bid = '" + bid + "'";
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                total += rs.getDouble("total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public int addBillDetail(BillDetail billdetail) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[BillDetail]\n" + "           ([bid]\n" + "           ,[pid]\n" + "           ,[buyQuantity]\n" + "           ,[buyPrice]\n" + "           ,[subtotal])\n" + "     VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, billdetail.getBid());
            pre.setString(2, billdetail.getPid());
            pre.setInt(3, billdetail.getBuyQuantity());
            pre.setDouble(4, billdetail.getBuyPrice());
            pre.setDouble(5, billdetail.getSubtotal());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    public int UpdateBillDetail(BillDetail billdetail) {
        int n = 0;
        String sql = "UPDATE [dbo].[BillDetail]\n" + "   SET [buyQuantity] = ?\n" + "      ,[buyPrice] = ?\n" + "      ,[subtotal] = ?\n" + " WHERE [bid] = ? and [pid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setInt(1, billdetail.getBuyQuantity());
            pre.setDouble(2, billdetail.getBuyPrice());
            pre.setDouble(3, billdetail.getSubtotal());
            pre.setString(4, billdetail.getBid());
            pre.setString(5, billdetail.getPid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<BillDetail> getAllBillDetail(String sql) {
        Vector<BillDetail> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                int quantity = rs.getInt("buyQuantity");
                double price = rs.getDouble("buyPrice");
                double subtotal = rs.getDouble("subtotal");
                String bid = rs.getString("bid");
                String pid = rs.getString("pid");
                BillDetail bl = new BillDetail(bid, pid, quantity, price, subtotal);
                vector.add(bl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int removeBillDetail(String pid, String bid) {
        int n = 0;
        String sql = "delete from BillDetail where pid ='" + pid + "' and bid ='" + bid + "'";
        try {
            Statement state = conn.createStatement();
            n = state.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DAOBillDetail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
}
