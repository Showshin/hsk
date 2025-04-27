package com.qlbv.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.LoaiPhim;
import com.qlbv.model.entities.Phim;

public class PhimDAO {
    private final ConnectDB db;

    public PhimDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themPhim(String maPhim, String maLoai, String tenPhim, int thoiLuong, int gioiHanTuoi, double giaGoc, String img) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemPhim(?,?,?,?,?,?,?)}")) {
            stmt.setString(1, maPhim);
            stmt.setString(2, maLoai);
            stmt.setString(3, tenPhim);
            stmt.setInt(4, thoiLuong);
            stmt.setInt(5, gioiHanTuoi);
            stmt.setDouble(6, giaGoc);
            stmt.setString(7, img);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaPhim(String maPhim, String maLoai, String tenPhim, int thoiLuong, int gioiHanTuoi, double giaGoc, String img) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaPhim(?,?,?,?,?,?,?)}")) {
            stmt.setString(1, maPhim);
            stmt.setString(2, maLoai);
            stmt.setString(3, tenPhim);
            stmt.setInt(4, thoiLuong);
            stmt.setInt(5, gioiHanTuoi);
            stmt.setDouble(6, giaGoc);
            stmt.setString(7, img);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaPhim(String maPhim) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaPhim(?)}")) {
            stmt.setString(1, maPhim);
            stmt.execute(); // dùng execute thay vì kiểm tra số dòng
            return true; // chỉ cần không lỗi là true
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Không thể xóa phim mã " + maPhim + ": " + e.getMessage());
            return false;
        }
    }
    

    public Phim timPhim(String maPhim) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimPhim(?)}")) {
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Phim(
                    rs.getString("maPhim"),
                    rs.getString("tenPhim"),
                    rs.getInt("thoiLuong"),
                    rs.getInt("gioiHanTuoi"),
                    rs.getDouble("giaGoc"),
                    new LoaiPhim(rs.getString("maLoai")),
                    rs.getString("img")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Phim> layDanhSachPhim() {
        List<Phim> dsPhim = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachPhim}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsPhim.add(new Phim(
                    rs.getString("maPhim"),
                    rs.getString("tenPhim"),
                    rs.getInt("thoiLuong"),
                    rs.getInt("gioiHanTuoi"),
                    rs.getDouble("giaGoc"),
                    new LoaiPhim(rs.getString("maLoai")),
                    rs.getString("img")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhim;
    }
}