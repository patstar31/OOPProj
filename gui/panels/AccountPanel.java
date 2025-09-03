package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountPanel extends JPanel {

    public AccountPanel(JPanel cardPanel) {
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
        JLabel titleLabel = new JLabel("Account");
        titleLabel.setFont(getFont("Montserrat", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        topBarPanel.add(backButton, BorderLayout.WEST);
        topBarPanel.add(titleLabel, BorderLayout.CENTER);

        gradientPanel.add(topBarPanel, BorderLayout.NORTH);

        // --- Main Content Area (Account Options) ---
        JPanel accountOptionsPanel = new JPanel();
        accountOptionsPanel.setOpaque(false);
        accountOptionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Spacing between panels

        // Sign Up Panel
        RoundedPanel signUpPanel = new RoundedPanel(20, softGray);
        signUpPanel.setPreferredSize(new Dimension(150, 100));
        signUpPanel.setLayout(new GridBagLayout());

        JLabel signUpLabel = new JLabel("Sign Up");
        signUpLabel.setForeground(Color.WHITE);
        signUpLabel.setFont(getFont("Montserrat", Font.BOLD, 16));
        signUpPanel.add(signUpLabel);

        signUpPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showSignUpFrame();
            }
        });

        // Log In Panel
        RoundedPanel logInPanel = new RoundedPanel(20, softGray);
        logInPanel.setPreferredSize(new Dimension(150, 100));
        logInPanel.setLayout(new GridBagLayout());

        JLabel logInLabel = new JLabel("Log In");
        logInLabel.setForeground(Color.WHITE);
        logInLabel.setFont(getFont("Montserrat", Font.BOLD, 16));
        logInPanel.add(logInLabel);

        logInPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLoginFrame();
            }
        });

        accountOptionsPanel.add(signUpPanel);
        accountOptionsPanel.add(logInPanel);

        gradientPanel.add(accountOptionsPanel, BorderLayout.CENTER);

        // Add the bottom navigation bar
        JPanel navBar = createBottomNavBar(mauvePink, Color.WHITE, cardPanel);
        gradientPanel.add(navBar, BorderLayout.SOUTH);

        // Add the shadow effect
        ShadowPanel shadowPanel = new ShadowPanel(phonePanel);
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
        this.add(shadowPanel);
    }

    /**
     * Shows a mini frame for the sign-up process.
     */
    private void showSignUpFrame() {
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(300, 200);
        signUpFrame.setLocationRelativeTo(this);
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.decode("#355C7D"));

        // Email field
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.setOpaque(false);
        JTextField emailField = new JTextField(15);
        emailPanel.add(new JLabel("Email:"));
        emailPanel.add(emailField);

        // Username field
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.setOpaque(false);
        JTextField userField = new JTextField(15);
        userPanel.add(new JLabel("Username:"));
        userPanel.add(userField);

        // Password field
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passPanel.setOpaque(false);
        JPasswordField passField = new JPasswordField(15);
        passPanel.add(new JLabel("Password:"));
        passPanel.add(passField);

        // Create button
        JButton createButton = new JButton("Create");
        createButton.setBackground(Color.decode("#C56C86"));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);

        createButton.addActionListener(e -> {
            String email = emailField.getText();
            String username = userField.getText();
            String password = new String(passField.getPassword());
            System.out.println("Sign Up Details:");
            System.out.println("Email: " + email);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            JOptionPane.showMessageDialog(signUpFrame, "Account Created!");
            signUpFrame.dispose();
        });

        panel.add(emailPanel);
        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(createButton);

        signUpFrame.add(panel);
        signUpFrame.setVisible(true);
    }

    /**
     * Shows a mini frame for the log-in process.
     */
    private void showLoginFrame() {
        JFrame logInFrame = new JFrame("Log In");
        logInFrame.setSize(300, 180);
        logInFrame.setLocationRelativeTo(this);
        logInFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        logInFrame.setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.decode("#355C7D"));

        // Username field
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.setOpaque(false);
        JTextField userField = new JTextField(15);
        userPanel.add(new JLabel("Username:"));
        userPanel.add(userField);

        // Password field
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passPanel.setOpaque(false);
        JPasswordField passField = new JPasswordField(15);
        passPanel.add(new JLabel("Password:"));
        passPanel.add(passField);

        // Log In button
        JButton logInButton = new JButton("Log In");
        logInButton.setBackground(Color.decode("#C56C86"));
        logInButton.setForeground(Color.WHITE);
        logInButton.setFocusPainted(false);

        logInButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            System.out.println("Log In Details:");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            JOptionPane.showMessageDialog(logInFrame, "Log In Attempted!");
            logInFrame.dispose();
        });

        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(logInButton);

        logInFrame.add(panel);
        logInFrame.setVisible(true);
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