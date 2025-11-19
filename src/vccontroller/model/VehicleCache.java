package vccontroller.model;

import job.model.JobOwner;
import vehicle.model.Vehicle;

import java.util.ArrayList;

public class VehicleCache {
    private static VehicleCache instance;
    private ArrayList<Vehicle> vehicleCache = new ArrayList<>();
    private VehicleCache(){

    }

    public static VehicleCache getInstance(){
        if(instance == null){
            instance = new VehicleCache();
        }
        return instance;
    }
    public Vehicle getVehicle(int index){
        return vehicleCache.get(index);
    }
    public void addVehicle(Vehicle vehicle){
        vehicleCache.add(vehicle);

    }
    public void removeJob(int index){
        vehicleCache.remove(index);
    }
    public int length(){
        return vehicleCache.size();
    }


}
