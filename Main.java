package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Set this property to ensure AWT environment is not headless,
        // which can sometimes affect image loading.
        System.setProperty("java.awt.headless", "false");

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Music Player");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setSize(420, 880);
            frame.setMinimumSize(new Dimension(420, 880));
            frame.setLocationRelativeTo(null);

            JPanel cardPanel = new JPanel(new CardLayout());

            Home homePanel = new Home(cardPanel);
            AllSongsPanel allSongsPanel = new AllSongsPanel(cardPanel);
            PlaylistPanel playlistPanel = new PlaylistPanel(cardPanel);
            SearchPanel searchPanel = new SearchPanel(cardPanel);
            AccountPanel accountPanel = new AccountPanel(cardPanel);

            cardPanel.add(homePanel, "Home");
            cardPanel.add(allSongsPanel, "AllSongs");
            cardPanel.add(playlistPanel, "Playlists");
            cardPanel.add(searchPanel, "Search");
            cardPanel.add(accountPanel, "Account");

            frame.setContentPane(cardPanel);
            frame.setVisible(true);
        });
    }

    private static JPanel createExamplePanel(String text, Color bgColor) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.WHITE);
        panel.add(label);
        return panel;
    }
}