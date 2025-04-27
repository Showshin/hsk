package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.qlbv.model.dao.LichChieuDAO;
import com.qlbv.model.dao.PhimDAO;
import com.qlbv.model.dao.PhongDAO;
import com.qlbv.model.entities.LichChieu;
import com.qlbv.model.entities.Phim;
import com.qlbv.model.entities.Phong;

public class ManageShowtimePanel extends JPanel {
    private JPanel formPanel;
    private JPanel tablePanel;
    private JTable table_LichChieu;
    private DefaultTableModel tableModel;
    
    private JTextField maLichChieuField;
    private JComboBox<Phim> phimComboBox;
    private JComboBox<Phong> phongComboBox;
    private JTextField thoiGianBDField;
    private JTextField thoiGianKTField;
    private JTextField searchField;
    
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton clearButton;
    
    private LichChieuDAO lichChieuDAO;
    private PhimDAO phimDAO;
    private PhongDAO phongDAO;
    
    private SimpleDateFormat dateFormat;
    
    public ManageShowtimePanel() {
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 242, 245));
        
        lichChieuDAO = new LichChieuDAO();
        phimDAO = new PhimDAO();
        phongDAO = new PhongDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        taoFormNhapDuLieu();
        taoBangChuaLichChieu();
        
        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        
        loadTableData();
    }
    
    private void taoFormNhapDuLieu() {
        formPanel = new JPanel();
        formPanel.setLayout(null); 
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin lịch chiếu"));
        formPanel.setBackground(Color.WHITE);
        formPanel.setMinimumSize(new Dimension(800, 300));
        formPanel.setPreferredSize(new Dimension(900, 300));
    
       //MA LICH CHIEU
        JLabel maLichChieuLabel = new JLabel("Mã lịch chiếu:");
        maLichChieuLabel.setBounds(20, 30, 120, 25);
        formPanel.add(maLichChieuLabel);
    
        maLichChieuField = new JTextField();
        maLichChieuField.setBounds(150, 30, 200, 25);
        formPanel.add(maLichChieuField);
    
        // PHIM
        JLabel phimLabel = new JLabel("Phim:");
        phimLabel.setBounds(400, 30, 50, 25);
        formPanel.add(phimLabel);
    
        phimComboBox = new JComboBox<>();
        phimComboBox.setBounds(450, 30, 200, 25);
        loadPhimComboBox();
        formPanel.add(phimComboBox);
    
        // PHONG
        JLabel phongLabel = new JLabel("Phòng:");
        phongLabel.setBounds(20, 70, 120, 25);
        formPanel.add(phongLabel);
    
        phongComboBox = new JComboBox<>();
        phongComboBox.setBounds(150, 70, 200, 25);
        loadPhongComboBox();
        formPanel.add(phongComboBox);
  
        // THOI GIAN BAT DAU
        JLabel thoiGianBDLabel = new JLabel("Thời gian bắt đầu (dd/MM/yyyy HH:mm):");
        thoiGianBDLabel.setBounds(400, 70, 250, 25);
        formPanel.add(thoiGianBDLabel);
    
        thoiGianBDField = new JTextField();
        thoiGianBDField.setBounds(660, 70, 200, 25);
        formPanel.add(thoiGianBDField);
    
        // THOI GIAN KET THUC
        JLabel thoiGianKTLabel = new JLabel("Thời gian kết thúc (dd/MM/yyyy HH:mm):");
        thoiGianKTLabel.setBounds(400, 110, 250, 25);
        formPanel.add(thoiGianKTLabel);
    
        thoiGianKTField = new JTextField();
        thoiGianKTField.setBounds(660, 110, 200, 25);
        formPanel.add(thoiGianKTField);
    
     
        // Nút thêm, cập nhật, xóa, xóa rỗng
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setBounds(20, 160, 100, 30);
        formPanel.add(addButton);
    
        updateButton = new JButton("Cập nhật");
        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setBounds(130, 160, 100, 30);
        formPanel.add(updateButton);
    
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBounds(240, 160, 100, 30);
        formPanel.add(deleteButton);
    
        clearButton = new JButton("Xóa rỗng");
        clearButton.setBackground(new Color(243, 156, 18));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBounds(350, 160, 100, 30);
        formPanel.add(clearButton);
    
        // Ô tìm kiếm + Button tìm kiếm
        JLabel searchLabel = new JLabel("Tìm kiếm phim:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setBounds(500, 160, 120, 30);
        formPanel.add(searchLabel);
    
        searchField = new JTextField();
        searchField.setBounds(620, 160, 150, 30);
        formPanel.add(searchField);
    
        searchButton = new JButton("Tìm");
        searchButton.setBackground(new Color(155, 89, 182));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        searchButton.setBounds(780, 160, 70, 30);
        formPanel.add(searchButton);
    
        // Gán action cho các nút
        addButton.addActionListener(e -> addLichChieu());
        updateButton.addActionListener(e -> capNhatLichChieu());
        deleteButton.addActionListener(e -> xoaLichChieu());
        clearButton.addActionListener(e -> xoaRong());
        searchButton.addActionListener(e -> searchShowtime());
    }
    
    
    private void taoBangChuaLichChieu() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách lịch chiếu"));
        tablePanel.setBackground(Color.WHITE);
        
        String[] columns = {"Mã lịch chiếu", "Phim", "Phòng", "Thời gian bắt đầu", "Thời gian kết thúc"};
        tableModel = new DefaultTableModel(columns, 0);
        table_LichChieu = new JTable(tableModel);
        table_LichChieu.setSelectionBackground(new Color(52, 152, 219));
        table_LichChieu.setSelectionForeground(Color.WHITE);
        table_LichChieu.setRowHeight(25);

        
        table_LichChieu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table_LichChieu.getSelectedRow();
                if (selectedRow >= 0) {
                    chonDongDuaVaoInput(selectedRow);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table_LichChieu);
        scrollPane.setPreferredSize(new Dimension(tablePanel.getWidth(), 350));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadPhimComboBox() {
        DefaultComboBoxModel<Phim> model = new DefaultComboBoxModel<>();
        List<Phim> dsPhim = phimDAO.layDanhSachPhim();
        for (Phim phim : dsPhim) {
            model.addElement(phim);
        }
        phimComboBox.setModel(model);
        phimComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> 
            new JLabel(value != null ? value.getTenPhim() : ""));
    }
    
    private void loadPhongComboBox() {
        DefaultComboBoxModel<Phong> model = new DefaultComboBoxModel<>();
        List<Phong> dsPhong = phongDAO.layDanhSachPhong();
        for (Phong phong : dsPhong) {
            model.addElement(phong);
        }
        phongComboBox.setModel(model);
        phongComboBox.setRenderer((list, value, index, isSelected, cellHasFocus) -> 
            new JLabel(value != null ? value.getTenPhong() : ""));
    }
    
    private void loadTableData() {
        tableModel.setRowCount(0);
        
        List<LichChieu> dsLichChieu = lichChieuDAO.layDanhSachLichChieu();
        
        for (LichChieu lichChieu : dsLichChieu) {
            Phim phim = phimDAO.timPhim(lichChieu.getMaPhim().getMaPhim());
            
            String maPhong = lichChieu.getMaPhong().getMaPhong();
            Phong phong = null;
            for (Phong p : phongDAO.layDanhSachPhong()) {
                if (p.getMaPhong().equals(maPhong)) {
                    phong = p;
                    break;
                }
            }
            
            String tenPhim = phim != null ? phim.getTenPhim() : "";
            String tenPhong = phong != null ? phong.getTenPhong() : "";
            
            Object[] rowData = {
                lichChieu.getMaLichChieu(),
                tenPhim,
                tenPhong,
                dateFormat.format(lichChieu.getThoiGianBD()),
                dateFormat.format(lichChieu.getThoiGianKT())
            };
            tableModel.addRow(rowData);
        }
    }
    
    private void chonDongDuaVaoInput(int row) {
        String maLichChieu = tableModel.getValueAt(row, 0).toString();
        String tenPhim = tableModel.getValueAt(row, 1).toString();
        String tenPhong = tableModel.getValueAt(row, 2).toString();
        String thoiGianBD = tableModel.getValueAt(row, 3).toString();
        String thoiGianKT = tableModel.getValueAt(row, 4).toString();
        
        maLichChieuField.setText(maLichChieu);
        thoiGianBDField.setText(thoiGianBD);
        thoiGianKTField.setText(thoiGianKT);
        
        maLichChieuField.setEditable(false);
        maLichChieuField.setBackground(new Color(240, 240, 240));
        
        for (int i = 0; i < phimComboBox.getItemCount(); i++) {
            Phim phim = phimComboBox.getItemAt(i);
            if (phim.getTenPhim().equals(tenPhim)) {
                phimComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        for (int i = 0; i < phongComboBox.getItemCount(); i++) {
            Phong phong = phongComboBox.getItemAt(i);
            if (phong.getTenPhong().equals(tenPhong)) {
                phongComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    

    private void addLichChieu() {
        try {
            String maLichChieu = maLichChieuField.getText().trim();
            Phim phim = (Phim) phimComboBox.getSelectedItem();
            Phong phong = (Phong) phongComboBox.getSelectedItem();
            Date thoiGianBD = dateFormat.parse(thoiGianBDField.getText().trim());
            Date thoiGianKT = dateFormat.parse(thoiGianKTField.getText().trim());
            
            if (maLichChieu.isEmpty() || phim == null || phong == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (thoiGianBD.after(thoiGianKT)) {
                JOptionPane.showMessageDialog(this, "Thời gian bắt đầu phải trước thời gian kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean result = lichChieuDAO.themLichChieu(
                maLichChieu,
                phim.getMaPhim(),
                phong.getMaPhong(),
                new Timestamp(thoiGianBD.getTime()),
                new Timestamp(thoiGianKT.getTime())
            );
            
            if (result) {
                Object[] rowData = {
                    maLichChieu,
                    phim.getTenPhim(),
                    phong.getTenPhong(),
                    dateFormat.format(thoiGianBD),
                    dateFormat.format(thoiGianKT)
                };
                tableModel.addRow(rowData);
                
                JOptionPane.showMessageDialog(this, "Thêm lịch chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void capNhatLichChieu() {
        try {
            int selectedRow = table_LichChieu.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String maLichChieu = maLichChieuField.getText().trim();
            Phim phim = (Phim) phimComboBox.getSelectedItem();
            Phong phong = (Phong) phongComboBox.getSelectedItem();
            Date thoiGianBD = dateFormat.parse(thoiGianBDField.getText().trim());
            Date thoiGianKT = dateFormat.parse(thoiGianKTField.getText().trim());
            
            if (maLichChieu.isEmpty() || phim == null || phong == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (thoiGianBD.after(thoiGianKT)) {
                JOptionPane.showMessageDialog(this, "Thời gian bắt đầu phải trước thời gian kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean result = lichChieuDAO.suaLichChieu(
                maLichChieu,
                phim.getMaPhim(),
                phong.getMaPhong(),
                new Timestamp(thoiGianBD.getTime()),
                new Timestamp(thoiGianKT.getTime())
            );
            
            if (result) {
                int row = table_LichChieu.getSelectedRow();
                tableModel.setValueAt(maLichChieu, row, 0);
                tableModel.setValueAt(phim.getTenPhim(), row, 1);
                tableModel.setValueAt(phong.getTenPhong(), row, 2);
                tableModel.setValueAt(dateFormat.format(thoiGianBD), row, 3);
                tableModel.setValueAt(dateFormat.format(thoiGianKT), row, 4);
                
                JOptionPane.showMessageDialog(this, "Cập nhật lịch chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void xoaLichChieu() {
        int selectedRow = table_LichChieu.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String maLichChieu = tableModel.getValueAt(selectedRow, 0).toString();
        
        int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa lịch chiếu này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            boolean result = lichChieuDAO.xoaLichChieu(maLichChieu);
            if (result) {
                int dongHienTai = table_LichChieu.convertRowIndexToModel(selectedRow);
                tableModel.removeRow(dongHienTai);
                
                JOptionPane.showMessageDialog(this, "Xóa lịch chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "Lịch chiếu đã có người đặt xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchShowtime() {
        String keyword = searchField.getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }
        
        tableModel.setRowCount(0);
        
        List<LichChieu> dsLichChieu = lichChieuDAO.layDanhSachLichChieu();
        
        for (LichChieu lichChieu : dsLichChieu) {
            Phim phim = phimDAO.timPhim(lichChieu.getMaPhim().getMaPhim());
            
            String maPhong = lichChieu.getMaPhong().getMaPhong();
            Phong phong = null;
            for (Phong p : phongDAO.layDanhSachPhong()) {
                if (p.getMaPhong().equals(maPhong)) {
                    phong = p;
                    break;
                }
            }
            
            String tenPhim = phim != null ? phim.getTenPhim() : "";
            String tenPhong = phong != null ? phong.getTenPhong() : "";
            
            if (tenPhim.toLowerCase().contains(keyword)) {
                Object[] rowData = {
                    lichChieu.getMaLichChieu(),
                    tenPhim,
                    tenPhong,
                    dateFormat.format(lichChieu.getThoiGianBD()),
                    dateFormat.format(lichChieu.getThoiGianKT())
                };
                tableModel.addRow(rowData);
            }
        }
    }
    
    private void xoaRong() {
        maLichChieuField.setText("");
        thoiGianBDField.setText("");
        thoiGianKTField.setText("");
        searchField.setText("");
        if (phimComboBox.getItemCount() > 0) phimComboBox.setSelectedIndex(0);
        if (phongComboBox.getItemCount() > 0) phongComboBox.setSelectedIndex(0);
        table_LichChieu.clearSelection();
        
        maLichChieuField.setEditable(true);
        maLichChieuField.setBackground(Color.WHITE);
    }
}
