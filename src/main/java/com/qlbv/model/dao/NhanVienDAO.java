package com.qlbv.model.dao;






import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.NhanVien;



public class NhanVienDAO {
    private final ConnectDB db;

    public NhanVienDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themNhanVien(String maNV, String tenNV, String sdt) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemNhanVien(?,?,?)}")) {
            stmt.setString(1, maNV);
            stmt.setString(2, tenNV);
            stmt.setString(3, sdt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaNhanVien(String maNV, String tenNV, String sdt) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaNhanVien(?,?,?)}")) {
            stmt.setString(1, maNV);
            stmt.setString(2, tenNV);
            stmt.setString(3, sdt);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaNhanVien(String maNV) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaNhanVien(?)}")) {
            stmt.setString(1, maNV);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public NhanVien timNhanVien(String maNV) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimNhanVien(?)}")) {
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new NhanVien(rs.getString("maNV"), rs.getString("tenNV"), rs.getString("sdt"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVien> layDanhSachNhanVien() {
        List<NhanVien> dsNhanVien = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachNhanVien}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsNhanVien.add(new NhanVien(rs.getString("maNV"), rs.getString("tenNV"), rs.getString("sdt")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsNhanVien;
    }


}