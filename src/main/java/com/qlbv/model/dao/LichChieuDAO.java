package com.qlbv.model.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.LichChieu;
import com.qlbv.model.entities.LoaiPhim;
import com.qlbv.model.entities.Phim;
import com.qlbv.model.entities.Phong;



public class LichChieuDAO {
    private final ConnectDB db;

    public LichChieuDAO() {
        db = ConnectDB.getInstance();
    }

    // Thêm một lịch chiếu mới vào cơ sở dữ liệu
    public boolean themLichChieu(String maLichChieu, String maPhim, String maPhong, Timestamp thoiGianBD, Timestamp thoiGianKT) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemLichChieu(?,?,?,?,?)}")) {
            stmt.setString(1, maLichChieu);
            stmt.setString(2, maPhim);
            stmt.setString(3, maPhong);
            stmt.setTimestamp(4, thoiGianBD);
            stmt.setTimestamp(5, thoiGianKT);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin lịch chiếu
    public boolean suaLichChieu(String maLichChieu, String maPhim, String maPhong, Timestamp thoiGianBD, Timestamp thoiGianKT) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaLichChieu(?,?,?,?,?)}")) {
            stmt.setString(1, maLichChieu);
            stmt.setString(2, maPhim);
            stmt.setString(3, maPhong);
            stmt.setTimestamp(4, thoiGianBD);
            stmt.setTimestamp(5, thoiGianKT);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một lịch chiếu
    public boolean xoaLichChieu(String maLichChieu) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaLichChieu(?)}")) {
            stmt.setString(1, maLichChieu);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách tất cả lịch chiếu
    public List<LichChieu> layDanhSachLichChieu() {
        List<LichChieu> dsLichChieu = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachLichChieu}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsLichChieu.add(new LichChieu(
                    rs.getString("maLichChieu"),
                    new Phim(rs.getString("maPhim")),
                    new Phong(rs.getString("maPhong")),
                    rs.getTimestamp("thoiGianBD"),
                    rs.getTimestamp("thoiGianKT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLichChieu;
    }

    // Lấy danh sách lịch chiếu theo mã phim
    public List<LichChieu> layLichChieuTheoPhim(String maPhim) {
        List<LichChieu> dsLichChieu = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayLichChieuTheoPhim(?)}")) {
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsLichChieu.add(new LichChieu(
                    rs.getString("maLichChieu"),
                    new Phim(rs.getString("maPhim")),
                    new Phong(rs.getString("maPhong")),
                    rs.getTimestamp("thoiGianBD"),
                    rs.getTimestamp("thoiGianKT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLichChieu;
    }

    // Lấy danh sách lịch chiếu theo ngày
    public List<LichChieu> layLichChieuTheoNgay(Timestamp ngay) {
        List<LichChieu> dsLichChieu = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayLichChieuTheoNgay(?)}")) {
            stmt.setTimestamp(1, ngay);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsLichChieu.add(new LichChieu(
                    rs.getString("maLichChieu"),
                    new Phim(rs.getString("maPhim")),
                    new Phong(rs.getString("maPhong")),
                    rs.getTimestamp("thoiGianBD"),
                    rs.getTimestamp("thoiGianKT")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLichChieu;
    }

    public List<Phim> layPhimCoSoVeNhieuNhat() {
        List<Phim> dsPhim = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayPhimCoSoVeNhieuNhat}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Phim phim = new Phim(
                    rs.getString("maPhim"),
                    rs.getString("tenPhim"),
                    rs.getInt("thoiLuong"),
                    rs.getInt("gioiHanTuoi"),
                    rs.getDouble("giaGoc"),
                    new LoaiPhim(rs.getString("maLoai")),
                    rs.getString("img"),
                    rs.getInt("soVe")
                );
                dsPhim.add(phim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsPhim;
    }

    //Tìm lịch chiếu theo mã lịch chiếu
    public LichChieu timLichChieuTheoMa(String maLichChieu) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_TimLichChieuTheoMa(?)}")) {
            stmt.setString(1, maLichChieu);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new LichChieu(
                    rs.getString("maLichChieu"),
                    new Phim(rs.getString("maPhim")),
                    new Phong(rs.getString("maPhong")),
                    rs.getTimestamp("thoiGianBD"),
                    rs.getTimestamp("thoiGianKT")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}