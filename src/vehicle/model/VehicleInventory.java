package vehicle.model;
import java.util.ArrayList;
import java.util.HashMap;

public class VehicleInventory{
    private HashMap<String, Vehicle> vehicleInventory;
    public VehicleInventory(){
        this.vehicleInventory = new HashMap<String, Vehicle>();
    }
    public HashMap<String, Vehicle> get(){
        return vehicleInventory;
    }
    public void addVehicle(String licensePlate, Vehicle vehicle){
        vehicleInventory.put(licensePlate, vehicle);
    }

    public void removeVehicle(String licensePlate){
        vehicleInventory.remove(licensePlate);
    }

    public Vehicle findVehicle(String licensePlate){
        Vehicle foundVehicle = vehicleInventory.get(licensePlate);
        if (foundVehicle == null){
            System.err.println("Vehicle Not Found");
        }
        return foundVehicle;
    }

    public ArrayList<Vehicle> convertToArray(){
        ArrayList<Vehicle> vehicleList = new ArrayList<>(vehicleInventory.values());
        return vehicleList;
    }
}