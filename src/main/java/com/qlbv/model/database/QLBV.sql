CREATE DATABASE QLBanVe;
GO
USE QLBanVe;
GO
CREATE TABLE KhachHang (
    maKH nvarchar(50) PRIMARY KEY,
    tenKH nvarchar(50),
    sdt nvarchar(50)
);

CREATE TABLE NhanVien (
    maNV nvarchar(50) PRIMARY KEY,
    tenNV nvarchar(50),
    sdt nvarchar(50)
);

CREATE TABLE TaiKhoanNhanVien (
    maTaiKhoan nvarchar(50) PRIMARY KEY,
    maNV nvarchar(50) NOT NULL,
    tenDangNhap nvarchar(50) NOT NULL,
    matKhau nvarchar(50) NOT NULL,
    vaiTro nvarchar(50), -- Quản lý, nhân viên bán vé, v.v.
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);

CREATE TABLE LoaiPhim (
    maLoai nvarchar(50) PRIMARY KEY,
    tenLoai nvarchar(50) NOT NULL
);

CREATE TABLE Phim (
    maPhim nvarchar(50) PRIMARY KEY,
    maLoai nvarchar(50) NOT NULL,
    tenPhim nvarchar(50) NOT NULL,
    thoiLuong int,
    gioiHanTuoi int,
    giaGoc real,
    img nvarchar(255), -- Thêm trường img để lưu đường dẫn ảnh
    FOREIGN KEY (maLoai) REFERENCES LoaiPhim(maLoai)
);

CREATE TABLE Phong (
    maPhong nvarchar(50) PRIMARY KEY,
    tenPhong nvarchar(50) NOT NULL,
    sucChua int
);

CREATE TABLE Ghe (
    maGhe nvarchar(50) PRIMARY KEY,
    maPhong nvarchar(50) NOT NULL,
    hangGhe nvarchar(50),
    heSo real,
    trangThai int,
    FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);

CREATE TABLE LichChieu (
    maLichChieu nvarchar(50) PRIMARY KEY,
    maPhim nvarchar(50) NOT NULL,
    maPhong nvarchar(50) NOT NULL,
    thoiGianBD datetime,
    thoiGianKT datetime,
    FOREIGN KEY (maPhim) REFERENCES Phim(maPhim),
    FOREIGN KEY (maPhong) REFERENCES Phong(maPhong)
);

CREATE TABLE Ve (
    maVe nvarchar(50) PRIMARY KEY,
    maGhe nvarchar(50),
    maLichChieu nvarchar(50), -- Thêm để biết vé thuộc lịch chiếu nào
    gia real,
    FOREIGN KEY (maGhe) REFERENCES Ghe(maGhe),
    FOREIGN KEY (maLichChieu) REFERENCES LichChieu(maLichChieu)
);

CREATE TABLE HoaDon (
    maHD nvarchar(50) PRIMARY KEY,
    maKH nvarchar(50) NOT NULL,
    maNV nvarchar(50) NOT NULL,
    ngayTao date,
    tongTien real,
    FOREIGN KEY (maKH) REFERENCES KhachHang(maKH),
    FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);

CREATE TABLE ChiTietHD (
    maHD nvarchar(50),
    maVe nvarchar(50),
    PRIMARY KEY (maHD, maVe),
    FOREIGN KEY (maHD) REFERENCES HoaDon(maHD),
    FOREIGN KEY (maVe) REFERENCES Ve(maVe)
);

-- Dữ liệu mẫu
INSERT INTO KhachHang (maKH, tenKH, sdt) VALUES 
('KH01', N'Trần Thị X', '0965432187'),
('KH02', N'Phùng Văn Y', '0978123456'),
('KH03', N'Đỗ Thị Z', '0945566778');

INSERT INTO NhanVien (maNV, tenNV, sdt) VALUES 
('NV01', N'Nguyễn Văn A', '0912345678'),
('NV02', N'Lê Thị B', '0987654321'),
('NV03', N'Phạm Văn C', '0901122334');

INSERT INTO TaiKhoanNhanVien (maTaiKhoan, maNV, tenDangNhap, matKhau, vaiTro) VALUES 
('TK01', 'NV01', 'nhanvien1', 'pass123', 'QuanLy'),
('TK02', 'NV02', 'nhanvien2', 'pass456', 'NhanVienBanVe'),
('TK03', 'NV03', 'nhanvien3', 'pass789', 'NhanVienBanVe');

INSERT INTO LoaiPhim (maLoai, tenLoai) VALUES 
('L01', N'Hành động'),
('L02', N'Kinh dị'),
('L03', N'Hài hước');

