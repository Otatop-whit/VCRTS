package vehicle.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    //Comparator made to sort through timestamps
    class SortByTimestamp implements Comparator<Vehicle>{
        public int compare(Vehicle vehicle1, Vehicle vehicle2){
            return vehicle1.getTimestamp().compareTo(vehicle2.getTimestamp());
        }
    }

    //Sorts the list of vehicles by timestamps (entry dates) for file writer
    public ArrayList<Vehicle> sortByEntryDate(){
        Comparator<Vehicle> yearSort = new SortByTimestamp();
        ArrayList<Vehicle> sortedVehicles = new ArrayList<>(vehicleInventory.values());
        Collections.sort(sortedVehicles, yearSort);
        return sortedVehicles;
    }

    //Allows for isEmpty method to be used without needing vehicleInventory.get()
    public boolean isEmpty(){
        if(vehicleInventory.isEmpty() == true){
            return true;
        }
        return false;
    }
    
}