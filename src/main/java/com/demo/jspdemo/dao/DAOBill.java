package com.demo.jspdemo.dao;

import com.demo.jspdemo.entity.Bill;
import com.demo.jspdemo.entity.Cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOBill extends DBConnect {

    public static void main(String[] args) {
        DAOBill dao = new DAOBill();
        int n = dao.removeBillWithCascade("Bill1");
        if (n > 0) {
            System.out.println("Removed");
        }
    }

    public int addBill(Bill bill) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[Bill]\n" + "([bid]\n" + ",[dateCreate]\n" + ",[recAddress]\n" + ",[recPhone]\n" + ",[note]\n" + ",[totalMoney]\n" + ",[status]\n" + ",[cid])\n" + "VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, bill.getBid());
            pre.setString(2, bill.getDateCreate());
            pre.setString(3, bill.getRecAddress());
            pre.setString(4, bill.getRecPhone());
            pre.setString(5, bill.getNote());
            pre.setDouble(6, bill.getTotalMoney());
            pre.setInt(7, bill.getStatus());
            pre.setString(8, bill.getCid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }


    public int UpdateBill(Bill bill) {
        int n = 0;
        String sql = "UPDATE [dbo].[Bill]\n" + "   SET [dateCreate] = ?\n" + "      ,[recAddress] = ?\n" + "      ,[recPhone] = ?\n" + "      ,[note] = ?\n" + "      ,[totalMoney] = ?\n" + "      ,[status] = ?\n" + "      ,[cid] = ?\n" + " WHERE [bid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, bill.getDateCreate());
            pre.setString(2, bill.getRecAddress());
            pre.setString(3, bill.getRecPhone());
            pre.setString(4, bill.getNote());
            pre.setDouble(5, bill.getTotalMoney());
            pre.setInt(6, bill.getStatus());
            pre.setString(7, bill.getCid());
            pre.setString(8, bill.getBid());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }

        return n;
    }

    public Vector<Bill> getAllBill(String sql) {
        Vector<Bill> vector = new Vector<>();
        ResultSet rs = this.getData(sql);
        try {
            while (rs.next()) {
                String dateCreate = rs.getString("dateCreate");
                String recAddress = rs.getString("recAddress");
                String recPhone = rs.getString("recPhone");
                String note = rs.getString("note");
                int status = rs.getInt("status");
                String cid = rs.getString("cid");
                String bid = rs.getString("bid");
                Bill bill = new Bill(bid, dateCreate, recAddress, recPhone, note, status, cid);
                vector.add(bill);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int removeBillWithCascade(String bid) {
        // Remove a bill will remove all bill detail of that bill
        int n = 0;
        String sql = "DELETE FROM [dbo].[BillDetail]\n" + "      WHERE [bid] = ?";
        String billSql = "DELETE FROM [dbo].[Bill]\n" + "      WHERE [bid] = ?";

        try {
            PreparedStatement pre = conn.prepareStatement(sql), preBill = conn.prepareStatement(billSql);
            pre.setString(1, bid);
            preBill.setString(1, bid);
            n = pre.executeUpdate();
            n += preBill.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }


        return n;
    }


    public int updateBillStatus(String bid, String status) {
        String sql = "UPDATE [dbo].[Bill]" + "   SET [status] = ?" + " WHERE [bid] = ?";
        try {
            PreparedStatement pre = conn.prepareStatement(sql);
            pre.setString(1, status);
            pre.setString(2, bid);
            return pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOBill.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
