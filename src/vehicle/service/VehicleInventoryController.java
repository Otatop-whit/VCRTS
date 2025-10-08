package vehicle.service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeParseException;

import javax.swing.JTextField;

import vehicle.model.Vehicle;
import vehicle.model.VehicleInventory;

public class VehicleInventoryController {
    private VehicleInventory vehicleInventory;
    private VehicleOwnerServiceImpl vehOwnServ;
    public VehicleInventoryController(){
        this.vehOwnServ = new VehicleOwnerServiceImpl();
        this.vehicleInventory = start();
    }
    public VehicleInventory start(){
        try{
            vehicleInventory = vehOwnServ.FileRead("src/vehicle/repo/VehicleData.txt");
        }catch(IOException e){
            System.err.println("Error, File not Found!");
        }
        return vehicleInventory;
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
        saveChanges("src/vehicle/repo/VehicleData.txt", vehicleInventory);
        }
        catch(DateTimeParseException e){
        }
    }

    public void setAvailability(String plateNumber, JTextField arriveDate, JTextField departureDate){
        String arrivalDate = arriveDate.getText().trim();
        String departDate = departureDate.getText().trim();
        LocalDate arrDate = LocalDate.parse(arrivalDate);
        LocalDate depDate = LocalDate.parse(departDate);
        vehicleInventory.findVehicle(plateNumber).setArrivalDate(arrDate);
        vehicleInventory.findVehicle(plateNumber).setDepartureDate(depDate);
        System.out.println("Arrival Date: " + arrDate);
        System.out.println("Departure Date: " + depDate);
        saveChanges("src/vehicle/repo/VehicleData.txt", vehicleInventory);

    }
    public void saveChanges(String fileName, VehicleInventory vehicleInventory){
        vehOwnServ.addVehicle(vehicleInventory);
    }
}
