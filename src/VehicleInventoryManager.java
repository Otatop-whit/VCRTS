import java.util.HashMap;

public class VehicleInventoryManager{
    private static VehicleInventoryManager inventory_instance;
    private HashMap<String, Vehicle> vehicleInventory;
    private VehicleInventoryManager(){
        vehicleInventory = new HashMap<String, Vehicle>();
    }
    public static VehicleInventoryManager getInstance(){
        if(inventory_instance == null){
            inventory_instance = new VehicleInventoryManager();
        }
        return inventory_instance;
    }

    public void addVehicle(String vehicleID, Vehicle vehicle){
        vehicleInventory.put(vehicleID, vehicle);
    }

    public void removeVehicle(String vehicleID){
        vehicleInventory.remove(vehicleID);
    }

    public Vehicle findVehicle(String vehicleID){
        Vehicle findableVehicle = vehicleInventory.get(vehicleID);
        if (findableVehicle == null){
            System.err.println("Vehicle Not Found");
        }
        return findableVehicle;
    }

    
}