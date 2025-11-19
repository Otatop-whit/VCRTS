package vccontroller.model;


import java.time.Year;

import vehicle.model.Vehicle;

public class VehicleParser {

    public static Vehicle parse(String raw) {
        try {
            String[] p = raw.split("/");

            if (p.length < 12) {
                System.err.println("VehicleParser: Invalid vehicle info (fields missing) → " + raw);
                return null;
            }

            Vehicle.VehicleBuilder builder = new Vehicle.VehicleBuilder()
                    .setVehicleOwnerEmail(p[0])
                    .setVehicleId(Integer.parseInt(p[1]))
                    .setLicensePlate(p[2])
                    .setVehicleModel(p[3])
                    .setVehicleMake(p[4])
                    .setVehicleYear(Year.parse(p[5]))
                    .setComputingPower(p[6])
                    .setArrivalDate(p[7])
                    .setDepatureDate(p[8])
                    .setResidency(p[9])
                    .setTimestamp(p[10])
                    .setLastModified(p[11]);

            return builder.build();

        } catch (Exception e) {
            System.err.println("VehicleParser Error: " + e.getMessage());
            return null;
        }
    }
}
