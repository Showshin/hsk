package com.qlbv.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.itextpdf.text.BaseColor;
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
    private static final BaseColor PRIMARY_COLOR = new BaseColor(52, 152, 219);
    private static final BaseColor SECONDARY_COLOR = new BaseColor(46, 204, 113);
    private static final BaseColor DARK_COLOR = new BaseColor(44, 62, 80);
    
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
     * Mở file PDF bằng ứng dụng mặc định của hệ thống
     */
    private static void openPDF(String filePath) {
        try {
            File file = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                throw new RuntimeException("Không thể mở file PDF: Desktop không được hỗ trợ");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi mở file PDF: " + e.getMessage());
        }
    }
    
    /**
     * Tạo file PDF hóa đơn bán vé với đầy đủ thông tin khách hàng, phim, ghế và tổng tiền
     */
    public static void generateInvoice(String fileName, String maHD, String maKH, String tenKH, String sdt,
                                     Phim phim, LichChieu lichChieu, List<String> danhSachGhe,
                                     List<String> danhSachMaVe, double tongTien, String tenNV) {
        try {
            File invoicesDir = new File("invoices");
            if (!invoicesDir.exists()) {
                invoicesDir.mkdir();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Header
            Font titleFont = new Font(baseFont, 24, Font.BOLD, PRIMARY_COLOR);
            Paragraph title = new Paragraph("HÓA ĐƠN BÁN VÉ", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Invoice Info
            Font headerFont = new Font(baseFont, 14, Font.BOLD, DARK_COLOR);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);
            
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10f);
            infoTable.setSpacingAfter(10f);

            addTableRow(infoTable, "Mã hóa đơn:", maHD, headerFont, normalFont);
            addTableRow(infoTable, "Ngày:", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()), headerFont, normalFont);
            addTableRow(infoTable, "Nhân viên:", tenNV, headerFont, normalFont);
            document.add(infoTable);

            // Customer Info
            Paragraph customerHeader = new Paragraph("THÔNG TIN KHÁCH HÀNG", headerFont);
            customerHeader.setSpacingBefore(15f);
            customerHeader.setSpacingAfter(10f);
            document.add(customerHeader);

            PdfPTable customerTable = new PdfPTable(2);
            customerTable.setWidthPercentage(100);
            addTableRow(customerTable, "Tên khách hàng:", tenKH, headerFont, normalFont);
            addTableRow(customerTable, "Số điện thoại:", sdt, headerFont, normalFont);
            document.add(customerTable);

            // Movie Info
            Paragraph movieHeader = new Paragraph("THÔNG TIN VÉ", headerFont);
            movieHeader.setSpacingBefore(15f);
            movieHeader.setSpacingAfter(10f);
            document.add(movieHeader);

            PdfPTable movieTable = new PdfPTable(2);
            movieTable.setWidthPercentage(100);
            addTableRow(movieTable, "Phim:", phim.getTenPhim(), headerFont, normalFont);
            addTableRow(movieTable, "Phòng:", lichChieu.getMaPhong().getMaPhong(), headerFont, normalFont);
            addTableRow(movieTable, "Suất chiếu:", new SimpleDateFormat("HH:mm dd/MM/yyyy").format(lichChieu.getThoiGianBD()), headerFont, normalFont);
            document.add(movieTable);

            // Ticket Details
            Paragraph ticketHeader = new Paragraph("CHI TIẾT VÉ", headerFont);
            ticketHeader.setSpacingBefore(15f);
            ticketHeader.setSpacingAfter(10f);
            document.add(ticketHeader);

            PdfPTable ticketTable = new PdfPTable(2);
            ticketTable.setWidthPercentage(100);
            ticketTable.setSpacingBefore(10f);
            ticketTable.setSpacingAfter(10f);

            // Add table headers
            PdfPCell cell = new PdfPCell(new Phrase("Ghế", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(PRIMARY_COLOR);
            cell.setPadding(10f);
            ticketTable.addCell(cell);

            cell = new PdfPCell(new Phrase("Mã vé", headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(PRIMARY_COLOR);
            cell.setPadding(10f);
            ticketTable.addCell(cell);

            // Add ticket rows
            for (int i = 0; i < danhSachGhe.size(); i++) {
                cell = new PdfPCell(new Phrase(danhSachGhe.get(i), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8f);
                ticketTable.addCell(cell);

                cell = new PdfPCell(new Phrase(danhSachMaVe.get(i), normalFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8f);
                ticketTable.addCell(cell);
            }

            document.add(ticketTable);

            // Total
            Paragraph total = new Paragraph("Tổng tiền: " + String.format("%,.0f VNĐ", tongTien), headerFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(20f);
            document.add(total);

            // Footer
            Paragraph footer = new Paragraph("Cảm ơn quý khách đã sử dụng dịch vụ của chúng tôi!", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30f);
            document.add(footer);

            document.close();
            
            // Chỉ mở file hóa đơn
            openPDF(fileName);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo file PDF: " + e.getMessage());
        }
    }

    public static void generateTicket(String fileName, String maVe, Phim phim, LichChieu lichChieu, String tenGhe) {
        try {
            File ticketsDir = new File("tickets");
            if (!ticketsDir.exists()) {
                ticketsDir.mkdir();
            }

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Header
            Font titleFont = new Font(baseFont, 24, Font.BOLD, PRIMARY_COLOR);
            Paragraph title = new Paragraph("VÉ XEM PHIM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Ticket Info
            Font headerFont = new Font(baseFont, 14, Font.BOLD, DARK_COLOR);
            Font normalFont = new Font(baseFont, 12, Font.NORMAL);

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.setSpacingBefore(10f);
            infoTable.setSpacingAfter(10f);

            addTableRow(infoTable, "Mã vé:", maVe, headerFont, normalFont);
            addTableRow(infoTable, "Phim:", phim.getTenPhim(), headerFont, normalFont);
            addTableRow(infoTable, "Phòng:", lichChieu.getMaPhong().getMaPhong(), headerFont, normalFont);
            addTableRow(infoTable, "Suất chiếu:", new SimpleDateFormat("HH:mm dd/MM/yyyy").format(lichChieu.getThoiGianBD()), headerFont, normalFont);
            addTableRow(infoTable, "Ghế:", tenGhe, headerFont, normalFont);
            document.add(infoTable);

            // QR Code Placeholder
            Paragraph qrPlaceholder = new Paragraph("[QR Code]", normalFont);
            qrPlaceholder.setAlignment(Element.ALIGN_CENTER);
            qrPlaceholder.setSpacingBefore(20f);
            document.add(qrPlaceholder);

            // Footer
            Paragraph footer = new Paragraph("Vui lòng giữ vé này để vào rạp", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30f);
            document.add(footer);

            document.close();
            
            // Mở file PDF sau khi tạo
            openPDF(fileName);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi tạo file PDF: " + e.getMessage());
        }
    }

    private static void addTableRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
        labelCell.setPadding(8f);
        labelCell.setBackgroundColor(new BaseColor(240, 240, 240));
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setPadding(8f);
        table.addCell(valueCell);
    }
} 