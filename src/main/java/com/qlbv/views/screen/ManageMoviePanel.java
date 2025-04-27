package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
        
        init();
        loadData();
    }
    
    private void init() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
       
        JPanel pnlContent = new JPanel(new BorderLayout(10, 0));
        
        
        JPanel pnlForm = createFormPanel();
        pnlContent.add(pnlForm, BorderLayout.WEST);
        
        
        JPanel pnlMovieTable = createMovieTablePanel();
        pnlContent.add(pnlMovieTable, BorderLayout.CENTER);
        
        add(pnlContent, BorderLayout.CENTER);
    }
    
    
    // TAO INPUT NHAAPJ PHIM
    private JPanel createFormPanel() {
        JPanel pnlForm = new JPanel(new BorderLayout());
        pnlForm.setPreferredSize(new Dimension(350, 500));
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        // TITLE
        JLabel jlbTitle = new JLabel("THÔNG TIN PHIM", SwingConstants.CENTER);
        jlbTitle.setFont(new Font("Arial", Font.BOLD, 16));
        jlbTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pnlForm.add(jlbTitle, BorderLayout.NORTH);
        
        // Form fields
        JPanel pnlFields = new JPanel();
        pnlFields.setLayout(null);

        
        JLabel jlbMovieId = new JLabel("Mã phim:");
        jlbMovieId.setFont(new Font("Arial", Font.BOLD, 13));
        
        txtMovieId = new JTextField(10);
        txtMovieId.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMovieId.setPreferredSize(new Dimension(0, 25));
        
        jlbMovieId.setBounds(0,30,100,20);
        txtMovieId.setBounds(100,30,200,20);

        // Movie Name
        JLabel jlbMovieName = new JLabel("Tên phim:");
        jlbMovieName.setFont(new Font("Arial", Font.BOLD, 13));
 
        
        txtMovieName = new JTextField(20);
        txtMovieName.setFont(new Font("Arial", Font.PLAIN, 13));
        txtMovieName.setPreferredSize(new Dimension(0, 25));

        
        jlbMovieName.setBounds(0,60,100,20);
        txtMovieName.setBounds(100,60,200,20);
        
        // LOAI PHIMM
        JLabel jlbMovieType = new JLabel("Thể loại:");
        jlbMovieType.setFont(new Font("Arial", Font.BOLD, 13));


        cboMovieType = new JComboBox<>();
        cboMovieType.setFont(new Font("Arial", Font.PLAIN, 13));
        cboMovieType.setPreferredSize(new Dimension(0, 25));

        
        jlbMovieType.setBounds(0,90,100,20);
        cboMovieType.setBounds(100,90,200,20);
        
        
        // THOIW LUONG PHIM

        JLabel jlbDuration = new JLabel("Thời lượng (phút):");
        jlbDuration.setFont(new Font("Arial", Font.BOLD, 13));

        spnDuration = new JSpinner(new SpinnerNumberModel(90, 1, 300, 1));
        spnDuration.setFont(new Font("Arial", Font.PLAIN, 13));
        spnDuration.setPreferredSize(new Dimension(200, 25));
        
        jlbDuration.setBounds(0,120,100,20);
        spnDuration.setBounds(100,120,200,25);
        
        
        
        // DO TUOI GIOI HAN

        JLabel jlbAgeLimit = new JLabel("Giới hạn tuổi:");
        jlbAgeLimit.setFont(new Font("Arial", Font.BOLD, 13));

        spnAgeLimit = new JSpinner(new SpinnerNumberModel(13, 0, 1000, 1));
        spnAgeLimit.setFont(new Font("Arial", Font.PLAIN, 13));
        spnAgeLimit.setPreferredSize(new Dimension(200, 25));

        
        jlbAgeLimit.setBounds(0,150,100,20);
        spnAgeLimit.setBounds(100,150,200,25);
        
        // GIAS GOC

        JLabel jlbBasePrice = new JLabel("Giá gốc (VNĐ):");
        jlbBasePrice.setFont(new Font("Arial", Font.BOLD, 13));


        spnBasePrice = new JSpinner(new SpinnerNumberModel(75000.0, 10000.0, 500000.0, 1000.0));
        spnBasePrice.setFont(new Font("Arial", Font.PLAIN, 13));
        spnBasePrice.setPreferredSize(new Dimension(200, 25));
        // Make the spinner editor more suitable for currency values
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spnBasePrice, "#,##0.0");
        spnBasePrice.setEditor(editor);
        
        jlbBasePrice.setBounds(0,180,100,20);
        spnBasePrice.setBounds(100,180,200,25);
 
        // DDUONG DAN ANH

        JLabel jlbImagePath = new JLabel("Ảnh phim:");
        jlbImagePath.setFont(new Font("Arial", Font.BOLD, 13));

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
        

        jlbImagePath.setBounds(0,210,100,20);
        pnlImagePath.setBounds(100,210,200,20);
    
        // Add a wide Clear button
        JButton btnClearFields = new JButton("Xóa trắng form");
        btnClearFields.setPreferredSize(new Dimension(300, 35));
        btnClearFields.setBackground(new Color(243, 156, 18)); // Orange color
        btnClearFields.setForeground(Color.WHITE);
        btnClearFields.setFocusPainted(false);
        btnClearFields.setFont(new Font("Arial", Font.BOLD, 14));
        btnClearFields.addActionListener(e -> clearForm());
        btnClearFields.setBounds(0, 250, 300, 35);
        
        pnlFields.add(btnClearFields);
        
        pnlFields.add(jlbMovieId);
        pnlFields.add(txtMovieId);
        
        pnlFields.add(jlbMovieName);
        pnlFields.add(txtMovieName);
        
        
        pnlFields.add(jlbMovieType);
        pnlFields.add(cboMovieType);
        
        pnlFields.add(jlbDuration);
        pnlFields.add(spnDuration);
        
        pnlFields.add(jlbAgeLimit);
        pnlFields.add(spnAgeLimit);
      
        
        pnlFields.add(jlbBasePrice);
        pnlFields.add(spnBasePrice);
      
        pnlFields.add(jlbImagePath);
        pnlFields.add(pnlImagePath);
      
        
        
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
        
        btnAdd.addActionListener(e -> addMovie());
        btnSave.addActionListener(e -> saveMovie());
        btnDelete.addActionListener(e -> deleteMovie());
        btnClear.addActionListener(e -> clearForm());
        
        return pnlForm;
    }
    
    private JPanel createMovieTablePanel() {
        JPanel pnlMovieTable = new JPanel(new BorderLayout(0, 10));
        
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
        
        JPanel pnlTableActions = new JPanel(new BorderLayout(0, 10));
        pnlTableActions.add(pnlSearch, BorderLayout.NORTH);
        
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
        
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("Mã phim");
        tableModel.addColumn("Tên phim");
        tableModel.addColumn("Thể loại");
        tableModel.addColumn("Thời lượng");
        tableModel.addColumn("Giới hạn tuổi");
        tableModel.addColumn("Giá vé");
        
        tblMovies = new JTable(tableModel);
        tblMovies.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblMovies.setRowHeight(35);
        tblMovies.setFont(new Font("Arial", Font.PLAIN, 14));
        
        tblMovies.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tblMovies.getSelectedRow();
                    if (selectedRow != -1 && tblMovies.getSelectedRowCount() == 1) {
                        populateFormFromSelectedRow(selectedRow);
                    } else if (tblMovies.getSelectedRowCount() > 1) {
                        clearForm();
                        btnDelete.setEnabled(true);
                    }
                }
            }
        });
        
        JTableHeader header = tblMovies.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(52, 152, 219));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tblMovies.getColumnCount(); i++) {
            tblMovies.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        tblMovies.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblMovies.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        JScrollPane scrollPane = new JScrollPane(tblMovies);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(0, 35 * 10 + 40)); 
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
        
        String maPhim = tblMovies.getValueAt(selectedRow, 0).toString();
        Phim selectedPhim = null;
        for (Phim phim : danhSachPhim) {
            if (phim.getMaPhim().equals(maPhim)) {
                selectedPhim = phim;
                break;
            }
        }
        
        if (selectedPhim != null) {
            for (int i = 0; i < cboMovieType.getItemCount(); i++) {
                String loaiPhim = cboMovieType.getItemAt(i);
                if (loaiPhim.equals(selectedPhim.getLoaiPhim().getTenLoai())) {
                    cboMovieType.setSelectedIndex(i);
                    break;
                }
            }
            
            spnDuration.setValue(selectedPhim.getThoiLuong());
            spnAgeLimit.setValue(selectedPhim.getGioiHanTuoi());
            spnBasePrice.setValue(selectedPhim.getGiaGoc());
            txtImagePath.setText(selectedPhim.getImg());
            
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
        
        isEditMode = false;
        txtMovieId.setEditable(true);
        btnAdd.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(false);
        
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
        
        String selectedType = (String) cboMovieType.getSelectedItem();
        LoaiPhim loaiPhim = null;
        
        for (LoaiPhim lp : danhSachLoaiPhim) {
            if (lp.getTenLoai().equals(selectedType)) {
                loaiPhim = lp;
                break;
            }
        }
        
        if (loaiPhim == null) {
            JOptionPane.showMessageDialog(this, 
                    "Lỗi: Không tìm thấy thể loại phim. Vui lòng thử lại.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
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
        
        String selectedType = (String) cboMovieType.getSelectedItem();
        LoaiPhim loaiPhim = null;
        
        for (LoaiPhim lp : danhSachLoaiPhim) {
            if (lp.getTenLoai().equals(selectedType)) {
                loaiPhim = lp;
                break;
            }
        }
        
        if (loaiPhim == null) {
            JOptionPane.showMessageDialog(this, 
                    "Lỗi: Không tìm thấy thể loại phim. Vui lòng thử lại.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
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
                "Bạn có chắc chắn muốn xóa ", 
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

