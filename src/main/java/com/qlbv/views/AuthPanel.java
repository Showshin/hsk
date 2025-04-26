package com.qlbv.views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.qlbv.model.dao.NhanVienDAO;
import com.qlbv.model.dao.TaiKhoanNhanVienDAO;
import com.qlbv.model.entities.NhanVien;
import com.qlbv.model.entities.TaiKhoanNhanVien;

public class AuthPanel extends JFrame {
    private JPanel formPanel;
    private JButton switchButton;
    
    // Thông tin nhân viên đăng nhập
    private static String maNV = null;
    private static TaiKhoanNhanVien taiKhoanHienTai = null;
    private static NhanVien nhanVienHienTai = null;
    
    public static String getMaNV() {
        return maNV;
    }
    
    private static void setMaNV(String ma) {
        maNV = ma;
    }
    
    public static TaiKhoanNhanVien getTaiKhoanHienTai() {
        return taiKhoanHienTai;
    }
    
    public static void setTaiKhoanHienTai(TaiKhoanNhanVien taiKhoan) {
        taiKhoanHienTai = taiKhoan;
    }
    
    public static NhanVien getNhanVienHienTai() {
        return nhanVienHienTai;
    }
    
    public static void setNhanVienHienTai(NhanVien nhanVien) {
        nhanVienHienTai = nhanVien;
    }
    
    public static boolean isAuthenticated() {
        return maNV != null && !maNV.isEmpty();
    }
    
    // Phương thức đăng xuất
    public static void logout() {
        maNV = null;
        taiKhoanHienTai = null;
        nhanVienHienTai = null;
    }

    public AuthPanel() {
        setTitle("Đăng nhập hệ thống");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(new Color(240, 240, 240));
        left.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        left.setPreferredSize(new Dimension(400, 600));

        ImageIcon logoIcon = new ImageIcon("resources/images/logo.png");
        Image logoImg = logoIcon.getImage().getScaledInstance(160, 120, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setAlignmentX(CENTER_ALIGNMENT);
        left.add(logo);
        left.add(Box.createVerticalStrut(30));

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(null);

        left.add(formPanel);
        add(left, BorderLayout.WEST);

        ImageIcon sideImg = new ImageIcon("resources/images/bg.png");
        Image bg = sideImg.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        JLabel bgLabel = new JLabel(new ImageIcon(bg));
        bgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(bgLabel, BorderLayout.CENTER);

        showLoginForm();
    }

    private void showLoginForm() {
        formPanel.removeAll();

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        formPanel.add(createInputRow("Tài khoản:", usernameField));
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(createInputRow("Mật khẩu:", passwordField));
        formPanel.add(Box.createVerticalStrut(20));
        
        usernameField.setText("nhanvien1");
        passwordField.setText("pass123");
        
        JButton loginBtn = new JButton("Đăng nhập");
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            TaiKhoanNhanVienDAO taiKhoanDAO = new TaiKhoanNhanVienDAO();
            TaiKhoanNhanVien taiKhoan = taiKhoanDAO.dangNhap(username, password);
            
            if (taiKhoan != null) {
                // Lưu thông tin tài khoản
                setTaiKhoanHienTai(taiKhoan);
                setMaNV(taiKhoan.getMaNV());
                
                // Lấy thông tin nhân viên
                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                NhanVien nhanVien = nhanVienDAO.timNhanVien(taiKhoan.getMaNV());
                if (nhanVien != null) {
                    setNhanVienHienTai(nhanVien);
                }
                
                dispose();
                new MainLayout().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Tên đăng nhập hoặc mật khẩu không đúng!",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        switchButton = new JButton("Đăng ký");
        switchButton.addActionListener(e -> showRegisterForm());

        styleButton(loginBtn, new Color(0, 102, 204));
        styleButton(switchButton, new Color(100, 100, 100));

        formPanel.add(createButtonPanel(loginBtn, switchButton));
        formPanel.revalidate();
        formPanel.repaint();
    }

    private void showRegisterForm() {
        formPanel.removeAll();

        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        formPanel.add(createInputRow("Họ tên:", nameField));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createInputRow("Số điện thoại:", phoneField));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createInputRow("Tài khoản:", usernameField));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createInputRow("Mật khẩu:", passwordField));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createInputRow("Nhập lại:", confirmPasswordField));
        formPanel.add(Box.createVerticalStrut(20));
        
        JButton registerBtn = new JButton("Xác nhận");
        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            
            // Kiểm tra dữ liệu nhập
            if (name.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Mật khẩu nhập lại không khớp!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // 1. Tạo nhân viên mới
                String maNV = "NV" + System.currentTimeMillis();
                NhanVienDAO nhanVienDAO = new NhanVienDAO();
                boolean kqThemNV = nhanVienDAO.themNhanVien(maNV, name, phone);
                
                if (!kqThemNV) {
                    throw new Exception("Không thể tạo tài khoản nhân viên!");
                }
                
                // Tạo đối tượng nhân viên
                NhanVien nhanVien = new NhanVien(maNV, name, phone);
                
                // 2. Tạo tài khoản cho nhân viên
                String maTK = "TK" + System.currentTimeMillis();
                TaiKhoanNhanVienDAO taiKhoanDAO = new TaiKhoanNhanVienDAO();
                boolean kqThemTK = taiKhoanDAO.themTaiKhoanNhanVien(
                    maTK,
                    maNV,
                    username,
                    password,
                    "NHANVIEN" // Mặc định là nhân viên bình thường
                );
                
                if (!kqThemTK) {
                    throw new Exception("Không thể tạo tài khoản đăng nhập!");
                }
                
                // Tạo đối tượng tài khoản
                TaiKhoanNhanVien taiKhoan = new TaiKhoanNhanVien(maTK, maNV, username, password, "NHANVIEN");
                
                // Đăng nhập tự động
                setMaNV(maNV);
                setNhanVienHienTai(nhanVien);
                setTaiKhoanHienTai(taiKhoan);
                
                JOptionPane.showMessageDialog(this,
                    "Đăng ký thành công!\nĐang chuyển đến trang chủ...",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
                    
                dispose();
                new MainLayout().setVisible(true);
                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Lỗi đăng ký: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        switchButton = new JButton("Quay lại");
        switchButton.addActionListener(e -> showLoginForm());

        styleButton(registerBtn, new Color(0, 102, 0));
        styleButton(switchButton, new Color(100, 100, 100));

        formPanel.add(createButtonPanel(registerBtn, switchButton));
        formPanel.revalidate();
        formPanel.repaint();
    }

    private JPanel createInputRow(String labelText, JComponent input) {
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(100, 30));
        input.setPreferredSize(new Dimension(250, 35));

        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(null);
        row.add(label, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return row;
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(null);
        panel.add(Box.createHorizontalGlue());
        for (int i = 0; i < buttons.length; i++) {
            panel.add(buttons[i]);
            if (i < buttons.length - 1) panel.add(Box.createHorizontalStrut(20));
        }
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    private void styleButton(JButton button, Color bg) {
        Dimension size = new Dimension(120, 35);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
    }
}
