package vccontroller.model;

import vehicle.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class VehicleCache {
    private static VehicleCache instance;
    private List<Vehicle> vehicles;

    private VehicleCache() {
        vehicles = new ArrayList<>();
    }

    public static synchronized VehicleCache getInstance() {
        if (instance == null) {
            instance = new VehicleCache();
        }
        return instance;
    }

    public synchronized void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public synchronized void removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
    }

    public synchronized List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public synchronized int size() {
        return vehicles.size();
    }
    

}
