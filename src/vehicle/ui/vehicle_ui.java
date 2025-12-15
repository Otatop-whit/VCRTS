package vehicle.ui;
import javax.swing.*;

import common.model.User;
import vehicle.model.VehicleOwner;
import vehicle.service.VehicleClient;
import vehicle.service.VehicleIDCache;
import vehicle.service.VehicleOwnerServiceImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private static final Font LABEL_FONT = new Font("SansSerif", Font.PLAIN, 12);
    private static final Dimension BUTTON_SIZE = new Dimension(220, 56); 
    private JFrame window;
    User user = User.getInstance();
    VehicleOwner vehicleOwner = VehicleOwnerServiceImpl.loadOwner(user);
   
    VehicleIDCache cache = VehicleIDCache.getInstance();

       
    public vehicle_ui(){
        //Loads Files to Vehicle Owner
         //vehicleOwner = VehicleOwnerServiceImpl.loadVehicles(vehicleOwner);

        window = new JFrame("VCRTS — Vehicle Owner Dashboard");
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
        
        //background and layout
        window.setBackground(BACKGROUND_COLOR);
        window.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("Vehicle Owner Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TITLE_COLOR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        titlePanel.add(Box.createVerticalStrut(30)); 
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(10));
        
       
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(130, 80, 80, 50));
    
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        
        //get a new frame and added a back arrow. 
        JButton vehicle_btn = new JButton("Register vehicle");
        styleButton(vehicle_btn, BUTTON_COLOR_GREEN);
        
        vehicle_btn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                registerVehicle();
        }
 
    });
        buttonPanel.add(vehicle_btn);
        buttonPanel.add(Box.createHorizontalStrut(40));
            //added button for selecting arrival AND departure date

        mainPanel.add(buttonPanel);
           
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
            private void registerVehicle() {
                JFrame registerFrame = new JFrame ("Register New Vehicle");
                registerFrame.setSize(720,480);
                registerFrame.setLocationRelativeTo(window);
                registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                registerFrame.getContentPane().setBackground(BACKGROUND_COLOR);

                //  Back Arrow in Menu Bar 
                JButton backBtn = new JButton("⟵");
                backBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
                backBtn.setFocusPainted(false);
                backBtn.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));
                backBtn.setContentAreaFilled(false);
                backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                 // When clicked, go back to UserPage
                 backBtn.addActionListener(e -> {
                    registerFrame.dispose(); // closes current register vehicle window
                        window.toFront(); // brings main window to front
                });

                // Add to top menu or a small bar
                JMenuBar menuBar = new JMenuBar();
                menuBar.add(backBtn);
                menuBar.add(Box.createHorizontalStrut(8)); 
                registerFrame.setJMenuBar(menuBar);

                registerFrame.setLayout(new BorderLayout());

                JPanel titlePanel = new JPanel();
                titlePanel.setBackground(BACKGROUND_COLOR);
                titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
                JLabel titleLabel = new JLabel("Register New Vehicle", SwingConstants.CENTER);
                titleLabel.setFont(TITLE_FONT);
                titleLabel.setForeground(TITLE_COLOR);
                titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                titlePanel.add(Box.createVerticalStrut(20));
                titlePanel.add(titleLabel);
                titlePanel.add(Box.createVerticalStrut(20));

                JPanel main = new JPanel();
                main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
                main.setBackground(BACKGROUND_COLOR);
                main.setBorder(BorderFactory.createEmptyBorder(5,20,5,20));
                
                JPanel form = new JPanel(new GridLayout(9,2,3,3));
                form.setOpaque(false);                

                //input fields
                JLabel vehicleOwnerIDLabel = new JLabel("ID:");
                vehicleOwnerIDLabel.setFont(LABEL_FONT);
        
                JLabel modelLabel = new JLabel("Model:");
                modelLabel.setFont(LABEL_FONT);
        
                JLabel makeLabel = new JLabel("Make:");
                makeLabel.setFont(LABEL_FONT);
        
                JLabel yearLabel = new JLabel("Year:");
                yearLabel.setFont(LABEL_FONT);
        
                JLabel computingPowerLabel = new JLabel("Computing power:");
                computingPowerLabel.setFont(LABEL_FONT);
        
                JLabel licensePlateLabel = new JLabel("License plate:");
                licensePlateLabel.setFont(LABEL_FONT);
        
                JLabel arrivalDateLabel = new JLabel("Arrival date:");
                arrivalDateLabel.setFont(LABEL_FONT);
                
                JLabel departureDateLabel = new JLabel("Departure date:");
                departureDateLabel.setFont(LABEL_FONT);
        
                JLabel residencyLabel = new JLabel("Residency:");
                residencyLabel.setFont(LABEL_FONT);

                JTextField vehicleOwnerId = new JTextField();
                vehicleOwnerId.setFont(LABEL_FONT);
        
                JTextField model = new JTextField();
                model.setFont(LABEL_FONT);
        
                JTextField make = new JTextField();
                make.setFont(LABEL_FONT);

                DefaultComboBoxModel<String> yearModel = new DefaultComboBoxModel<>();
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                for (int y = 2000; y <= currentYear; y++) {
                    yearModel.addElement(String.valueOf(y));
                }
                JComboBox<String> year = new JComboBox<>(yearModel);
                year.setSelectedIndex(-1);
                year.setFont(LABEL_FONT);

                String[] powerLevels = {"High", "Medium", "Low"};
                JComboBox<String> computingPower = new JComboBox<>(powerLevels);
                computingPower.setSelectedIndex(-1);
                computingPower.setFont(LABEL_FONT);

                JTextField licensePlate = new JTextField();
                licensePlate.setFont(LABEL_FONT);
                JTextField arrivalDate = new JTextField();
                arrivalDate.setFont(LABEL_FONT);
                JButton arrival_btn = new JButton("📅");

                JTextField departureDate = new JTextField();
                departureDate.setFont(LABEL_FONT);
                JButton departure_btn = new JButton("📅");

                String[] usStates = "AL,AK,AZ,AR,CA,CO,CT,DE,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO,MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY".split(",");
                JComboBox<String> residency = new JComboBox<>(usStates);
                residency.setSelectedIndex(-1);
                residency.setFont(new Font("SansSerif", Font.PLAIN, 12));
                 
                //added panels for each date
                JPanel arrivalDatePanel = new JPanel(new BorderLayout());
                arrivalDatePanel.add(arrivalDate, BorderLayout.CENTER);
                arrivalDatePanel.add(arrival_btn, BorderLayout.EAST);
    
                JPanel departureDatePanel = new JPanel(new BorderLayout());
                departureDatePanel.add(departureDate, BorderLayout.CENTER);
                departureDatePanel.add(departure_btn, BorderLayout.EAST);
                
                arrival_btn.addActionListener(e -> showSimpleDateDialog(arrivalDate));
                departure_btn.addActionListener(e -> showSimpleDateDialog(departureDate));

            form.add(vehicleOwnerIDLabel);
            form.add(vehicleOwnerId);
            form.add(modelLabel);
            form.add(model);
            form.add(makeLabel);
            form.add(make);
            form.add(yearLabel);
            form.add(year);
            form.add(computingPowerLabel);
            form.add(computingPower);
            form.add(licensePlateLabel);
            form.add(licensePlate);
            form.add(arrivalDateLabel);
            form.add(arrivalDatePanel);
            form.add(departureDateLabel);
            form.add(departureDatePanel);
            form.add(residencyLabel);
            form.add(residency);

            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
            buttonPanel.setBackground(BACKGROUND_COLOR);
    
             JButton submitBtn = new JButton("submit");
            styleButton(submitBtn, BUTTON_COLOR_GREEN);
            submitBtn.setPreferredSize(new Dimension(140, 32));
    
            buttonPanel.add(submitBtn);

            main.add(form);
            main.add(Box.createVerticalStrut(1));
            main.add(buttonPanel);

            JPanel footerPanel = new JPanel(new BorderLayout());
            footerPanel.setBackground(new Color(235, 240, 250));
            footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 220, 235)));
            footerPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, 24));

            JLabel footerText = new JLabel(
            "© 2025 Vehicular Cloud Real Time System  |  All Rights Reserved",
            SwingConstants.CENTER
    );
            footerText.setFont(new Font("SansSerif", Font.PLAIN, 11));
            footerText.setForeground(new Color(90, 90, 90));
            footerPanel.add(footerText, BorderLayout.CENTER);

   
            registerFrame.add(titlePanel, BorderLayout.NORTH);
            registerFrame.add(main, BorderLayout.CENTER);
            registerFrame.add(footerPanel, BorderLayout.SOUTH);
                 
                submitBtn.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                    if(vehicleOwnerId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, 
                            "Vehicle Owner ID cannot be empty!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(verifyIDInput(vehicleOwnerId) == false){ //Checks if the ID is an integer
                        JOptionPane.showMessageDialog(null, 
                            "Vehicle Owner ID must be an Integer!", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(cache.getVehicleIds().contains(Integer.parseInt(vehicleOwnerId.getText().trim()))){ //Checks if the ID already exists in cache
                            JOptionPane.showMessageDialog(null, 
                            "Vehicle Owner ID already exists! Please use a different ID.", "Error", 
                            JOptionPane.ERROR_MESSAGE);
                            return;
                    }
                    
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
                    int vehicleId = Integer.parseInt(vehicleOwnerId.getText().trim());
                    cache.storeVehicleId(vehicleId);//Stores the new vehicle ID in cache
        
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
                    user.getEmail()); //get user id

                    //Vehicle vehicle = vehicleOwner.createVehicle(licensePlate, model, make, year, computingPower, arrivalDate, departureDate, residency);

                    JDialog statusDialog = new JDialog(registerFrame, "Vehicle Status", false);
                    JLabel statusLabel = new JLabel("Waiting for approval...please don't close this message");
                    statusLabel.setFont(LABEL_FONT);
                    statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    statusDialog.add(statusLabel);
                    statusDialog.pack();
                    statusDialog.setLocationRelativeTo(registerFrame);
                    statusDialog.setVisible(true);

                    VehicleClient client = new VehicleClient("localhost", 1234);
                    client.setStatusCallback(status -> {
                        SwingUtilities.invokeLater(() -> {
                            if (status.equals("Accepted")) {
            JOptionPane.showMessageDialog(
                null,
                "Your vehicle has been accepted!",
                "Status",
                JOptionPane.INFORMATION_MESSAGE
            );
        } 
        else if (status.equals("Rejected")) {
            JOptionPane.showMessageDialog(
                null,
                "Sorry, your vehicle was rejected.",
                "Status",
                JOptionPane.ERROR_MESSAGE
            );
        }
                registerFrame.dispose();
                window.toFront();
                        });
                    });
                    client.sendJobLine(line);

                    String timestamp = getCurrentTimestamp();
                    System.out.println("[" + timestamp + "] Vehicle registered: " + licensePlate.getText());
               
                  
                    }
            

               });
               registerFrame.setVisible(true);
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
    
    private boolean verifyIDInput(JTextField vehicleID){
        String idInput = vehicleID.getText();
        try{
            Integer.parseInt(idInput);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
}
