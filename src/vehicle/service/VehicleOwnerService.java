package vehicle.service;
import vehicle.model.VehicleOwner;

public interface VehicleOwnerService {
    void writeFile(VehicleOwner owner);
    void loadOwner(String emailString);
}
