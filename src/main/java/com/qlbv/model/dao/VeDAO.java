package com.qlbv.model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.Ghe;
import com.qlbv.model.entities.LichChieu;
import com.qlbv.model.entities.Ve;

public class VeDAO {
    private final ConnectDB db;

    public VeDAO() {
        db = ConnectDB.getInstance();
    }

    //lay tat ca ve
    public List<Ve> layDanhSachVe() {
        List<Ve> dsVe = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachVe}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsVe.add(new Ve(rs.getString("maVe"), new Ghe(rs.getString("maGhe")), new LichChieu(rs.getString("maLichChieu")), rs.getDouble("gia")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsVe;
    }

    public boolean themVe(String maVe, String maGhe, String maLichChieu, double gia) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemVe(?,?,?,?)}")) {
            stmt.setString(1, maVe);
            stmt.setString(2, maGhe);
            stmt.setString(3, maLichChieu);
            stmt.setDouble(4, gia);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaVe(String maVe, int trangThai) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaVe(?,?)}")) {
            stmt.setString(1, maVe);
            stmt.setInt(2, trangThai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaVe(String maVe) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaVe(?)}")) {
            stmt.setString(1, maVe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ve> layDanhSachVeTheoLichChieu(String maLichChieu) {
        List<Ve> dsVe = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachVeTheoLichChieu(?)}")) {
            stmt.setString(1, maLichChieu);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsVe.add(new Ve(
                    rs.getString("maVe"),
                    new Ghe(rs.getString("maGhe")),
                    new LichChieu( rs.getString("maLichChieu")),
                    rs.getDouble("gia")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsVe;
    }

    public double tinhGiaVe(String maGhe, String maPhim) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TinhGiaVe(?,?)}")) {
            stmt.setString(1, maGhe);
            stmt.setString(2, maPhim);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("giaVe");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}