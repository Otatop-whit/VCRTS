package common.ui;

import javax.swing.*;
import java.awt.*;

public class WelcomePage {
    private JFrame frame;

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
        title.setFont(new Font("SansSerif", Font.BOLD, 22)); // Match UserPage title font
        title.setForeground(new Color(25, 50, 120)); // deep navy

        JTextArea desc = new JTextArea(
            "Register vehicles, submit compute jobs, and monitor progress in real time.\n" +
            "Use your account to access dashboards and manage jobs."
        );
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setEditable(false);
        desc.setFocusable(false);
        desc.setOpaque(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setMaximumSize(new Dimension(700, 90));
        desc.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
        desc.setForeground(new Color(80, 80, 80)); 
        
        // Center align the text 
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Wrap in a panel to ensure proper centering
        JPanel descPanel = new JPanel();
        descPanel.setLayout(new BoxLayout(descPanel, BoxLayout.Y_AXIS));
        descPanel.setOpaque(false);
        descPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLine1 = new JLabel("Register vehicles, submit compute jobs, and monitor progress in real time.");
        JLabel descLine2 = new JLabel("Use your account to access dashboards and manage jobs.");
        
        descLine1.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descLine1.setForeground(new Color(80, 80, 80));
        descLine1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        descLine2.setFont(new Font("SansSerif", Font.PLAIN, 16));
        descLine2.setForeground(new Color(80, 80, 80));
        descLine2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        descPanel.add(descLine1);
        descPanel.add(Box.createVerticalStrut(4));
        descPanel.add(descLine2);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginBtn = new JButton("Log In");
        JButton createBtn = new JButton("Create Account");

        // Match styling
        Font btnFont = new Font("SansSerif", Font.BOLD, 18);
        Dimension btnSize = new Dimension(220, 56);

        styleButton(loginBtn, new Color(35, 99, 188), btnFont, btnSize);   // blue
        styleButton(createBtn, new Color(25, 140, 100), btnFont, btnSize); // Green

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        buttonPanel.add(loginBtn, gbc);
        buttonPanel.add(createBtn, gbc);

        loginBtn.addActionListener(e -> {
            frame.dispose();
            new UserPage(); // login
        });

        createBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                frame,
                "Create Account is coming soon.\n(We'll plug in RegisterPage here when it's ready.)",
                "Create Account",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        root.add(Box.createVerticalStrut(40));
        root.add(title);
        root.add(Box.createVerticalStrut(16));
        root.add(descPanel);
        root.add(Box.createVerticalGlue()); // Push buttons down
        root.add(buttonPanel);
        root.add(Box.createVerticalStrut(60)); // Space at bottom

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color baseColor, Font font, Dimension size) {
        button.setFont(font);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);




            }
        });
    }
}