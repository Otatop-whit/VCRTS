package vehicle.service;
import vehicle.model.VehicleOwner;
import vehicle.model.VehicleInventory;

public interface VehicleOwnerService {
    void addVehicleOwner(VehicleOwner vehicleOwner);
    void addVehicle(VehicleInventory vehicleInventory);
}
