package com.qlbv;
import javax.swing.SwingUtilities;

import com.qlbv.views.AuthPanel;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AuthPanel authPanel = new AuthPanel();
            authPanel.setSize(1000, 600);
            authPanel.setLocationRelativeTo(null);
            authPanel.setVisible(true);
        });
    }
}

