import java.util.HashMap;

public class VehicleInventory{
    private static VehicleInventory inventory_instance;
    private HashMap<String, Vehicle> vehicleInventory;
    private VehicleInventory(){
        vehicleInventory = new HashMap<String, Vehicle>();
    }
    public static VehicleInventory getInstance(){
        if(inventory_instance == null){
            inventory_instance = new VehicleInventory();
        }
        return inventory_instance;
    }

    public void addVehicle(String licensePlate, Vehicle vehicle){
        vehicleInventory.put(licensePlate, vehicle);
    }

    public void removeVehicle(String licensePlate){
        vehicleInventory.remove(licensePlate);
    }

    public Vehicle findVehicle(String licensePlate){
        Vehicle foundVehicle = null;
        for(Vehicle vehicles : vehicleInventory.values()){
            if(licensePlate.equalsIgnoreCase(vehicles.getLicensePlate())){
                foundVehicle = vehicleInventory.get(licensePlate);
            }
        }
        if (foundVehicle == null){
            System.err.println("Vehicle Not Found");
        }
        return foundVehicle;
    }

    
}