package common.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePage {
    private JFrame frame;
    private JPanel loginPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel loginMsg;
    private Timer fadeTimer;
    private float panelOpacity = 0f;

    private JPanel buttonPanel;

    public WelcomePage() {
        SwingUtilities.invokeLater(this::initUI);
    }

    private void initUI() {
        frame = new JFrame("VCRTS — Vehicular Cloud Real Time System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720, 480);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));
        root.setBackground(new Color(240, 248, 255)); // light blue

        JLabel title = new JLabel("Vehicular Cloud Real Time System (VCRTS)");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(new Color(25, 50, 120));

        // Description
        JPanel descPanel = new JPanel();
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));
        descPanel.setOpaque(false);
        descPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLine1 = new JLabel("Register vehicles, submit compute jobs, and monitor progress in real time.");
        JLabel descLine2 = new JLabel("Use your account to access dashboards and manage jobs.");
        for (JLabel l : new JLabel[]{descLine1, descLine2}) {
            l.setFont(new Font("SansSerif", Font.PLAIN, 16));
            l.setForeground(new Color(80, 80, 80));
            l.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        descPanel.add(descLine1);
        descPanel.add(Box.createVerticalStrut(4));
        descPanel.add(descLine2);

        // Buttons
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn = new JButton("Log In");
        JButton createBtn = new JButton("Create Account");

        Font btnFont = new Font("SansSerif", Font.BOLD, 18);
        Dimension btnSize = new Dimension(220, 56);
        styleButton(loginBtn, new Color(35, 99, 188), btnFont, btnSize);
        styleButton(createBtn, new Color(25, 140, 100), btnFont, btnSize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        buttonPanel.add(loginBtn, gbc);
        buttonPanel.add(createBtn, gbc);

        // Smaller login box
        buildLoginPanel();

        // Actions
        loginBtn.addActionListener(e -> {
            toggleButtons(false); // hide buttons
            showLoginPanelWithFade();
        });
      createBtn.addActionListener(e -> {
                frame.dispose();
                new createAccount();
       
        });

        
        root.add(Box.createVerticalStrut(40));
        root.add(title);
        root.add(Box.createVerticalStrut(16));
        root.add(descPanel);
        root.add(Box.createVerticalGlue());
        root.add(buttonPanel);
        root.add(Box.createVerticalStrut(12));
        root.add(loginPanel);
        root.add(Box.createVerticalStrut(20));

        // Footer 
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(235, 240, 250));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 220, 235)));
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        footerPanel.setMinimumSize(new Dimension(Integer.MAX_VALUE, 20));
        footerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));

        JLabel footerText = new JLabel("© 2025 Vehicular Cloud Real Time System  |  All Rights Reserved", SwingConstants.CENTER);
        footerText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footerText.setForeground(new Color(90, 90, 90));
        footerPanel.add(footerText, BorderLayout.CENTER);

        root.add(Box.createVerticalGlue());
        root.add(Box.createVerticalStrut(8));
        root.add(footerPanel);

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    // Login Panel 
    private void buildLoginPanel() {
        loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, panelOpacity));
                super.paintComponent(g2d);
                g2d.dispose();
            }
        };
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setOpaque(true);
        loginPanel.setBackground(new Color(250, 252, 255));
        loginPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.setMaximumSize(new Dimension(380, 130));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(25, 50, 120), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel loginTitle = new JLabel("Log in");
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        loginTitle.setForeground(new Color(25, 50, 120));

        JPanel emailRow = new JPanel(new BorderLayout(5, 0));
        emailRow.setOpaque(false);
        JLabel emailLbl = new JLabel("Email:");
        emailLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emailField = new JTextField(16);
        emailRow.add(emailLbl, BorderLayout.WEST);
        emailRow.add(emailField, BorderLayout.CENTER);

        JPanel passRow = new JPanel(new BorderLayout(5, 0));
        passRow.setOpaque(false);
        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passwordField = new JPasswordField(16);
        passRow.add(passLbl, BorderLayout.WEST);
        passRow.add(passwordField, BorderLayout.CENTER);

        JPanel actionRow = new JPanel();
        actionRow.setOpaque(false);
        JButton signIn = new JButton("Sign In");
        styleButton(signIn, new Color(35, 99, 188), new Font("SansSerif", Font.BOLD, 13), new Dimension(100, 32));
        JButton testSignIn = new JButton("Test Sign In");
        styleButton(signIn, new Color(35, 99, 188), new Font("SansSerif", Font.BOLD, 13), new Dimension(100, 32));

        JButton cancel = new JButton("Cancel");
        styleButton(cancel, new Color(140, 140, 140), new Font("SansSerif", Font.BOLD, 13), new Dimension(90, 30));
        actionRow.add(signIn);
        actionRow.add(cancel);
        actionRow.add(testSignIn);

        loginMsg = new JLabel(" ");
        loginMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginMsg.setFont(new Font("SansSerif", Font.PLAIN, 12));
        loginMsg.setForeground(new Color(170, 30, 30));

        signIn.addActionListener(e -> doLogin());
        testSignIn.addActionListener(e -> testLogin());
        cancel.addActionListener(e -> {
            toggleLoginPanel(false);
            toggleButtons(true);
        });
        passwordField.addActionListener(e -> doLogin());

        loginPanel.add(loginTitle);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(emailRow);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(passRow);
        loginPanel.add(Box.createVerticalStrut(8));
        loginPanel.add(actionRow);
        loginPanel.add(Box.createVerticalStrut(4));
        loginPanel.add(loginMsg);

        loginPanel.setVisible(false);
    }

    // animation 
    private void showLoginPanelWithFade() {
        if (loginPanel.isVisible()) return;
        panelOpacity = 0f;
        loginPanel.setVisible(true);

        fadeTimer = new Timer(25, e -> {
            panelOpacity += 0.08f;
            if (panelOpacity >= 1f) {
                panelOpacity = 1f;
                fadeTimer.stop();
            }
            loginPanel.repaint();
        });
        fadeTimer.start();
    }

    private void toggleLoginPanel(boolean show) {
        if (!show) {
            loginPanel.setVisible(false);
            panelOpacity = 0f;
        }
        loginPanel.getParent().revalidate();
        loginPanel.getParent().repaint();
    }

    private void toggleButtons(boolean show) {
        buttonPanel.setVisible(show);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    //  Login logic 
    private void doLogin() {
        String email = emailField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (email.equalsIgnoreCase("test@vcrts.com") && pass.equals("test")) {
            loginMsg.setForeground(new Color(20, 120, 60));
            loginMsg.setText("Login successful!");

            JOptionPane.showMessageDialog(
                    frame,
                    "✅ Login successful! Welcome to VCRTS.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            frame.dispose();
            new UserPage(); 
        } else {
            loginMsg.setForeground(new Color(170, 30, 30));
            loginMsg.setText("Invalid email or password.");
        }
    }
    private void testLogin() {
            loginMsg.setForeground(new Color(20, 120, 60));
            loginMsg.setText("Login successful!");

            JOptionPane.showMessageDialog(
                    frame,
                    "✅ Login successful! Welcome to VCRTS.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            frame.dispose();
            new UserPage();
    }

    // Styling helper 
    private void styleButton(JButton button, Color baseColor, Font font, Dimension size) {
        button.setFont(font);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(baseColor.darker()); }
            public void mouseExited (java.awt.event.MouseEvent evt) { button.setBackground(baseColor); }
        });
    }
}
