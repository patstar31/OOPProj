package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchPanel extends JPanel {

    public SearchPanel(JPanel cardPanel) {
        // Define colors
        Color deepBlue = Color.decode("#355C7D");
        Color mutedPurple = Color.decode("#725A7A");
        Color mauvePink = Color.decode("#C56C86");
        Color lightGray = new Color(240, 240, 240);

        // Main phone panel container with rounded corners
        RoundedPanel phonePanel = new RoundedPanel(35, Color.BLACK);
        phonePanel.setLayout(new BorderLayout());
        phonePanel.setPreferredSize(new Dimension(400, 800));
        phonePanel.setMaximumSize(new Dimension(400, 800));
        phonePanel.setMinimumSize(new Dimension(400, 800));

        // Gradient background
        GradientPanel gradientPanel = new GradientPanel(deepBlue, mutedPurple);
        gradientPanel.setLayout(new BorderLayout());
        phonePanel.add(gradientPanel, BorderLayout.CENTER);

        // --- Top Search Bar Panel ---
        JPanel topSearchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topSearchPanel.setOpaque(false);
        topSearchPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        RoundedPanel searchBar = new RoundedPanel(25, Color.WHITE);
        searchBar.setLayout(new BorderLayout(10, 0));
        searchBar.setPreferredSize(new Dimension(350, 50));

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Arial", Font.BOLD, 18));
        searchIcon.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        searchBar.add(searchIcon, BorderLayout.WEST);

        JTextField searchField = new JTextField("Search for songs, artists, and more...");
        searchField.setBorder(BorderFactory.createEmptyBorder());
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.GRAY);
        searchField.setFont(new Font("Montserrat", Font.PLAIN, 14));
        searchBar.add(searchField, BorderLayout.CENTER);

        topSearchPanel.add(searchBar);
        gradientPanel.add(topSearchPanel, BorderLayout.NORTH);

        // --- Main Content Area ---
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        gradientPanel.add(contentPanel, BorderLayout.CENTER);

        // --- Bottom Navigation Bar ---
        JPanel navBar = createBottomNavBar(mauvePink, Color.WHITE, cardPanel);
        gradientPanel.add(navBar, BorderLayout.SOUTH);

        // Add shadow to the main phone panel
        ShadowPanel shadowPanel = new ShadowPanel(phonePanel);
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.add(shadowPanel);
    }

    // Helper method to get a font, with a fallback
    private Font getFont(String name, int style, int size) {
        try {
            return new Font(name, style, size);
        } catch (Exception e) {
            return new Font("SansSerif", style, size);
        }
    }

    // Helper method to create the navigation bar with click listeners
    private JPanel createBottomNavBar(Color activeColor, Color inactiveColor, JPanel cardPanel) {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setOpaque(false);
        navBar.setPreferredSize(new Dimension(0, 70));
        navBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // Home button is inactive on this page
        JPanel homeItem = createNavItem("⌂", "Home", inactiveColor, false);
        homeItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home");
            }
        });

        // Search button is active on this page
        JPanel searchItem = createNavItem("🔍", "Search", activeColor, true);
        searchItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Search");
            }
        });

        // Account button is inactive on this page
        JPanel accountItem = createNavItem("👤", "Account", inactiveColor, false);
        accountItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Account");
            }
        });

        navBar.add(homeItem);
        navBar.add(searchItem);
        navBar.add(accountItem);

        return navBar;
    }

    // Helper method for creating individual navigation items
    private JPanel createNavItem(String iconText, String labelText, Color color, boolean isActive) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(color);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Montserrat", isActive ? Font.BOLD : Font.PLAIN, 12));
        textLabel.setForeground(color);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        itemPanel.add(iconLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        itemPanel.add(textLabel);
        return itemPanel;
    }
}