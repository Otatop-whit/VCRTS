package common.ui;

import common.model.AccountCache;
import common.model.User;
import vccontroller.ui.ControllerPage;

import javax.swing.*;
import java.awt.*;

//import common.service.AccountService;

public class WelcomePage {
    private JFrame frame;
    private JPanel loginPanel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel loginMsg;
    private Timer fadeTimer;
    private float panelOpacity = 0f;
    private CreateAccount createPanel;
    private AccountCache accountCache = AccountCache.getInstance();

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

                // Controller access 
        JPanel controllerAccessPanel = new JPanel();
        controllerAccessPanel.setOpaque(false);
        controllerAccessPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel controllerLabel = new JLabel("Are you the Controller?");
        controllerLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JButton controllerLink = new JButton("<HTML><U>Log in here</U></HTML>");
        controllerLink.setFont(new Font("SansSerif", Font.PLAIN, 13));
        controllerLink.setFocusPainted(false);
        controllerLink.setContentAreaFilled(false);
        controllerLink.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        controllerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        controllerAccessPanel.add(controllerLabel);
        controllerAccessPanel.add(controllerLink);

        // Small controller password panel (initially hidden)
        JPanel controllerPasswordPanel = new JPanel();
        controllerPasswordPanel.setOpaque(false);
        controllerPasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel controllerPasswordLabel = new JLabel("Password:");
        JPasswordField controllerPasswordField = new JPasswordField(12);
        JButton controllerSubmitBtn = new JButton("Submit");

        controllerPasswordPanel.add(controllerPasswordLabel);
        controllerPasswordPanel.add(controllerPasswordField);
        controllerPasswordPanel.add(controllerSubmitBtn);
        controllerPasswordPanel.setVisible(false);

        // Show password box when link is clicked
        controllerLink.addActionListener(e -> {
            controllerPasswordPanel.setVisible(true);
            frame.revalidate();
            frame.repaint();
        });

        // Logic to open ControllerPage UI after password check
        controllerSubmitBtn.addActionListener(e -> {
            String pw = new String(controllerPasswordField.getPassword());
            if ("controller".equals(pw)) {
                User.getInstance().logout();
                User.getInstance().login("controller@vcrts.com", "Controller");
                frame.dispose();
                new ControllerPage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(
                    frame,
                    "Incorrect controller password.",
                    "Access denied",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });



        //  login box
        buildLoginPanel();

        createPanel = new CreateAccount(new CreateAccount.Listener() {
            @Override
            public void onCreate(String name, String email, String password) {
                // For now just show success, DB later on
                JOptionPane.showMessageDialog(
                    frame,
                    "✅ Account created!\nName: " + name + "\nEmail: " + email,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );
                createPanel.setSuccess("Account created successfully!");
                // Hide form and return to main buttons 
                createPanel.setVisible(false);
                // show main buttons again:
                buttonPanel.setVisible(true);
             
            }
        
            @Override
            public void onCancel() {
                createPanel.setVisible(false);
                // bring back main buttons
                buttonPanel.setVisible(true);
            }
        });
        

        // Actions
        loginBtn.addActionListener(e -> {
            toggleButtons(false); // hide buttons
            showLoginPanelWithFade();
        });
        createBtn.addActionListener(e -> {
            // Hide the big buttons row so it matches the login flow
            buttonPanel.setVisible(false);
            // Hide login panel 
            loginPanel.setVisible(false);
            // Reset and show CreateAccount inline card
            createPanel.clearFields();
            createPanel.setVisible(true);
        });

        
        root.add(Box.createVerticalStrut(40));
        root.add(title);
        root.add(Box.createVerticalStrut(16));
        root.add(descPanel);
        root.add(Box.createVerticalGlue());
        root.add(buttonPanel);
        root.add(Box.createVerticalStrut(12));
        root.add(loginPanel);
        root.add(controllerAccessPanel);
        root.add(controllerPasswordPanel);
        root.add(createPanel); 
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
    
        JButton cancel = new JButton("Cancel");
        styleButton(cancel, new Color(140, 140, 140), new Font("SansSerif", Font.BOLD, 13), new Dimension(90, 30));
        actionRow.add(signIn);
        actionRow.add(cancel);
    

        loginMsg = new JLabel(" ");
        loginMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginMsg.setFont(new Font("SansSerif", Font.PLAIN, 12));
        loginMsg.setForeground(new Color(170, 30, 30));

        signIn.addActionListener(e -> doLogin());
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
        String email = emailField.getText().trim().toLowerCase();
        String pass = new String(passwordField.getPassword());
        if (accountCache.emailExists(email) && accountCache.getAccount(email).getPassword().equals(pass)){
        //if (email.equalsIgnoreCase("controller@vcrts.com") && pass.equals("controller")) {
            loginMsg.setForeground(new Color(20, 120, 60));
            loginMsg.setText("Login successful!");
            User.getInstance().login(email, accountCache.getAccount(email).getName());
            JOptionPane.showMessageDialog(
                    frame,
                    "✅ Login successful! Welcome to VCRTS.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            frame.dispose();
            if(accountCache.getAccount(email).getRole().equals("client") ){

                new UserPage();
            }else{
                //new ControllerPage();
            }
        } else {
            loginMsg.setForeground(new Color(170, 30, 30));
            loginMsg.setText("Invalid email or password.");
        }
    }
   /* private void testLogin() {
            loginMsg.setForeground(new Color(20, 120, 60));
            loginMsg.setText("Login successful!");

            String email = "controller@vcrts.com";
            User.getInstance().login(email, accountCache.getAccount(email).getName());

            JOptionPane.showMessageDialog(
                    frame,
                    "✅ Login successful! Welcome to VCRTS.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            frame.dispose();
        if(accountCache.getAccount(email).getRole().equals("client") ){

            new UserPage();
        }else{
            new ControllerPage();
        }
    }
*/
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
