package vehicle.ui;
import javax.swing.*;

import vehicle.service.VehicleInventoryController;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

public class vehicle_ui {

    public vehicle_ui(){
        VehicleInventoryController inventoryController =  new VehicleInventoryController();
        JFrame window = new JFrame("Vehicle owner");
      //60px from left edge of window, 100px from right, width is 500, height is 600
        window.setBounds(60,100,500,600); 
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
            	//need to connect to logging page
                System.out.println("Owner id:");
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
            	String[]options = {"Register Vehicle", "View Vehicle Vnfo"};
            	//choose an option: register vehicle or view vehicle info, the first option needs to input info, the second option needs message ,options array and initial value is 0
            	int selectedOption = JOptionPane.showOptionDialog(null, "choose an option", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            
            	if(selectedOption == 0) {
            		registerVehicle();
            		
            	}else if(selectedOption == 1) {
            		viewVehicleInfo();
            	}
        }
        
         private void registerVehicle() {
        	 JTextField model = new JTextField();
        	 JTextField make = new JTextField();
        	 JTextField year = new JTextField();
        	 JTextField licensePlate = new JTextField();
        	 
        	 Object[]message = {
        			 "Model: ", model,
        			 "Manufacturer: ", make,
        			 "Manufacture Year: ", year,
        			 "LicensePlate: ",licensePlate,
        	 };


        	 int options1 = JOptionPane.showConfirmDialog(null, message, "Register New Vehicle", JOptionPane.OK_CANCEL_OPTION);
             if(options1 == JOptionPane.OK_OPTION){
                inventoryController.addVehicle(licensePlate,model,make,year);
                
             }
         }
         //need to connect here
        	 private void viewVehicleInfo() {
        		JOptionPane.showMessageDialog(null, null, "My Vehicle", JOptionPane.INFORMATION_MESSAGE);
                
        	 }
         
            });
       
        JButton residency_btn = new JButton("Residency");
        window.add(residency_btn); 
      

        residency_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options2 = {"Arrival Time", "Departure Time"};
                int selectedOption2 = JOptionPane.showOptionDialog(null, "choose an option", null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);
            
                if(selectedOption2 == 0) {
                    ArrivalDate();
                } else if(selectedOption2 == 1) {
                    DepartureDate();
                }
            }
            
            private void ArrivalDate() {
                JTextField dateField = new JTextField(10);
                dateField.setText("YYYY-MM-DD");
                
                Object[] message = {
                    "Enter Arrival Date (YYYY-MM-DD):", dateField
                };
                
                int result1 = JOptionPane.showConfirmDialog(null, message, "Arrival Date", JOptionPane.OK_CANCEL_OPTION);
                
                if (result1 == JOptionPane.OK_OPTION) {
                    String dateString = dateField.getText();
                  
                    if (isValidDate(dateString)) {
                        JOptionPane.showMessageDialog(null, "Arrival Date: " + dateString);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid date format! Please use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            
                
                
            }

            private void DepartureDate() {
                JTextField dateField = new JTextField(10);
                dateField.setText("YYYY-MM-DD");
                
                Object[] message = {
                    "Enter Departure Date (YYYY-MM-DD):", dateField
                };
                
                int result2 = JOptionPane.showConfirmDialog(null, message, "Departure Date", JOptionPane.OK_CANCEL_OPTION);
                if (result2 == JOptionPane.OK_OPTION) {
                    String dateString = dateField.getText();
                    
                    if (isValidDate(dateString)) {
                        JOptionPane.showMessageDialog(null, "Arrival Date: " + dateString);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid date format! Please use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
        window.add(panel);
    }

}
