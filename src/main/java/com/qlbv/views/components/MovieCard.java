package com.qlbv.views.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.qlbv.model.entities.Phim;

public class MovieCard extends JPanel {
    private Phim movie;
    private Runnable onClick;

    public MovieCard(Phim movie, Runnable onClick) {
        this.movie = movie;
        this.onClick = onClick;
        
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setPreferredSize(new Dimension(200, 300));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Thêm ảnh phim với kích thước đầy đủ chiều rộng
        ImageIcon originalIcon = new ImageIcon(movie.getImg());
        Image image = originalIcon.getImage().getScaledInstance(200, 250, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(image);
        
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 250));
        add(imageLabel, BorderLayout.CENTER);
        
        // Panel thông tin phim
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        infoPanel.setBackground(Color.WHITE);
        
        // Tên phim
        JLabel titleLabel = new JLabel(movie.getTenPhim(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Thời lượng
        JLabel durationLabel = new JLabel(movie.getThoiLuong() + " phút", SwingConstants.CENTER);
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        durationLabel.setForeground(new Color(100, 100, 100));
        
        infoPanel.add(titleLabel);
        infoPanel.add(durationLabel);
        add(infoPanel, BorderLayout.SOUTH);
        
        // Thêm sự kiện hover và click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClick != null) {
                    onClick.run();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(240, 240, 240));
                infoPanel.setBackground(new Color(240, 240, 240));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
                infoPanel.setBackground(Color.WHITE);
            }
        });
    }
}
