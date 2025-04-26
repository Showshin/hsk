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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;

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
    
    public HoaDon timHoaDonTheoMaHD(String maHD, List<HoaDon> dsHoaDon){
        if (dsHoaDon == null || maHD == null) {
            return null;
        }

        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaHD().equalsIgnoreCase(maHD)) {
                return hd; // Tìm thấy thì trả về hóa đơn đó
            }
        }

        return null; // Không tìm thấy
    }
    
    public HoaDon timHoaDonTheoMaKH(String MaKH, List<HoaDon> dsHoaDon){
        if (dsHoaDon == null || MaKH == null) {
            return null;
        }

        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaKH().getMaKH().equalsIgnoreCase(MaKH)) {
                return hd; // Tìm thấy thì trả về hóa đơn đó
            }
        }

        return null; // Không tìm thấy
    }
    
    public HoaDon timHoaDonTheoMaNV(String MaNV, List<HoaDon> dsHoaDon){
        if (dsHoaDon == null || MaNV == null) {
            return null;
        }

        for (HoaDon hd : dsHoaDon) {
            if (hd.getMaNV().getMaNV().equalsIgnoreCase(MaNV)) {
                return hd; // Tìm thấy thì trả về hóa đơn đó
            }
        }

        return null; // Không tìm thấy
    }
    
    public List<HoaDon> sapXepTangTheoTien(List<HoaDon> dsHoaDon){
        if (dsHoaDon == null) {
            return null;
        }
        Collections.sort(dsHoaDon, new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon o1, HoaDon o2) {
                return Double.compare(o1.getTongTien(), o2.getTongTien());
            }
        });
        return dsHoaDon;
    }
    
    public List<HoaDon> sapXepGiamTheoTien(List<HoaDon> dsHoaDon){
        if (dsHoaDon == null) {
            return null;
        }
        Collections.sort(dsHoaDon, new Comparator<HoaDon>() {
            @Override
            public int compare(HoaDon o1, HoaDon o2) {
                return Double.compare(o2.getTongTien(), o1.getTongTien());
            }
        });
        return dsHoaDon;
    }
    
    public List<HoaDon> thongKeHomNay(List<HoaDon> dsHoaDon) {
        List<HoaDon> ketQua = new ArrayList<>();
        LocalDate homNay = LocalDate.now();

        for (HoaDon hd : dsHoaDon) {
            LocalDate ngayHD = ((java.sql.Date) hd.getNgayTao()).toLocalDate();
            if (ngayHD.isEqual(homNay)) {
                ketQua.add(hd);
            }
        }

        return ketQua;
    }
    
    public List<HoaDon> thongKeHomQua(List<HoaDon> dsHoaDon) {
        List<HoaDon> ketQua = new ArrayList<>();
        LocalDate homQua = LocalDate.now().minusDays(1);

        for (HoaDon hd : dsHoaDon) {
            LocalDate ngayHD = ((java.sql.Date) hd.getNgayTao()).toLocalDate(); 
            if (ngayHD.isEqual(homQua)) {
                ketQua.add(hd);
            }
        }

        return ketQua;
    }
    
    public List<HoaDon> thongKeThangNay(List<HoaDon> dsHoaDon) {
        List<HoaDon> ketQua = new ArrayList<>();
        LocalDate ngayHienTai = LocalDate.now(); // Ngày hôm nay
        int thangHienTai = ngayHienTai.getMonthValue(); // Lấy tháng hiện tại
        int namHienTai = ngayHienTai.getYear(); // Lấy năm hiện tại

        for (HoaDon hd : dsHoaDon) {
            LocalDate ngayHD = ((java.sql.Date) hd.getNgayTao()).toLocalDate(); // chuyển sang LocalDate
            if (ngayHD.getMonthValue() == thangHienTai && ngayHD.getYear() == namHienTai) {
                ketQua.add(hd); // nếu cùng tháng và cùng năm thì thêm vào
            }
        }

        return ketQua;
    }
    
    public List<HoaDon> thongKeThangTruoc(List<HoaDon> dsHoaDon) {
        List<HoaDon> ketQua = new ArrayList<>();
        LocalDate ngayHienTai = LocalDate.now();
        LocalDate thangTruoc = ngayHienTai.minusMonths(1); // Lùi về 1 tháng trước
        int thang = thangTruoc.getMonthValue();
        int nam = thangTruoc.getYear();

        for (HoaDon hd : dsHoaDon) {
            LocalDate ngayHD = ((java.sql.Date) hd.getNgayTao()).toLocalDate();
            if (ngayHD.getMonthValue() == thang && ngayHD.getYear() == nam) {
                ketQua.add(hd);
            }
        }

        return ketQua;
    }
    
    public List<HoaDon> thongKeTheoNam(List<HoaDon> dsHoaDon, int namCanTim) {
        List<HoaDon> ketQua = new ArrayList<>();

        for (HoaDon hd : dsHoaDon) {
            LocalDate ngayHD = ((java.sql.Date) hd.getNgayTao()).toLocalDate();
            if (ngayHD.getYear() == namCanTim) {
                ketQua.add(hd);
            }
        }

        return ketQua;
    }
    
    public double tinhDoanhThu(List<HoaDon> dsHoaDon){
        double tongTien = 0;
        for (HoaDon hoaDon : dsHoaDon) {
            tongTien += hoaDon.getTongTien();
        }
        return tongTien;
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
