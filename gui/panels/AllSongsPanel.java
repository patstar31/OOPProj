package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AllSongsPanel extends JPanel {

    public AllSongsPanel(JPanel cardPanel) {
        // Define colors
        Color deepBlue = Color.decode("#355C7D");
        Color mutedPurple = Color.decode("#725A7A");
        Color mauvePink = Color.decode("#C56C86");
        Color coral = Color.decode("#FF7682");
        Color softGray = new Color(220, 220, 220, 100);

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
        JLabel titleLabel = new JLabel("All Songs");
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topBarPanel.add(backButton, BorderLayout.WEST);
        topBarPanel.add(titleLabel, BorderLayout.CENTER);

        gradientPanel.add(topBarPanel, BorderLayout.NORTH);

        // --- Main Content Area (Songs List) ---
        JPanel songsListContainer = new JPanel(); // A container to center the songs list
        songsListContainer.setOpaque(false);
        songsListContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10)); // Vertical spacing only

        JPanel songsListPanel = new JPanel();
        songsListPanel.setOpaque(false);
        songsListPanel.setLayout(new BoxLayout(songsListPanel, BoxLayout.Y_AXIS));
        // No horizontal border here, as the FlowLayout for songsListContainer handles centering

        // Add the songs to the list using the updated addSongItem method
        addSongItem(songsListPanel, "Decode", "Paramore", "/decode_album_art.jpg", softGray);
        addSongItem(songsListPanel, "Televised", "HUNNY", "/televised_album_art.jpg", softGray);
        addSongItem(songsListPanel, "Complicated", "Avril Lavigne", "/complicated_album_art.jpg", softGray);
        addSongItem(songsListPanel, "Still into you", "Paramore", "/still_into_you_album_art.jpg", softGray);

        songsListContainer.add(songsListPanel); // Add the actual list to its container

        // Use a JScrollPane for the songs list in case there are many songs
        JScrollPane scrollPane = new JScrollPane(songsListContainer); // Wrap the container in a scroll pane
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove the border

        gradientPanel.add(scrollPane, BorderLayout.CENTER);

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
     * Helper method to add a single song item to the list, now using RoundedPanel.
     */
    private void addSongItem(JPanel parentPanel, String title, String artist, String imagePath, Color bgColor) {
        RoundedPanel songContainerPanel = new RoundedPanel(15, bgColor); // Use RoundedPanel for each song
        songContainerPanel.setPreferredSize(new Dimension(350, 80)); // Adjusted size for the panel
        songContainerPanel.setMaximumSize(new Dimension(350, 80));
        songContainerPanel.setLayout(new BorderLayout(15, 0)); // Horizontal gap between image and text
        songContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Internal padding

        // Album Art (resized)
        if (imagePath != null) {
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
                Image originalImage = originalIcon.getImage();
                Image scaledImage = originalImage.getScaledInstance(60, 60, Image.SCALE_SMOOTH); // Match panel height
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                songContainerPanel.add(imageLabel, BorderLayout.WEST);
            } catch (Exception e) {
                System.err.println("Could not load image: " + imagePath + " - " + e.getMessage());
                // Fallback if image not found
                JLabel placeholder = new JLabel("♪");
                placeholder.setFont(getFont("SansSerif", Font.PLAIN, 40));
                placeholder.setForeground(Color.WHITE);
                songContainerPanel.add(placeholder, BorderLayout.WEST);
            }
        }

        // Song Info
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to the left

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel artistLabel = new JLabel(artist);
        artistLabel.setForeground(Color.LIGHT_GRAY);
        artistLabel.setFont(getFont("Montserrat", Font.PLAIN, 12));
        artistLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(artistLabel);

        songContainerPanel.add(textPanel, BorderLayout.CENTER);

        parentPanel.add(songContainerPanel);
        parentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between song panels
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

        JPanel accountItem = createNavItem("👤", "Account", activeColor, true); // Assuming Account is the active tab for AllSongsPanel
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