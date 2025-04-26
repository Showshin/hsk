package com.qlbv.model.entities;

import java.util.Objects;

public class Phong {
    private String maPhong, tenPhong;
    private int sucChua;

    /**
     * @param maPhong mã phòng 
     * @param tenPhong tên phòng 
     * @param sucChua sức chứa
     */
    
     public Phong(String maPhong, String tenPhong, int sucChua) {
        this.maPhong = maPhong;
        setSucChua(sucChua);
        setTenPhong(tenPhong);
    }

     
     
     
     
     
    public Phong(String maPhong) {
		super();
		this.maPhong = maPhong;
	}






	public String getMaPhong() {
        return maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.maPhong);
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
        final Phong other = (Phong) obj;
        return Objects.equals(this.maPhong, other.maPhong);
    }
    
    
}
