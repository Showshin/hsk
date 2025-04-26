package com.qlbv.model.entities;



import java.util.Date;
import java.util.Objects;

public class LichChieu {
    private String maLichChieu;
    private Phim maPhim;
    private Phong maPhong;
    private Date thoiGianBD, thoiGianKT;

    /**
     * @param maLichChieu mã lịch chiếu
     * @param maPhim phim
     * @param maPhong phòng
     * @param thoiGianBD thời gian đặt bàn
     * @param thoiGianKT thời gian kiểm tra
     */
    
     public LichChieu(String maLichChieu, Phim maPhim, Phong maPhong, Date thoiGianBD, Date thoiGianKT) {
        this.maLichChieu = maLichChieu;
        setMaPhim(maPhim);
        setMaPhong(maPhong);
        setThoiGianBD(thoiGianBD);
        setThoiGianKT(thoiGianKT);
    }

     
     
     
    public LichChieu(String maLichChieu) {
		super();
		this.maLichChieu = maLichChieu;
	}




	public String getMaLichChieu() {
        return maLichChieu;
    }

    public Phim getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(Phim maPhim) {
        this.maPhim = maPhim;
    }

    public Phong getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(Phong maPhong) {
        this.maPhong = maPhong;
    }

    public Date getThoiGianBD() {
        return thoiGianBD;
    }

    public void setThoiGianBD(Date thoiGianBD) {
        this.thoiGianBD = thoiGianBD;
    }

    public Date getThoiGianKT() {
        return thoiGianKT;
    }

    public void setThoiGianKT(Date thoiGianKT) {
        this.thoiGianKT = thoiGianKT;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.maLichChieu);
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
        final LichChieu other = (LichChieu) obj;
        return Objects.equals(this.maLichChieu, other.maLichChieu);
    }
    
    
}
