package com.qlbv.model.entities;
import java.util.Objects;

public class NhanVien {
    private String maNV, tenNV, sdt;

    /**
     * @param maNV mã nhân viên
     * @param tenNV tên nhân viên
     * @param sdt số điện thoại
     */
    
    public NhanVien(String maNV, String tenNV, String sdt) {
        this.maNV = maNV;
        setTenNV(tenNV);
        setSdt(sdt);
    }
    
    public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}


    public String getMaNV() {
        return maNV;
    }


	public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.maNV);
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
        final NhanVien other = (NhanVien) obj;
        return Objects.equals(this.maNV, other.maNV);
    }
    
    
}
