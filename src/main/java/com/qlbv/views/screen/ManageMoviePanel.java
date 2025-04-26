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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.qlbv.model.dao.LoaiPhimDAO;
import com.qlbv.model.dao.PhimDAO;
import com.qlbv.model.entities.LoaiPhim;
import com.qlbv.model.entities.Phim;

public class ManageMoviePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    // Table components
    private JTextField txtSearch;
    private JButton btnSearch, btnAdd, btnSave, btnDelete, btnClear;
    private JTable tblMovies;
    private DefaultTableModel tableModel;
    
    // Form components
    private JTextField txtMovieId, txtMovieName;
    private JTextField txtImagePath;
    private JButton btnBrowseImage;
    private JFileChooser fileChooser;
    private JComboBox<String> cboMovieType;
    private JSpinner spnDuration, spnAgeLimit, spnBasePrice;
    
    private PhimDAO phimDAO;
    private LoaiPhimDAO loaiPhimDAO;
    private List<Phim> danhSachPhim;
    private List<LoaiPhim> danhSachLoaiPhim;
    
    private boolean isEditMode = false;
    
    // Money formatter
    private DecimalFormat moneyFormat = new DecimalFormat("#,###");
    
    public ManageMoviePanel() {
        phimDAO = new PhimDAO();
        loaiPhimDAO = new LoaiPhimDAO();
        danhSachPhim = new ArrayList<>();
        danhSachLoaiPhim = new ArrayList<>();
        
        initializeUI();
        loadData();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        // Main content panel (left: form, right: table)
        JPanel pnlContent = new JPanel(new BorderLayout(10, 0));
        
        // Form Panel (Left)
        JPanel pnlForm = createFormPanel();
        pnlContent.add(pnlForm, BorderLayout.WEST);
        
        // Movie Table Panel (Right)
        JPanel pnlMovieTable = createMovieTablePanel();
        pnlContent.add(pnlMovieTable, BorderLayout.CENTER);
        
        add(pnlContent, BorderLayout.CENTER);
    }
    
    private JPanel createFormPanel() {
        JPanel pnlForm = new JPanel(new BorderLayout(0, 10));
        pnlForm.setPreferredSize(new Dimension(350, 500));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // Form title
        JLabel lblFormTitle = new JLabel("THÔNG TIN PHIM", SwingConstants.CENTER);
        lblFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pnlForm.add(lblFormTitle, BorderLayout.NORTH);
        
        // Form fields
        JPanel pnlFields = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Movie ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblMovieId = new JLabel("Mã phim:");
        lblMovieId.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblMovieId, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtMovieId = new JTextField(10);
        txtMovieId.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMovieId.setPreferredSize(new Dimension(0, 25));
        pnlFields.add(txtMovieId, gbc);
        
        // Movie Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        JLabel lblMovieName = new JLabel("Tên phim:");
        lblMovieName.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblMovieName, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtMovieName = new JTextField(20);
        txtMovieName.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMovieName.setPreferredSize(new Dimension(0, 25));
        pnlFields.add(txtMovieName, gbc);
        
        // Movie Type
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        JLabel lblMovieType = new JLabel("Thể loại:");
        lblMovieType.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblMovieType, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboMovieType = new JComboBox<>();
        cboMovieType.setFont(new Font("Arial", Font.PLAIN, 13));
        cboMovieType.setPreferredSize(new Dimension(0, 25));
        pnlFields.add(cboMovieType, gbc);
        
        // Duration
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        JLabel lblDuration = new JLabel("Thời lượng (phút):");
        lblDuration.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblDuration, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        spnDuration = new JSpinner(new SpinnerNumberModel(90, 1, 300, 1));
        spnDuration.setFont(new Font("Arial", Font.PLAIN, 13));
        spnDuration.setPreferredSize(new Dimension(0, 25));
        pnlFields.add(spnDuration, gbc);
        
        // Age Limit
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        JLabel lblAgeLimit = new JLabel("Giới hạn tuổi:");
        lblAgeLimit.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblAgeLimit, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        spnAgeLimit = new JSpinner(new SpinnerNumberModel(13, 0, 21, 1));
        spnAgeLimit.setFont(new Font("Arial", Font.PLAIN, 13));
        spnAgeLimit.setPreferredSize(new Dimension(0, 25));
        pnlFields.add(spnAgeLimit, gbc);
        
        // Base Price
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        JLabel lblBasePrice = new JLabel("Giá gốc (VNĐ):");
        lblBasePrice.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblBasePrice, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        spnBasePrice = new JSpinner(new SpinnerNumberModel(75000.0, 10000.0, 500000.0, 1000.0));
        spnBasePrice.setFont(new Font("Arial", Font.PLAIN, 13));
        spnBasePrice.setPreferredSize(new Dimension(0, 25));
        pnlFields.add(spnBasePrice, gbc);
        
        // Image Path
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0.0;
        JLabel lblImagePath = new JLabel("Ảnh phim:");
        lblImagePath.setFont(new Font("Arial", Font.BOLD, 13));
        pnlFields.add(lblImagePath, gbc);
        
        // Panel for image path and browse button
        JPanel pnlImagePath = new JPanel(new BorderLayout(5, 0));
        
        txtImagePath = new JTextField(20);
        txtImagePath.setFont(new Font("Arial", Font.PLAIN, 13));
        txtImagePath.setEditable(false);
        txtImagePath.setPreferredSize(new Dimension(0, 25));
        
        btnBrowseImage = new JButton("Chọn...");
        btnBrowseImage.setPreferredSize(new Dimension(80, 25));
        btnBrowseImage.setBackground(new Color(52, 152, 219));
        btnBrowseImage.setForeground(Color.WHITE);
        btnBrowseImage.setFocusPainted(false);
        
        // Initialize file chooser
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Hình ảnh (*.jpg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));
        
        // Add action listener for browse button
        btnBrowseImage.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(ManageMoviePanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtImagePath.setText(fileChooser.getSelectedFile().getPath());
            }
        });
        
        pnlImagePath.add(txtImagePath, BorderLayout.CENTER);
        pnlImagePath.add(btnBrowseImage, BorderLayout.EAST);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        pnlFields.add(pnlImagePath, gbc);
        
        pnlForm.add(pnlFields, BorderLayout.CENTER);
        
        // Button Panel
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        btnAdd = createButton("Thêm", new Color(46, 204, 113));
        btnSave = createButton("Lưu", new Color(52, 152, 219));
        btnDelete = createButton("Xóa", new Color(231, 76, 60));
        btnClear = createButton("Làm mới", new Color(155, 89, 182));
        
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnSave);
        pnlButtons.add(btnDelete);
        pnlButtons.add(btnClear);
        
        pnlForm.add(pnlButtons, BorderLayout.SOUTH);
        
        // Add action listeners for buttons
        btnAdd.addActionListener(e -> addMovie());
        btnSave.addActionListener(e -> saveMovie());
        btnDelete.addActionListener(e -> deleteMovie());
        btnClear.addActionListener(e -> clearForm());
        
        return pnlForm;
    }
    
    private JPanel createMovieTablePanel() {
        JPanel pnlMovieTable = new JPanel(new BorderLayout(0, 10));
        
        // Search Panel
        JPanel pnlSearch = new JPanel(new BorderLayout(10, 0));
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(0, 35));
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                txtSearch.getBorder(), 
                BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSearch();
                }
            }
        });
        
        btnSearch = new JButton("Tìm kiếm");
        btnSearch.setPreferredSize(new Dimension(120, 35));
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnSearch.addActionListener(e -> performSearch());
        
        pnlSearch.add(txtSearch, BorderLayout.CENTER);
        pnlSearch.add(btnSearch, BorderLayout.EAST);
        
        // Table Actions Panel
        JPanel pnlTableActions = new JPanel(new BorderLayout(0, 10));
        pnlTableActions.add(pnlSearch, BorderLayout.NORTH);
        
        // Bulk Delete Button
        JButton btnBulkDelete = new JButton("Xóa phim");
        btnBulkDelete.setPreferredSize(new Dimension(150, 35));
        btnBulkDelete.setBackground(new Color(231, 76, 60));
        btnBulkDelete.setForeground(Color.WHITE);
        btnBulkDelete.setFocusPainted(false);
        btnBulkDelete.setFont(new Font("Arial", Font.BOLD, 14));
        btnBulkDelete.addActionListener(e -> deleteSelectedMovies());
        
        JPanel pnlBulkActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBulkActions.add(btnBulkDelete);
        pnlTableActions.add(pnlBulkActions, BorderLayout.SOUTH);
        
        pnlMovieTable.add(pnlTableActions, BorderLayout.NORTH);
        
        // Table Panel
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Define table columns based on movie properties
        tableModel.addColumn("Mã phim");
        tableModel.addColumn("Tên phim");
        tableModel.addColumn("Thể loại");
        tableModel.addColumn("Thời lượng");
        tableModel.addColumn("Giới hạn tuổi");
        tableModel.addColumn("Giá vé");
        
        tblMovies = new JTable(tableModel);
        // Cho phép chọn nhiều dòng
        tblMovies.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblMovies.setRowHeight(35);
        tblMovies.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Add selection listener to update form when row is selected
        tblMovies.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblMovies.getSelectedRow();
                    if (selectedRow != -1 && tblMovies.getSelectedRowCount() == 1) {
                        // Chỉ hiển thị thông tin khi chọn đúng 1 dòng
                        populateFormFromSelectedRow(selectedRow);
                    } else if (tblMovies.getSelectedRowCount() > 1) {
                        // Xóa form nếu chọn nhiều dòng
                        clearForm();
                        btnDelete.setEnabled(true);
                    }
                }
            }
        });
        
        // Set header style
        JTableHeader header = tblMovies.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tblMovies.getColumnCount(); i++) {
            tblMovies.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Adjust column widths
        tblMovies.getColumnModel().getColumn(0).setPreferredWidth(80); // Mã phim
        tblMovies.getColumnModel().getColumn(1).setPreferredWidth(200); // Tên phim
        
        JScrollPane scrollPane = new JScrollPane(tblMovies);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // Thiết lập kích thước cho scroll pane để hiển thị tối đa 10 dòng, nếu nhiều hơn thì cuộn
        scrollPane.setPreferredSize(new Dimension(0, 35 * 10 + 40)); // 10 rows + header height
        pnlMovieTable.add(scrollPane, BorderLayout.CENTER);
        
        return pnlMovieTable;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 35));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
    
    public void loadData() {
        danhSachPhim = phimDAO.layDanhSachPhim();
        danhSachLoaiPhim = loaiPhimDAO.layDanhSachLoaiPhim();
        
        // Add movie types to combobox
        cboMovieType.removeAllItems();
        for (LoaiPhim loaiPhim : danhSachLoaiPhim) {
            cboMovieType.addItem(loaiPhim.getTenLoai());
        }
        
        updateTableData();
        clearForm();
    }
    
    private void updateTableData() {
        tableModel.setRowCount(0);
        
        for (Phim phim : danhSachPhim) {
            String tenLoai = "";
            
            // Find the corresponding LoaiPhim
            for (LoaiPhim loaiPhim : danhSachLoaiPhim) {
                if (loaiPhim.getMaLoai().equals(phim.getLoaiPhim().getMaLoai())) {
                    tenLoai = loaiPhim.getTenLoai();
                    break;
                }
            }
            
            tableModel.addRow(new Object[] {
                phim.getMaPhim(),
                phim.getTenPhim(),
                tenLoai,
                phim.getThoiLuong() + " phút",
                phim.getGioiHanTuoi() + "+",
                moneyFormat.format(phim.getGiaGoc()) + " VNĐ"
            });
        }
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }
        
        List<Phim> filteredList = new ArrayList<>();
        
        for (Phim phim : phimDAO.layDanhSachPhim()) {
            if (phim.getMaPhim().toLowerCase().contains(keyword.toLowerCase()) ||
                phim.getTenPhim().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(phim);
            }
        }
        
        danhSachPhim = filteredList;
        updateTableData();
    }
    
    private void populateFormFromSelectedRow(int selectedRow) {
        txtMovieId.setText(tblMovies.getValueAt(selectedRow, 0).toString());
        txtMovieName.setText(tblMovies.getValueAt(selectedRow, 1).toString());
        
        // Find the selected movie
        String maPhim = tblMovies.getValueAt(selectedRow, 0).toString();
        Phim selectedPhim = null;
        for (Phim phim : danhSachPhim) {
            if (phim.getMaPhim().equals(maPhim)) {
                selectedPhim = phim;
                break;
            }
        }
        
        if (selectedPhim != null) {
            // Set the movie type in the combo box
            for (int i = 0; i < cboMovieType.getItemCount(); i++) {
                String loaiPhim = cboMovieType.getItemAt(i);
                if (loaiPhim.equals(selectedPhim.getLoaiPhim().getTenLoai())) {
                    cboMovieType.setSelectedIndex(i);
                    break;
                }
            }
            
            // Set spinner values
            spnDuration.setValue(selectedPhim.getThoiLuong());
            spnAgeLimit.setValue(selectedPhim.getGioiHanTuoi());
            spnBasePrice.setValue(selectedPhim.getGiaGoc());
            txtImagePath.setText(selectedPhim.getImg());
            
            // Switch to edit mode
            isEditMode = true;
            txtMovieId.setEditable(false);
            btnAdd.setEnabled(false);
            btnSave.setEnabled(true);
            btnDelete.setEnabled(true);
        }
    }
    
    private void clearForm() {
        txtMovieId.setText("");
        txtMovieName.setText("");
        if (cboMovieType.getItemCount() > 0) {
            cboMovieType.setSelectedIndex(0);
        }
        spnDuration.setValue(90);
        spnAgeLimit.setValue(13);
        spnBasePrice.setValue(75000.0);
        txtImagePath.setText("");
        
        // Reset edit mode
        isEditMode = false;
        txtMovieId.setEditable(true);
        btnAdd.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        
        // Clear table selection
        if (tblMovies.getRowCount() > 0) {
            tblMovies.clearSelection();
        }
    }
    
    /**
     * Thêm phim mới vào cơ sở dữ liệu
     */
    private void addMovie() {
        if (!validateInputs()) {
            return;
        }
        
        String maPhim = txtMovieId.getText().trim();
        String tenPhim = txtMovieName.getText().trim();
        LoaiPhim loaiPhim = (LoaiPhim) cboMovieType.getSelectedItem();
        int thoiLuong = (Integer) spnDuration.getValue();
        int gioiHanTuoi = (Integer) spnAgeLimit.getValue();
        double giaGoc = (Double) spnBasePrice.getValue();
        String img = txtImagePath.getText().trim();
        
        for (Phim phim : danhSachPhim) {
            if (phim.getMaPhim().equals(maPhim)) {
                JOptionPane.showMessageDialog(this, 
                        "Mã phim '" + maPhim + "' đã tồn tại. Vui lòng nhập mã phim khác.", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtMovieId.requestFocus();
                return;
            }
        }
        
        boolean success = phimDAO.themPhim(maPhim, loaiPhim.getMaLoai(), tenPhim, thoiLuong, gioiHanTuoi, giaGoc, img);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Thêm phim thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phim thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Cập nhật thông tin phim trong cơ sở dữ liệu
     */
    private void saveMovie() {
        if (!isEditMode) {
            addMovie();
            return;
        }
        
        if (!validateInputs()) {
            return;
        }
        
        String maPhim = txtMovieId.getText().trim();
        String tenPhim = txtMovieName.getText().trim();
        LoaiPhim loaiPhim = (LoaiPhim) cboMovieType.getSelectedItem();
        int thoiLuong = (Integer) spnDuration.getValue();
        int gioiHanTuoi = (Integer) spnAgeLimit.getValue();
        double giaGoc = (Double) spnBasePrice.getValue();
        String img = txtImagePath.getText().trim();
        
        boolean success = phimDAO.suaPhim(maPhim, loaiPhim.getMaLoai(), tenPhim, thoiLuong, gioiHanTuoi, giaGoc, img);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Cập nhật phim thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật phim thất bại. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Xóa phim khỏi cơ sở dữ liệu
     */
    private void deleteMovie() {
        int[] selectedRows = tblMovies.getSelectedRows();
        
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn ít nhất một phim để xóa", 
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        StringBuilder movieListText = new StringBuilder();
        List<String> movieIdsToDelete = new ArrayList<>();
        
        for (int row : selectedRows) {
            String maPhim = tblMovies.getValueAt(row, 0).toString();
            String tenPhim = tblMovies.getValueAt(row, 1).toString();
            movieIdsToDelete.add(maPhim);
            movieListText.append("- ").append(tenPhim).append(" (Mã: ").append(maPhim).append(")\n");
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Bạn có chắc chắn muốn xóa " + selectedRows.length + " phim sau?\n\n" + movieListText.toString() +
                "\nCảnh báo: Hành động này sẽ xóa vĩnh viễn phim khỏi cơ sở dữ liệu!", 
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            int successCount = 0;
            List<String> failedMovies = new ArrayList<>();
            
            for (String maPhim : movieIdsToDelete) {
                boolean success = phimDAO.xoaPhim(maPhim);
                
                if (success) {
                    successCount++;
                } else {
                    for (int row : selectedRows) {
                        if (tblMovies.getValueAt(row, 0).toString().equals(maPhim)) {
                            failedMovies.add(tblMovies.getValueAt(row, 1).toString() + " (Mã: " + maPhim + ")");
                            break;
                        }
                    }
                }
            }
            
            if (successCount >0) {
                JOptionPane.showMessageDialog(this, 
                        "Đã xóa thành công " + successCount + " phim khỏi cơ sở dữ liệu.", 
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        successCount= 0;
            } else {
                StringBuilder message = new StringBuilder();
                message.append("Đã xóa thành công ").append(successCount).append(" phim.\n");
                message.append("Không thể xóa ").append(failedMovies.size()).append(" phim sau:\n");
                
                for (String movie : failedMovies) {
                    message.append("- ").append(movie).append("\n");
                }
                message.append("\nNguyên nhân có thể do phim đang được sử dụng ở nơi khác hoặc có ràng buộc trong cơ sở dữ liệu.");
                
                JOptionPane.showMessageDialog(this, message.toString(), 
                        "Kết quả xóa", JOptionPane.WARNING_MESSAGE);
            }
            
            loadData();
        }
    }
    
    private void deleteSelectedMovies() {
        deleteMovie();
    }
    
    private boolean validateInputs() {
        if (txtMovieId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phim", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMovieId.requestFocus();
            return false;
        }
        
        if (txtMovieName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên phim", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMovieName.requestFocus();
            return false;
        }
        
        if (cboMovieType.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thể loại phim", "Lỗi", JOptionPane.ERROR_MESSAGE);
            cboMovieType.requestFocus();
            return false;
        }
        
        return true;
    }
}

