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

public class vehicle_ui {

    public vehicle_ui(){
        VehicleInventoryController inventoryController =  new VehicleInventoryController();
            JFrame window = new JFrame("Vehicle owner");
      //60px from left edge of window, 100px from right, width is 500, height is 600
      window.setSize(720, 480);
      window.setLocationRelativeTo(null);
      //make window visible
        window.setVisible(true); 
        window.setLayout(new GridLayout(3,1));
       //set default close operation
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        JPanel panel = new JPanel();
        //Account button: check the owner id
        JButton account_btn = new JButton("Account");
         window.add(account_btn);
        
        account_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//need to connect 
                JOptionPane.showMessageDialog(null, "Owner ID: ", "Your info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    /*vehicle button: check vehicle id, model, year
       -if vehicle registered on data, it will display all the info
       -if not, user would be able to input and store in the database*/ 
        JButton vehicle_btn = new JButton("Vehicle info");
        window.add(vehicle_btn);
        
        
        vehicle_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String[]options = {"Register vehicle", "View vehicle info"};
            	//choose an option: register vehicle or view vehicle info, the first option needs to input info, the second option needs message ,options array and initial value is 0
            	int selectedOption = JOptionPane.showOptionDialog(null, "choose an option", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            
            	if(selectedOption == 0) {
            		registerVehicle();
            		
            	}else if(selectedOption == 1) {
            		new ViewVehicleInfo().setVisible(true);
            	}
        }
        
         private void registerVehicle() {
        	 JTextField model = new JTextField();
        	 JTextField make = new JTextField();
        	 JTextField year = new JTextField();
        	 JTextField licensePlate = new JTextField();
        	 JTextField residency = new JTextField();
        	 
        	 Object[]message = {
        			 "Model: ", model,
        			 "Make: ", make,
        			 "Year: ", year,
        			 "LicensePlate: ",licensePlate,
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
        		    inventoryController.addVehicle(licensePlate,model,make,year,residency);
        		    JOptionPane.showMessageDialog(null, 
        		        "Vehicle registered successfully!\nTimestamp: " + timestamp, 
        		        "Success", JOptionPane.INFORMATION_MESSAGE);
        		}
         }
         
            });
       
        JButton ts_btn = new JButton("Time Selection");
        window.add(ts_btn); 
      
        ts_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	 JTextField ArrivalTime = new JTextField(10);
            	 JTextField DepartureTime = new JTextField(10);
            	 JTextField licensePlate = new JTextField();
            	 
            	 Object[]message = {
            			 "LicensePlate: ",licensePlate,
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
                        //store to data file
                    }

               // timestamp
                  String timestamp = getCurrentTimestamp();
                  System.out.println("[" + timestamp + "] Time selection set for license: " + license);
                  inventoryController.setAvailability(license, ArrivalTime, DepartureTime);
                  JOptionPane.showMessageDialog(null, 
                      "Time selection saved successfully!\nTimestamp: " + timestamp, 
                      "Success", JOptionPane.INFORMATION_MESSAGE);
                }
        };
    
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
     
        JButton residency_btn = new JButton("Residency");
        window.add(residency_btn);   
        
        residency_btn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	JOptionPane.showMessageDialog(null, "location: ", "Residency", JOptionPane.OK_CANCEL_OPTION, null);
        }
        });
        
        
     window.add(panel);   
    }
    
 // update: get current timestamp
    private static String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    
    static class ViewVehicleInfo extends JFrame{
        private JTextArea area = new JTextArea();
        ViewVehicleInfo(){
            setTitle("List of Vehicles");
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
