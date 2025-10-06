package vehicle.service;

import vehicle.model.VehicleOwner;
import vehicle.model.VehicleInventory;
import vehicle.model.Vehicle;
import java.io.*;


public class VehicleOwnerServiceImpl implements VehicleOwnerService{
    private int entryNumber;
    private int vehicleEntryNumber = 0;

    public void addVehicleOwner(VehicleOwner vehicleOwner, VehicleInventory vehicleInventory){

        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/vehicle/repo/Data.txt"));
            entryNumber = Integer.valueOf(reader.readLine());
            System.out.println(entryNumber);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/vehicle/repo/VehicleOwnerData.txt"));

            writer.write("\n");
            writer.write("Vehicle Data Entry " + entryNumber + ":");
            writer.write("\n");
            writer.write("\nVehicleOwnerID: " + vehicleOwner.getId());
            writer.write("\nVehicleOwnerName: " + vehicleOwner.getVehicleOwnerName());
            writer.write("\nNumberOfVehicles: " + vehicleOwner.getNumOfVehicles());
            if(vehicleInventory.get().size() > 0){
                for(Vehicle vehicles : vehicleInventory.get().values()){
                    vehicleEntryNumber++;
                    writer.write("\n");
                    writer.write("Vehicle Entry " + vehicleEntryNumber + ":");
                    writer.write("\n");
                    writer.write("VehicleLicense: " + vehicles.getLicensePlate());
                    writer.write("VehicleModel: " + vehicles.getModel());
                    writer.write("VehicleMake: " + vehicles.getMake());
                    writer.write("VehicleYear: " + vehicles.getYear());
                    writer.write("VehicleArrivalDate: " + vehicles.getArriveDate());
                    writer.write("VehicleDepartureDate: " + vehicles.getDepartDate());
                    writer.write("Residency: " + vehicles.getResidency());
                    writer.write("VehicleOwnerID Assigned: " + vehicles.getVehicleOwnerID());
                }
            }
            writer.write("\n");
            writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writer.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        try{
            entryNumber ++;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/vehicle/repo/VehicleData.txt"));
            bufferedWriter.write(String.valueOf(entryNumber));
            bufferedWriter.close();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

}
