package com.qlbv.model.entities;



import java.util.Objects;

public class Ghe {
    private String maGhe, hangGhe;
    private Phong maPhong;
    private double heSo;
    private int trangThai; // 0: trống, 1: đã đặt, 2: gặp vấn đề

    /**
     * @param maGhe mã ghế 
     * @param maPhong phòng 
     * @param hangGhe hàng ghế
     * @param heSo hệ số
     * @param trangThai trạng thái (0: trống, 1: đã đặt, 2: gặp vấn đề)
     */

    public Ghe(String maGhe, Phong maPhong, String hangGhe, double heSo, int trangThai) {
        this.maGhe = maGhe;
        setMaPhong(maPhong);
        setHangGhe(hangGhe);
        setHeSo(heSo);
        setTrangThai(trangThai);
    }
    
    

    public Ghe(String maGhe) {
		super();
		this.maGhe = maGhe;
	}



	public String getMaGhe() {
        return maGhe;
    }

    public String getHangGhe() {
        return hangGhe;
    }

    public void setHangGhe(String hangGhe) {
        this.hangGhe = hangGhe;
    }

    public Phong getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Phong maPhong) {
        this.maPhong = maPhong;
    }

    public double getHeSo() {
        return heSo;
    }

    public void setHeSo(double heSo) {
        this.heSo = heSo;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    public boolean isTrangThai() {
        return trangThai == 1;
    }
    
    public boolean isGheLoi() {
        return trangThai == 2;
    }
    
    public boolean isGheTrong() {
        return trangThai == 0;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 22 * hash + Objects.hashCode(this.maGhe);
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
        final Ghe other = (Ghe) obj;
        return Objects.equals(this.maGhe, other.maGhe);
    }
    
    
}
