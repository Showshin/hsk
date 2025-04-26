package com.qlbv.model.entities;



public class ChiTietHD {
    private HoaDon hd;
    private Ve maVe;

    public HoaDon getHd() {
        return hd;
    }

    public void setHd(HoaDon hd) {
        this.hd = hd;
    }

    public Ve getMaVe() {
        return maVe;
    }

    public void setMaVe(Ve maVe) {
        this.maVe = maVe;
    }

    public ChiTietHD(HoaDon hd, Ve maVe) {
        setHd(hd);
        setMaVe(maVe);
    }
}
