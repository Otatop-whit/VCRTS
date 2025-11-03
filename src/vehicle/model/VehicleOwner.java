package vehicle.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import javax.swing.JTextField;

import common.model.Role;
import common.model.User;
import vehicle.service.VehicleOwnerServiceImpl;


public class VehicleOwner extends User {
    private int numOfVehicles;
    private VehicleInventory userInventory;
    private Vehicle vehicle;
    private String filename; //Vehicle Owner saves their direct File Link
    
    public VehicleOwner(int id, String username, String email, String name, Role role){
        super(id, username, email, name, role);
        this.userInventory = new VehicleInventory();
    }
    //Getters
    public int getID(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public Role getRole(){
        return role;
    }
    public int getNumOfVehicles(){
        return numOfVehicles;
    }
    public VehicleInventory getInventory(){
        if(userInventory == null){
            userInventory = new VehicleInventory();
        }
        return userInventory;
    }
    public String getFilename(){
        return filename;
    }
    //Setters
    public void setVehicleOwnerName(String vehicleOwnerName){
        this.username = vehicleOwnerName;
    }
    public void setNumOfVehicles(int vehicleCount){
        this.numOfVehicles = vehicleCount;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }

    //Creates Vehicle from UI
    public Vehicle createVehicle(JTextField licensePlate, JTextField model, JTextField make, JTextField year, JTextField computingPower ,JTextField arriveDate, JTextField departureDate, JTextField residency){
       try{
            String vehiclePlate = licensePlate.getText().trim();
            String vehicleModel = model.getText().trim();
            String vehicleMake = make.getText().trim();
            Year vehicleYear = Year.parse(year.getText().trim());
            String computePower = computingPower.getText().trim();

            String arrivalDate = arriveDate.getText().trim();
            String departDate = departureDate.getText().trim();
            String resident = residency.getText().trim();

            this.vehicle = new Vehicle.VehicleBuilder().setVehicleOwnerID(id).setLicensePlate(vehiclePlate)
            .setVehicleModel(vehicleModel).setVehicleMake(vehicleMake).setVehicleYear(vehicleYear)
            .setComputingPower(computePower).setArrivalDate(arrivalDate).setDepatureDate(departDate)
            .setResidency(resident).build();
            
            numOfVehicles++;
       }
       catch(Exception e){
        System.err.println("Error! Failed to create vehicle.");
        e.printStackTrace();
       }
        return vehicle;
    }

    //Creates Vehicle from File
    public Vehicle importVehicle(Vehicle fileVehicle){
        this.vehicle = fileVehicle;
        return vehicle;
    }

    //Stores Vehicle in userInventory
    public void storeVehicle(){
        userInventory.addVehicle(this.vehicle.getLicensePlate(), this.vehicle);
    }

    //Removes the vehicle constructed from personal Vehicle Inventory
    public void removeVehicle(String licensePlate){
        userInventory.removeVehicle(licensePlate);
        numOfVehicles--;
    }
    
    //Modifies Duration of Vehicle's Residency
       public void setAvailability(String plateNumber, JTextField arriveDate, JTextField departureDate){
        String arrivalDate = arriveDate.getText().trim();
        String departDate = departureDate.getText().trim();
        LocalDate arrDate = LocalDate.parse(arrivalDate);
        LocalDate depDate = LocalDate.parse(departDate);
        userInventory.findVehicle(plateNumber).setArrivalDate(arrDate);
        userInventory.findVehicle(plateNumber).setDepartureDate(depDate);
        System.out.println("Arrival Date: " + arrDate);
        System.out.println("Departure Date: " + depDate);
        VehicleOwnerServiceImpl.writeFile(this);
    }
    
    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        VehicleOwner vehicleOwner = (VehicleOwner) o;
        return id == vehicleOwner.id && Objects.equals(username, vehicleOwner.username) && Objects.equals(numOfVehicles, vehicleOwner.numOfVehicles);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, username, numOfVehicles);
    }
}
