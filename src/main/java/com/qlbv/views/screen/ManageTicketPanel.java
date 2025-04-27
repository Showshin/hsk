package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.qlbv.model.dao.ChiTietHDDAO;
import com.qlbv.model.dao.GheDAO;
import com.qlbv.model.dao.HoaDonDAO;
import com.qlbv.model.dao.LichChieuDAO;
import com.qlbv.model.dao.PhimDAO;
import com.qlbv.model.dao.VeDAO;
import com.qlbv.model.entities.ChiTietHD;
import com.qlbv.model.entities.Ghe;
import com.qlbv.model.entities.LichChieu;
import com.qlbv.model.entities.Phim;
import com.qlbv.model.entities.Ve;
import com.qlbv.utils.PDFGenerator;

public class ManageTicketPanel extends JPanel {
    
    // Components
    private JTextField txtMaHD;
    private JComboBox<String> cbMaVe;
    private JButton btnSearch;
    private JButton btnPrint;
    private JButton btnDelete;
    private JTable bangVe;
    private DefaultTableModel tableModel;
    
    private VeDAO veDAO;
    private HoaDonDAO hoaDonDAO;
    private ChiTietHDDAO chiTietHDDAO;
    private SimpleDateFormat dateFormat;
    
    public ManageTicketPanel() {
        veDAO = new VeDAO();
        hoaDonDAO = new HoaDonDAO();
        chiTietHDDAO = new ChiTietHDDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        JPanel pnlSearch = taoPanelTimKiem();
        add(pnlSearch, BorderLayout.NORTH);
        
       
        JPanel pnlTable = taoBang();
        add(pnlTable, BorderLayout.CENTER);
    }
    
    private JPanel taoPanelTimKiem() {
        JPanel pnlSearch = new JPanel(null);
        pnlSearch.setPreferredSize(new Dimension(800, 120)); 
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
    
        // Mã hóa đơn
        JLabel jlbMaHD = new JLabel("Mã hóa đơn:");
        jlbMaHD.setFont(new Font("Arial", Font.BOLD, 13));
        jlbMaHD.setBounds(20, 20, 100, 30);
        pnlSearch.add(jlbMaHD);
    
        txtMaHD = new JTextField();
        txtMaHD.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMaHD.setBounds(130, 20, 200, 30);
        pnlSearch.add(txtMaHD);
    
        // NÚT TÌM KIẾM
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 13));
        btnSearch.setBounds(350, 20, 100, 30);
        pnlSearch.add(btnSearch);
    
        // Mã vé
        JLabel jlbMaVe = new JLabel("Mã vé:");
        jlbMaVe.setFont(new Font("Arial", Font.BOLD, 13));
        jlbMaVe.setBounds(20, 70, 100, 30);
        pnlSearch.add(jlbMaVe);
    
        cbMaVe = new JComboBox<>();
        cbMaVe.setFont(new Font("Arial", Font.PLAIN, 13));
        cbMaVe.setBounds(130, 70, 200, 30);
        pnlSearch.add(cbMaVe);
    
        // IN VE
        btnPrint = new JButton("In vé");
        btnPrint.setBackground(new Color(46, 204, 113));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Arial", Font.BOLD, 13));
        btnPrint.setBounds(700, 70, 100, 30);
        pnlSearch.add(btnPrint);
    
        // HUY VE
        btnDelete = new JButton("Hủy vé");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 13));
        btnDelete.setBounds(820, 70, 100, 30);
        pnlSearch.add(btnDelete);
    
        
        btnSearch.addActionListener(e -> timKiemHoaDonTheoMaHD());
    
        btnPrint.addActionListener(e -> {
            String maVe = (String) cbMaVe.getSelectedItem();
            if (maVe != null && !maVe.isEmpty()) {
                PhimDAO phimDAO = new PhimDAO();
                LichChieuDAO lichChieuDAO = new LichChieuDAO();
                GheDAO gheDAO = new GheDAO();
    
                Ve ve = veDAO.timVe(maVe);
                LichChieu lichChieu = lichChieuDAO.timLichChieuTheoMa(ve.getMaLichChieu().getMaLichChieu());
                Phim phim = phimDAO.timPhim(lichChieu.getMaPhim().getMaPhim());
                Ghe ghe = gheDAO.timGheTheoMa(ve.getMaGhe().getMaGhe());
    
                PDFGenerator.generateTicket(
                    "tickets/ticket" + maVe + ".pdf",
                    maVe,
                    phim,
                    lichChieu,
                    ghe.getHangGhe()
                );
                JOptionPane.showMessageDialog(this,
                    "Vé đã được in thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn vé cần in!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
    
        btnDelete.addActionListener(e -> {
            String maVe = (String) cbMaVe.getSelectedItem();
            if (maVe != null && !maVe.isEmpty()) {
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn Hủy vé này không?",
                    "Xác nhận Hủy",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
    
                if (confirm == JOptionPane.YES_OPTION) {
                    if (veDAO.xoaVe(maVe)) {
                        JOptionPane.showMessageDialog(this,
                            "Hủy vé thành công!",
                            "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                            timKiemHoaDonTheoMaHD();
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Hủy vé thất bại!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn vé cần Hủy!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
    
        txtMaHD.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timKiemHoaDonTheoMaHD();
                }
            }
        });
    
        return pnlSearch;
    }
    
    
    private JPanel taoBang() {
        JPanel pnlTable = new JPanel(new BorderLayout());
        
        tableModel = new DefaultTableModel();
        
     
        tableModel.addColumn("Mã vé");
        tableModel.addColumn("Mã ghế");
        tableModel.addColumn("Phòng");
        tableModel.addColumn("Mã lịch chiếu");
        tableModel.addColumn("Giá vé");
        
        // TAOJ BANG
        bangVe = new JTable(tableModel);
        bangVe.setRowHeight(35);
        bangVe.setFont(new Font("Arial", Font.PLAIN, 13));
        bangVe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style header
        JTableHeader header = bangVe.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < bangVe.getColumnCount(); i++) {
            bangVe.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(bangVe);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Khi chọn dòng trong bảng thì cập nhật combobox mã vé
        bangVe.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && bangVe.getSelectedRow() != -1) {
                int row = bangVe.getSelectedRow();
                Object maVe = bangVe.getValueAt(row, 0); // cột 0 là mã vé
                if (maVe != null) {
                    cbMaVe.setSelectedItem(maVe.toString());
                }
            }
        });

        return pnlTable;
    }
    
    private void timKiemHoaDonTheoMaHD() {
        String maHD = txtMaHD.getText().trim();
        if (maHD.isEmpty()) {
            return;
        }
        tableModel.setRowCount(0);
        cbMaVe.removeAllItems();
        List<ChiTietHD> dsCTHD = chiTietHDDAO.layChiTietHD(maHD);
        GheDAO gheDAO = new GheDAO();
        LichChieuDAO lichChieuDAO = new LichChieuDAO();
        for (ChiTietHD cthd : dsCTHD) {
            Ve ve = veDAO.timVe(cthd.getMaVe().getMaVe());
            if (ve != null) {
                cbMaVe.addItem(ve.getMaVe());
                Object[] rowData = {
                    ve.getMaVe(),
                    gheDAO.timGheTheoMa(ve.getMaGhe().getMaGhe()).getHangGhe(),
                    lichChieuDAO.timLichChieuTheoMa(ve.getMaLichChieu().getMaLichChieu()).getMaPhong().getMaPhong(),
                    ve.getMaLichChieu().getMaLichChieu(),
                    String.format("%,.0f VNĐ", ve.getGia())
                };
                tableModel.addRow(rowData);
            }
        }
    }
}
