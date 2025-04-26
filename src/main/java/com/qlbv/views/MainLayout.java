package com.qlbv.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.qlbv.views.screen.DashboardPanel;
import com.qlbv.views.screen.ManageMoviePanel;
import com.qlbv.views.screen.ManageShowtimePanel;
import com.qlbv.views.screen.ManageTicketPanel;
import com.qlbv.views.screen.SellTicketPanel; 
import com.qlbv.views.screen.StatisticPanel;

public class MainLayout extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private String currentScreen = "DASHBOARD";
    

    public MainLayout() {
        setTitle("Quản lý rạp phim");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Tạo layout chính
        setLayout(new BorderLayout());

        // Tạo header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Tạo sidebar
        JPanel sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        // Tạo panel chứa nội dung
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(new Color(245, 245, 245));

        // Thêm các panel vào contentPanel
        contentPanel.add(new DashboardPanel(), "DASHBOARD");
        contentPanel.add(new ManageMoviePanel(), "MOVIE");
        contentPanel.add(new ManageShowtimePanel(), "SHOWTIME");
        contentPanel.add(new SellTicketPanel(), "TICKET");
        contentPanel.add(new ManageTicketPanel(), "TICKETS");
        contentPanel.add(new StatisticPanel(), "STATISTIC");

        // Thêm contentPanel vào frame
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(new Color(25, 33, 57));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel titleLabel = new JLabel("  " + getTitleForScreen(currentScreen));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(null);
        JLabel userLabel = new JLabel(AuthPanel.getNhanVienHienTai().getTenNV());
        userLabel.setForeground(Color.WHITE);
        
        // Tạo nút đăng xuất
        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setFocusPainted(false);
        
        // Thêm sự kiện cho nút đăng xuất
        logoutButton.addActionListener(e -> {
            // Hiển thị hộp thoại xác nhận
            int option = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION
            );
            
            if (option == JOptionPane.YES_OPTION) {
                // Đăng xuất
                AuthPanel.logout();
                dispose();
                
                SwingUtilities.invokeLater(() -> {
                    AuthPanel authPanel = new AuthPanel();
                    authPanel.setSize(1000, 600);
                    authPanel.setLocationRelativeTo(null);
                    authPanel.setVisible(true);
                });
            }
        });
        
        // Thêm hiệu ứng hover
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(203, 67, 53));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });
        
        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(15));
        userPanel.add(logoutButton);
        userPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 20));
        headerPanel.add(userPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(44, 57, 75));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
    
        // Logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setMaximumSize(new Dimension(200, 100));
        logoPanel.setBackground(new Color(44, 57, 75));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        ImageIcon logoIcon = new ImageIcon("resources/images/logo.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        sidebarPanel.add(logoPanel);
    
        // Menu buttons except "Bán vé"
        String[][] menuItems = {
            {"Dashboard", "DASHBOARD"},
            {"Quản lý phim", "MOVIE"},
            {"Quản lý lịch chiếu", "SHOWTIME"},
            {"Quản lý vé", "TICKETS"},
            {"Thống kê", "STATISTIC"}
        };
    
        for (String[] item : menuItems) {
            JButton button = createMenuButton(item[0], item[1]);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createVerticalStrut(5));
        }
    
        // Push everything above lên trên
        sidebarPanel.add(Box.createVerticalGlue());
    
        // Nút Bán vé (đặt ở dưới cùng và nổi bật)
        JButton ticketButton = createMenuButton("Bán vé", "TICKET");
        ticketButton.setBackground(Color.RED);
        ticketButton.setForeground(Color.WHITE);
        ticketButton.setFont(new Font("Arial", Font.BOLD, 14));
        ticketButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ticketButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        // Hover effect cho nút đỏ (nếu muốn)
        ticketButton.addMouseListener(new MouseAdapter() {
        
            @Override
            public void mouseEntered(MouseEvent e) {
                ticketButton.setBackground(new Color(200, 0, 0)); // màu đỏ đậm hơn khi hover
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                ticketButton.setBackground(Color.RED);
            }
        });
    
        sidebarPanel.add(ticketButton);
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        sidebarPanel.add(Box.createVerticalStrut(50));
    
        return sidebarPanel;
    }
    
    

    private JButton createMenuButton(String text, String screenName) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(200, 50));
        button.setBackground(new Color(44, 57, 75));
        button.setForeground(Color.WHITE);

        button.addActionListener(e -> {
            cardLayout.show(contentPanel, screenName);
            currentScreen = screenName;
            updateHeaderTitle();
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(52, 73, 94));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(44, 57, 75));
            }
        });

        return button;
    }

    private String getTitleForScreen(String screenName) {
        switch (screenName) {
            case "DASHBOARD": return "Dashboard";
            case "MOVIE": return "Quản lý phim";
            case "SHOWTIME": return "Quản lý lịch chiếu";
            case "TICKETS" : return "Quản lý vé";
            case "TICKET": return "Bán vé";
            case "STATISTIC": return "Thống kê";
            default: return "";
        }
    }

    private void updateHeaderTitle() {
        Component[] components = getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && comp.getParent() == getContentPane()) {
                JPanel headerPanel = (JPanel) comp;
                for (Component headerComp : headerPanel.getComponents()) {
                    if (headerComp instanceof JLabel) {
                        JLabel titleLabel = (JLabel) headerComp;
                        titleLabel.setText("  " + getTitleForScreen(currentScreen));
                        break;
                    }
                }
                break;
            }
        }
    }
} 