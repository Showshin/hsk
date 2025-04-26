package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.qlbv.model.dao.HoaDonDAO;
import com.qlbv.model.dao.KhachHangDAO;
import com.qlbv.model.dao.LichChieuDAO;
import com.qlbv.model.dao.VeDAO;
import com.qlbv.model.entities.Phim;
import com.qlbv.views.components.MovieCard;

public class DashboardPanel extends JPanel {
    private LichChieuDAO lichChieuDAO;
    private VeDAO veDAO;
    private HoaDonDAO hoaDonDAO;
    private KhachHangDAO khachHangDAO;
    private JLabel veBanLabel;
    private JLabel doanhThuLabel;
    private JLabel khachHangLabel;
    private JPanel phimHotContentPanel;

    public DashboardPanel() {
        lichChieuDAO = new LichChieuDAO();
        veDAO = new VeDAO();
        hoaDonDAO = new HoaDonDAO();
        khachHangDAO = new KhachHangDAO();

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(240, 242, 245));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Title của Dashboard
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(null);
        JLabel titleLabel = new JLabel("Tổng quan hệ thống");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // Panel chứa nội dung chính
        JPanel mainContent = new JPanel(new BorderLayout(0, 20));
        mainContent.setBackground(null);

        // Panel chứa các thẻ thống kê
        JPanel statsPanel = createStatsPanel();
        mainContent.add(statsPanel, BorderLayout.NORTH);

        // Panel chứa các phim hot
        JPanel phimHotPanel = createPhimHotPanel();
        mainContent.add(phimHotPanel, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        // Load dữ liệu
        loadStatsData();
        loadPhimHotData();
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(null);

        // Tạo các thẻ thống kê với label để cập nhật dữ liệu
        veBanLabel = new JLabel("0");
        doanhThuLabel = new JLabel("0 VND");
        khachHangLabel = new JLabel("0");

        statsPanel.add(createStatCard("Vé đã bán", veBanLabel, new Color(45, 152, 218)));
        statsPanel.add(createStatCard("Doanh thu tổng", doanhThuLabel, new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Số khách hàng", khachHangLabel, new Color(155, 89, 182)));

        return statsPanel;
    }

    private JPanel createPhimHotPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(null);
        
        // Title
        JLabel titleLabel = new JLabel("Phim đang hot", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(50, 50, 50));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Content Panel
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(null);
        
        phimHotContentPanel = new JPanel();
        phimHotContentPanel.setLayout(new BoxLayout(phimHotContentPanel, BoxLayout.X_AXIS));
        phimHotContentPanel.setBackground(null);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(phimHotContentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        containerPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(containerPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);

        return card;
    }
    
    private JPanel createMovieStatCard(Phim movie, int soVe) {
        JPanel container = new JPanel(new BorderLayout(0, 0));
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        // Tạo MovieCard
        MovieCard movieCard = new MovieCard(movie, null);
        
        // Tạo panel hiển thị số vé đã bán
        JPanel vePanel = new JPanel();
        vePanel.setBackground(new Color(231, 76, 60));
        vePanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        vePanel.setPreferredSize(new Dimension(200, 40));
        
        JLabel veLabel = new JLabel("Số vé đã bán: " + soVe, SwingConstants.CENTER);
        veLabel.setFont(new Font("Arial", Font.BOLD, 14));
        veLabel.setForeground(Color.WHITE);
        veLabel.setHorizontalAlignment(SwingConstants.CENTER);
        vePanel.add(veLabel);
        
        container.add(movieCard, BorderLayout.CENTER);
        container.add(vePanel, BorderLayout.SOUTH);
        
        return container;
    }

    private void loadStatsData() {
        // Lấy số vé bán hôm nay
        int soVeBan = veDAO.layDanhSachVe().size();
        veBanLabel.setText(String.valueOf(soVeBan));

        // Lấy doanh thu hôm nay
        double doanhThu = hoaDonDAO.layDoanhThuHienTai();
        doanhThuLabel.setText(String.format("%,.0f VND", doanhThu));

        // Lấy số khách hàng
        int soKhachHang = khachHangDAO.layDanhSachKhachHang().size();
        khachHangLabel.setText(String.valueOf(soKhachHang));
    }
    
    private void loadPhimHotData() {
        phimHotContentPanel.removeAll();
        
        // Thêm khoảng cách ở đầu để căn giữa
        phimHotContentPanel.add(Box.createHorizontalGlue());
        
        try {
            List<Phim> phimHotList = lichChieuDAO.layPhimCoSoVeNhieuNhat();
            
            // Nếu có phim, hiển thị
            if (phimHotList != null && !phimHotList.isEmpty()) {
                for (int i = 0; i < phimHotList.size(); i++) {
                    // Lấy số vé từ phim
                    int soVe = phimHotList.get(i).getSoVe();
                    
                    // Tạo thẻ phim kèm số vé
                    JPanel movieCard = createMovieStatCard(phimHotList.get(i), soVe);
                    phimHotContentPanel.add(movieCard);
                    
                    // Thêm khoảng cách giữa các phim
                    if (i < phimHotList.size() - 1) {
                        phimHotContentPanel.add(Box.createHorizontalStrut(20));
                    }
                }
            } else {
                // Hiển thị thông báo nếu không có phim
                JLabel noMoviesLabel = new JLabel("Chưa có dữ liệu phim", SwingConstants.CENTER);
                noMoviesLabel.setFont(new Font("Arial", Font.BOLD, 16));
                phimHotContentPanel.add(noMoviesLabel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Lỗi khi tải dữ liệu phim", SwingConstants.CENTER);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 16));
            phimHotContentPanel.add(errorLabel);
        }
        
        // Thêm khoảng cách ở cuối để căn giữa
        phimHotContentPanel.add(Box.createHorizontalGlue());
        
        phimHotContentPanel.revalidate();
        phimHotContentPanel.repaint();
    }
}