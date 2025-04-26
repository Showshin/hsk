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
('P009', 'L03', N'Ông bố đối đầu', 100, 10, 75000, 'resources/images/phim/9.png');


INSERT INTO Phong (maPhong, tenPhong, sucChua) VALUES 
('R001', N'Phòng A', 120),
('R002', N'Phòng B', 100),
('R003', N'Phòng VIP', 80),
('R004', N'Phòng C', 90),
('R005', N'Phòng D', 110),
('R006', N'Phòng E', 85),
('R007', N'Phòng VIP 2', 75),
('R008', N'Phòng F', 150),
('R009', N'Phòng G', 60),
('R010', N'Phòng H', 100),
('R011', N'Phòng I', 90);



INSERT INTO LichChieu (maLichChieu, maPhim, maPhong, thoiGianBD, thoiGianKT) VALUES 
('LC01', 'P001', 'R001', '2025-04-28 14:00:00', '2025-04-28 16:15:00'), -- Đại chiến người khổng lồ (120 min + 15 min)
('LC02', 'P002', 'R002', '2025-04-28 14:00:00', '2025-04-28 15:45:00'), -- Đặc vụ bóng ma (90 min + 15 min)
('LC03', 'P003', 'R003', '2025-04-28 14:00:00', '2025-04-28 16:05:00'), -- The smile have left your eyes (110 min + 15 min)
('LC04', 'P004', 'R004', '2025-04-28 16:30:00', '2025-04-28 18:55:00'), -- Biệt đội cảm tử (130 min + 15 min)
('LC05', 'P005', 'R005', '2025-04-28 16:30:00', '2025-04-28 18:40:00'), -- Tốc độ kinh hoàng (115 min + 15 min)
('LC06', 'P006', 'R006', '2025-04-28 16:30:00', '2025-04-28 18:25:00'), -- Lời nguyền số mệnh (100 min + 15 min)
('LC07', 'P007', 'R007', '2025-04-28 18:00:00', '2025-04-28 19:50:00'), -- Nhà trọ lúc nửa đêm (95 min + 15 min)
('LC08', 'P008', 'R008', '2025-04-28 18:00:00', '2025-04-28 20:00:00'), -- Con mắt âm dương (105 min + 15 min)
('LC09', 'P009', 'R009', '2025-04-28 18:00:00', '2025-04-28 19:55:00'); -- Ông bố đối đầu (100 min + 15 min)

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


