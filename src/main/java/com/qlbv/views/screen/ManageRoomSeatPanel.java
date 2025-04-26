package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.qlbv.model.dao.GheDAO;
import com.qlbv.model.dao.PhongDAO;
import com.qlbv.model.entities.Ghe;
import com.qlbv.model.entities.Phong;

public class ManageRoomSeatPanel extends JPanel {
    private JPanel mainPanel;
    private JPanel roomListPanel;
    private JPanel seatPanel;
    private JPanel contentPanel;
    private JComboBox<String> roomSelector;
    private JButton addRoomBtn;
    private GheDAO gheDAO;
    private PhongDAO phongDAO;
    private List<Phong> dsPhong;

    public ManageRoomSeatPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gheDAO = new GheDAO();
        phongDAO = new PhongDAO();
        dsPhong = phongDAO.layDanhSachPhong();
        
        createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
    }

    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout(10, 10));

        // Header panel với title và nút thêm phòng
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Quản lý phòng và ghế ngồi", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        addRoomBtn = new JButton("Thêm phòng");
        addRoomBtn.setFont(new Font("Arial", Font.BOLD, 14));
        addRoomBtn.setBackground(new Color(46, 204, 113));
        addRoomBtn.setForeground(Color.WHITE);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addRoomBtn);
        headerPanel.add(buttonPanel, BorderLayout.EAST);

        // Room selector panel
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel roomLabel = new JLabel("Chọn phòng:");
        roomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Tạo danh sách phòng từ database
        String[] roomNames = new String[dsPhong.size()];
        for (int i = 0; i < dsPhong.size(); i++) {
            roomNames[i] = dsPhong.get(i).getTenPhong();
        }
        roomSelector = new JComboBox<>(roomNames);
        roomSelector.setFont(new Font("Arial", Font.PLAIN, 14));
        roomSelector.addActionListener(e -> updateSeatMap());
        selectorPanel.add(roomLabel);
        selectorPanel.add(roomSelector);

        // Content panel chứa sơ đồ ghế
        contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "SƠ ĐỒ GHẾ",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));

        // Seat panel
        createSeatPanel();

        // Legend panel
        JPanel legendPanel = createLegendPanel();

        contentPanel.add(seatPanel, BorderLayout.CENTER);
        contentPanel.add(legendPanel, BorderLayout.SOUTH);

        // Add all components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(selectorPanel, BorderLayout.CENTER);
        mainPanel.add(contentPanel, BorderLayout.SOUTH);
    }

    private void createSeatPanel() {
        seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        updateSeatMap();
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Ghế chưa đặt
        JPanel availableSeat = new JPanel();
        availableSeat.setPreferredSize(new Dimension(30, 30));
        availableSeat.setBackground(Color.WHITE);
        availableSeat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        legendPanel.add(availableSeat);
        legendPanel.add(new JLabel("Ghế chưa đặt"));

        // Ghế đã đặt
        JPanel bookedSeat = new JPanel();
        bookedSeat.setPreferredSize(new Dimension(30, 30));
        bookedSeat.setBackground(Color.RED);
        bookedSeat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        legendPanel.add(bookedSeat);
        legendPanel.add(new JLabel("Ghế đã đặt"));

        return legendPanel;
    }

    private void updateSeatMap() {
        seatPanel.removeAll();
        String selectedRoomName = (String) roomSelector.getSelectedItem();
        
        // Tìm phòng được chọn
        Phong selectedPhong = null;
        for (Phong phong : dsPhong) {
            if (phong.getTenPhong().equals(selectedRoomName)) {
                selectedPhong = phong;
                break;
            }
        }
        
        if (selectedPhong == null) return;
        
        // Lấy danh sách ghế của phòng được chọn
        List<Ghe> dsGhe = gheDAO.layDanhSachGheTheoPhong(selectedPhong.getMaPhong());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tạo nhãn hàng (A-G)
        for (int row = 0; row < 7; row++) {
            JLabel rowLabel = new JLabel(String.valueOf((char)('A' + row)));
            rowLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = row + 1;
            seatPanel.add(rowLabel, gbc);
        }

        // Tạo nhãn cột (1-7)
        for (int col = 0; col < 7; col++) {
            JLabel colLabel = new JLabel(String.valueOf(col + 1));
            colLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = col + 1;
            gbc.gridy = 0;
            seatPanel.add(colLabel, gbc);
        }

        // Tạo các ghế
        for (int row = 0; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                String seatId = String.format("%c%d", (char)('A' + row), col + 1);
                JButton seatButton = createSeatButton(seatId);
                
                // Kiểm tra trạng thái ghế trong database
                for (Ghe ghe : dsGhe) {
                    if (ghe.getMaGhe().equals(seatId)) {
                        // Cập nhật hiển thị dựa trên trạng thái ghế
                        if (ghe.getTrangThai() == 1) {
                            // Ghế đã đặt
                            seatButton.setBackground(Color.RED);
                            seatButton.setForeground(Color.WHITE);
                        } else if (ghe.getTrangThai() == 2) {
                            // Ghế gặp vấn đề
                            seatButton.setBackground(Color.YELLOW);
                            seatButton.setForeground(Color.BLACK);
                        }
                        break;
                    }
                }
                
                gbc.gridx = col + 1;
                gbc.gridy = row + 1;
                seatPanel.add(seatButton, gbc);
            }
        }

        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private JButton createSeatButton(String seatId) {
        JButton button = new JButton(seatId);
        button.setPreferredSize(new Dimension(50, 50));
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Thêm popup menu cho mỗi ghế
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem availableItem = new JMenuItem("Đánh dấu chưa đặt");
        JMenuItem bookedItem = new JMenuItem("Đánh dấu đã đặt");
        JMenuItem problemItem = new JMenuItem("Đánh dấu gặp vấn đề");

        availableItem.addActionListener(e -> {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            
            // Cập nhật trạng thái trong cơ sở dữ liệu
            Phong selectedPhong = null;
            for (Phong phong : dsPhong) {
                if (phong.getTenPhong().equals(roomSelector.getSelectedItem())) {
                    selectedPhong = phong;
                    break;
                }
            }
            
            if (selectedPhong != null) {
                gheDAO.suaGhe(seatId, selectedPhong.getMaPhong(), seatId, 1.0, 0); // 0 = trống
            }
        });

        bookedItem.addActionListener(e -> {
            button.setBackground(Color.RED);
            button.setForeground(Color.WHITE);
            
            // Cập nhật trạng thái trong cơ sở dữ liệu
            Phong selectedPhong = null;
            for (Phong phong : dsPhong) {
                if (phong.getTenPhong().equals(roomSelector.getSelectedItem())) {
                    selectedPhong = phong;
                    break;
                }
            }
            
            if (selectedPhong != null) {
                gheDAO.suaGhe(seatId, selectedPhong.getMaPhong(), seatId, 1.0, 1); // 1 = đã đặt
            }
        });
        
        problemItem.addActionListener(e -> {
            button.setBackground(Color.YELLOW);
            button.setForeground(Color.BLACK);
            
            // Cập nhật trạng thái trong cơ sở dữ liệu
            Phong selectedPhong = null;
            for (Phong phong : dsPhong) {
                if (phong.getTenPhong().equals(roomSelector.getSelectedItem())) {
                    selectedPhong = phong;
                    break;
                }
            }
            
            if (selectedPhong != null) {
                gheDAO.suaGhe(seatId, selectedPhong.getMaPhong(), seatId, 1.0, 2); // 2 = gặp vấn đề
            }
        });

        popupMenu.add(availableItem);
        popupMenu.add(bookedItem);
        popupMenu.add(problemItem);

        button.addActionListener(e -> popupMenu.show(button, 0, button.getHeight()));

        return button;
    }
} 