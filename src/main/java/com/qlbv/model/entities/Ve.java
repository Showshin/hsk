package com.qlbv.model.entities;



import java.util.Objects;



public class Ve {
	
    private String maVe;
    private Ghe maGhe;
    private LichChieu maLichChieu; 
    private double gia;
    
    
    
	public Ve(String maVe, Ghe maGhe, LichChieu maLichChieu, double gia) {
		super();
		this.maVe = maVe;
		this.maGhe = maGhe;
		this.maLichChieu = maLichChieu;
		this.gia = gia;
	}
	

	
	
	public Ve(String maVe) {
		super();
		this.maVe = maVe;
	}

	public String getMaVe() {
		return maVe;
	}
	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}
	public Ghe getMaGhe() {
		return maGhe;
	}
	public void setMaGhe(Ghe maGhe) {
		this.maGhe = maGhe;
	}
	public LichChieu getMaLichChieu() {
		return maLichChieu;
	}
	public void setMaLichChieu(LichChieu maLichChieu) {
		this.maLichChieu = maLichChieu;
	}
	public double getGia() {
		return gia;
	}
	public void setGia(double gia) {
		this.gia = gia;
	}
	@Override
	public int hashCode() {
		return Objects.hash(maVe);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ve other = (Ve) obj;
		return Objects.equals(maVe, other.maVe);
	}
	
	
	

    
}
