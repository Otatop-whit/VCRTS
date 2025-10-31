package vehicle.ui;
import javax.swing.*;

import vehicle.service.VehicleInventoryController;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class vehicle_ui {
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color TITLE_COLOR = new Color(25, 50, 120);
    private static final Color BUTTON_COLOR_BLUE = new Color(35, 99, 188);
    private static final Color BUTTON_COLOR_GREEN = new Color(25, 140, 100);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 22);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public vehicle_ui(){
        VehicleInventoryController inventoryController =  new VehicleInventoryController();
        JFrame window = new JFrame("VCRTS — Vehicle Owner Portal");
        window.setSize(720, 480);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      
        window.getContentPane().setBackground(BACKGROUND_COLOR);
        window.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Vehicle Owner Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("What would you like to do?", SwingConstants.CENTER);
        subtitleLabel.setFont(LABEL_FONT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(Box.createVerticalStrut(30));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        titlePanel.add(subtitleLabel);
        titlePanel.add(Box.createVerticalStrut(30));
        
       
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

    
        JButton account_btn = new JButton("Account");
        styleButton(account_btn, BUTTON_COLOR_BLUE);
        mainPanel.add(account_btn);
        
        account_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Owner ID: ", "Your info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton vehicle_btn = new JButton("Vehicle info");
        styleButton(vehicle_btn, BUTTON_COLOR_GREEN);
        mainPanel.add(vehicle_btn);
        
        vehicle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Register vehicle", "View vehicle info"};
                int selectedOption = JOptionPane.showOptionDialog(null, "Choose an option", "Vehicle Management", 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            
                if(selectedOption == 0) {
                    registerVehicle();
                } else if(selectedOption == 1) {
                    new ViewVehicleInfo().setVisible(true);
                }
            }
            
            private void registerVehicle() {
                JTextField model = new JTextField();
                JTextField make = new JTextField();
                JTextField year = new JTextField();
                JTextField licensePlate = new JTextField();
                JTextField residency = new JTextField();
                 
                Object[] message = {
                    "Model: ", model,
                    "Make: ", make,
                    "Year: ", year,
                    "LicensePlate: ", licensePlate,
                    "Residency: ", residency,
                };
                 
                int registerOption = JOptionPane.showConfirmDialog(null, message, "Register New Vehicle", JOptionPane.OK_CANCEL_OPTION);
                if(registerOption == JOptionPane.OK_OPTION){
                    if(model.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Model cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(make.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Make cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(year.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Year cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(licensePlate.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "License Plate cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(residency.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Residency cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                     
                    String timestamp = getCurrentTimestamp();
                    System.out.println("[" + timestamp + "] Vehicle registered: " + licensePlate.getText());
                    inventoryController.addVehicle(licensePlate, model, make, year, residency);
                    JOptionPane.showMessageDialog(null, 
                        "Vehicle registered successfully!\nTimestamp: " + timestamp, 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

       
        JButton ts_btn = new JButton("Time Selection");
        styleButton(ts_btn, BUTTON_COLOR_BLUE);
        mainPanel.add(ts_btn);
      
        ts_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField ArrivalTime = new JTextField(10);
                JTextField DepartureTime = new JTextField(10);
                JTextField licensePlate = new JTextField();
                 
                Object[] message = {
                    "LicensePlate: ", licensePlate,
                    "ArrivalTime(YYYY-MM-DD): ", ArrivalTime,
                    "DepartureTime(YYYY-MM-DD): ", DepartureTime,
                };
            
                int option = JOptionPane.showConfirmDialog(null, message, 
                    "Time Selection", JOptionPane.OK_CANCEL_OPTION);
                
                if (option == JOptionPane.OK_OPTION) {
                    String license = licensePlate.getText();
                    String arrival = ArrivalTime.getText();
                    String departure = DepartureTime.getText();
                  
                    if (license.isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "License plate cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (!isValidDate(arrival)) {
                        JOptionPane.showMessageDialog(null, 
                            "Invalid arrival date format! Please use YYYY-MM-DD", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (!isValidDate(departure)) {
                        JOptionPane.showMessageDialog(null, 
                            "Invalid departure date format! Please use YYYY-MM-DD", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String timestamp = getCurrentTimestamp();
                    System.out.println("[" + timestamp + "] Time selection set for license: " + license);
                    inventoryController.setAvailability(license, ArrivalTime, DepartureTime);
                    JOptionPane.showMessageDialog(null, 
                        "Time selection saved successfully!\nTimestamp: " + timestamp, 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
    
            private boolean isValidDate(String dateStr) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    format.setLenient(false);
                    format.parse(dateStr);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });

        JButton editVehicle_btn = new JButton("Edit Vehicle");
        styleButton(editVehicle_btn, BUTTON_COLOR_GREEN);
        mainPanel.add(editVehicle_btn);

        editVehicle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
                
                JTextField licenseField = new JTextField(10);
                JTextField arrivalField = new JTextField(10);
                JTextField departureField = new JTextField(10);
                
                panel.add(new JLabel("License Plate:"));
                panel.add(licenseField);
                panel.add(new JLabel("Arrival Time (YYYY-MM-DD):"));
                panel.add(arrivalField);
                panel.add(new JLabel("Departure Time (YYYY-MM-DD):"));
                panel.add(departureField);
                
                int result = JOptionPane.showConfirmDialog(null, panel, "Edit Vehicle", 
                          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
                if (result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(null, "Vehicle information updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        // Add all panels to window
        window.add(titlePanel, BorderLayout.NORTH);
        window.add(mainPanel, BorderLayout.CENTER);
        window.setVisible(true);
    }
    
    // Button styling method to coordinate with job owner
    private void styleButton(JButton button, Color baseColor) {
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(baseColor.darker());
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
    }
    
    private static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    
    static class ViewVehicleInfo extends JFrame{
        private JTextArea area = new JTextArea();
        ViewVehicleInfo(){
            setTitle("Vehicle Information");
            setSize(650, 500);
            setLocationRelativeTo(null);
            add(new JScrollPane(area));
            loadFile();
        }

        private void loadFile(){
            try{
                byte[] data = Files.readAllBytes(Paths.get("src/vehicle/repo/VehicleData.txt"));
                area.setText(new String(data));
                area.setVisible(true);
            } catch(java.io.IOException e){
                area.setText("Error! File Not Found.");
            }
        }
    }
}
