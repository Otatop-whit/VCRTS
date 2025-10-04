import java.time.LocalDateTime;
import java.time.Year;

public class Vehicle {
    //Required Vehicle Information
    private String licensePlate;
    private String vehicleModel;
    private String vehicleMake;
    private Year vehicleYear;

    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private String resident;

    public Vehicle(VehicleBuilder builder){
        this.licensePlate = builder.licensePlate;
        this.vehicleModel = builder.vehicleModel;
        this.vehicleYear = builder.vehicleYear;
        this.vehicleMake = builder.vehicleMake;
        this.arrivalDate = builder.arrivalDate;
        this.departureDate = builder.departureDate;
        this.resident = builder.residency;
    }

    //Get Commands
    public String getLicensePlate(){
        return licensePlate;
    }
    public String getModel(){
        return vehicleModel;
    }
    public String getMake(){
        return vehicleMake;
    }
    public Year getYear(){
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

    //Allows for users to fix any errors/typos
    public void setLicensePlate(String plateNumber){
        licensePlate = plateNumber;
    }
    public void setVehicleModel(String model){
        vehicleModel = model;
    }
    public void setVehicleMake(String make){
        vehicleMake = make;
    }
    public void setVehicleYear(Year year){
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

    public static class VehicleBuilder{
        private String licensePlate;
        private String vehicleModel;
        private String vehicleMake;
        private Year vehicleYear;
        private LocalDateTime arrivalDate;
        private LocalDateTime departureDate;
        private String residency;
        public VehicleBuilder setLicensePlate(String plateNumber){
            this.licensePlate = plateNumber;
            return this;
        }
        public VehicleBuilder setVehicleModel(String vehicleModel){
            this.vehicleModel = vehicleModel;
            return this;
        }
        public VehicleBuilder setVehicleMake(String vehicleMake){
            this.vehicleMake = vehicleMake;
            return this;
        }
        public VehicleBuilder setVehicleYear(Year vehicleYear){
            this.vehicleYear = vehicleYear;
            return this;
        }
        public VehicleBuilder setArrivalDate(LocalDateTime arrivalDate){
            this.arrivalDate = arrivalDate;
            return this;
        }
        public VehicleBuilder setDepatureDate(LocalDateTime departureDate){
            this.departureDate = departureDate;
            return this;
        }
        public VehicleBuilder setResidency(String residency){
            this.residency = residency;
            return this;
        }
        public Vehicle build(){
            return new Vehicle(this);
        }
    }

}
