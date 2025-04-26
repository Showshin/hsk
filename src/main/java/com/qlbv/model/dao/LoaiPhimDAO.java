package com.qlbv.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qlbv.model.database.ConnectDB;
import com.qlbv.model.entities.LoaiPhim;



public class LoaiPhimDAO {
    private final ConnectDB db;

    public LoaiPhimDAO() {
        db = ConnectDB.getInstance();
    }

    public boolean themLoaiPhim(String maLoai, String tenLoai) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_ThemLoaiPhim(?,?)}")) {
            stmt.setString(1, maLoai);
            stmt.setString(2, tenLoai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean suaLoaiPhim(String maLoai, String tenLoai) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_SuaLoaiPhim(?,?)}")) {
            stmt.setString(1, maLoai);
            stmt.setString(2, tenLoai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaLoaiPhim(String maLoai) {
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_XoaLoaiPhim(?)}")) {
            stmt.setString(1, maLoai);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LoaiPhim> layDanhSachLoaiPhim() {
        List<LoaiPhim> dsLoaiPhim = new ArrayList<>();
        try (Connection con = db.getConnection();
             PreparedStatement stmt = con.prepareStatement("{CALL sp_LayDanhSachLoaiPhim}")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dsLoaiPhim.add(new LoaiPhim(rs.getString("maLoai"), rs.getString("tenLoai")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsLoaiPhim;
    }


}