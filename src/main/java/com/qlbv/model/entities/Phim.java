package com.qlbv.model.entities;
import java.util.Objects;

public class Phim {
    private String maPhim, tenPhim;
    private int thoiLuong, gioiHanTuoi;
    private double giaGoc;
    private LoaiPhim loaiPhim;
    private String img;
    private int soVe;
	public Phim(String maPhim, String tenPhim, int thoiLuong, int gioiHanTuoi, double giaGoc, LoaiPhim loaiPhim,
			String img) {
		super();
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.thoiLuong = thoiLuong;
		this.gioiHanTuoi = gioiHanTuoi;
		this.giaGoc = giaGoc;
		this.loaiPhim = loaiPhim;
		this.img = img;
		this.soVe = 0;
	}
	
	public Phim(String maPhim, String tenPhim, int thoiLuong, int gioiHanTuoi, double giaGoc, LoaiPhim loaiPhim,
			String img, int soVe) {
		super();
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.thoiLuong = thoiLuong;
		this.gioiHanTuoi = gioiHanTuoi;
		this.giaGoc = giaGoc;
		this.loaiPhim = loaiPhim;
		this.img = img;
		this.soVe = soVe;
	}
	
	public Phim(String maPhim) {
		super();
		this.maPhim = maPhim;
	}
	public String getMaPhim() {
		return maPhim;
	}
	public void setMaPhim(String maPhim) {
		this.maPhim = maPhim;
	}
	public String getTenPhim() {
		return tenPhim;
	}
	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}
	public int getThoiLuong() {
		return thoiLuong;
	}
	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}
	public int getGioiHanTuoi() {
		return gioiHanTuoi;
	}
	public void setGioiHanTuoi(int gioiHanTuoi) {
		this.gioiHanTuoi = gioiHanTuoi;
	}
	public double getGiaGoc() {
		return giaGoc;
	}
	public void setGiaGoc(double giaGoc) {
		this.giaGoc = giaGoc;
	}
	public LoaiPhim getLoaiPhim() {
		return loaiPhim;
	}
	public void setLoaiPhim(LoaiPhim loaiPhim) {
		this.loaiPhim = loaiPhim;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getSoVe() {
		return soVe;
	}
	public void setSoVe(int soVe) {
		this.soVe = soVe;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maPhim);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phim other = (Phim) obj;
		return Objects.equals(maPhim, other.maPhim);
	}
}
