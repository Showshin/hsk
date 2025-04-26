package com.qlbv.views.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.qlbv.model.entities.LichChieu;

public class ShowtimeCard extends JPanel {
    private LichChieu showtime;
    private Runnable onClick;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public ShowtimeCard(LichChieu showtime, Runnable onClick) {
        this.showtime = showtime;
        this.onClick = onClick;
        
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        setPreferredSize(new Dimension(200, 200));
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Thêm giờ chiếu
        JLabel timeLabel = new JLabel(TIME_FORMAT.format(showtime.getThoiGianBD()));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Thêm ngày chiếu
        JLabel dateLabel = new JLabel(DATE_FORMAT.format(showtime.getThoiGianBD()));
        dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(100, 100, 100));
        
        // Thêm phòng chiếu
        JLabel roomLabel = new JLabel("P." + showtime.getMaPhong().getMaPhong());
        roomLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        roomLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        roomLabel.setForeground(new Color(100, 100, 100));
        
        // Panel cho spacing tốt hơn
        add(Box.createVerticalGlue());
        add(timeLabel);
        add(Box.createVerticalStrut(5));
        add(dateLabel);
        add(Box.createVerticalStrut(3));
        add(roomLabel);
        add(Box.createVerticalGlue());
        
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
                setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243), 2, true));
                setBackground(new Color(240, 240, 240));
                for (Component comp : getComponents()) {
                    if (comp instanceof JLabel) {
                        comp.setBackground(new Color(240, 240, 240));
                    }
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
                setBackground(Color.WHITE);
                for (Component comp : getComponents()) {
                    if (comp instanceof JLabel) {
                        comp.setBackground(Color.WHITE);
                    }
                }
            }
        });
    }
} 