INSERT INTO Phim (maPhim, maLoai, tenPhim, thoiLuong, gioiHanTuoi, giaGoc, img) VALUES 
('P001', 'L01', N'Đại chiến người khổng lồ', 120, 16, 100000, 'resources/images/phim/1.png'),
('P002', 'L02', N'Đặc vụ bóng ma', 90, 18, 80000, 'resources/images/phim/2.png'),
('P003', 'L03', N'The smile have left your eyes', 110, 13, 90000, 'resources/images/phim/3.png'),
('P004', 'L01', N'Biệt đội cảm tử', 130, 16, 110000, 'resources/images/phim/4.png'),
('P005', 'L01', N'Tốc độ kinh hoàng', 115, 13, 95000, 'resources/images/phim/5.png'),
('P006', 'L02', N'Lời nguyền số mệnh', 100, 18, 85000, 'resources/images/phim/6.png'),
('P007', 'L02', N'Nhà trọ lúc nửa đêm', 95, 18, 80000, 'resources/images/phim/7.png'),
('P008', 'L02', N'Con mắt âm dương', 105, 16, 88000, 'resources/images/phim/8.png'),
('P009', 'L03', N'Ông bố đối đầu', 100, 10, 75000, 'resources/images/phim/9.png'),
('P010', 'L03', N'The victim games', 90, 13, 70000, 'resources/images/phim/10.png'),
('P011', 'L03', N'Yêu nhầm bạn thân', 95, 13, 85000, 'resources/images/phim/11.png'),
('P012', 'L01', N'Kẻ săn tiền thưởng', 125, 16, 98000, 'resources/images/phim/12.png'),
('P013', 'L02', N'Thi thể không thể nói', 110, 18, 90000, 'resources/images/phim/13.png');

INSERT INTO Phong (maPhong, tenPhong, sucChua) VALUES 
('R001', N'Phòng A', 120),
('R002', N'Phòng B', 100),
('R003', N'Phòng VIP', 80);

