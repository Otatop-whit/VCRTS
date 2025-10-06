package vehicle.model;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Objects;

public class Vehicle {
    //Required Vehicle Information
    private final String vehicleOwnerID;
    private String licensePlate;
    private String vehicleModel;
    private String vehicleMake;
    private Year vehicleYear;
    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;
    private String residency;
    

    public Vehicle(VehicleBuilder builder){
        this.vehicleOwnerID = builder.vehicleOwnerID;
        this.licensePlate = builder.licensePlate;
        this.vehicleModel = builder.vehicleModel;
        this.vehicleYear = builder.vehicleYear;
        this.vehicleMake = builder.vehicleMake;
        this.arrivalDate = builder.arrivalDate;
        this.departureDate = builder.departureDate;
        this.residency = builder.residency;
    }

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
    public LocalDateTime getDepartDate(){
        return departureDate;
    }
    public String getResidency(){
        return residency;
    }
    public String getVehicleOwnerID(){
        return vehicleOwnerID;
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
    public void setResidency(String status){
        residency = status;
    }

    public static class VehicleBuilder{
        private String vehicleOwnerID;
        private String licensePlate;
        private String vehicleModel;
        private String vehicleMake;
        private Year vehicleYear;
        private LocalDateTime arrivalDate;
        private LocalDateTime departureDate;
        private String residency;
        public VehicleBuilder setVehicleOwnerID(String ownerID){
            this.vehicleOwnerID = ownerID;
            return this;
        }
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

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleOwnerID == vehicle.getVehicleOwnerID() && 
        Objects.equals(licensePlate, vehicle.licensePlate) && 
        Objects.equals(vehicleModel, vehicle.vehicleModel) && 
        Objects.equals(vehicleMake, vehicle.vehicleMake) &&
        Objects.equals(vehicleYear, vehicle.vehicleYear) &&
        Objects.equals(arrivalDate, vehicle.arrivalDate) &&
        Objects.equals(departureDate, vehicle.departureDate) &&
        Objects.equals(residency, vehicle.residency);
    }

    @Override
    public int hashCode(){
        return Objects.hash(vehicleOwnerID, licensePlate, vehicleModel, vehicleMake, vehicleYear, arrivalDate, departureDate, residency);
    }
}
