package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import com.qlbv.model.dao.KhachHangDAO;
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
    private JTable tblTickets;
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
        
        // Search Panel at top
        JPanel pnlSearch = createSearchPanel();
        add(pnlSearch, BorderLayout.NORTH);
        
        // Tickets Table
        JPanel pnlTable = createTablePanel();
        add(pnlTable, BorderLayout.CENTER);
    }
    
    private JPanel createSearchPanel() {
        JPanel pnlSearch = new JPanel(new GridBagLayout());
        pnlSearch.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Mã hóa đơn
        JLabel lblMaHD = new JLabel("Mã hóa đơn:");
        lblMaHD.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        pnlSearch.add(lblMaHD, gbc);
        
        txtMaHD = new JTextField();
        txtMaHD.setPreferredSize(new Dimension(200, 30));
        txtMaHD.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.weightx = 1;
        pnlSearch.add(txtMaHD, gbc);
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(100, 30));
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 2;
        gbc.weightx = 0;
        pnlSearch.add(btnSearch, gbc);
        
        // Mã vé (ComboBox) và các nút
        JLabel lblMaVe = new JLabel("Mã vé:");
        lblMaVe.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        pnlSearch.add(lblMaVe, gbc);
        
        cbMaVe = new JComboBox<>();
        cbMaVe.setPreferredSize(new Dimension(200, 30));
        cbMaVe.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.weightx = 1;
        pnlSearch.add(cbMaVe, gbc);
        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlButtons.setOpaque(false);
        btnPrint = new JButton("In vé");
        btnPrint.setPreferredSize(new Dimension(100, 30));
        btnPrint.setBackground(new Color(46, 204, 113));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFont(new Font("Arial", Font.BOLD, 13));
        pnlButtons.add(btnPrint);
        btnDelete = new JButton("Hủy vé");
        btnDelete.setPreferredSize(new Dimension(100, 30));
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Arial", Font.BOLD, 13));
        pnlButtons.add(btnDelete);
        gbc.gridx = 2;
        gbc.weightx = 0;
        pnlSearch.add(pnlButtons, gbc);
        
        // Add action listeners
        btnSearch.addActionListener(e -> performSearch());
        btnPrint.addActionListener(e -> {
            String maVe = (String) cbMaVe.getSelectedItem();
            if (maVe != null && !maVe.isEmpty()) {
                //Dao
                HoaDonDAO hoaDonDAO = new HoaDonDAO();
                KhachHangDAO khachHangDAO = new KhachHangDAO();
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
                        performSearch();
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
                    performSearch();
                }
            }
        });
        
        return pnlSearch;
    }
    
    private JPanel createTablePanel() {
        JPanel pnlTable = new JPanel(new BorderLayout());
        
        // Create table model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Add columns
        tableModel.addColumn("Mã vé");
        tableModel.addColumn("Mã ghế");
        tableModel.addColumn("Phòng");
        tableModel.addColumn("Mã lịch chiếu");
        tableModel.addColumn("Giá vé");
        
        // Create table
        tblTickets = new JTable(tableModel);
        tblTickets.setRowHeight(35);
        tblTickets.setFont(new Font("Arial", Font.PLAIN, 13));
        tblTickets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style header
        JTableHeader header = tblTickets.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tblTickets.getColumnCount(); i++) {
            tblTickets.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(tblTickets);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlTable.add(scrollPane, BorderLayout.CENTER);

        // Khi chọn dòng trong bảng thì cập nhật combobox mã vé
        tblTickets.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblTickets.getSelectedRow() != -1) {
                int row = tblTickets.getSelectedRow();
                Object maVe = tblTickets.getValueAt(row, 0); // cột 0 là mã vé
                if (maVe != null) {
                    cbMaVe.setSelectedItem(maVe.toString());
                }
            }
        });

        return pnlTable;
    }
    
    private void performSearch() {
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
