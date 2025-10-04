import java.time.LocalDateTime;
import java.time.Year;

import javax.swing.JTextField;

public class VehicleInventoryController {
    private final VehicleInventory vehicleInventory;
    public VehicleInventoryController(){
        this.vehicleInventory = VehicleInventory.getInstance();
    }

    public void addVehicle(JTextField licensePlate, JTextField model, JTextField make, JTextField year){
        String vehiclePlate = licensePlate.getText();
        String vehicleModel = model.getText();
        String vehicleMake = make.getText();
        Year vehicleYear = Year.parse(year.getText());
        Vehicle vehicle = new Vehicle.VehicleBuilder().setLicensePlate(vehiclePlate).setVehicleModel(vehicleModel).setVehicleMake(vehicleMake).setVehicleYear(vehicleYear).build();
        vehicleInventory.addVehicle(vehicle.getLicensePlate(), vehicle);
    }

    public void setAvailability(String plateNumber, JTextField arriveDate, JTextField departureDate){
        String arrivalDate = arriveDate.getText();
        String departDate = departureDate.getText();
        LocalDateTime arrDate = LocalDateTime.parse(arrivalDate);
        LocalDateTime depDate = LocalDateTime.parse(departDate);
        vehicleInventory.findVehicle(plateNumber).setArrivalDate(arrDate);
        vehicleInventory.findVehicle(plateNumber).setDepartureDate(depDate);
    }

    
}
