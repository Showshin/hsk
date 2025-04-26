package com.qlbv.model.dao;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.Phong;



public class PhongDAO {
    private final ConnectDB db;

    public PhongDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themPhong(String maPhong, String tenPhong, int sucChua) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemPhong(?,?,?)}")) {
            stmt.setString(1, maPhong);
            stmt.setString(2, tenPhong);
            stmt.setInt(3, sucChua);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaPhong(String maPhong, String tenPhong, int sucChua) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaPhong(?,?,?)}")) {
            stmt.setString(1, maPhong);
            stmt.setString(2, tenPhong);
            stmt.setInt(3, sucChua);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaPhong(String maPhong) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaPhong(?)}")) {
            stmt.setString(1, maPhong);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Phong> layDanhSachPhong() {
        List<Phong> dsPhong = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachPhong}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPhong.add(new Phong(rs.getString("maPhong"), rs.getString("tenPhong"), rs.getInt("sucChua")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhong;
    }


}