-- ====================== Phòng R004 ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G106', 'R004', 'A1', 1.0, 0), ('G107', 'R004', 'A2', 1.0, 0), ('G108', 'R004', 'A3', 1.0, 0), ('G109', 'R004', 'A4', 1.0, 0),
('G110', 'R004', 'A5', 1.0, 0), ('G111', 'R004', 'A6', 1.0, 0), ('G112', 'R004', 'A7', 1.0, 0),
-- Hàng B (1.0)
('G113', 'R004', 'B1', 1.0, 0), ('G114', 'R004', 'B2', 1.0, 1), ('G115', 'R004', 'B3', 1.0, 1), ('G116', 'R004', 'B4', 1.0, 0),
('G117', 'R004', 'B5', 1.0, 0), ('G118', 'R004', 'B6', 1.0, 0), ('G119', 'R004', 'B7', 1.0, 0),
-- Hàng C (1.0)
('G120', 'R004', 'C1', 1.0, 0), ('G121', 'R004', 'C2', 1.0, 0), ('G122', 'R004', 'C3', 1.0, 0), ('G123', 'R004', 'C4', 1.0, 0),
('G124', 'R004', 'C5', 1.0, 0), ('G125', 'R004', 'C6', 1.0, 0), ('G126', 'R004', 'C7', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G127', 'R004', 'D1', 1.1, 0), ('G128', 'R004', 'D2', 1.1, 0), ('G129', 'R004', 'D3', 1.1, 0), ('G130', 'R004', 'D4', 1.1, 0),
('G131', 'R004', 'D5', 1.1, 0), ('G132', 'R004', 'D6', 1.1, 0), ('G133', 'R004', 'D7', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G134', 'R004', 'E1', 1.2, 0), ('G135', 'R004', 'E2', 1.2, 1), ('G136', 'R004', 'E3', 1.2, 1), ('G137', 'R004', 'E4', 1.2, 0),
('G138', 'R004', 'E5', 1.2, 0), ('G139', 'R004', 'E6', 1.2, 0), ('G140', 'R004', 'E7', 1.2, 0);

-- ====================== Phòng R005 ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G141', 'R005', 'A1', 1.0, 0), ('G142', 'R005', 'A2', 1.0, 0), ('G143', 'R005', 'A3', 1.0, 0), ('G144', 'R005', 'A4', 1.0, 0),
('G145', 'R005', 'A5', 1.0, 0), ('G146', 'R005', 'A6', 1.0, 0), ('G147', 'R005', 'A7', 1.0, 0),
-- Hàng B (1.0)
('G148', 'R005', 'B1', 1.0, 0), ('G149', 'R005', 'B2', 1.0, 0), ('G150', 'R005', 'B3', 1.0, 0), ('G151', 'R005', 'B4', 1.0, 0),
('G152', 'R005', 'B5', 1.0, 0), ('G153', 'R005', 'B6', 1.0, 1), ('G154', 'R005', 'B7', 1.0, 0),
-- Hàng C (1.0)
('G155', 'R005', 'C1', 1.0, 0), ('G156', 'R005', 'C2', 1.0, 0), ('G157', 'R005', 'C3', 1.0, 0), ('G158', 'R005', 'C4', 1.0, 0),
('G159', 'R005', 'C5', 1.0, 0), ('G160', 'R005', 'C6', 1.0, 0), ('G161', 'R005', 'C7', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G162', 'R005', 'D1', 1.1, 0), ('G163', 'R005', 'D2', 1.1, 0), ('G164', 'R005', 'D3', 1.1, 1), ('G165', 'R005', 'D4', 1.1, 0),
('G166', 'R005', 'D5', 1.1, 0), ('G167', 'R005', 'D6', 1.1, 0), ('G168', 'R005', 'D7', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G169', 'R005', 'E1', 1.2, 0), ('G170', 'R005', 'E2', 1.2, 1), ('G171', 'R005', 'E3', 1.2, 1), ('G172', 'R005', 'E4', 1.2, 0),
('G173', 'R005', 'E5', 1.2, 0), ('G174', 'R005', 'E6', 1.2, 0), ('G175', 'R005', 'E7', 1.2, 0);

-- ====================== Phòng R006 ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G176', 'R006', 'A1', 1.0, 0), ('G177', 'R006', 'A2', 1.0, 0), ('G178', 'R006', 'A3', 1.0, 0), ('G179', 'R006', 'A4', 1.0, 0),
('G180', 'R006', 'A5', 1.0, 0), ('G181', 'R006', 'A6', 1.0, 0), ('G182', 'R006', 'A7', 1.0, 0),
-- Hàng B (1.0)
('G183', 'R006', 'B1', 1.0, 0), ('G184', 'R006', 'B2', 1.0, 0), ('G185', 'R006', 'B3', 1.0, 1), ('G186', 'R006', 'B4', 1.0, 0),
('G187', 'R006', 'B5', 1.0, 0), ('G188', 'R006', 'B6', 1.0, 0), ('G189', 'R006', 'B7', 1.0, 0),
-- Hàng C (1.0)
('G190', 'R006', 'C1', 1.0, 0), ('G191', 'R006', 'C2', 1.0, 0), ('G192', 'R006', 'C3', 1.0, 0), ('G193', 'R006', 'C4', 1.0, 0),
('G194', 'R006', 'C5', 1.0, 0), ('G195', 'R006', 'C6', 1.0, 0), ('G196', 'R006', 'C7', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G197', 'R006', 'D1', 1.1, 0), ('G198', 'R006', 'D2', 1.1, 1), ('G199', 'R006', 'D3', 1.1, 1), ('G200', 'R006', 'D4', 1.1, 0),
('G201', 'R006', 'D5', 1.1, 0), ('G202', 'R006', 'D6', 1.1, 0), ('G203', 'R006', 'D7', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G204', 'R006', 'E1', 1.2, 0), ('G205', 'R006', 'E2', 1.2, 0), ('G206', 'R006', 'E3', 1.2, 0), ('G207', 'R006', 'E4', 1.2, 0),
('G208', 'R006', 'E5', 1.2, 0), ('G209', 'R006', 'E6', 1.2, 0), ('G210', 'R006', 'E7', 1.2, 0);

-- ====================== Phòng R007 (VIP 2) ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.2, gần màn hình, VIP)
('G211', 'R007', 'A1', 1.2, 0), ('G212', 'R007', 'A2', 1.2, 0), ('G213', 'R007', 'A3', 1.2, 0), ('G214', 'R007', 'A4', 1.2, 0),
('G215', 'R007', 'A5', 1.2, 0), ('G216', 'R007', 'A6', 1.2, 0), ('G217', 'R007', 'A7', 1.2, 0),
-- Hàng B (1.2)
('G218', 'R007', 'B1', 1.2, 0), ('G219', 'R007', 'B2', 1.2, 0), ('G220', 'R007', 'B3', 1.2, 1), ('G221', 'R007', 'B4', 1.2, 0),
('G222', 'R007', 'B5', 1.2, 0), ('G223', 'R007', 'B6', 1.2, 0), ('G224', 'R007', 'B7', 1.2, 0),
-- Hàng C (1.2)
('G225', 'R007', 'C1', 1.2, 0), ('G226', 'R007', 'C2', 1.2, 0), ('G227', 'R007', 'C3', 1.2, 0), ('G228', 'R007', 'C4', 1.2, 0),
('G229', 'R007', 'C5', 1.2, 0), ('G230', 'R007', 'C6', 1.2, 0), ('G231', 'R007', 'C7', 1.2, 0),
-- Hàng D (1.3, vị trí tốt)
('G232', 'R007', 'D1', 1.3, 0), ('G233', 'R007', 'D2', 1.3, 1), ('G234', 'R007', 'D3', 1.3, 1), ('G235', 'R007', 'D4', 1.3, 0),
('G236', 'R007', 'D5', 1.3, 0), ('G237', 'R007', 'D6', 1.3, 0), ('G238', 'R007', 'D7', 1.3, 0),
-- Hàng E (1.4, vị trí tốt nhất)
('G239', 'R007', 'E1', 1.4, 0), ('G240', 'R007', 'E2', 1.4, 0), ('G241', 'R007', 'E3', 1.4, 0), ('G242', 'R007', 'E4', 1.4, 0),
('G243', 'R007', 'E5', 1.4, 0), ('G244', 'R007', 'E6', 1.4, 0), ('G245', 'R007', 'E7', 1.4, 0);

-- ====================== Phòng R008 (Hội nghị) ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G246', 'R008', 'A1', 1.0, 0), ('G247', 'R008', 'A2', 1.0, 0), ('G248', 'R008', 'A3', 1.0, 0), ('G249', 'R008', 'A4', 1.0, 0),
('G250', 'R008', 'A5', 1.0, 0), ('G251', 'R008', 'A6', 1.0, 0), ('G252', 'R008', 'A7', 1.0, 0), ('G253', 'R008', 'A8', 1.0, 0),
-- Hàng B (1.0)
('G254', 'R008', 'B1', 1.0, 0), ('G255', 'R008', 'B2', 1.0, 0), ('G256', 'R008', 'B3', 1.0, 0), ('G257', 'R008', 'B4', 1.0, 0),
('G258', 'R008', 'B5', 1.0, 1), ('G259', 'R008', 'B6', 1.0, 0), ('G260', 'R008', 'B7', 1.0, 0), ('G261', 'R008', 'B8', 1.0, 0),
-- Hàng C (1.0)
('G262', 'R008', 'C1', 1.0, 0), ('G263', 'R008', 'C2', 1.0, 0), ('G264', 'R008', 'C3', 1.0, 0), ('G265', 'R008', 'C4', 1.0, 0),
('G266', 'R008', 'C5', 1.0, 0), ('G267', 'R008', 'C6', 1.0, 0), ('G268', 'R008', 'C7', 1.0, 0), ('G269', 'R008', 'C8', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G270', 'R008', 'D1', 1.1, 0), ('G271', 'R008', 'D2', 1.1, 0), ('G272', 'R008', 'D3', 1.1, 1), ('G273', 'R008', 'D4', 1.1, 1),
('G274', 'R008', 'D5', 1.1, 0), ('G275', 'R008', 'D6', 1.1, 0), ('G276', 'R008', 'D7', 1.1, 0), ('G277', 'R008', 'D8', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G278', 'R008', 'E1', 1.2, 0), ('G279', 'R008', 'E2', 1.2, 0), ('G280', 'R008', 'E3', 1.2, 0), ('G281', 'R008', 'E4', 1.2, 0),
('G282', 'R008', 'E5', 1.2, 0), ('G283', 'R008', 'E6', 1.2, 0), ('G284', 'R008', 'E7', 1.2, 0), ('G285', 'R008', 'E8', 1.2, 0);

-- ====================== Phòng R009 (Seminar) ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G286', 'R009', 'A1', 1.0, 0), ('G287', 'R009', 'A2', 1.0, 0), ('G288', 'R009', 'A3', 1.0, 0), ('G289', 'R009', 'A4', 1.0, 0),
('G290', 'R009', 'A5', 1.0, 0), ('G291', 'R009', 'A6', 1.0, 0),
-- Hàng B (1.0)
('G292', 'R009', 'B1', 1.0, 0), ('G293', 'R009', 'B2', 1.0, 0), ('G294', 'R009', 'B3', 1.0, 1), ('G295', 'R009', 'B4', 1.0, 0),
('G296', 'R009', 'B5', 1.0, 0), ('G297', 'R009', 'B6', 1.0, 0),
-- Hàng C (1.0)
('G298', 'R009', 'C1', 1.0, 0), ('G299', 'R009', 'C2', 1.0, 0), ('G300', 'R009', 'C3', 1.0, 0), ('G301', 'R009', 'C4', 1.0, 0),
('G302', 'R009', 'C5', 1.0, 0), ('G303', 'R009', 'C6', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G304', 'R009', 'D1', 1.1, 0), ('G305', 'R009', 'D2', 1.1, 0), ('G306', 'R009', 'D3', 1.1, 1), ('G307', 'R009', 'D4', 1.1, 0),
('G308', 'R009', 'D5', 1.1, 0), ('G309', 'R009', 'D6', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G310', 'R009', 'E1', 1.2, 0), ('G311', 'R009', 'E2', 1.2, 0), ('G312', 'R009', 'E3', 1.2, 0), ('G313', 'R009', 'E4', 1.2, 0),
('G314', 'R009', 'E5', 1.2, 0), ('G315', 'R009', 'E6', 1.2, 0);

-- ====================== Phòng R010  ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G316', 'R010', 'A1', 1.0, 0), ('G317', 'R010', 'A2', 1.0, 0), ('G318', 'R010', 'A3', 1.0, 0), ('G319', 'R010', 'A4', 1.0, 0),
('G320', 'R010', 'A5', 1.0, 0), ('G321', 'R010', 'A6', 1.0, 0), ('G322', 'R010', 'A7', 1.0, 0),
-- Hàng B (1.0)
('G323', 'R010', 'B1', 1.0, 0), ('G324', 'R010', 'B2', 1.0, 0), ('G325', 'R010', 'B3', 1.0, 0), ('G326', 'R010', 'B4', 1.0, 0),
('G327', 'R010', 'B5', 1.0, 0), ('G328', 'R010', 'B6', 1.0, 0), ('G329', 'R010', 'B7', 1.0, 0),
-- Hàng C (1.0)
('G330', 'R010', 'C1', 1.0, 0), ('G331', 'R010', 'C2', 1.0, 0), ('G332', 'R010', 'C3', 1.0, 1), ('G333', 'R010', 'C4', 1.0, 1),
('G334', 'R010', 'C5', 1.0, 0), ('G335', 'R010', 'C6', 1.0, 0), ('G336', 'R010', 'C7', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G337', 'R010', 'D1', 1.1, 0), ('G338', 'R010', 'D2', 1.1, 0), ('G339', 'R010', 'D3', 1.1, 0), ('G340', 'R010', 'D4', 1.1, 0),
('G341', 'R010', 'D5', 1.1, 0), ('G342', 'R010', 'D6', 1.1, 0), ('G343', 'R010', 'D7', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G344', 'R010', 'E1', 1.2, 0), ('G345', 'R010', 'E2', 1.2, 1), ('G346', 'R010', 'E3', 1.2, 1), ('G347', 'R010', 'E4', 1.2, 0),
('G348', 'R010', 'E5', 1.2, 0), ('G349', 'R010', 'E6', 1.2, 0), ('G350', 'R010', 'E7', 1.2, 0);

-- ====================== Phòng R011 ======================
INSERT INTO Ghe (maGhe, maPhong, hangGhe, heSo, trangThai) VALUES
-- Hàng A (1.0, gần màn hình)
('G351', 'R011', 'A1', 1.0, 0), ('G352', 'R011', 'A2', 1.0, 0), ('G353', 'R011', 'A3', 1.0, 0), ('G354', 'R011', 'A4', 1.0, 0),
('G355', 'R011', 'A5', 1.0, 0),
-- Hàng B (1.0)
('G356', 'R011', 'B1', 1.0, 0), ('G357', 'R011', 'B2', 1.0, 1), ('G358', 'R011', 'B3', 1.0, 0), ('G359', 'R011', 'B4', 1.0, 0),
('G360', 'R011', 'B5', 1.0, 0),
-- Hàng C (1.0)
('G361', 'R011', 'C1', 1.0, 0), ('G362', 'R011', 'C2', 1.0, 0), ('G363', 'R011', 'C3', 1.0, 0), ('G364', 'R011', 'C4', 1.0, 0),
('G365', 'R011', 'C5', 1.0, 0),
-- Hàng D (1.1, vị trí tốt)
('G366', 'R011', 'D1', 1.1, 0), ('G367', 'R011', 'D2', 1.1, 0), ('G368', 'R011', 'D3', 1.1, 1), ('G369', 'R011', 'D4', 1.1, 0),
('G370', 'R011', 'D5', 1.1, 0),
-- Hàng E (1.2, vị trí tốt nhất)
('G371', 'R011', 'E1', 1.2, 0), ('G372', 'R011', 'E2', 1.2, 0), ('G373', 'R011', 'E3', 1.2, 0), ('G374', 'R011', 'E4', 1.2, 0),
('G375', 'R011', 'E5', 1.2, 0);




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
    DELETE FROM ChiTietHD WHERE maVe = @maVe;
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

--Tìm vé theo mã vé
CREATE PROCEDURE sp_TimVe
    @maVe nvarchar(50)
AS
BEGIN
    SELECT * FROM Ve WHERE maVe = @maVe;
END;
GO

--Tìm lịch chiếu theo mã lịch chiếu
CREATE PROCEDURE sp_TimLichChieuTheoMa
    @maLichChieu nvarchar(50)
AS
BEGIN
    SELECT * FROM LichChieu WHERE maLichChieu = @maLichChieu;
END;
GO
