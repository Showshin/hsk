package com.qlbv.model.entities;



import java.util.Objects;

public class TaiKhoanNhanVien {
    private String maTaiKhoan;
    private String maNV;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;

    /**
     * @param maTaiKhoan mã tài khoản
     * @param maNV mã nhân viên
     * @param tenDangNhap tên đăng nhập
     * @param matKhau mật khẩu
     * @param vaiTro vai trò (Quản lý, nhân viên bán vé, v.v.)
     */
    
    public TaiKhoanNhanVien(String maTaiKhoan, String maNV, String tenDangNhap, String matKhau, String vaiTro) {
        this.maTaiKhoan = maTaiKhoan;
        this.maNV = maNV;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTaiKhoan);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TaiKhoanNhanVien other = (TaiKhoanNhanVien) obj;
        return Objects.equals(this.maTaiKhoan, other.maTaiKhoan);
    }

    @Override
    public String toString() {
        return "TaiKhoanNhanVien{" + "maTaiKhoan=" + maTaiKhoan + ", maNV=" + maNV + ", tenDangNhap=" + tenDangNhap + ", vaiTro=" + vaiTro + '}';
    }
} 