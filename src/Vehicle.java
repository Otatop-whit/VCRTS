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

    public void setResident(String status){
        resident = status;
    }
}
