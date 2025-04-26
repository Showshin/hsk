package com.qlbv.model.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.TaiKhoanNhanVien;


public class TaiKhoanNhanVienDAO {
    private final ConnectDB db;

    public TaiKhoanNhanVienDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themTaiKhoanNhanVien(String maTaiKhoan, String maNV, String tenDangNhap, String matKhau, String vaiTro) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemTaiKhoanNhanVien(?,?,?,?,?)}")) {
            stmt.setString(1, maTaiKhoan);
            stmt.setString(2, maNV);
            stmt.setString(3, tenDangNhap);
            stmt.setString(4, matKhau);
            stmt.setString(5, vaiTro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaTaiKhoanNhanVien(String maTaiKhoan, String tenDangNhap, String matKhau, String vaiTro) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaTaiKhoanNhanVien(?,?,?,?)}")) {
            stmt.setString(1, maTaiKhoan);
            stmt.setString(2, tenDangNhap);
            stmt.setString(3, matKhau);
            stmt.setString(4, vaiTro);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaTaiKhoanNhanVien(String maTaiKhoan) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaTaiKhoanNhanVien(?)}")) {
            stmt.setString(1, maTaiKhoan);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoanNhanVien dangNhap(String tenDangNhap, String matKhau) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_DangNhap(?,?)}")) {
            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhau);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaiKhoanNhanVien(
                    rs.getString("maTaiKhoan"),
                    rs.getString("maNV"),
                    rs.getString("tenDangNhap"),
                    rs.getString("matKhau"),
                    rs.getString("vaiTro")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}