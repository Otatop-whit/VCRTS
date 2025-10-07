package vehicle.service;

import vehicle.model.VehicleOwner;
import vehicle.model.VehicleInventory;
import vehicle.model.Vehicle;
import java.io.*;
import java.util.Scanner;


public class VehicleOwnerServiceImpl implements VehicleOwnerService{
    private int entryNumber;
    private int vehicleEntryNumber = 0;

    public void addVehicleOwner(VehicleOwner vehicleOwner){

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
            writer.write("\n");
            writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writer.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        try{
            entryNumber ++;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/vehicle/repo/Data.txt"));
            bufferedWriter.write(String.valueOf(entryNumber));
            bufferedWriter.close();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    public void addVehicle(VehicleInventory vehicleInventory){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/vehicle/repo/Data.txt"));
            entryNumber = Integer.valueOf(reader.readLine());
            System.out.println(entryNumber);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/vehicle/repo/VehicleData.txt"));
            if(vehicleInventory.get().size() > 0){
                writer.write("\n");
                writer.write("Vehicle Data Entry " + entryNumber + ":");
                for(Vehicle vehicles : vehicleInventory.get().values()){
                    vehicleEntryNumber++;
                    writer.write("\n");
                    writer.write("Vehicle Entry " + vehicleEntryNumber + ":");
                    writer.write("\n");
                    writer.write("VehicleLicense: " + vehicles.getLicensePlate() + "\n");
                    writer.write("VehicleModel: " + vehicles.getModel() + "\n");
                    writer.write("VehicleMake: " + vehicles.getMake() + "\n");
                    writer.write("VehicleYear: " + vehicles.getYear() + "\n");
                    writer.write("VehicleArrivalDate: " + vehicles.getArriveDate() + "\n");
                    writer.write("VehicleDepartureDate: " + vehicles.getDepartDate() + "\n");
                    writer.write("Residency: " + vehicles.getResidency() + "\n");
                    writer.write("VehicleOwnerID Assigned: " + vehicles.getVehicleOwnerID());
                }
                writer.write("\n");
                writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                writer.close();
            }
        }catch (IOException e){
            throw new RuntimeException();
        }try{
            entryNumber ++;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/vehicle/repo/VehicleEntryData.txt"));
            bufferedWriter.write(String.valueOf(entryNumber));
            bufferedWriter.close();
        } catch (IOException e){
            throw new RuntimeException();
        }
    }

    public void FileRead(String filename) throws IOException{
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while( reader.readLine() != null){
                System.out.println("File is not empty");
            }
        } catch(FileNotFoundException e){
            System.err.println("Error! File not found.\n" + e.getStackTrace());
        }
    }

}
