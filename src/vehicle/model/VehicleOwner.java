package vehicle.model;

import java.time.LocalDate;
import java.time.Year;
import java.util.Objects;
import javax.swing.JTextField;
import common.model.User;
import vehicle.service.VehicleClient;
import vehicle.service.VehicleOwnerServiceImpl;


public class VehicleOwner {
    private int numOfVehicles;
    private VehicleInventory userInventory;
    private Vehicle vehicle;
    private String filename; //Vehicle Owner saves their direct File Link
    private final User user = User.getInstance();
    String email;
    String username;
    
    public VehicleOwner(){
        email = user.getEmail();
        username = user.getUsername();
        this.userInventory = new VehicleInventory();
    }
    //Getters
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
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

            this.vehicle = new Vehicle.VehicleBuilder().setVehicleOwnerEmail(email).setLicensePlate(vehiclePlate)
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
        System.out.println("Sending Request");
        //VehicleClient.clientRequest("V_StF/" + vehicle.toString());
    }

    //Removes the vehicle constructed from personal Vehicle Inventory
    public void removeVehicle(String licensePlate){
        userInventory.removeVehicle(licensePlate);
        numOfVehicles--;
        VehicleOwnerServiceImpl.writeFile(this);
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

    //Formats the Vehicle Information to String for Sockets
    public String fileFormat(){
        if(vehicle.getLicensePlate().equals(null)){
            return null;
        }
        String fileFormat = vehicle.toString();
        return fileFormat;
    }

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        VehicleOwner vehicleOwner = (VehicleOwner) o;
        return Objects.equals(email, vehicleOwner.email) && Objects.equals(username, vehicleOwner.username) && Objects.equals(numOfVehicles, vehicleOwner.numOfVehicles);
    }

    @Override
    public int hashCode(){
        return Objects.hash(email, username, numOfVehicles);
    }
}
