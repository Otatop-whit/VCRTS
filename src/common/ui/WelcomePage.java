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
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
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
        desc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        desc.setForeground(Color.DARK_GRAY);

        JPanel buttons = new JPanel(new GridLayout(1, 2, 16, 0));
        buttons.setMaximumSize(new Dimension(400, 40));
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.setOpaque(false);

        JButton loginBtn = new JButton("Log In");
        JButton createBtn = new JButton("Create Account");

        styleButton(loginBtn, new Color(0, 120, 215));   // blue
        styleButton(createBtn, new Color(0, 180, 120)); // green

        buttons.add(loginBtn);
        buttons.add(createBtn);

        loginBtn.addActionListener(e -> {
            frame.dispose();
            new UserPage(); // login
        });

        createBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                frame,
                "Create Account is coming soon.\n(We’ll plug in RegisterPage here when it’s ready.)",
                "Create Account",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        root.add(Box.createVerticalStrut(24));
        root.add(title);
        root.add(Box.createVerticalStrut(12));
        root.add(desc);
        root.add(Box.createVerticalStrut(28));
        root.add(buttons);
        root.add(Box.createVerticalGlue());

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color baseColor) {
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
    }
}
