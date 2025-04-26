package com.qlbv.model.entities;

import java.util.Objects;

public class LoaiPhim {
    private String maLoai, tenLoai;

    /**
     * @param maLoai mã loại
     * @param tenLoai tên loại
     */

    public LoaiPhim(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        setTenLoai(tenLoai);
    }
    

    public LoaiPhim(String maLoai) {
		super();
		this.maLoai = maLoai;
	}


	public String getMaLoai() {
        return maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.maLoai);
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
        final LoaiPhim other = (LoaiPhim) obj;
        return Objects.equals(this.maLoai, other.maLoai);
    }
    
    
}
