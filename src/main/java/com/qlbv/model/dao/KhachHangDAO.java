package com.qlbv.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.KhachHang;

public class KhachHangDAO {
    private final ConnectDB db;

    public KhachHangDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themKhachHang(String maKH, String tenKH, String sdt) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemKhachHang(?,?,?)}")) {
            stmt.setString(1, maKH);
            stmt.setString(2, tenKH);
            stmt.setString(3, sdt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaKhachHang(String maKH, String tenKH, String sdt) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaKhachHang(?,?,?)}")) {
            stmt.setString(1, maKH);
            stmt.setString(2, tenKH);
            stmt.setString(3, sdt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaKhachHang(String maKH) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaKhachHang(?)}")) {
            stmt.setString(1, maKH);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public KhachHang timKhachHang(String maKH) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimKhachHang(?)}")) {
            stmt.setString(1, maKH);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHang timKhachHangTheoSDT(String sdt) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimKhachHangTheoSDT(?)}")) {
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHang> layDanhSachKhachHang() {
        List<KhachHang> dsKhachHang = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachKhachHang}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsKhachHang.add(new KhachHang(rs.getString("maKH"), rs.getString("tenKH"), rs.getString("sdt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsKhachHang;
    }

}