package com.qlbv.model.entities;
import java.util.Objects;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String sdt;

    /**     
     * @param maKH mã khách hàng
     * @param tenKH tên khách hàng 
     * @param sdt số điện thoại
     */
    
    public KhachHang(String maKH, String tenKH, String sdt) {
        this.maKH = maKH;
        setTenKH(tenKH);
        setSdt(sdt);
    }
    
    

    public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}



	public String getMaKH() {
        return maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.maKH);
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
        final KhachHang other = (KhachHang) obj;
        return Objects.equals(this.maKH, other.maKH);
    }
    
    
}
