package vehicle.ui;
import javax.swing.*;

import common.model.User;
import common.service.JobNetworkClient;
import job.ui.JobOwnerPage;
import vehicle.model.Vehicle;
import vehicle.model.VehicleOwner;
import vehicle.service.VehicleClient;
import vehicle.service.VehicleOwnerServiceImpl;

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
import java.util.Date;
import java.util.Calendar;


public class vehicle_ui {
    private static final Color BACKGROUND_COLOR = new Color(240, 248, 255);
    private static final Color TITLE_COLOR = new Color(25, 50, 120);
    private static final Color BUTTON_COLOR_BLUE = new Color(35, 99, 188);
    private static final Color BUTTON_COLOR_GREEN = new Color(25, 140, 100);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 22);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Dimension BUTTON_SIZE = new Dimension(220, 56); // 新增
    User user = User.getInstance();
    VehicleOwner vehicleOwner = VehicleOwnerServiceImpl.loadOwner(user);
    
    public vehicle_ui(){
        //Loads Files to Vehicle Owner
        //vehicleOwner = VehicleOwnerServiceImpl.loadVehicles(vehicleOwner);

        JFrame window = new JFrame("VCRTS — Vehicle Owner Dashboard");
        window.setSize(720, 480);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //  Back Arrow in Menu Bar 
        JButton backBtn = new JButton("⟵");
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
        backBtn.setContentAreaFilled(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // When clicked, go back to UserPage
        backBtn.addActionListener(e -> {
            window.dispose(); // closes current Vehicle UI window
            new common.ui.UserPage(); // navigates back to UserPage
        });

        // Add to top menu or a small bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(backBtn);
        menuBar.add(Box.createHorizontalStrut(8)); 
        window.setJMenuBar(menuBar);
        
      
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
        
       
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 80, 50));
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton vehicle_btn = new JButton("Register vehicle");
        styleButton(vehicle_btn, BUTTON_COLOR_GREEN);
        buttonPanel.add(vehicle_btn);
        buttonPanel.add(Box.createHorizontalStrut(20));
        
        vehicle_btn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                registerVehicle();
        }
 

            //added button for selecting arrival AND departure date
            private void registerVehicle() {
                JTextField model = new JTextField(25);
                JTextField make = new JTextField(25);

                DefaultComboBoxModel<String> yearModel = new DefaultComboBoxModel<>();
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int y = 2000; y <= currentYear; y++) {
                    yearModel.addElement(String.valueOf(y));
                }
                JComboBox<String> year = new JComboBox<>(yearModel);
                year.setSelectedIndex(-1);

                String[] powerLevels = {"High", "Medium", "Low"};
                JComboBox<String> computingPower = new JComboBox<>(powerLevels);
                computingPower.setSelectedIndex(-1);

                JTextField licensePlate = new JTextField(25);
                JTextField arrivalDate = new JTextField(25);
                JButton arrival_btn = new JButton("📅");

                JTextField departureDate = new JTextField(25);
                JButton departure_btn = new JButton("📅");

                String[] usStates = "AL,AK,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO,MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY".split(",");
                JComboBox<String> residency = new JComboBox<>(usStates);
                residency.setSelectedIndex(-1);

                User user = User.getInstance();
                String userEmail = user.getEmail();
                JTextField vehicleOwnerId = new JTextField(userEmail);
                vehicleOwnerId.setEditable(true);
                 
                //added panels for each date
                JPanel arrivalDatePanel = new JPanel(new BorderLayout());
                arrivalDatePanel.add(arrivalDate, BorderLayout.CENTER);
                arrivalDatePanel.add(arrival_btn, BorderLayout.EAST);
    
                JPanel departureDatePanel = new JPanel(new BorderLayout());
                departureDatePanel.add(departureDate, BorderLayout.CENTER);
                departureDatePanel.add(departure_btn, BorderLayout.EAST);
                
                arrival_btn.addActionListener(e -> showSimpleDateDialog(arrivalDate));
                departure_btn.addActionListener(e -> showSimpleDateDialog(departureDate));

                Object[] message = {
                    "ID: ", vehicleOwnerId,
                    "Model: ", model,
                    "Make: ", make,
                    "Year: ", year,
                    "Computing Power: ", computingPower,
                    "LicensePlate: ", licensePlate,
                    "Arrival Date: ", arrivalDatePanel,
                    "Departure Date: ",departureDatePanel,
                    "Residency: ", residency,
                };

            
                 
                int registerOption = JOptionPane.showConfirmDialog(
                        window, message, "Register New Vehicle", JOptionPane.OK_CANCEL_OPTION);
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
                    if(year.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, 
                            "Year cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(computingPower.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, 
                            "Computing power cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(licensePlate.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "License Plate cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(residency.getSelectedItem() == null) {
                        JOptionPane.showMessageDialog(null, 
                            "Residency cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    //String line = String.join("|", "JOB", enteredName, String.valueOf(enteredDuration), enteredDeadline);
                    String line = String.join("|","VEHICLE",
                    vehicleOwnerId.getText(),
                    model.getText(),
                    make.getText(),
                    (String) year.getSelectedItem(),
                    licensePlate.getText(),
                    (String) computingPower.getSelectedItem(),
                    arrivalDate.getText(),
                    departureDate.getText(),
                    (String) residency.getSelectedItem(),
                    vehicleOwner.getEmail());

                    //Vehicle vehicle = vehicleOwner.createVehicle(licensePlate, model, make, year, computingPower, arrivalDate, departureDate, residency);

                    JDialog statusDialog = new JDialog(window, "Vehicle Status", false);
                    JLabel statusLabel = new JLabel("Waiting for approval...please don't close this message");
                    statusLabel.setFont(LABEL_FONT);
                    statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    statusDialog.add(statusLabel);
                    statusDialog.pack();
                    statusDialog.setLocationRelativeTo(window);
                    statusDialog.setVisible(true);

                    VehicleClient client = new VehicleClient("localhost", 1234);
                    client.setStatusCallback(status -> {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            if (status.equals("Accepted")) {
                                statusLabel.setText("Your vehicle has been accepted!");
                            } else if (status.equals("Rejected")) {
                                statusLabel.setText("Sorry, this vehicle has been rejected.");
                            } else {
                                statusLabel.setText("Status: " + status);
                            }
                        });
                    });
                    client.sendJobLine(line);

                    String timestamp = getCurrentTimestamp();
                    System.out.println("[" + timestamp + "] Vehicle registered: " + licensePlate.getText());
                    JOptionPane.showMessageDialog(null, 
                        "Vehicle registered successfully!\nTimestamp: " + timestamp, 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }

               });

       
        JButton ts_btn = new JButton("Edit Time ");
        styleButton(ts_btn, BUTTON_COLOR_BLUE);
        buttonPanel.add(ts_btn);
      
        ts_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField ArrivalTime = new JTextField(25);
                JButton arrival_btn = new JButton("📅");
                JTextField DepartureTime = new JTextField(25);
                JButton departure_btn = new JButton("📅");
                JTextField licensePlate = new JTextField();

                JPanel arrivalDatePanel = new JPanel(new BorderLayout());
                arrivalDatePanel.add(ArrivalTime, BorderLayout.CENTER);
                arrivalDatePanel.add(arrival_btn, BorderLayout.EAST);

                JPanel departureDatePanel = new JPanel(new BorderLayout());
                departureDatePanel.add(DepartureTime, BorderLayout.CENTER);
                departureDatePanel.add(departure_btn, BorderLayout.EAST);

                arrival_btn.addActionListener(evt -> showSimpleDateDialog(ArrivalTime));
                departure_btn.addActionListener(evt -> showSimpleDateDialog(DepartureTime));
                 
                Object[] message = {
                    "LicensePlate: ", licensePlate,
                    "ArrivalTime(YYYY-MM-DD): ", arrivalDatePanel,
                    "DepartureTime(YYYY-MM-DD): ", departureDatePanel,
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
                    vehicleOwner.setAvailability(license, ArrivalTime, DepartureTime);
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

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
           
        window.add(titlePanel, BorderLayout.NORTH);
        window.add(mainPanel, BorderLayout.CENTER);

        // Footer 
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(235, 240, 250));
        footerPanel.setBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 220, 235))
        );
        footerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 24));

        JLabel footerText = new JLabel(
                "© 2025 Vehicular Cloud Real Time System  |  All Rights Reserved",
                SwingConstants.CENTER
        );
        footerText.setFont(new Font("SansSerif", Font.PLAIN, 11));
        footerText.setForeground(new Color(90, 90, 90));
        footerPanel.add(footerText, BorderLayout.CENTER);

        window.add(footerPanel, BorderLayout.SOUTH);

        // Bottom padding 
        ((JComponent) window.getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(12, 12, 28, 12)
        );

        window.setVisible(true);
            } 
            
    // Button styling method to coordinate with job owner
    private void styleButton(JButton button, Color baseColor) {
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBackground(baseColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(BUTTON_SIZE);
        button.setMaximumSize(BUTTON_SIZE);

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
    
    private void showSimpleDateDialog(JTextField targetField) {

        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY);
        JSpinner dateSpinner = new JSpinner(model);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd HH:mm");
        dateSpinner.setEditor(editor);
  
        int result = JOptionPane.showConfirmDialog(
            null, 
            dateSpinner, 
            "Select Date and Time", 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE
    );
    
        if (result == JOptionPane.OK_OPTION) {
        Date selectedDate = (Date) dateSpinner.getValue();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        targetField.setText(sdf.format(selectedDate));
        
    }
    
}
    
    }

