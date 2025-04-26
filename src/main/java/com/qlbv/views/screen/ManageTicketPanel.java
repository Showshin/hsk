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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.qlbv.model.dao.VeDAO;
import com.qlbv.model.entities.Ve;

public class ManageTicketPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    // Table components
    private JTextField txtSearch;
    private JComboBox<String> cboStatus;
    private JComboBox<String> cboTimeFilter;
    private JButton btnSearch;
    private JTable tblTickets;
    private DefaultTableModel tableModel;
    
    private VeDAO veDAO;
    private List<Ve> danhSachVe;
    
    public ManageTicketPanel() {
        veDAO = new VeDAO();
        danhSachVe = new ArrayList<>();
        
        initializeUI();
        loadData();
    }
    
    private void initializeUI() {
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
        
        // Search field
        JLabel lblSearch = new JLabel("Tìm kiếm vé:");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlSearch.add(lblSearch, gbc);
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(200, 30));
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        pnlSearch.add(txtSearch, gbc);
        
        // Status filter
        JLabel lblStatus = new JLabel("Trạng thái:");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 2;
        gbc.weightx = 0.0;
        pnlSearch.add(lblStatus, gbc);
        
        cboStatus = new JComboBox<>(new String[]{"Tất cả", "Đã bán", "Đã dùng", "Đã hủy", "Chờ dùng"});
        cboStatus.setPreferredSize(new Dimension(120, 30));
        cboStatus.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 3;
        pnlSearch.add(cboStatus, gbc);
        
        // Time filter
        JLabel lblTime = new JLabel("Thời gian:");
        lblTime.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 4;
        pnlSearch.add(lblTime, gbc);
        
        cboTimeFilter = new JComboBox<>(new String[]{"Hôm nay", "Tuần này", "Tháng này", "Tất cả"});
        cboTimeFilter.setPreferredSize(new Dimension(120, 30));
        cboTimeFilter.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 5;
        pnlSearch.add(cboTimeFilter, gbc);
        
        // Search button
        btnSearch = new JButton("Tìm");
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 6;
        pnlSearch.add(btnSearch, gbc);
        
        // Add action listeners
        btnSearch.addActionListener(e -> performSearch());
        txtSearch.addKeyListener(new KeyAdapter() {
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
        tableModel.addColumn("Tên phim");
        tableModel.addColumn("Khách hàng");
        tableModel.addColumn("Thời gian");
        tableModel.addColumn("Trạng thái");
        tableModel.addColumn("Thao tác");
        
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
        
        // Center align all columns except the last one
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        for (int i = 0; i < tblTickets.getColumnCount() - 1; i++) {
            tblTickets.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Custom renderer for status column
        tblTickets.getColumnModel().getColumn(4).setCellRenderer(new StatusColumnRenderer());
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(tblTickets);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pnlTable.add(scrollPane, BorderLayout.CENTER);
        
        // Add pagination panel
        JPanel pnlPagination = createPaginationPanel();
        pnlTable.add(pnlPagination, BorderLayout.SOUTH);
        
        return pnlTable;
    }
    
    private JPanel createPaginationPanel() {
        JPanel pnlPagination = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        
        JButton btnFirst = createPaginationButton("<<");
        JButton btnPrev = createPaginationButton("<");
        JButton btnPage1 = createPaginationButton("1");
        JButton btnPage2 = createPaginationButton("2");
        JButton btnPage3 = createPaginationButton("3");
        JButton btnNext = createPaginationButton(">");
        JButton btnLast = createPaginationButton(">>");
        
        // Style current page button
        btnPage1.setBackground(new Color(52, 152, 219));
        btnPage1.setForeground(Color.WHITE);
        
        pnlPagination.add(btnFirst);
        pnlPagination.add(btnPrev);
        pnlPagination.add(btnPage1);
        pnlPagination.add(btnPage2);
        pnlPagination.add(btnPage3);
        pnlPagination.add(btnNext);
        pnlPagination.add(btnLast);
        
        return pnlPagination;
    }
    
    private JButton createPaginationButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(40, 30));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        button.setBackground(Color.WHITE);
        return button;
    }
    
    private void loadData() {
        // Clear table
        tableModel.setRowCount(0);
        
        // Add sample data (replace with actual data from database)
        Object[][] sampleData = {
            {"V001", "Đắc vũ bóng ma", "Trần Văn Bình", "28/04/2025 20:30", "Đã bán", ""},
            {"V002", "The smile have left your eyes", "Lê Thị Hương", "28/04/2025 14:00", "Đã dùng", ""},
            {"V003", "Yêu nhầm bạn thân", "Nguyễn Thành Nam", "30/04/2025 20:30", "Đã hủy", ""},
            {"V004", "Đắc vũ bóng ma", "Phạm Thị Lan", "02/05/2025 14:00", "Chờ dùng", ""}
        };
        
        for (Object[] row : sampleData) {
            tableModel.addRow(row);
        }
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText().trim();
        String status = (String) cboStatus.getSelectedItem();
        String timeFilter = (String) cboTimeFilter.getSelectedItem();
        
        // Implement search logic here
        loadData(); // Temporary: just reload all data
    }
    
    // Custom renderer for status column
    private class StatusColumnRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            java.awt.Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            
            setHorizontalAlignment(SwingConstants.CENTER);
            
            if (value != null) {
                String status = value.toString();
                switch (status) {
                    case "Đã bán":
                        setBackground(new Color(46, 204, 113));
                        setForeground(Color.WHITE);
                        break;
                    case "Đã dùng":
                        setBackground(new Color(52, 152, 219));
                        setForeground(Color.WHITE);
                        break;
                    case "Đã hủy":
                        setBackground(new Color(231, 76, 60));
                        setForeground(Color.WHITE);
                        break;
                    case "Chờ dùng":
                        setBackground(new Color(243, 156, 18));
                        setForeground(Color.WHITE);
                        break;
                    default:
                        setBackground(table.getBackground());
                        setForeground(table.getForeground());
                }
            }
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }
            
            return c;
        }
    }
}
