package common.ui;

import common.model.User;
import job.ui.JobOwnerPage;
import vehicle.ui.vehicle_ui;

import javax.swing.*;
import java.awt.*;

public class UserPage {
    User user = User.getInstance();
    public UserPage(){
        JFrame frame = new JFrame("VCRTS — Vehicular Cloud Real Time System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(720,480);

        // Subtitle below main title 
        JLabel subtitle = new JLabel("Please select your role to continue."); 
        subtitle.setHorizontalAlignment(SwingConstants.CENTER); 
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16)); 
        subtitle.setForeground(new Color(80, 80, 80)); 
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); 

   
        frame.setLocationRelativeTo(null); //  center window on screen
        
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // light blue background

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu(user.getUsername());
        JMenuItem logoutItem = new JMenuItem("Log-Out");
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu); 
            logoutItem.addActionListener(e -> {
                frame.dispose();
                new WelcomePage();
            });
            fileMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            logoutItem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            

        frame.setJMenuBar(menuBar); 

        JLabel label = new JLabel("Vehicular Cloud Real Time System");
        JPanel panel= new JPanel();
        JButton JobOwnerbutton = new JButton("Job Owner");
        JButton VehicleOwnerbutton = new JButton("Vehicle Owner");

        // added: make title larger, centered, and colored
        label.setHorizontalAlignment(SwingConstants.CENTER); 
        label.setFont(new Font("SansSerif", Font.BOLD, 22)); 
        label.setForeground(new Color(25, 50, 120)); 
        label.setBorder(BorderFactory.createEmptyBorder(20, 16, 12, 16)); 

        
        panel.setOpaque(false); //  let background color show through
        panel.setLayout(new GridBagLayout()); // centering for the buttons

       
        Font btnFont = new Font("SansSerif", Font.BOLD, 18); // bigger button text
        Dimension btnSize = new Dimension(220, 56);

        JobOwnerbutton.setFont(btnFont); 
        JobOwnerbutton.setPreferredSize(btnSize); 
        JobOwnerbutton.setFocusPainted(false); 
        JobOwnerbutton.setBackground(new Color(35, 99, 188)); // blue button
        JobOwnerbutton.setForeground(Color.WHITE); //  white text
        JobOwnerbutton.setOpaque(true); //  background color
        JobOwnerbutton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18)); 
        JobOwnerbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

        // Job Owner Button styling
        JobOwnerbutton.setFont(btnFont); 
        JobOwnerbutton.setPreferredSize(btnSize); 
        JobOwnerbutton.setFocusPainted(false); 
        Color jobOwnerBaseColor = new Color(35, 99, 188); 
        JobOwnerbutton.setBackground(jobOwnerBaseColor);
        JobOwnerbutton.setForeground(Color.WHITE);
        JobOwnerbutton.setOpaque(true);
        JobOwnerbutton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18)); 
        JobOwnerbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add hover effect for Job Owner button
        JobOwnerbutton.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
        JobOwnerbutton.setBackground(jobOwnerBaseColor.darker()); 
    }
         public void mouseExited(java.awt.event.MouseEvent evt) {
        JobOwnerbutton.setBackground(jobOwnerBaseColor);
    }
});

        // Vehicle Owner Button styling
        VehicleOwnerbutton.setFont(btnFont); 
        VehicleOwnerbutton.setPreferredSize(btnSize); 
        VehicleOwnerbutton.setFocusPainted(false);
        Color vehicleOwnerBaseColor = new Color(25, 140, 100); 
        VehicleOwnerbutton.setBackground(vehicleOwnerBaseColor);
        VehicleOwnerbutton.setForeground(Color.WHITE); 
        VehicleOwnerbutton.setOpaque(true); 
        VehicleOwnerbutton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18)); 
        VehicleOwnerbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add hover effect for Vehicle Owner button
        VehicleOwnerbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                VehicleOwnerbutton.setBackground(vehicleOwnerBaseColor.darker()); 
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                VehicleOwnerbutton.setBackground(vehicleOwnerBaseColor);
    }
});

        VehicleOwnerbutton.setFont(btnFont); 
        VehicleOwnerbutton.setPreferredSize(btnSize); 
        VehicleOwnerbutton.setFocusPainted(false); 
        VehicleOwnerbutton.setBackground(new Color(25, 140, 100)); //green button
        VehicleOwnerbutton.setForeground(Color.WHITE); 
        VehicleOwnerbutton.setOpaque(true); 
        VehicleOwnerbutton.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18)); 
        VehicleOwnerbutton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 

       
        GridBagConstraints gbc = new GridBagConstraints(); 
        gbc.insets = new Insets(12, 12, 12, 12); // spacing around buttons
        panel.add(JobOwnerbutton, gbc); // add first button centered
        panel.add(VehicleOwnerbutton, gbc); // add second button centered

        JobOwnerbutton.addActionListener(e -> {
            frame.dispose();
            new JobOwnerPage().setVisible(true);
        });
        VehicleOwnerbutton.addActionListener(e -> {
            frame.dispose();
            new vehicle_ui();
        });

        frame.setLayout(new BorderLayout());

        // Combine title + subtitle vertically and center both
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));// stack vertically
        titlePanel.setBackground(new Color(240, 248, 255)); 

        label.setAlignmentX(Component.CENTER_ALIGNMENT);    // center the main title
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT); // center the subtitle

        titlePanel.add(Box.createVerticalStrut(10)); 
        titlePanel.add(label);
        titlePanel.add(subtitle);
        titlePanel.add(Box.createVerticalStrut(10)); 

        frame.add(titlePanel, BorderLayout.NORTH);

        frame.add(panel, BorderLayout.CENTER); 
        
        ((JComponent) frame.getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(12, 12, 12, 12) 
        );

        frame.setVisible(true); 

        // === Back Arrow in Menu Bar 
        JButton backBtn = new JButton("⟵");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(2,10,2,10));
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // When clicked, go back to the previous screen 
        backBtn.addActionListener(e -> {
            frame.dispose();
            new WelcomePage(); // adjust as needed
        });

        menuBar.add(backBtn);
        menuBar.add(Box.createHorizontalStrut(8)); // optional spacing


                
            }
        }

        
