import java.time.LocalDateTime;

public class Vehicle {
    private String vehicleID;
    private String vehicleModel;
    private String vehicleMake;
    private LocalDateTime vehicleYear;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private String resident;

    public Vehicle(String vehicleID, String vehicleModel, String vehicleMake, LocalDateTime vehicleYear, LocalDateTime arrivalDate, LocalDateTime departureDate, String resident){
        this.vehicleID = vehicleID;
        this.vehicleModel = vehicleModel;
        this.vehicleYear = vehicleYear;
        this.vehicleMake = vehicleMake;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.resident = resident;

    }

    //Get Commands
    public String getID(){
        return vehicleID;
    }
    public String getModel(){
        return vehicleModel;
    }
    public String getMake(){
        return vehicleMake;
    }
    public LocalDateTime getYear(){
        return vehicleYear;
    }
    public LocalDateTime getArriveDate(){
        return arrivalDate;
    }
    public LocalDateTime getDepartDateTime(){
        return departureDate;
    }
    public String getResident(){
        return resident;
    }

    //Set Commands
    public void setVehicleModel(String model){
        vehicleModel = model;
    }
    public void setVehicleMake(String make){
        vehicleMake = make;
    }
    public void setVehicleYear(LocalDateTime year){
        vehicleYear = year;
    }
    public void setArrivalDate(LocalDateTime arriveDate){
        arrivalDate = arriveDate;
    }

    public void setDepartureDate(LocalDateTime departDate){
        departureDate = departDate;
    }
    public void setResident(String status){
        resident = status;
    }

}
