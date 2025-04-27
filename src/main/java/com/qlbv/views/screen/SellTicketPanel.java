package com.qlbv.views.screen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.qlbv.model.dao.ChiTietHDDAO;
import com.qlbv.model.dao.GheDAO;
import com.qlbv.model.dao.HoaDonDAO;
import com.qlbv.model.dao.KhachHangDAO;
import com.qlbv.model.dao.LichChieuDAO;
import com.qlbv.model.dao.PhimDAO;
import com.qlbv.model.dao.VeDAO;
import com.qlbv.model.entities.Ghe;
import com.qlbv.model.entities.LichChieu;
import com.qlbv.model.entities.Phim;
import com.qlbv.utils.PDFGenerator;
import com.qlbv.views.AuthPanel;
import com.qlbv.views.components.MovieCard;
import com.qlbv.views.components.ShowtimeCard;

public class SellTicketPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel stepPanel;
    private JPanel contentPanel;
    
    private JPanel chonPhimPanel;
    private JPanel chonSuatPanel;
    private JPanel chonGhePanel;
    private JPanel thanhToanPanel;
    
    private JPanel movieListPanel;
    private PhimDAO phimDAO;
    
    private JPanel showtimePanel;
    private LichChieuDAO lichChieuDAO;
    
    private JPanel seatPanel;
    private JPanel paymentPanel;
    
    private Phim selectedMovie;
    private LichChieu selectedShowtime;
    private List<String> selectedSeats;
    
    private GheDAO gheDAO;
    
    private JLabel movieTitleLabel;
    private JLabel roomLabel;
    private JLabel showtimeLabel;
    
    private JTextField tenKHField;
    private JTextField sdtField;
    
    public SellTicketPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        phimDAO = new PhimDAO();
        lichChieuDAO = new LichChieuDAO();
        gheDAO = new GheDAO();
        
        setupMainLayout();
        createStepPanel();
        createContentPanel();
        
        add(stepPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        showStep(1);
    }
    
    private void setupMainLayout() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
    }
    
    private void createStepPanel() {
        stepPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        stepPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        addStepLabel("1. Chọn phim", true);
        addStepLabel("2. Chọn suất chiếu", false);
        addStepLabel("3. Chọn ghế", false);
        addStepLabel("4. Thanh toán", false);
    }
    
    private void addStepLabel(String text, boolean active) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        if (active) {
            label.setBackground(new Color(52, 152, 219));
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(new Color(236, 240, 241));
            label.setForeground(new Color(149, 165, 166));
        }
        
        stepPanel.add(label);
    }
    
    private void createContentPanel() {
        contentPanel = new JPanel(cardLayout);
        
        createChonPhimPanel();
        createChonSuatPanel();
        createChonGhePanel();
        
        thanhToanPanel = new JPanel(new BorderLayout(10, 10));
        
        contentPanel.add(chonPhimPanel, "step1");
        contentPanel.add(chonSuatPanel, "step2");
        contentPanel.add(chonGhePanel, "step3");
        contentPanel.add(thanhToanPanel, "step4");
    }
    
    private void createChonPhimPanel() {
        chonPhimPanel = new JPanel(new BorderLayout(10, 10));
        
        // Thêm panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton searchButton = new JButton("Tìm kiếm");
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.WHITE);
        
        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim().toLowerCase();
            movieListPanel.removeAll();
            List<Phim> movies = phimDAO.layDanhSachPhim();
            
            for (Phim movie : movies) {
                if (movie.getTenPhim().toLowerCase().contains(keyword)) {
                    MovieCard movieCard = new MovieCard(movie, () -> {
                        selectedMovie = movie;
                        loadShowtimes(movie);
                        showStep(2);
                    });
                    movieListPanel.add(movieCard);
                }
            }
            
            movieListPanel.revalidate();
            movieListPanel.repaint();
        });
        
        searchPanel.add(new JLabel("Tìm kiếm phim:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        movieListPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        loadMovies();
        
        JScrollPane scrollPane = new JScrollPane(movieListPanel);
        scrollPane.setBorder(null);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton backButton = new JButton("Quay lại");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            resetForm();
            showStep(1);
        });

        JButton nextButton = new JButton("Tiếp tục");
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(46, 204, 113));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            if (selectedMovie == null) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một phim!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                showStep(2);
            }
        });
        
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        
        chonPhimPanel.add(searchPanel, BorderLayout.NORTH);
        chonPhimPanel.add(scrollPane, BorderLayout.CENTER);
        chonPhimPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createChonSuatPanel() {
        chonSuatPanel = new JPanel(new BorderLayout(10, 10));
        showtimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(showtimePanel);
        scrollPane.setBorder(null);
        chonSuatPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton backButton = new JButton("Quay lại");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            resetForm();
            showStep(1);
        });

        JButton nextButton = new JButton("Tiếp tục");
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(46, 204, 113));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            if (selectedShowtime == null) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn một suất chiếu!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                showStep(3);
            }
        });
        
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        chonSuatPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createChonGhePanel() {
        chonGhePanel = new JPanel(new BorderLayout(10, 10));
        chonGhePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel titlePanel = new JPanel(new GridLayout(1, 3, 5, 5));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        movieTitleLabel = new JLabel("", SwingConstants.CENTER);
        movieTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        roomLabel = new JLabel("", SwingConstants.CENTER);
        roomLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        showtimeLabel = new JLabel("", SwingConstants.CENTER);
        showtimeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        titlePanel.add(movieTitleLabel);
        titlePanel.add(roomLabel);
        titlePanel.add(showtimeLabel);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        
        seatPanel = new JPanel(new GridBagLayout());
        seatPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createSeatMap();
        
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        JPanel availableSeat = new JPanel();
        availableSeat.setPreferredSize(new Dimension(30, 30));
        availableSeat.setBackground(Color.WHITE);
        availableSeat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(availableSeat);
        infoPanel.add(new JLabel("Ghế trống"));
        
        JPanel reservedSeat = new JPanel();
        reservedSeat.setPreferredSize(new Dimension(30, 30));
        reservedSeat.setBackground(Color.RED);
        reservedSeat.setForeground(Color.WHITE);
        infoPanel.add(reservedSeat);
        infoPanel.add(new JLabel("Ghế đã đặt"));
        
        JPanel selectedSeat = new JPanel();
        selectedSeat.setPreferredSize(new Dimension(30, 30));
        selectedSeat.setBackground(new Color(120, 180, 240));
        selectedSeat.setForeground(Color.BLACK);
        selectedSeat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.add(selectedSeat);
        infoPanel.add(new JLabel("Ghế đang chọn"));
        
        JPanel problemSeat = new JPanel();
        problemSeat.setPreferredSize(new Dimension(30, 30));
        problemSeat.setBackground(Color.YELLOW);
        problemSeat.setForeground(Color.BLACK);
        infoPanel.add(problemSeat);
        infoPanel.add(new JLabel("Ghế gặp vấn đề"));
        
        contentPanel.add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        contentPanel.add(infoPanel, BorderLayout.SOUTH);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton backButton = new JButton("Quay lại");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> {
            resetForm();
            showStep(1);
        });

        JButton nextButton = new JButton("Tiếp tục");
        nextButton.setPreferredSize(new Dimension(120, 40));
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setBackground(new Color(46, 204, 113));
        nextButton.setForeground(Color.WHITE);
        nextButton.addActionListener(e -> {
            if (selectedSeats == null || selectedSeats.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn ít nhất một ghế!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            } else {
                showStep(4);
            }
        });
        
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);
        
        chonGhePanel.add(titlePanel, BorderLayout.NORTH);
        chonGhePanel.add(contentPanel, BorderLayout.CENTER);
        chonGhePanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void createThanhToanPanel() {
        if (thanhToanPanel == null) {
            thanhToanPanel = new JPanel(new BorderLayout(10, 10));
        }
        thanhToanPanel.removeAll();
        thanhToanPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
            BorderFactory.createEmptyBorder(0, 0, 10, 0)
        ));

        JLabel headerLabel = new JLabel("Phim: " + (selectedMovie != null ? selectedMovie.getTenPhim() : ""));
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(headerLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "THÔNG TIN THANH TOÁN",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel leftPanel = new JPanel(new GridBagLayout());
        addFormRow(leftPanel, "Ghế đã chọn:", selectedSeats != null ? String.join(", ", selectedSeats) : "", 0);
        addFormRow(leftPanel, "Số lượng vé:", String.valueOf(selectedSeats != null ? selectedSeats.size() : 0), 1);
        
        double giaVe = selectedMovie != null ? selectedMovie.getGiaGoc() : 0;
        addFormRow(leftPanel, "Giá vé:", String.format("%,.0f VNĐ x %d", giaVe, selectedSeats != null ? selectedSeats.size() : 0), 2);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        tenKHField = new JTextField(20);
        sdtField = new JTextField(20);
        addFormInput(rightPanel, "Tên khách hàng:", tenKHField, 0);
        addFormInput(rightPanel, "Số điện thoại:", sdtField, 1);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        formPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        formPanel.add(rightPanel, gbc);

        JPanel totalPanel = new JPanel(new BorderLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        totalPanel.setBackground(new Color(52, 152, 219));
        totalPanel.setPreferredSize(new Dimension(0, 50));

        JLabel totalLabel = new JLabel("TỔNG TIỀN:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        int soGhe = selectedSeats != null ? selectedSeats.size() : 0;
        double tongTien = (giaVe * soGhe);
        
        JLabel totalValue = new JLabel(String.format("%,.0f VNĐ", tongTien));
        totalValue.setFont(new Font("Arial", Font.BOLD, 18));
        totalValue.setForeground(Color.WHITE);
        totalValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        totalPanel.add(totalLabel, BorderLayout.WEST);
        totalPanel.add(totalValue, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        
        JButton backButton = new JButton("Quay lại");
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(e -> showStep(3));

        JButton confirmButton = new JButton("Xác nhận đặt vé");
        confirmButton.setPreferredSize(new Dimension(180, 40));
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setBackground(new Color(46, 204, 113));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(e -> processPayment());

        buttonPanel.add(backButton);
        buttonPanel.add(confirmButton);

        JPanel mainContent = new JPanel(new BorderLayout(0, 10));
        mainContent.add(headerPanel, BorderLayout.NORTH);
        mainContent.add(formPanel, BorderLayout.CENTER);
        mainContent.add(totalPanel, BorderLayout.SOUTH);

        thanhToanPanel.add(mainContent, BorderLayout.CENTER);
        thanhToanPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        thanhToanPanel.revalidate();
        thanhToanPanel.repaint();
    }
    
    private void addFormRow(JPanel panel, String label, String value, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = row;

        JLabel lblTitle = new JLabel(label);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        panel.add(lblTitle, gbc);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(lblValue, gbc);
    }

    private void addFormInput(JPanel panel, String label, JTextField field, int row) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = row;

        JLabel lblTitle = new JLabel(label);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        panel.add(lblTitle, gbc);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        panel.add(field, gbc);
    }
    
    private void loadMovies() {
        movieListPanel.removeAll();
        List<Phim> movies = phimDAO.layDanhSachPhim();
        
        for (Phim movie : movies) {
            MovieCard movieCard = new MovieCard(movie, () -> {
                selectedMovie = movie;
                loadShowtimes(movie);
                showStep(2);
            });
            movieListPanel.add(movieCard);
        }
        
        movieListPanel.revalidate();
        movieListPanel.repaint();
    }
    
    private void loadShowtimes(Phim movie) {
        showtimePanel.removeAll();
        showtimePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        if (movie == null) {
            JLabel label = new JLabel("Vui lòng chọn phim trước", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            showtimePanel.add(label);
        } else {
            JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            contentPanel.setOpaque(false);
            
            List<LichChieu> showtimes = lichChieuDAO.layLichChieuTheoPhim(movie.getMaPhim());
            
            for (LichChieu showtime : showtimes) {
                ShowtimeCard showtimeCard = new ShowtimeCard(showtime, () -> {
                    selectedShowtime = showtime;
                    showStep(3);
                    updateSeatPanelInfo();
                    createSeatMap();
                });
                contentPanel.add(showtimeCard);
            }
            
            showtimePanel.add(contentPanel);
        }
        
        showtimePanel.revalidate();
        showtimePanel.repaint();
    }
    
    private void createSeatMap() {
        seatPanel.removeAll();
        
        if (selectedShowtime == null) {
            JLabel label = new JLabel("Vui lòng chọn suất chiếu trước", SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 16));
            seatPanel.add(label);
            return;
        }

        String maPhong = selectedShowtime.getMaPhong().getMaPhong();
        List<Ghe> dsGhe = gheDAO.layDanhSachGheTheoPhong(maPhong);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Thêm màn hình
        JPanel screenPanel = new JPanel();
        screenPanel.setPreferredSize(new Dimension(400, 50));
        screenPanel.setBackground(new Color(200, 200, 200));
        screenPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel screenLabel = new JLabel("MÀN HÌNH");
        screenLabel.setFont(new Font("Arial", Font.BOLD, 16));
        screenLabel.setForeground(Color.WHITE);
        screenPanel.add(screenLabel);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 8;
        gbc.anchor = GridBagConstraints.CENTER;
        seatPanel.add(screenPanel, gbc);
        
        // Thêm khoảng cách giữa màn hình và hàng ghế
        gbc.gridy = 1;
        gbc.gridheight = 1;
        seatPanel.add(Box.createVerticalStrut(20), gbc);
        
        // Reset gridwidth cho các hàng ghế
        gbc.gridwidth = 1;
        
        for (int hang = 0; hang < 8; hang++) {
            JLabel rowLabel = new JLabel(String.valueOf((char)('A' + hang)));
            rowLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = hang + 2; // +2 vì có màn hình và khoảng cách
            seatPanel.add(rowLabel, gbc);
            
            for (int cot = 0; cot < 7; cot++) {
                String tenGhe = String.format("%c%d", (char)('A' + hang), cot + 1);
                JToggleButton nutGhe = new JToggleButton(tenGhe);
                nutGhe.setPreferredSize(new Dimension(50, 50));
                nutGhe.setFont(new Font("Arial", Font.BOLD, 12));
                nutGhe.setOpaque(true);
                
                final String[] maGhe = {""};
                final int[] trangThaiGhe = {0};
                
                dsGhe.stream()
                    .filter(ghe -> ghe.getHangGhe().equals(tenGhe))
                    .findFirst()
                    .ifPresent(ghe -> {
                        maGhe[0] = ghe.getMaGhe();
                        Ghe trangThai = gheDAO.kiemTraTrangThaiGhe(ghe.getMaGhe(), selectedShowtime.getMaLichChieu());
                        if (trangThai != null) {
                            trangThaiGhe[0] = trangThai.getTrangThai();
                        } else {
                            trangThaiGhe[0] = ghe.getTrangThai();
                        }
                    });
                
                if (trangThaiGhe[0] == 1) {
                    setupReservedSeat(nutGhe);
                } else if (trangThaiGhe[0] == 2) {
                    setupProblemSeat(nutGhe);
                } else {
                    setupAvailableSeat(nutGhe);
                }
                
                nutGhe.addActionListener(e -> handleSeatSelection(nutGhe, maGhe[0], tenGhe));
                
                gbc.gridx = cot + 1;
                gbc.gridy = hang + 2; // +2 vì có màn hình và khoảng cách
                seatPanel.add(nutGhe, gbc);
            }
        }
        
        seatPanel.revalidate();
        seatPanel.repaint();
    }
    
    private void setupReservedSeat(JToggleButton nutGhe) {
        nutGhe.setEnabled(false);
        nutGhe.setBackground(Color.RED);
        nutGhe.setForeground(Color.WHITE);
        nutGhe.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
    
    private void setupProblemSeat(JToggleButton nutGhe) {
        nutGhe.setEnabled(false);
        nutGhe.setBackground(Color.YELLOW);
        nutGhe.setForeground(Color.BLACK);
        nutGhe.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void setupAvailableSeat(JToggleButton nutGhe) {
        nutGhe.setBackground(Color.WHITE);
        nutGhe.setForeground(Color.BLACK);
        nutGhe.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void handleSeatSelection(JToggleButton nutGhe, String maGhe, String tenGhe) {
        if (selectedSeats == null) {
            selectedSeats = new ArrayList<>();
        }
        
        if (nutGhe.isSelected()) {
            selectedSeats.add(tenGhe); // Lưu hàng ghế để hiển thị
            nutGhe.setBackground(new Color(120, 180, 240));
            nutGhe.setForeground(Color.BLACK);
            nutGhe.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        } else {
            selectedSeats.remove(tenGhe);
            setupAvailableSeat(nutGhe);
        }
    
    }
    
    private void showStep(int step) {
        Component[] steps = stepPanel.getComponents();
        for (int i = 0; i < steps.length; i++) {
            JLabel label = (JLabel) steps[i];
            if (i < step) {
                label.setBackground(new Color(52, 152, 219));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(new Color(236, 240, 241));
                label.setForeground(new Color(149, 165, 166));
            }
        }

        if (step == 4) {
            createThanhToanPanel();
        }
        
        cardLayout.show(contentPanel, "step" + step);
    }
    
    /**
     * Xử lý quá trình thanh toán và tạo hóa đơn
     */
    private void processPayment() {
        String tenKH = tenKHField.getText().trim();
        String sdt = sdtField.getText().trim();
        
        if (tenKH.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập đầy đủ thông tin khách hàng!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String maKH = "KH" + System.currentTimeMillis();
            
            KhachHangDAO khachHangDAO = new KhachHangDAO();
            boolean kqLuuKH = khachHangDAO.themKhachHang(maKH, tenKH, sdt);
            
            if (!kqLuuKH) {
                throw new Exception("Không thể lưu thông tin khách hàng!");
            }
            
            VeDAO veDAO = new VeDAO();
            GheDAO gheDAO = new GheDAO();
            List<String> danhSachMaVe = new ArrayList<>();
            
            // Lấy danh sách ghế theo hàng ghế
            List<Ghe> dsGhe = gheDAO.layDanhSachGheTheoPhong(selectedShowtime.getMaPhong().getMaPhong());
            
            for (String hangGhe : selectedSeats) {
                // Tìm mã ghế tương ứng với hàng ghế
                Ghe ghe = dsGhe.stream()
                    .filter(g -> g.getHangGhe().equals(hangGhe))
                    .findFirst()
                    .orElse(null);
                
                if (ghe == null) {
                    throw new Exception("Không tìm thấy ghế " + hangGhe);
                }
                
                String maVe = "VE" + System.currentTimeMillis() + "_" + ghe.getMaGhe();
                double giaVe = veDAO.tinhGiaVe(ghe.getMaGhe(), selectedMovie.getMaPhim());
                
                boolean kqTaoVe = veDAO.themVe(
                    maVe,
                    ghe.getMaGhe(),
                    selectedShowtime.getMaLichChieu(),
                    giaVe
                );
                
                if (!kqTaoVe) {
                    throw new Exception("Không thể tạo vé cho ghế " + hangGhe);
                }
                
                boolean kqCapNhatGhe = gheDAO.suaGhe(
                    ghe.getMaGhe(),
                    ghe.getMaPhong().getMaPhong(),
                    ghe.getHangGhe(),
                    ghe.getHeSo(),
                    1
                );
                
                if (!kqCapNhatGhe) {
                    throw new Exception("Không thể cập nhật trạng thái ghế " + hangGhe);
                }
                
                danhSachMaVe.add(maVe);
            }
            
            String maHD = "HD" + System.currentTimeMillis();
            double tongTien = selectedMovie.getGiaGoc() * selectedSeats.size();
            
            HoaDonDAO hoaDonDAO = new HoaDonDAO();
            boolean kqLuuHD = hoaDonDAO.themHoaDon(
                maHD,
                maKH,
                AuthPanel.getNhanVienHienTai().getMaNV(),
                new java.sql.Date(System.currentTimeMillis()),
                tongTien
            );
            
            if (!kqLuuHD) {
                throw new Exception("Không thể lưu hóa đơn!");
            }
            
            ChiTietHDDAO chiTietHDDAO = new ChiTietHDDAO();
            for (String maVe : danhSachMaVe) {
                boolean kqLuuCTHD = chiTietHDDAO.themChiTietHD(maHD, maVe);
                if (!kqLuuCTHD) {
                    throw new Exception("Không thể lưu chi tiết hóa đơn cho vé " + maVe);
                }
            }
            
            String fileName = "HoaDon_" + maHD + ".pdf";
            PDFGenerator.generateInvoice(
                fileName,
                maHD,
                maKH,
                tenKH,
                sdt,
                selectedMovie,
                selectedShowtime,
                selectedSeats, // Truyền danh sách hàng ghế
                danhSachMaVe,
                tongTien,
                AuthPanel.getNhanVienHienTai().getTenNV()
            );
            
            JOptionPane.showMessageDialog(this,
                String.format("Đặt vé thành công!\nMã hóa đơn: %s\nSố lượng vé: %d\nTổng tiền: %,.0f VNĐ\n\nHóa đơn đã được lưu tại: %s",
                    maHD, danhSachMaVe.size(), tongTien, fileName),
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
                
            // Cập nhật lại sơ đồ ghế sau khi đặt vé thành công
            createSeatMap();
            
            resetForm();
            showStep(1);
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        selectedMovie = null;
        selectedShowtime = null;
        selectedSeats = null;
        if (tenKHField != null) tenKHField.setText("");
        if (sdtField != null) sdtField.setText("");
        
        // Reset các panel
        if (movieListPanel != null) {
            movieListPanel.removeAll();
            loadMovies();
            movieListPanel.revalidate();
            movieListPanel.repaint();
        }
        
        if (showtimePanel != null) {
            showtimePanel.removeAll();
            showtimePanel.revalidate();
            showtimePanel.repaint();
        }
        
        if (seatPanel != null) {
            seatPanel.removeAll();
            createSeatMap();
            seatPanel.revalidate();
            seatPanel.repaint();
        }
        
        // Reset các label
        if (movieTitleLabel != null) movieTitleLabel.setText("");
        if (roomLabel != null) roomLabel.setText("");
        if (showtimeLabel != null) showtimeLabel.setText("");
    }

    private void updateSeatPanelInfo() {
        if (selectedMovie != null && selectedShowtime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            movieTitleLabel.setText(selectedMovie.getTenPhim());
            roomLabel.setText("Phòng: " + selectedShowtime.getMaPhong().getMaPhong());
            showtimeLabel.setText("Suất chiếu: " + sdf.format(selectedShowtime.getThoiGianBD()));
        }
    }
} 