-- ====================== Phòng R001 ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G001', 'R001', 'A1', 1.0, 2), ('G002', 'R001', 'A2', 1.0, 2), ('G003', 'R001', 'A3', 1.0, 2), ('G004', 'R001', 'A4', 1.0, 2),
('G005', 'R001', 'A5', 1.0, 0), ('G006', 'R001', 'A6', 1.0, 0), ('G007', 'R001', 'A7', 1.0, 0),
-- Hàng B (1.0)
('G008', 'R001', 'B1', 1.0, 0), ('G009', 'R001', 'B2', 1.0, 0), ('G010', 'R001', 'B3', 1.0, 0), ('G011', 'R001', 'B4', 1.0, 0),
('G012', 'R001', 'B5', 1.0, 0), ('G013', 'R001', 'B6', 1.0, 0), ('G014', 'R001', 'B7', 1.0, 0),
-- Hàng C (1.0)
('G015', 'R001', 'C1', 1.0, 0), ('G016', 'R001', 'C2', 1.0, 0), ('G017', 'R001', 'C3', 1.0, 0), ('G018', 'R001', 'C4', 1.0, 0),
('G019', 'R001', 'C5', 1.0, 1), ('G020', 'R001', 'C6', 1.0, 0), ('G021', 'R001', 'C7', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G022', 'R001', 'D1', 1.1, 0), ('G023', 'R001', 'D2', 1.1, 1), ('G024', 'R001', 'D3', 1.1, 1), ('G025', 'R001', 'D4', 1.1, 0),
('G026', 'R001', 'D5', 1.1, 0), ('G027', 'R001', 'D6', 1.1, 0), ('G028', 'R001', 'D7', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G029', 'R001', 'E1', 1.2, 0), ('G030', 'R001', 'E2', 1.2, 1), ('G031', 'R001', 'E3', 1.2, 1), ('G032', 'R001', 'E4', 1.2, 0),
('G033', 'R001', 'E5', 1.2, 0), ('G034', 'R001', 'E6', 1.2, 0), ('G035', 'R001', 'E7', 1.2, 0);

-- ====================== Phòng R002 ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G036', 'R002', 'A1', 1.0, 0), ('G037', 'R002', 'A2', 1.0, 0), ('G038', 'R002', 'A3', 1.0, 0), ('G039', 'R002', 'A4', 1.0, 0),
('G040', 'R002', 'A5', 1.0, 0), ('G041', 'R002', 'A6', 1.0, 0), ('G042', 'R002', 'A7', 1.0, 0),
-- Hàng B (1.0)
('G043', 'R002', 'B1', 1.0, 0), ('G044', 'R002', 'B2', 1.0, 0), ('G045', 'R002', 'B3', 1.0, 0), ('G046', 'R002', 'B4', 1.0, 0),
('G047', 'R002', 'B5', 1.0, 0), ('G048', 'R002', 'B6', 1.0, 1), ('G049', 'R002', 'B7', 1.0, 0),
-- Hàng C (1.0)
('G050', 'R002', 'C1', 1.0, 0), ('G051', 'R002', 'C2', 1.0, 0), ('G052', 'R002', 'C3', 1.0, 0), ('G053', 'R002', 'C4', 1.0, 0),
('G054', 'R002', 'C5', 1.0, 0), ('G055', 'R002', 'C6', 1.0, 0), ('G056', 'R002', 'C7', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G057', 'R002', 'D1', 1.1, 0), ('G058', 'R002', 'D2', 1.1, 1), ('G059', 'R002', 'D3', 1.1, 0), ('G060', 'R002', 'D4', 1.1, 0),
('G061', 'R002', 'D5', 1.1, 0), ('G062', 'R002', 'D6', 1.1, 0), ('G063', 'R002', 'D7', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G064', 'R002', 'E1', 1.2, 0), ('G065', 'R002', 'E2', 1.2, 0), ('G066', 'R002', 'E3', 1.2, 1), ('G067', 'R002', 'E4', 1.2, 1),
('G068', 'R002', 'E5', 1.2, 0), ('G069', 'R002', 'E6', 1.2, 0), ('G070', 'R002', 'E7', 1.2, 0);

-- ====================== Phòng R003 (VIP) ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.2, gần màn hình, VIP)
('G071', 'R003', 'A1', 1.2, 0), ('G072', 'R003', 'A2', 1.2, 0), ('G073', 'R003', 'A3', 1.2, 0), ('G074', 'R003', 'A4', 1.2, 0),
('G075', 'R003', 'A5', 1.2, 0), ('G076', 'R003', 'A6', 1.2, 0), ('G077', 'R003', 'A7', 1.2, 0),
-- Hàng B (1.2)
('G078', 'R003', 'B1', 1.2, 0), ('G079', 'R003', 'B2', 1.2, 0), ('G080', 'R003', 'B3', 1.2, 1), ('G081', 'R003', 'B4', 1.2, 0),
('G082', 'R003', 'B5', 1.2, 0), ('G083', 'R003', 'B6', 1.2, 0), ('G084', 'R003', 'B7', 1.2, 0),
-- Hàng C (1.2)
('G085', 'R003', 'C1', 1.2, 0), ('G086', 'R003', 'C2', 1.2, 0), ('G087', 'R003', 'C3', 1.2, 0), ('G088', 'R003', 'C4', 1.2, 0),
('G089', 'R003', 'C5', 1.2, 0), ('G090', 'R003', 'C6', 1.2, 0), ('G091', 'R003', 'C7', 1.2, 0),
-- Hàng D (1.3, vị trí tốt)
('G092', 'R003', 'D1', 1.3, 0), ('G093', 'R003', 'D2', 1.3, 1), ('G094', 'R003', 'D3', 1.3, 1), ('G095', 'R003', 'D4', 1.3, 0),
('G096', 'R003', 'D5', 1.3, 0), ('G097', 'R003', 'D6', 1.3, 0), ('G098', 'R003', 'D7', 1.3, 0),
-- Hàng E (1.4, vị trí tốt nhất)
('G099', 'R003', 'E1', 1.4, 0), ('G100', 'R003', 'E2', 1.4, 1), ('G101', 'R003', 'E3', 1.4, 1), ('G102', 'R003', 'E4', 1.4, 0),
('G103', 'R003', 'E5', 1.4, 0), ('G104', 'R003', 'E6', 1.4, 0), ('G105', 'R003', 'E7', 1.4, 0);

INSERT INTO LichChieu (maLichChieu, maPhim, maPhong, thoiGianBD, thoiGianKT) VALUES 
('LC01', 'P001', 'R001', '2025-04-28 18:00:00', '2025-04-28 20:15:00'), -- Đại chiến thành (120p + 15p nghỉ)
('LC02', 'P002', 'R002', '2025-04-28 20:30:00', '2025-04-28 22:15:00'), -- Bóng ma đêm khuya (90p + 15p nghỉ)
('LC03', 'P003', 'R003', '2025-04-28 14:00:00', '2025-04-28 16:05:00'), -- Tiếng cười rộn rã (110p + 15p nghỉ)
('LC04', 'P004', 'R002', '2025-04-29 14:00:00', '2025-04-29 16:25:00'), -- Biệt đội báo thù (130p + 15p nghỉ)
('LC05', 'P005', 'R001', '2025-04-29 16:30:00', '2025-04-29 18:40:00'), -- Tốc độ tử thần (115p + 15p nghỉ)
('LC06', 'P006', 'R003', '2025-04-29 18:00:00', '2025-04-29 19:55:00'), -- Lời nguyền bóng tối (100p + 15p nghỉ)
('LC07', 'P007', 'R001', '2025-04-29 20:00:00', '2025-04-29 21:50:00'), -- Nhà hoang lúc nửa đêm (95p + 15p nghỉ)
('LC08', 'P008', 'R002', '2025-04-30 18:00:00', '2025-04-30 19:50:00'), -- Mắt quỷ (105p + 15p nghỉ)
('LC09', 'P009', 'R003', '2025-04-30 14:00:00', '2025-04-30 15:55:00'), -- Ông bố tuổi teen (100p + 15p nghỉ)
('LC10', 'P010', 'R001', '2025-04-30 16:30:00', '2025-04-30 18:15:00'), -- Trò chơi khăm bá đạo (90p + 15p nghỉ)
('LC11', 'P011', 'R002', '2025-04-30 20:30:00', '2025-04-30 22:20:00'), -- Yêu nhầm bạn thân (95p + 15p nghỉ)
('LC12', 'P012', 'R003', '2025-05-01 18:00:00', '2025-05-01 20:20:00'), -- Kẻ săn tiền thưởng (125p + 15p nghỉ)
('LC13', 'P013', 'R001', '2025-05-01 14:00:00', '2025-05-01 16:05:00'), -- Thi thể biết nói (110p + 15p nghỉ)
('LC14', 'P001', 'R002', '2025-05-01 16:30:00', '2025-05-01 18:45:00'), -- Đại chiến thành (120p + 15p nghỉ)
('LC15', 'P002', 'R003', '2025-05-02 14:00:00', '2025-05-02 15:45:00'), -- Bóng ma đêm khuya (90p + 15p nghỉ)
('LC16', 'P003', 'R001', '2025-05-02 18:00:00', '2025-05-02 20:05:00'), -- Tiếng cười rộn rã (110p + 15p nghỉ)
('LC17', 'P004', 'R002', '2025-05-02 20:00:00', '2025-05-02 22:25:00'), -- Biệt đội báo thù (130p + 15p nghỉ)
('LC18', 'P005', 'R001', '2025-05-03 14:00:00', '2025-05-03 16:10:00'), -- Tốc độ tử thần (115p + 15p nghỉ)
('LC19', 'P006', 'R003', '2025-05-03 18:00:00', '2025-05-03 19:55:00'), -- Lời nguyền bóng tối (100p + 15p nghỉ)
('LC20', 'P007', 'R002', '2025-05-03 16:30:00', '2025-05-03 18:20:00'), -- Nhà hoang lúc nửa đêm (95p + 15p nghỉ)
('LC21', 'P008', 'R001', '2025-05-04 18:00:00', '2025-05-04 19:50:00'), -- Mắt quỷ (105p + 15p nghỉ)
('LC22', 'P009', 'R003', '2025-05-04 14:00:00', '2025-05-04 15:55:00'), -- Ông bố tuổi teen (100p + 15p nghỉ)
('LC23', 'P010', 'R002', '2025-05-04 16:30:00', '2025-05-04 18:15:00'), -- Trò chơi khăm bá đạo (90p + 15p nghỉ)
('LC24', 'P011', 'R001', '2025-05-05 14:00:00', '2025-05-05 15:50:00'), -- Yêu nhầm bạn thân (95p + 15p nghỉ)
('LC25', 'P012', 'R003', '2025-05-05 18:00:00', '2025-05-05 20:20:00'), -- Kẻ săn tiền thưởng (125p + 15p nghỉ)
('LC26', 'P013', 'R002', '2025-05-05 20:00:00', '2025-05-05 22:05:00'), -- Thi thể biết nói (110p + 15p nghỉ)
('LC27', 'P001', 'R001', '2025-05-06 14:00:00', '2025-05-06 16:15:00'), -- Đại chiến thành (120p + 15p nghỉ)
('LC28', 'P002', 'R003', '2025-05-06 16:30:00', '2025-05-06 18:15:00'), -- Bóng ma đêm khuya (90p + 15p nghỉ)
('LC29', 'P003', 'R002', '2025-05-06 18:00:00', '2025-05-06 20:05:00'); -- Tiếng cười rộn rã (110p + 15p nghỉ)

-- Giá vé = giaGoc (Phim) × heSo (Ghe)
INSERT INTO Ve (maVe, maGhe, maLichChieu, gia) VALUES 
('V001', 'G001', 'LC01', 100000), -- Đã bán
('V002', 'G002', 'LC02', 80000),  -- Đã bán
('V003', 'G010', 'LC03', 108000); -- Đã bán

INSERT INTO HoaDon (maHD, maKH, maNV, ngayTao, tongTien) VALUES 
('HD01', 'KH01', 'NV01', '2025-04-20', 100000),
('HD02', 'KH02', 'NV02', '2025-04-21', 80000),
('HD03', 'KH03', 'NV01', '2025-04-21', 108000);

INSERT INTO ChiTietHD (maHD, maVe) VALUES 
('HD01', 'V001'),
('HD02', 'V002'),
('HD03', 'V003');

GO
-- Stored procedures cho bảng KhachHang
CREATE PROCEDURE sp_ThemKhachHang
    @maKH nvarchar(50),
    @tenKH nvarchar(50),
    @sdt nvarchar(50)
AS
BEGIN
    INSERT INTO KhachHang (maKH, tenKH, sdt)
    VALUES (@maKH, @tenKH, @sdt);
END;
GO

CREATE PROCEDURE sp_SuaKhachHang
    @maKH nvarchar(50),
    @tenKH nvarchar(50),
    @sdt nvarchar(50)
AS
BEGIN
    UPDATE KhachHang
    SET tenKH = @tenKH, sdt = @sdt
    WHERE maKH = @maKH;
END;
GO

CREATE PROCEDURE sp_XoaKhachHang
    @maKH nvarchar(50)
AS
BEGIN
    DELETE FROM KhachHang WHERE maKH = @maKH;
END;
GO

CREATE PROCEDURE sp_TimKhachHang
    @maKH nvarchar(50)
AS
BEGIN
    SELECT * FROM KhachHang WHERE maKH = @maKH;
END;
GO

CREATE PROCEDURE sp_TimKhachHangTheoSDT
    @sdt nvarchar(50)
AS
BEGIN
    SELECT * FROM KhachHang WHERE sdt = @sdt;
END;
GO

CREATE PROCEDURE sp_LayDanhSachKhachHang
AS
BEGIN
    SELECT * FROM KhachHang;
END;
GO

-- Stored procedures cho bảng NhanVien
CREATE PROCEDURE sp_ThemNhanVien
    @maNV nvarchar(50),
    @tenNV nvarchar(50),
    @sdt nvarchar(50)
AS
BEGIN
    INSERT INTO NhanVien (maNV, tenNV, sdt)
    VALUES (@maNV, @tenNV, @sdt);
END;
GO

CREATE PROCEDURE sp_SuaNhanVien
    @maNV nvarchar(50),
    @tenNV nvarchar(50),
    @sdt nvarchar(50)
AS
BEGIN
    UPDATE NhanVien
    SET tenNV = @tenNV, sdt = @sdt
    WHERE maNV = @maNV;
END;
GO

CREATE PROCEDURE sp_XoaNhanVien
    @maNV nvarchar(50)
AS
BEGIN
    DELETE FROM NhanVien WHERE maNV = @maNV;
END;
GO

CREATE PROCEDURE sp_TimNhanVien
    @maNV nvarchar(50)
AS
BEGIN
    SELECT * FROM NhanVien WHERE maNV = @maNV;
END;
GO

CREATE PROCEDURE sp_LayDanhSachNhanVien
AS
BEGIN
    SELECT * FROM NhanVien;
END;
GO

-- Stored procedures cho bảng TaiKhoanNhanVien
CREATE PROCEDURE sp_ThemTaiKhoanNhanVien
    @maTaiKhoan nvarchar(50),
    @maNV nvarchar(50),
    @tenDangNhap nvarchar(50),
    @matKhau nvarchar(50),
    @vaiTro nvarchar(50)
AS
BEGIN
    INSERT INTO TaiKhoanNhanVien (maTaiKhoan, maNV, tenDangNhap, matKhau, vaiTro)
    VALUES (@maTaiKhoan, @maNV, @tenDangNhap, @matKhau, @vaiTro);
END;
GO

CREATE PROCEDURE sp_SuaTaiKhoanNhanVien
    @maTaiKhoan nvarchar(50),
    @tenDangNhap nvarchar(50),
    @matKhau nvarchar(50),
    @vaiTro nvarchar(50)
AS
BEGIN
    UPDATE TaiKhoanNhanVien
    SET tenDangNhap = @tenDangNhap, matKhau = @matKhau, vaiTro = @vaiTro
    WHERE maTaiKhoan = @maTaiKhoan;
END;
GO

CREATE PROCEDURE sp_XoaTaiKhoanNhanVien
    @maTaiKhoan nvarchar(50)
AS
BEGIN
    DELETE FROM TaiKhoanNhanVien WHERE maTaiKhoan = @maTaiKhoan;
END;
GO

CREATE PROCEDURE sp_DangNhap
    @tenDangNhap nvarchar(50),
    @matKhau nvarchar(50)
AS
BEGIN
    SELECT * FROM TaiKhoanNhanVien
    WHERE tenDangNhap = @tenDangNhap AND matKhau = @matKhau;
END;
GO

-- Stored procedures cho bảng LoaiPhim
CREATE PROCEDURE sp_ThemLoaiPhim
    @maLoai nvarchar(50),
    @tenLoai nvarchar(50)
AS
BEGIN
    INSERT INTO LoaiPhim (maLoai, tenLoai)
    VALUES (@maLoai, @tenLoai);
END;
GO

CREATE PROCEDURE sp_SuaLoaiPhim
    @maLoai nvarchar(50),
    @tenLoai nvarchar(50)
AS
BEGIN
    UPDATE LoaiPhim
    SET tenLoai = @tenLoai
    WHERE maLoai = @maLoai;
END;
GO

CREATE PROCEDURE sp_XoaLoaiPhim
    @maLoai nvarchar(50)
AS
BEGIN
    DELETE FROM LoaiPhim WHERE maLoai = @maLoai;
END;
GO

CREATE PROCEDURE sp_LayDanhSachLoaiPhim
AS
BEGIN
    SELECT * FROM LoaiPhim;
END;
GO

-- Stored procedures cho bảng Phim (đã cập nhật với trường img)
CREATE PROCEDURE sp_ThemPhim
    @maPhim nvarchar(50),
    @maLoai nvarchar(50),
    @tenPhim nvarchar(50),
    @thoiLuong int,
    @gioiHanTuoi int,
    @giaGoc real,
    @img nvarchar(255)
AS
BEGIN
    INSERT INTO Phim (maPhim, maLoai, tenPhim, thoiLuong, gioiHanTuoi, giaGoc, img)
    VALUES (@maPhim, @maLoai, @tenPhim, @thoiLuong, @gioiHanTuoi, @giaGoc, @img);
END;
GO

CREATE PROCEDURE sp_SuaPhim
    @maPhim nvarchar(50),
    @maLoai nvarchar(50),
    @tenPhim nvarchar(50),
    @thoiLuong int,
    @gioiHanTuoi int,
    @giaGoc real,
    @img nvarchar(255)
AS
BEGIN
    UPDATE Phim
    SET maLoai = @maLoai, tenPhim = @tenPhim, thoiLuong = @thoiLuong, 
        gioiHanTuoi = @gioiHanTuoi, giaGoc = @giaGoc, img = @img
    WHERE maPhim = @maPhim;
END;
GO

CREATE PROCEDURE sp_XoaPhim
    @maPhim nvarchar(50)
AS
BEGIN

        -- Xóa liên kết trong các bảng con
        DELETE FROM ChiTietHD WHERE maVe IN (SELECT v.maVe FROM Ve v JOIN LichChieu lc ON v.maLichChieu = lc.maLichChieu WHERE lc.maPhim = @maPhim);
        DELETE FROM Ve WHERE maLichChieu IN (SELECT maLichChieu FROM LichChieu WHERE maPhim = @maPhim);
        DELETE FROM LichChieu WHERE maPhim = @maPhim;
        
        -- Xóa phim
        DELETE FROM Phim WHERE maPhim = @maPhim;
        
END;

GO

CREATE PROCEDURE sp_TimPhim
    @maPhim nvarchar(50)
AS
BEGIN
    SELECT * FROM Phim WHERE maPhim = @maPhim;
END;
GO

CREATE PROCEDURE sp_LayDanhSachPhim
AS
BEGIN
    SELECT * FROM Phim;
END;
GO

-- Stored procedures cho bảng Phong
CREATE PROCEDURE sp_ThemPhong
    @maPhong nvarchar(50),
    @tenPhong nvarchar(50),
    @sucChua int
AS
BEGIN
    INSERT INTO Phong (maPhong, tenPhong, sucChua)
    VALUES (@maPhong, @tenPhong, @sucChua);
END;
GO

CREATE PROCEDURE sp_SuaPhong
    @maPhong nvarchar(50),
    @tenPhong nvarchar(50),
    @sucChua int
AS
BEGIN
    UPDATE Phong
    SET tenPhong = @tenPhong, sucChua = @sucChua
    WHERE maPhong = @maPhong;
END;
GO

CREATE PROCEDURE sp_XoaPhong
    @maPhong nvarchar(50)
AS
BEGIN
    DELETE FROM Phong WHERE maPhong = @maPhong;
END;
GO

CREATE PROCEDURE sp_LayDanhSachPhong
AS
BEGIN
    SELECT * FROM Phong;
END;
GO

-- Stored procedures cho bảng Ghe
CREATE PROCEDURE sp_ThemGhe
    @maGhe nvarchar(50),
    @maPhong nvarchar(50),
    @hangGhe nvarchar(50),
    @heSo real,
    @trangThai bit
AS
BEGIN
    INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai)
    VALUES (@maGhe, @maPhong, @hangGhe, @heSo, @trangThai);
END;
GO

CREATE PROCEDURE sp_SuaGhe
    @maGhe nvarchar(50),
    @maPhong nvarchar(50),
    @hangGhe nvarchar(50),
    @heSo real,
    @trangThai bit
AS
BEGIN
    UPDATE Ghe
    SET hangGhe = @hangGhe, heSo = @heSo, trangThai = @trangThai
    WHERE maGhe = @maGhe AND maPhong = @maPhong;
END;
GO

CREATE PROCEDURE sp_XoaGhe
    @maGhe nvarchar(50)
AS
BEGIN
    DELETE FROM Ghe WHERE maGhe = @maGhe;
END;
GO

CREATE PROCEDURE sp_LayDanhSachGheTheoPhong
    @maPhong nvarchar(50)
AS
BEGIN
    SELECT * FROM Ghe WHERE maPhong = @maPhong;
END;
GO

CREATE PROCEDURE sp_TimGheTheoMa
    @maGhe nvarchar(50)
AS
BEGIN
    SELECT * FROM Ghe WHERE maGhe = @maGhe;
END;
GO

CREATE PROCEDURE sp_TimGheTheoMaPhong
    @maPhong nvarchar(50)
AS
BEGIN
    SELECT * FROM Ghe WHERE maPhong = @maPhong;
END;
GO

CREATE PROCEDURE sp_KiemTraTrangThaiGhe
    @maGhe nvarchar(50),
    @maLichChieu nvarchar(50)
AS
BEGIN
    SELECT g.maGhe, g.trangThai
    FROM Ghe g
    LEFT JOIN Ve v ON g.maGhe = v.maGhe AND v.maLichChieu = @maLichChieu
    WHERE g.maGhe = @maGhe;
END;
GO

-- Stored procedures cho bảng LichChieu
CREATE PROCEDURE sp_ThemLichChieu
    @maLichChieu nvarchar(50),
    @maPhim nvarchar(50),
    @maPhong nvarchar(50),
    @thoiGianBD datetime,
    @thoiGianKT datetime
AS
BEGIN
    INSERT INTO LichChieu (maLichChieu, maPhim, maPhong, thoiGianBD, thoiGianKT)
    VALUES (@maLichChieu, @maPhim, @maPhong, @thoiGianBD, @thoiGianKT);
END;
GO

CREATE PROCEDURE sp_SuaLichChieu
    @maLichChieu nvarchar(50),
    @maPhim nvarchar(50),
    @maPhong nvarchar(50),
    @thoiGianBD datetime,
    @thoiGianKT datetime
AS
BEGIN
    UPDATE LichChieu
    SET maPhim = @maPhim, maPhong = @maPhong, thoiGianBD = @thoiGianBD, thoiGianKT = @thoiGianKT
    WHERE maLichChieu = @maLichChieu;
END;
GO

CREATE PROCEDURE sp_XoaLichChieu
    @maLichChieu nvarchar(50)
AS
BEGIN
    DELETE FROM LichChieu WHERE maLichChieu = @maLichChieu;
END;
GO

CREATE PROCEDURE sp_LayDanhSachLichChieu
AS
BEGIN
    SELECT * FROM LichChieu;
END;
GO

CREATE PROCEDURE sp_LayLichChieuTheoPhim
    @maPhim nvarchar(50)
AS
BEGIN
    SELECT * FROM LichChieu WHERE maPhim = @maPhim;
END;
GO

-- Stored procedures cho bảng Ve
CREATE PROCEDURE sp_ThemVe
    @maVe nvarchar(50),
    @maGhe nvarchar(50),   
    @maLichChieu nvarchar(50),
    @gia real
AS
BEGIN
    INSERT INTO Ve (maVe, maGhe, maLichChieu, gia)
    VALUES (@maVe, @maGhe, @maLichChieu, @gia);
END;
GO

CREATE PROCEDURE sp_XoaVe
    @maVe nvarchar(50)
AS
BEGIN
    DELETE FROM Ve WHERE maVe = @maVe;
END;
GO

CREATE PROCEDURE sp_LayDanhSachVeTheoLichChieu
    @maLichChieu nvarchar(50)
AS
BEGIN
    SELECT * FROM Ve WHERE maLichChieu = @maLichChieu;
END;
GO

-- Stored procedures cho bảng HoaDon
CREATE PROCEDURE sp_ThemHoaDon
    @maHD nvarchar(50),
    @maKH nvarchar(50),
    @maNV nvarchar(50),
    @ngayTao date,
    @tongTien real
AS
BEGIN
    INSERT INTO HoaDon (maHD, maKH, maNV, ngayTao, tongTien)
    VALUES (@maHD, @maKH, @maNV, @ngayTao, @tongTien);
END;
GO

CREATE PROCEDURE sp_SuaHoaDon
    @maHD nvarchar(50),
    @maKH nvarchar(50),
    @maNV nvarchar(50),
    @ngayTao date,
    @tongTien real
AS
BEGIN
    UPDATE HoaDon
    SET maKH = @maKH, maNV = @maNV, ngayTao = @ngayTao, tongTien = @tongTien
    WHERE maHD = @maHD;
END;
GO

CREATE PROCEDURE sp_XoaHoaDon
    @maHD nvarchar(50)
AS
BEGIN
    DELETE FROM HoaDon WHERE maHD = @maHD;
END;
GO

CREATE PROCEDURE sp_LayDanhSachHoaDon
AS
BEGIN
    SELECT * FROM HoaDon;
END;
GO

-- Stored procedures cho bảng ChiTietHD
CREATE PROCEDURE sp_ThemChiTietHD
    @maHD nvarchar(50),
    @maVe nvarchar(50)
AS
BEGIN
    INSERT INTO ChiTietHD (maHD, maVe)
    VALUES (@maHD, @maVe);
END;
GO

CREATE PROCEDURE sp_XoaChiTietHD
    @maHD nvarchar(50),
    @maVe nvarchar(50)
AS
BEGIN
    DELETE FROM ChiTietHD WHERE maHD = @maHD AND maVe = @maVe;
END;
GO

CREATE PROCEDURE sp_LayChiTietHD
    @maHD nvarchar(50)
AS
BEGIN
    SELECT * FROM ChiTietHD WHERE maHD = @maHD;
END;
GO

-- Tính giá vé dựa trên ghế và phim
CREATE PROCEDURE sp_TinhGiaVe
    @maGhe nvarchar(50),
    @maPhim nvarchar(50)
AS
BEGIN
    SELECT (p.giaGoc * g.heSo) AS giaVe
    FROM Phim p
    JOIN Ghe g ON g.maGhe = @maGhe
    WHERE p.maPhim = @maPhim;
END;
GO

-- Lấy tất cả vé
CREATE PROCEDURE sp_LayDanhSachVe
AS
BEGIN
    SELECT * FROM Ve;
END;
GO

-- lấy doanh thu hôm nay
CREATE PROCEDURE sp_LayDoanhThu
AS
BEGIN
    SELECT SUM(h.tongTien) AS doanhThu
    FROM HoaDon h
END;
GO

-- Tìm lịch chiếu theo mã phim
CREATE PROCEDURE sp_TimLichChieu
    @maPhim nvarchar(50)
AS
BEGIN
    SELECT * FROM LichChieu WHERE maPhim = @maPhim;
END;
GO

-- Lấy tất cả thông tin của 5 phim có số lượng đặt vé nhiều nhất 
CREATE PROCEDURE sp_LayPhimCoSoVeNhieuNhat
AS
BEGIN
    SELECT TOP 5 p.maPhim, p.tenPhim, p.thoiLuong, p.gioiHanTuoi, p.giaGoc, p.maLoai, p.img, COUNT(v.maVe) AS soVe
    FROM Phim p
    JOIN LichChieu lc ON p.maPhim = lc.maPhim
    JOIN Ve v ON lc.maLichChieu = v.maLichChieu
    GROUP BY p.maPhim, p.tenPhim, p.thoiLuong, p.gioiHanTuoi, p.giaGoc, p.maLoai, p.img
    ORDER BY COUNT(v.maVe) DESC;
END;
GO


