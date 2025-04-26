package com.qlbv.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.ChiTietHD;
import com.qlbv.model.entities.HoaDon;
import com.qlbv.model.entities.Ve;



public class ChiTietHDDAO {
    private final ConnectDB db;

    public ChiTietHDDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themChiTietHD(String maHD, String maVe) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemChiTietHD(?,?)}")) {
            stmt.setString(1, maHD);
            stmt.setString(2, maVe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaChiTietHD(String maHD, String maVe) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaChiTietHD(?,?)}")) {
            stmt.setString(1, maHD);
            stmt.setString(2, maVe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ChiTietHD> layChiTietHD(String maHD) {
        List<ChiTietHD> dsChiTietHD = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayChiTietHD(?)}")) {
            stmt.setString(1, maHD);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsChiTietHD.add(new ChiTietHD(
                		new HoaDon(rs.getString("maHD")),
                		new Ve(rs.getString("maVe"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsChiTietHD;
    }


}