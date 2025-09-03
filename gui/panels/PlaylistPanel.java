package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaylistPanel extends JPanel {
    private JPanel playlistListPanel; // Panel to hold the list of created playlists

    public PlaylistPanel(JPanel cardPanel) {
        // Define colors
        Color deepBlue = Color.decode("#355C7D");
        Color mutedPurple = Color.decode("#725A7A");
        Color mauvePink = Color.decode("#C56C86");

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

        // --- Top Panel with Back Button and Title ---
        JPanel topBarPanel = new JPanel();
        topBarPanel.setOpaque(false);
        topBarPanel.setLayout(new BorderLayout());
        topBarPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Back Button (using a JLabel with a unicode character)
        JLabel backButton = new JLabel("←");
        backButton.setFont(new Font("Arial", Font.BOLD, 28));
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home"); // Go back to the main Home panel
            }
        });

        // Panel Title
        JLabel titleLabel = new JLabel("Playlists");
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topBarPanel.add(backButton, BorderLayout.WEST);
        topBarPanel.add(titleLabel, BorderLayout.CENTER);

        gradientPanel.add(topBarPanel, BorderLayout.NORTH);

        // --- Main Content Area ---
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // "Create New Playlist" Panel
        RoundedPanel createPlaylistPanel = new RoundedPanel(15, mauvePink);
        createPlaylistPanel.setPreferredSize(new Dimension(300, 60));
        createPlaylistPanel.setMaximumSize(new Dimension(350, 60));
        createPlaylistPanel.setLayout(new GridBagLayout());

        JLabel createPlaylistLabel = new JLabel("Create New Playlist");
        createPlaylistLabel.setForeground(Color.WHITE);
        createPlaylistLabel.setFont(getFont("Montserrat", Font.BOLD, 16));
        createPlaylistPanel.add(createPlaylistLabel);

        // Add a mouse listener to show the new frame
        createPlaylistPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showCreatePlaylistFrame();
            }
        });

        contentPanel.add(createPlaylistPanel);

        // Container for dynamically added playlist panels
        playlistListPanel = new JPanel();
        playlistListPanel.setOpaque(false);
        playlistListPanel.setLayout(new BoxLayout(playlistListPanel, BoxLayout.Y_AXIS));
        contentPanel.add(playlistListPanel);

        // Add the content panel to the gradient panel
        gradientPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the bottom navigation bar with click listeners
        JPanel navBar = createBottomNavBar(mauvePink, Color.WHITE, cardPanel);
        gradientPanel.add(navBar, BorderLayout.SOUTH);

        // Add the shadow effect
        ShadowPanel shadowPanel = new ShadowPanel(phonePanel);
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.add(shadowPanel);
    }

    /**
     * Helper method to show the "Create New Playlist" pop-up frame.
     */
    private void showCreatePlaylistFrame() {
        JFrame newPlaylistFrame = new JFrame("Create Playlist");
        newPlaylistFrame.setSize(300, 150);
        newPlaylistFrame.setLocationRelativeTo(this); // Center the frame relative to the main panel
        newPlaylistFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newPlaylistFrame.setResizable(false);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.decode("#355C7D")); // Match the app's background

        JLabel promptLabel = new JLabel("Enter playlist name:");
        promptLabel.setForeground(Color.WHITE);
        promptLabel.setFont(getFont("Montserrat", Font.PLAIN, 14));

        JTextField nameField = new JTextField(15);

        JButton createButton = new JButton("Create");
        createButton.setBackground(Color.decode("#C56C86"));
        createButton.setForeground(Color.WHITE);
        createButton.setBorderPainted(false);
        createButton.setFocusPainted(false);

        createButton.addActionListener(e -> {
            String playlistName = nameField.getText();
            if (!playlistName.isEmpty()) {
                // Add the new playlist panel
                addPlaylistPanel(playlistName);

                JOptionPane.showMessageDialog(this, "Playlist '" + playlistName + "' created!");
                newPlaylistFrame.dispose(); // Close the pop-up frame
            } else {
                JOptionPane.showMessageDialog(this, "Playlist name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(promptLabel);
        panel.add(nameField);
        panel.add(createButton);

        newPlaylistFrame.add(panel);
        newPlaylistFrame.setVisible(true);
    }

    /**
     * Dynamically adds a new panel for a playlist with the given name.
     */
    private void addPlaylistPanel(String playlistName) {
        RoundedPanel newPlaylistPanel = new RoundedPanel(15, new Color(114, 90, 122, 150)); // Semi-transparent
        newPlaylistPanel.setPreferredSize(new Dimension(350, 60));
        newPlaylistPanel.setMaximumSize(new Dimension(350, 60));
        newPlaylistPanel.setLayout(new GridBagLayout());

        JLabel playlistTitleLabel = new JLabel(playlistName);
        playlistTitleLabel.setForeground(Color.WHITE);
        playlistTitleLabel.setFont(getFont("Montserrat", Font.BOLD, 16));
        newPlaylistPanel.add(playlistTitleLabel);

        // Add some vertical space between panels
        playlistListPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        playlistListPanel.add(newPlaylistPanel);

        // Re-validate and repaint to show the new panel
        playlistListPanel.revalidate();
        playlistListPanel.repaint();
    }

    /** Helper method to get a font, with a fallback. */
    private Font getFont(String name, int style, int size) {
        try {
            return new Font(name, style, size);
        } catch (Exception e) {
            return new Font("SansSerif", style, size);
        }
    }

    /** Creates the bottom navigation bar. */
    private JPanel createBottomNavBar(Color activeColor, Color inactiveColor, JPanel cardPanel) {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setOpaque(false);
        navBar.setPreferredSize(new Dimension(0, 70));
        navBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        JPanel homeItem = createNavItem("⌂", "Home", inactiveColor, false);
        homeItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home");
            }
        });

        JPanel searchItem = createNavItem("🔍", "Search", inactiveColor, false);
        searchItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Search");
            }
        });

        JPanel accountItem = createNavItem("👤", "Account", activeColor, true);
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

    /** Creates a single item for the navigation bar. */
    private JPanel createNavItem(String iconText, String labelText, Color color, boolean isActive) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(iconText);
        iconLabel.setFont(getFont("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(color);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(getFont("Montserrat", isActive ? Font.BOLD : Font.PLAIN, 12));
        textLabel.setForeground(color);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        itemPanel.add(iconLabel);
        itemPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        itemPanel.add(textLabel);
        return itemPanel;
    }
}
    

