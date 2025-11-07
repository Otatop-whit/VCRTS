package common.ui;

import javax.swing.*;
import java.awt.*;
import common.service.AccountService;


 //"Create Account" card embeded inside WelcomePage | Fields: Name, Email, Password. No role selection, for now..

class CreateAccount extends JPanel {

    public interface Listener {
        void onCreate(String name, String email, String password);
        void onCancel();
    }

    private final JTextField nameField;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JLabel statusMsg;

    public CreateAccount(Listener listener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(new Color(250, 252, 255)); // match login card
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setMaximumSize(new Dimension(380, 160));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(25, 50, 120), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel title = new JLabel("Create Account");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        title.setForeground(new Color(25, 50, 120));

        // Name row
        JPanel nameRow = new JPanel(new BorderLayout(5, 0));
        nameRow.setOpaque(false);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        nameField = new JTextField(16);
        nameRow.add(nameLabel, BorderLayout.WEST);
        nameRow.add(nameField, BorderLayout.CENTER);

        // Email row
        JPanel emailRow = new JPanel(new BorderLayout(5, 0));
        emailRow.setOpaque(false);
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        emailField = new JTextField(16);
        emailRow.add(emailLabel, BorderLayout.WEST);
        emailRow.add(emailField, BorderLayout.CENTER);

        // Password row
        JPanel pwdRow = new JPanel(new BorderLayout(5, 0));
        pwdRow.setOpaque(false);
        JLabel pwdLabel = new JLabel("Password:");
        pwdLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        passwordField = new JPasswordField(16);
        pwdRow.add(pwdLabel, BorderLayout.WEST);
        pwdRow.add(passwordField, BorderLayout.CENTER);

        // Buttons
        JPanel actionRow = new JPanel();
        actionRow.setOpaque(false);
        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
    
        styleButton(okBtn, new Color(25, 140, 100), new Font("SansSerif", Font.BOLD, 13), new Dimension(100, 32));
        styleButton(cancelBtn, new Color(140, 140, 140), new Font("SansSerif", Font.BOLD, 13), new Dimension(90, 30));
    
        actionRow.add(okBtn);
        actionRow.add(cancelBtn);
    
        
        

        // Status line
        statusMsg = new JLabel(" ");
        statusMsg.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusMsg.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusMsg.setForeground(new Color(170, 30, 30));

        // Actions
        okBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (name.length() < 2) { setError("Please enter your full name."); return; }
            if (!isValidEmail(email)) { setError("Invalid email. Please try again."); return; }
            if (password.length() < 6) { setError("Password must be at least 6 characters."); return; }
            
            AccountService accountService = new AccountService();
            boolean success = accountService.createNewAccount(name, email, password);
            
            if (success) {
                setSuccess("Account created successfully!");
                clearFields();
            } else {
                setError("Email already exists! Please use a different email.");
            }
            
            if (listener != null) listener.onCreate(name, email, password);
        });

        cancelBtn.addActionListener(e -> {
            if (listener != null) listener.onCancel();
        });
        
      
        
        // Enter submits
        passwordField.addActionListener(e -> okBtn.doClick());

        // Assemble
        add(title);
        add(Box.createVerticalStrut(5));
        add(nameRow);
        add(Box.createVerticalStrut(5));
        add(emailRow);
        add(Box.createVerticalStrut(5));
        add(pwdRow);
        add(Box.createVerticalStrut(8));
        add(actionRow);
        add(Box.createVerticalStrut(4));
        add(statusMsg);

        setVisible(false); // hidden until shown by WelcomePage
    }

    private void styleButton(JButton button, Color baseColor, Font font, Dimension size) {
        button.setFont(font);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(baseColor.darker()); }
            public void mouseExited (java.awt.event.MouseEvent evt) { button.setBackground(baseColor); }
        });
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        int at = email.indexOf('@');
        int dot = email.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < email.length() - 1;
    }

    public void setError(String msg) {
        statusMsg.setForeground(new Color(170, 30, 30));
        statusMsg.setText(msg);
    }

    public void setSuccess(String msg) {
        statusMsg.setForeground(new Color(20, 120, 60));
        statusMsg.setText(msg);
    }

    public void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        statusMsg.setText(" ");
    }
   private void showAccountData() {
        try {
            AccountService accountService = new AccountService();
            java.util.List<common.model.Account> accounts = accountService.getAllAccounts();
            
            if (accounts.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No accounts found.", 
                    "Account File", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder content = new StringBuilder();
            content.append("Total accounts: ").append(accounts.size()).append("\n\n");
            
            for (int i = 0; i < accounts.size(); i++) {
                common.model.Account account = accounts.get(i);
                content.append("Account ").append(i + 1).append(":\n");
                content.append("  Name: ").append(account.getName()).append("\n");
                content.append("  Email: ").append(account.getEmail()).append("\n");
                content.append("  Password: ").append(account.getPassword()).append("\n");
                content.append("-------------------\n");
            }

            JTextArea textArea = new JTextArea(content.toString(), 15, 40);
            textArea.setEditable(false);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JScrollPane scrollPane = new JScrollPane(textArea);
            
            JOptionPane.showMessageDialog(this, 
                scrollPane, 
                "Account Information", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error reading account data: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }


    } 

