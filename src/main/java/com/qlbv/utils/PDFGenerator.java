package com.qlbv.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.qlbv.model.entities.LichChieu;
import com.qlbv.model.entities.Phim;

public class PDFGenerator {
    private static BaseFont baseFont;
    
    static {
        try {
            // Sử dụng font Unicode có sẵn trong hệ thống
            baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
            try {
                // Fallback to Times New Roman nếu không tìm thấy Arial
                baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Tạo file PDF hóa đơn bán vé với đầy đủ thông tin khách hàng, phim, ghế và tổng tiền
     */
    public static void generateInvoice(String fileName, String maHD, String maKH, String tenKH, String sdt,
                                     Phim phim, LichChieu lichChieu, List<String> danhSachGhe,
                                     List<String> danhSachMaVe, double tongTien, String tenNV) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Add title
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN VÉ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Add invoice info
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);
            document.add(new Paragraph("Mã hóa đơn: " + maHD, normalFont));
            document.add(new Paragraph("Ngày: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()), normalFont));
            document.add(new Paragraph("Nhân viên: " + tenNV, normalFont));
            document.add(new Paragraph(" "));

            // Add customer info
            document.add(new Paragraph("THÔNG TIN KHÁCH HÀNG", normalFont));
            document.add(new Paragraph("Tên khách hàng: " + tenKH, normalFont));
            document.add(new Paragraph("Số điện thoại: " + sdt, normalFont));
            document.add(new Paragraph(" "));

            // Add movie info
            document.add(new Paragraph("THÔNG TIN VÉ", normalFont));
            document.add(new Paragraph("Phim: " + phim.getTenPhim(), normalFont));
            document.add(new Paragraph("Phòng: " + lichChieu.getMaPhong().getMaPhong(), normalFont));
            document.add(new Paragraph("Suất chiếu: " + new SimpleDateFormat("HH:mm dd/MM/yyyy").format(lichChieu.getThoiGianBD()), normalFont));
            document.add(new Paragraph(" "));

            // Add ticket details
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Add table headers
            PdfPCell cell = new PdfPCell(new Phrase("Ghế", normalFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Mã vé", normalFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            // Add ticket rows
            for (int i = 0; i < danhSachGhe.size(); i++) {
                table.addCell(new Phrase(danhSachGhe.get(i), normalFont));
                table.addCell(new Phrase(danhSachMaVe.get(i), normalFont));
            }

            document.add(table);

            // Add total
            document.add(new Paragraph("Tổng tiền: " + String.format("%,.0f VNĐ", tongTien), normalFont));

            document.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo file PDF: " + e.getMessage());
        }
    }
} 