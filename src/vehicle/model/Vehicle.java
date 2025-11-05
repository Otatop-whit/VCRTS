package vehicle.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Vehicle {
    //Required Vehicle Information
    private String vehicleOwnerEmail;
    private String licensePlate;
    private String vehicleModel;
    private String vehicleMake;
    private Year vehicleYear;
    private String computingPower;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private String residency;
    private LocalDateTime timestamp;
    private LocalDateTime lastModified; // Records if data was changed

    public Vehicle(VehicleBuilder builder){
        this.vehicleOwnerEmail = builder.vehicleOwnerEmail;
        this.licensePlate = builder.licensePlate;
        this.vehicleModel = builder.vehicleModel;
        this.vehicleYear = builder.vehicleYear;
        this.vehicleMake = builder.vehicleMake;
        this.computingPower = builder.computingPower;
        this.arrivalDate = builder.arrivalDate;
        this.departureDate = builder.departureDate;
        this.residency = builder.residency;
        this.timestamp = builder.timestamp;
        this.lastModified = builder.lastModified;
    }

    //Getters
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
    public LocalDate getArriveDate(){
        return arrivalDate;
    }
    public LocalDate getDepartDate(){
        return departureDate;
    }
    public String getResidency(){
        return residency;
    }
    public String getVehicleOwnerEmail(){
        return vehicleOwnerEmail;
    }
    public String getComputingPower(){
        return computingPower;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
    public LocalDateTime getLastModified(){
        return lastModified;
    }

    //Allows for users to fix any errors/typos
    public void setVehicleOwnerEmail(String email){
        vehicleOwnerEmail = email;
        lastModified = LocalDateTime.now();
    }
    public void setLicensePlate(String plateNumber){
        licensePlate = plateNumber;
        
    }
    public void setVehicleModel(String model){
        vehicleModel = model;
        lastModified = LocalDateTime.now();
    }
    public void setVehicleMake(String make){
        vehicleMake = make;
        lastModified = LocalDateTime.now();
    }
    public void setVehicleYear(Year year){
        vehicleYear = year;
        lastModified = LocalDateTime.now();
    }
    public void setArrivalDate(LocalDate arriveDate){
        arrivalDate = arriveDate;
        lastModified = LocalDateTime.now();
    }
    public void setDepartureDate(LocalDate departDate){
        departureDate = departDate;
        lastModified = LocalDateTime.now();
    }
    public void setResidency(String status){
        residency = status;
        lastModified = LocalDateTime.now();
    }
    public void setComputingPower(String computationPower){
        computingPower = computationPower;
        lastModified = LocalDateTime.now();
    }

    //Created builder to allow null values
    public static class VehicleBuilder{
        private String vehicleOwnerEmail;
        private String licensePlate;
        private String vehicleModel;
        private String vehicleMake;
        private Year vehicleYear;
        private String computingPower;
        private LocalDate arrivalDate;
        private LocalDate departureDate;
        private String residency;
        private LocalDateTime timestamp;
        private LocalDateTime lastModified;

        public VehicleBuilder setVehicleOwnerEmail(String email){
            this.vehicleOwnerEmail = email;
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
        public VehicleBuilder setArrivalDate(String arrivalDate){
            this.arrivalDate = LocalDate.parse(arrivalDate);
            return this;
        }
        public VehicleBuilder setDepatureDate(String departureDate){
            this.departureDate = LocalDate.parse(departureDate);
            return this;
        }
        public VehicleBuilder setResidency(String residency){
            this.residency = residency;
            return this;
        }
        public VehicleBuilder setComputingPower(String computingPower){
            this.computingPower = computingPower;
            return this;
        }
        public VehicleBuilder setTimestamp(String timestamp){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.timestamp = LocalDateTime.parse(timestamp, formatter);
            return this;
        }
        public VehicleBuilder setLastModified(String lastModified){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.lastModified = LocalDateTime.parse(lastModified, formatter);
            return this;
        }
        public Vehicle build(){
            if(timestamp == null){this.timestamp = LocalDateTime.now();}
            if(lastModified == null){this.lastModified = LocalDateTime.now();}
            return new Vehicle(this);
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleOwnerEmail == vehicle.getVehicleOwnerEmail() && 
        Objects.equals(licensePlate, vehicle.licensePlate) && 
        Objects.equals(vehicleModel, vehicle.vehicleModel) && 
        Objects.equals(vehicleMake, vehicle.vehicleMake) &&
        Objects.equals(vehicleYear, vehicle.vehicleYear) &&
        Objects.equals(arrivalDate, vehicle.arrivalDate) &&
        Objects.equals(departureDate, vehicle.departureDate) &&
        Objects.equals(residency, vehicle.residency) &&
        Objects.equals(computingPower, vehicle.computingPower) &&
        Objects.equals(timestamp, vehicle.timestamp);
    }

    @Override
    public int hashCode(){
        return Objects.hash(vehicleOwnerEmail, licensePlate, vehicleModel, vehicleMake, vehicleYear, arrivalDate, departureDate, residency, computingPower,timestamp);
    }
}
