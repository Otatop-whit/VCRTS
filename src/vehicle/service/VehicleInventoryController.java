package vehicle.service;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JTextField;

import vehicle.model.Vehicle;
import vehicle.model.VehicleInventory;

public class VehicleInventoryController {
    private VehicleInventory vehicleInventory;
    private VehicleOwnerServiceImpl vehOwnServ;
    public VehicleInventoryController(){
        this.vehicleInventory = new VehicleInventory();
        this.vehOwnServ = new VehicleOwnerServiceImpl();
    }

    public void addVehicle(JTextField licensePlate, JTextField model, JTextField make, JTextField year, JTextField residency){
        try{
        String vehiclePlate = licensePlate.getText();
        String vehicleModel = model.getText();
        String vehicleMake = make.getText();
        Year vehicleYear = Year.parse(year.getText());
        String resident = residency.getText();
        Vehicle vehicle = new Vehicle.VehicleBuilder().setLicensePlate(vehiclePlate).setVehicleModel(vehicleModel).setVehicleMake(vehicleMake).setVehicleYear(vehicleYear).setResidency(resident).build();
        vehicleInventory.addVehicle(vehicle.getLicensePlate(), vehicle);
        System.out.println("Vehicle's License Plate: " + vehicleInventory.findVehicle(vehiclePlate).getLicensePlate());
        System.out.println("Vehicle has been added!");
        vehOwnServ.addVehicle(vehicleInventory);
        }
        catch(DateTimeParseException e){
        }
    }

    public void setAvailability(String plateNumber, JTextField arriveDate, JTextField departureDate){
        String arrivalDate = arriveDate.getText();
        String departDate = departureDate.getText();
        LocalDateTime arrDate = LocalDateTime.parse(arrivalDate);
        LocalDateTime depDate = LocalDateTime.parse(departDate);
        vehicleInventory.findVehicle(plateNumber).setArrivalDate(arrDate);
        vehicleInventory.findVehicle(plateNumber).setDepartureDate(depDate);
        System.out.println("Arrival Date: " + arrDate);
        System.out.println("Departure Date: " + depDate);
    }

    public void displayVehicles(){
        ArrayList<Vehicle> listOfVehicles = vehicleInventory.convertToArray();

    }

    
}
