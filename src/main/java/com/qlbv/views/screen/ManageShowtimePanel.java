package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
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
    private JTable showtimeTable;
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
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 242, 245));
        
        // Khởi tạo DAO
        lichChieuDAO = new LichChieuDAO();
        phimDAO = new PhimDAO();
        phongDAO = new PhongDAO();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        // Tạo các thành phần
        createFormPanel();
        createTablePanel();
        
        // Thêm các thành phần vào panel chính
        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        
        // Load dữ liệu vào bảng
        loadTableData();
    }
    
    private void createFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Thông tin lịch chiếu",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        formPanel.setBackground(Color.WHITE);
        formPanel.setMinimumSize(new Dimension(800, 200));
        formPanel.setPreferredSize(new Dimension(900, 200));
        
        // Panel cho các trường nhập liệu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Mã lịch chiếu
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã lịch chiếu:"), gbc);
        
        maLichChieuField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(maLichChieuField, gbc);
        
        // Phim
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Phim:"), gbc);
        
        phimComboBox = new JComboBox<>();
        loadPhimComboBox();
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(phimComboBox, gbc);
        
        // Phòng
        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Phòng:"), gbc);
        
        phongComboBox = new JComboBox<>();
        loadPhongComboBox();
        gbc.gridx = 3;
        gbc.gridy = 0;
        inputPanel.add(phongComboBox, gbc);
        
        // Thời gian BD
        gbc.gridx = 2;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Thời gian bắt đầu (dd/MM/yyyy HH:mm):"), gbc);
        
        thoiGianBDField = new JTextField(20);
        gbc.gridx = 3;
        gbc.gridy = 1;
        inputPanel.add(thoiGianBDField, gbc);
        
        // Thời gian KT
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Thời gian kết thúc (dd/MM/yyyy HH:mm):"), gbc);
        
        thoiGianKTField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        inputPanel.add(thoiGianKTField, gbc);
        
        formPanel.add(inputPanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Thay thế SplitPane bằng một panel có BorderLayout
        JPanel buttonSearchPanel = new JPanel(new BorderLayout(10, 0));
        buttonSearchPanel.setOpaque(false);
        buttonSearchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Panel trái cho các nút chức năng
        JPanel leftButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftButtonPanel.setOpaque(false);
        
        addButton = new JButton("Thêm");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        
        updateButton = new JButton("Cập nhật");
        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        
        deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        
        clearButton = new JButton("Xóa rỗng");
        clearButton.setBackground(new Color(243, 156, 18));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        
        leftButtonPanel.add(addButton);
        leftButtonPanel.add(updateButton);
        leftButtonPanel.add(deleteButton);
        leftButtonPanel.add(clearButton);
        
        // Panel phải cho tìm kiếm theo tên phim
        JPanel rightSearchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightSearchPanel.setOpaque(false);
        
        JLabel searchLabel = new JLabel("Tìm kiếm phim:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        searchField = new JTextField(15);
        
        searchButton = new JButton("Tìm");
        searchButton.setBackground(new Color(155, 89, 182));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        
        rightSearchPanel.add(searchLabel);
        rightSearchPanel.add(searchField);
        rightSearchPanel.add(searchButton);
        
        // Thêm các panel vào panel chính
        buttonSearchPanel.add(leftButtonPanel, BorderLayout.WEST);
        buttonSearchPanel.add(rightSearchPanel, BorderLayout.EAST);
        
        // Thêm sự kiện cho các nút
        addButton.addActionListener(e -> addShowtime());
        updateButton.addActionListener(e -> updateShowtime());
        deleteButton.addActionListener(e -> deleteShowtime());
        searchButton.addActionListener(e -> searchShowtime());
        clearButton.addActionListener(e -> clearForm());
        
        formPanel.add(buttonSearchPanel);
    }
    
    private void createTablePanel() {
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                "Danh sách lịch chiếu",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        tablePanel.setBackground(Color.WHITE);
        
        // Tạo model và bảng
        String[] columns = {"Mã lịch chiếu", "Phim", "Phòng", "Thời gian bắt đầu", "Thời gian kết thúc"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        showtimeTable = new JTable(tableModel);
        showtimeTable.setSelectionBackground(new Color(52, 152, 219));
        showtimeTable.setSelectionForeground(Color.WHITE);
        showtimeTable.setRowHeight(25);
        showtimeTable.setAutoCreateRowSorter(true);
        
        // Thêm sự kiện chọn dòng trong bảng
        showtimeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = showtimeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showSelectedShowtime(selectedRow);
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(showtimeTable);
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
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);
        
        // Lấy danh sách lịch chiếu
        List<LichChieu> dsLichChieu = lichChieuDAO.layDanhSachLichChieu();
        
        // Thêm dữ liệu vào bảng
        for (LichChieu lichChieu : dsLichChieu) {
            Phim phim = phimDAO.timPhim(lichChieu.getMaPhim().getMaPhim());
            
            // Tìm phòng trong danh sách tất cả phòng
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
    
    private void showSelectedShowtime(int row) {
        String maLichChieu = tableModel.getValueAt(row, 0).toString();
        String tenPhim = tableModel.getValueAt(row, 1).toString();
        String tenPhong = tableModel.getValueAt(row, 2).toString();
        String thoiGianBD = tableModel.getValueAt(row, 3).toString();
        String thoiGianKT = tableModel.getValueAt(row, 4).toString();
        
        maLichChieuField.setText(maLichChieu);
        thoiGianBDField.setText(thoiGianBD);
        thoiGianKTField.setText(thoiGianKT);
        
        // Khóa trường mã lịch chiếu khi chọn một dòng
        maLichChieuField.setEditable(false);
        maLichChieuField.setBackground(new Color(240, 240, 240));
        
        // Chọn phim tương ứng trong combobox
        for (int i = 0; i < phimComboBox.getItemCount(); i++) {
            Phim phim = phimComboBox.getItemAt(i);
            if (phim.getTenPhim().equals(tenPhim)) {
                phimComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Chọn phòng tương ứng trong combobox
        for (int i = 0; i < phongComboBox.getItemCount(); i++) {
            Phong phong = phongComboBox.getItemAt(i);
            if (phong.getTenPhong().equals(tenPhong)) {
                phongComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void addShowtime() {
        try {
            // Lấy dữ liệu từ các trường nhập
            String maLichChieu = maLichChieuField.getText().trim();
            Phim phim = (Phim) phimComboBox.getSelectedItem();
            Phong phong = (Phong) phongComboBox.getSelectedItem();
            Date thoiGianBD = dateFormat.parse(thoiGianBDField.getText().trim());
            Date thoiGianKT = dateFormat.parse(thoiGianKTField.getText().trim());
            
            // Kiểm tra dữ liệu nhập
            if (maLichChieu.isEmpty() || phim == null || phong == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (thoiGianBD.after(thoiGianKT)) {
                JOptionPane.showMessageDialog(this, "Thời gian bắt đầu phải trước thời gian kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Thêm lịch chiếu mới
            boolean result = lichChieuDAO.themLichChieu(
                maLichChieu,
                phim.getMaPhim(),
                phong.getMaPhong(),
                new Timestamp(thoiGianBD.getTime()),
                new Timestamp(thoiGianKT.getTime())
            );
            
            if (result) {
                // Thêm trực tiếp vào bảng thay vì load lại toàn bộ dữ liệu
                Object[] rowData = {
                    maLichChieu,
                    phim.getTenPhim(),
                    phong.getTenPhong(),
                    dateFormat.format(thoiGianBD),
                    dateFormat.format(thoiGianKT)
                };
                tableModel.addRow(rowData);
                
                JOptionPane.showMessageDialog(this, "Thêm lịch chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateShowtime() {
        try {
            // Lấy dòng đang chọn
            int selectedRow = showtimeTable.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần cập nhật!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Lấy dữ liệu từ các trường nhập
            String maLichChieu = maLichChieuField.getText().trim();
            Phim phim = (Phim) phimComboBox.getSelectedItem();
            Phong phong = (Phong) phongComboBox.getSelectedItem();
            Date thoiGianBD = dateFormat.parse(thoiGianBDField.getText().trim());
            Date thoiGianKT = dateFormat.parse(thoiGianKTField.getText().trim());
            
            // Kiểm tra dữ liệu nhập
            if (maLichChieu.isEmpty() || phim == null || phong == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (thoiGianBD.after(thoiGianKT)) {
                JOptionPane.showMessageDialog(this, "Thời gian bắt đầu phải trước thời gian kết thúc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cập nhật lịch chiếu
            boolean result = lichChieuDAO.suaLichChieu(
                maLichChieu,
                phim.getMaPhim(),
                phong.getMaPhong(),
                new Timestamp(thoiGianBD.getTime()),
                new Timestamp(thoiGianKT.getTime())
            );
            
            if (result) {
                // Cập nhật trực tiếp trong bảng thay vì load lại toàn bộ dữ liệu
                int row = showtimeTable.getSelectedRow();
                tableModel.setValueAt(maLichChieu, row, 0);
                tableModel.setValueAt(phim.getTenPhim(), row, 1);
                tableModel.setValueAt(phong.getTenPhong(), row, 2);
                tableModel.setValueAt(dateFormat.format(thoiGianBD), row, 3);
                tableModel.setValueAt(dateFormat.format(thoiGianKT), row, 4);
                
                JOptionPane.showMessageDialog(this, "Cập nhật lịch chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng thời gian không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteShowtime() {
        // Lấy dòng đang chọn
        int selectedRow = showtimeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String maLichChieu = tableModel.getValueAt(selectedRow, 0).toString();
        
        // Xác nhận xóa
        int option = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn xóa lịch chiếu này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            boolean result = lichChieuDAO.xoaLichChieu(maLichChieu);
            if (result) {
                // Xóa trực tiếp từ bảng thay vì load lại toàn bộ dữ liệu
                int actualRow = showtimeTable.convertRowIndexToModel(selectedRow);
                tableModel.removeRow(actualRow);
                
                JOptionPane.showMessageDialog(this, "Xóa lịch chiếu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void searchShowtime() {
        String keyword = searchField.getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }
        
        // Xóa dữ liệu cũ
        tableModel.setRowCount(0);
        
        // Lấy danh sách lịch chiếu
        List<LichChieu> dsLichChieu = lichChieuDAO.layDanhSachLichChieu();
        
        // Lọc và thêm dữ liệu vào bảng - CHỈ TÌM THEO TÊN PHIM
        for (LichChieu lichChieu : dsLichChieu) {
            Phim phim = phimDAO.timPhim(lichChieu.getMaPhim().getMaPhim());
            
            // Tìm phòng trong danh sách tất cả phòng
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
            
            // Chỉ tìm kiếm theo tên phim
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
    
    private void clearForm() {
        maLichChieuField.setText("");
        thoiGianBDField.setText("");
        thoiGianKTField.setText("");
        searchField.setText("");
        if (phimComboBox.getItemCount() > 0) phimComboBox.setSelectedIndex(0);
        if (phongComboBox.getItemCount() > 0) phongComboBox.setSelectedIndex(0);
        showtimeTable.clearSelection();
        
        // Mở khóa trường mã lịch chiếu khi xóa rỗng
        maLichChieuField.setEditable(true);
        maLichChieuField.setBackground(Color.WHITE);
    }
}
