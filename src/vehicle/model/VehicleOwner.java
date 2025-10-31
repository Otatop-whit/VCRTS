package vehicle.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.InputMismatchException;
import java.util.Objects;

import javax.swing.JTextField;


public class VehicleOwner {
    private String id;
    private String vehicleOwnerName;
    private int numOfVehicles;
    private VehicleInventory userInventory;
    private Vehicle vehicle;
    
    public VehicleOwner(){
    }
    public VehicleOwner(String id, String vehicleOwnerName){
        this.id = id;
        this.vehicleOwnerName = vehicleOwnerName;
        this.userInventory = new VehicleInventory();
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getVehicleOwnerName(){
        return vehicleOwnerName;
    }
    public void setVehicleOwnerName(String vehicleOwnerName){
        this.vehicleOwnerName = vehicleOwnerName;
    }
    public int getNumOfVehicles(){
        return numOfVehicles;
    }
    public void setNumOfVehicles(int vehicleCount){
        this.numOfVehicles = vehicleCount;
    }
    public Vehicle createVehicle(JTextField licensePlate, JTextField model, JTextField make, JTextField year, JTextField arriveDate, JTextField departureDate, JTextField residency){
       try{
            String vehiclePlate = licensePlate.getText();
            String vehicleModel = model.getText();
            String vehicleMake = make.getText();
            Year vehicleYear = Year.parse(year.getText());
            String arrivalDate = arriveDate.getText().trim();
            LocalDate arrDate = LocalDate.parse(arrivalDate);
            String departDate = departureDate.getText().trim();
            LocalDate depDate = LocalDate.parse(departDate);
            String resident = residency.getText();
            this.vehicle = new Vehicle.VehicleBuilder().setVehicleOwnerID(id).setLicensePlate(vehiclePlate).setVehicleModel(vehicleModel).setVehicleMake(vehicleMake).setVehicleYear(vehicleYear).setArrivalDate(arrDate).setDepatureDate(depDate).setResidency(resident).build();
       }
       catch(Exception e){
        System.err.println("Error! Failed to create vehicle.");
        e.printStackTrace();
       }
        return vehicle;
    }

    public void storeVehicle(){
        userInventory.addVehicle(this.vehicle.getLicensePlate(), this.vehicle);
    }

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        VehicleOwner vehicleOwner = (VehicleOwner) o;
        return id == vehicleOwner.id && Objects.equals(vehicleOwnerName, vehicleOwner.vehicleOwnerName) && Objects.equals(numOfVehicles, vehicleOwner.numOfVehicles);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id, vehicleOwnerName, numOfVehicles);
    }
}
