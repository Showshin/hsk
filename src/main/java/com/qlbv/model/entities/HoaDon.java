package com.qlbv.model.entities;



import java.util.Date;
import java.util.Objects;

public class HoaDon {
    private String maHD;
    private KhachHang maKH;
    private NhanVien maNV;
    private Date ngayTao;
    private double tongTien;


    
     public HoaDon(String maHD, KhachHang maKH, NhanVien maNV, Date ngayTao, double tongTien) {
        this.maHD = maHD;
        setMaKH(maKH);
        setMaNV(maNV);
        setNgayTao(ngayTao);
        setTongTien(tongTien);
    }

     
   
     
    public HoaDon(String maHD) {
		super();
		this.maHD = maHD;
	}




	public String getMaHD() {
        return maHD;
    }

    public KhachHang getMaKH() {
        return maKH;
    }

    public void setMaKH(KhachHang maKH) {
        this.maKH = maKH;
    }

    public NhanVien getMaNV() {
        return maNV;
    }

    public void setMaNV(NhanVien maNV) {
        this.maNV = maNV;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.maHD);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final HoaDon other = (HoaDon) obj;
        return Objects.equals(this.maHD, other.maHD);
    }
    
    
}
