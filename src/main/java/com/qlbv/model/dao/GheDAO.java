package com.qlbv.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.Ghe;
import com.qlbv.model.entities.Phong;

public class GheDAO {
    private final ConnectDB db;

    public GheDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themGhe(String maGhe, String maPhong, String hangGhe, double heSo, int trangThai) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemGhe(?,?,?,?,?)}")) {
            stmt.setString(1, maGhe);
            stmt.setString(2, maPhong);
            stmt.setString(3, hangGhe);
            stmt.setDouble(4, heSo);
            stmt.setInt(5, trangThai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaGhe(String maGhe, String maPhong, String hangGhe, double heSo, int trangThai) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaGhe(?,?,?,?,?)}")) {
            stmt.setString(1, maGhe);
            stmt.setString(2, maPhong);
            stmt.setString(3, hangGhe);
            stmt.setDouble(4, heSo);
            stmt.setInt(5, trangThai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaGhe(String maGhe) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaGhe(?)}")) {
            stmt.setString(1, maGhe);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Ghe> layDanhSachGheTheoPhong(String maPhong) {
        List<Ghe> dsGhe = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachGheTheoPhong(?)}")) {
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsGhe.add(new Ghe(
                    rs.getString("maGhe"),
                    new Phong(rs.getString("maPhong")),
                    rs.getString("hangGhe"),
                    rs.getDouble("heSo"),
                    rs.getInt("trangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsGhe;
    }

    public Ghe timGheTheoMa(String maGhe) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimGheTheoMa(?)}")) {
            stmt.setString(1, maGhe);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ghe(
                    rs.getString("maGhe"),
                    new Phong(rs.getString("maPhong")),
                    rs.getString("hangGhe"),
                    rs.getDouble("heSo"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ghe> timGheTheoMaPhong(String maPhong) {
        List<Ghe> dsGhe = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimGheTheoMaPhong(?)}")) {
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsGhe.add(new Ghe(
                    rs.getString("maGhe"),
                    new Phong(rs.getString("maPhong")),
                    rs.getString("hangGhe"),
                    rs.getDouble("heSo"),
                    rs.getInt("trangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsGhe;
    }

    public Ghe kiemTraTrangThaiGhe(String maGhe, String maLichChieu) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_KiemTraTrangThaiGhe(?,?)}")) {
            stmt.setString(1, maGhe);
            stmt.setString(2, maLichChieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ghe(rs.getString("maGhe"), null, null, 0, rs.getInt("trangThai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}