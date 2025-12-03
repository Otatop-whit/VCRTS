package vehicle.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class VehicleIDCache {
    private static VehicleIDCache instance;
    private Set<Integer> vehicleIds;
    private static final String DATA_FILE = "src/vccontroller/repo/VehicleData.txt";

    private VehicleIDCache() {
        this.vehicleIds = new HashSet<>();
        loadVehicleIds();
    }

    public static VehicleIDCache getInstance() {
        if (instance == null) {
            instance = new VehicleIDCache();
        }
        return instance;
    }

    private void loadVehicleIds() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("/");
                if (parts.length > 1) {
                    try {
                        int id = Integer.parseInt(parts[1]);
                        vehicleIds.add(id);
                    } catch (NumberFormatException e) {
                        // Skip invalid IDs
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeVehicleId(int id) {
        vehicleIds.add(id);
    }
    public void removeVehicleId(int id) {
        vehicleIds.remove(id);
    }

    public Set<Integer> getVehicleIds() {
        return vehicleIds;
    }
    
}