package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

/**
 * A JPanel that represents the entire mobile UI, including the main
 * gradient background, panels, and navigation bar.
 */
public class Home extends JPanel {

    public Home(JPanel cardPanel) {
        // Define colors
        Color deepBlue = Color.decode("#355C7D");
        Color mutedPurple = Color.decode("#725A7A");
        Color mauvePink = Color.decode("#C56C86");
        Color coral = Color.decode("#FF7682");
        Color softGray = new Color(220, 220, 220, 100);

        // The main container for the mobile UI with rounded corners
        RoundedPanel phonePanel = new RoundedPanel(35, Color.BLACK);
        phonePanel.setLayout(new BorderLayout());
        phonePanel.setPreferredSize(new Dimension(400, 800));
        phonePanel.setMaximumSize(new Dimension(400, 800));
        phonePanel.setMinimumSize(new Dimension(400, 800));

        // Create the gradient panel and add it to the rounded panel
        GradientPanel gradientPanel = new GradientPanel(deepBlue, mutedPurple);
        gradientPanel.setLayout(new BorderLayout());
        phonePanel.add(gradientPanel, BorderLayout.CENTER);

        // Create a panel to hold the stacked components at the top
        JPanel topContentPanel = new JPanel();
        topContentPanel.setOpaque(false);
        topContentPanel.setLayout(new BoxLayout(topContentPanel, BoxLayout.Y_AXIS));
        gradientPanel.add(topContentPanel, BorderLayout.NORTH);

        // --- All Songs Panel (First big box) ---
        JPanel allSongsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        allSongsContainer.setOpaque(false);
        allSongsContainer.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        RoundedPanel allSongsPanel = new RoundedPanel(25, mauvePink);
        allSongsPanel.setLayout(new GridBagLayout());
        allSongsPanel.setPreferredSize(new Dimension(350, 140));

        JLabel allSongsLabel = new JLabel("All Songs");
        allSongsLabel.setForeground(Color.WHITE);
        allSongsLabel.setFont(getFont("Montserrat", Font.BOLD, 20));
        allSongsPanel.add(allSongsLabel);

        // Add a mouse listener to handle clicks
        allSongsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "AllSongs");
            }
        });

        allSongsContainer.add(allSongsPanel);
        topContentPanel.add(allSongsContainer);

        // --- Playlists Panel (Second big box) ---
        JPanel playlistsContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playlistsContainer.setOpaque(false);
        playlistsContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        RoundedPanel playlistsPanel = new RoundedPanel(25, coral);
        playlistsPanel.setLayout(new GridBagLayout());
        playlistsPanel.setPreferredSize(new Dimension(350, 100));

        JLabel playlistsLabel = new JLabel("Playlists");
        playlistsLabel.setForeground(Color.WHITE);
        playlistsLabel.setFont(getFont("Montserrat", Font.BOLD, 20));
        playlistsPanel.add(playlistsLabel);

        // Add a mouse listener to handle clicks
        playlistsPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Playlists");
            }
        });

        playlistsContainer.add(playlistsPanel);
        topContentPanel.add(playlistsContainer);

        // --- Recently Played Label ---
        JPanel recentlyPlayedLabelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recentlyPlayedLabelContainer.setOpaque(false);
        recentlyPlayedLabelContainer.setBorder(BorderFactory.createEmptyBorder(20, 35, 10, 0)); // Padding for the label
        JLabel recentlyPlayedLabel = new JLabel("Recently Played");
        recentlyPlayedLabel.setForeground(Color.WHITE);
        recentlyPlayedLabel.setFont(getFont("Montserrat", Font.BOLD, 18));
        recentlyPlayedLabelContainer.add(recentlyPlayedLabel);
        topContentPanel.add(recentlyPlayedLabelContainer);

        // --- The 2x2 grid panel for Recently Played Songs ---
        JPanel gridContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gridContainer.setOpaque(false);
        gridContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // No top border as label is separate

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Changed to 2x2 grid
        gridPanel.setOpaque(false);
        gridPanel.setPreferredSize(new Dimension(350, 180)); // Adjusted size for 2x2

        // Button 1: "Decode" by Paramore
        JPanel songBox1 = createSongBox("Decode", "Paramore", "/decode_album_art.jpg", softGray);
        gridPanel.add(songBox1);

        // Button 2: "Televised" by HUNNY
        JPanel songBox2 = createSongBox("Televised", "HUNNY", "/televised_album_art.jpg", softGray);
        gridPanel.add(songBox2);

        // Button 3: "Complicated" by Avril Lavigne
        JPanel songBox3 = createSongBox("Complicated", "Avril Lavigne", "/complicated_album_art.jpg", softGray);
        gridPanel.add(songBox3);

        // Button 4: "Still into you" by Paramore
        JPanel songBox4 = createSongBox("Still into you", "Paramore", "/still_into_you_album_art.jpg", softGray);
        gridPanel.add(songBox4);

        gridContainer.add(gridPanel);
        topContentPanel.add(gridContainer);

        // Add the bottom navigation bar with the cardPanel
        gradientPanel.add(createBottomNavBar(mauvePink, Color.WHITE, cardPanel), BorderLayout.SOUTH);

        // Now, add the ShadowPanel wrapping the phone panel to this Home JPanel
        ShadowPanel shadowPanel = new ShadowPanel(phonePanel);
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.add(shadowPanel);
    }

    /** Helper method to get a font, with a fallback to "SansSerif". */
    private Font getFont(String name, int style, int size) {
        try {
            return new Font(name, style, size);
        } catch (Exception e) {
            return new Font("SansSerif", style, size);
        }
    }

    /** Creates a song box with image and text. */
    private JPanel createSongBox(String title, String artist, String imagePath, Color bgColor) {
        RoundedPanel songPanel = new RoundedPanel(15, bgColor);
        songPanel.setLayout(new BorderLayout(5, 0)); // Small horizontal gap between image and text

        // Album Art
        if (imagePath != null) {
            try {
                // The key change is to prepend a "/" to the path to look from the root of the classpath
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(imagePath));
                Image originalImage = originalIcon.getImage();
                // Scale image down to fit the box nicely, e.g., 50x50 pixels
                Image scaledImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0)); // Padding around image
                songPanel.add(imageLabel, BorderLayout.WEST);
            } catch (Exception e) {
                System.err.println("Could not load image: " + imagePath + " - " + e.getMessage());
                // Fallback if image not found
                JLabel placeholder = new JLabel("♪");
                placeholder.setFont(getFont("SansSerif", Font.PLAIN, 30));
                placeholder.setForeground(Color.WHITE);
                placeholder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
                songPanel.add(placeholder, BorderLayout.WEST);
            }
        } else {
            // Placeholder if no image path is provided
            JLabel placeholder = new JLabel("♪");
            placeholder.setFont(getFont("SansSerif", Font.PLAIN, 30));
            placeholder.setForeground(Color.WHITE);
            placeholder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 0));
            songPanel.add(placeholder, BorderLayout.WEST);
        }

        // Song Info
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 5)); // Padding for text

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel artistLabel = new JLabel(artist);
        artistLabel.setForeground(Color.LIGHT_GRAY);
        artistLabel.setFont(getFont("Montserrat", Font.PLAIN, 10));
        artistLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(artistLabel);

        songPanel.add(textPanel, BorderLayout.CENTER);

        return songPanel;
    }

    /** Creates the bottom navigation bar. */
    private JPanel createBottomNavBar(Color activeColor, Color inactiveColor, JPanel cardPanel) {
        JPanel navBar = new JPanel(new GridLayout(1, 3));
        navBar.setOpaque(false);
        navBar.setPreferredSize(new Dimension(0, 70));
        navBar.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        // Home is the active button on this page
        JPanel homeItem = createNavItem("⌂", "Home", activeColor, true);
        homeItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Home");
            }
        });

        // Search is inactive
        JPanel searchItem = createNavItem("🔍", "Search", inactiveColor, false);
        searchItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Search");
            }
        });

        // Account is inactive
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