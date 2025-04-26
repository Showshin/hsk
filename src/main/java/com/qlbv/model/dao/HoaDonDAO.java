package com.qlbv.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.HoaDon;
import com.qlbv.model.entities.KhachHang;
import com.qlbv.model.entities.NhanVien;

public class HoaDonDAO {
    private final ConnectDB db;

    public HoaDonDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themHoaDon(String maHD, String maKH, String maNV, Date ngayTao, double tongTien) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemHoaDon(?,?,?,?,?)}")) {
            stmt.setString(1, maHD);
            stmt.setString(2, maKH);
            stmt.setString(3, maNV);
            stmt.setDate(4, ngayTao);
            stmt.setDouble(5, tongTien);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaHoaDon(String maHD, String maKH, String maNV, Date ngayTao, double tongTien) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaHoaDon(?,?,?,?,?)}")) {
            stmt.setString(1, maHD);
            stmt.setString(2, maKH);
            stmt.setString(3, maNV);
            stmt.setDate(4, ngayTao);
            stmt.setDouble(5, tongTien);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaHoaDon(String maHD) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaHoaDon(?)}")) {
            stmt.setString(1, maHD);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<HoaDon> layDanhSachHoaDon() {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachHoaDon}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsHoaDon.add(new HoaDon(
                    rs.getString("maHD"),
                    new KhachHang(rs.getString("maKH")),
                    new NhanVien(rs.getString("maNV")),
                    rs.getDate("ngayTao"),
                    rs.getDouble("tongTien")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsHoaDon;
    }

    public double layDoanhThuHienTai() {
        try (Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDoanhThu}")) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("doanhThu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
