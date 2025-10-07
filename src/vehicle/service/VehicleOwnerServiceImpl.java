package vehicle.service;

import vehicle.model.VehicleOwner;
import vehicle.model.VehicleInventory;
import vehicle.model.Vehicle;
import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;


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
                    writer.write("VehicleOwnerID Assigned: " + vehicles.getVehicleOwnerID() + "\n");
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

    public VehicleInventory FileRead(String filename) throws IOException{
        VehicleInventory inventory = new VehicleInventory();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            Vehicle vehicle;
            String vehicleInfo;
            reader.readLine();
            reader.readLine();
            reader.readLine();
            ArrayList<String> vehicleCreator = new ArrayList<String>();
            while((vehicleInfo = reader.readLine()) != null){
                vehicleInfo = vehicleInfo.substring(vehicleInfo.indexOf(":") + 1, vehicleInfo.length());
                vehicleCreator.add(vehicleInfo);
                if(vehicleCreator.size() == 8){
                    String licensePlate = vehicleCreator.get(0);
                    if(licensePlate.startsWith(" ")) licensePlate.substring(1, licensePlate.length());
                    String model = vehicleCreator.get(1);
                    String make = vehicleCreator.get(2);
                    Year year = Year.parse(vehicleCreator.get(3).substring(1));
                    String residency = vehicleCreator.get(6);
                    String vehicleOwnerId = vehicleCreator.get(7);
                    vehicle = new Vehicle.VehicleBuilder().setVehicleOwnerID(vehicleOwnerId).setLicensePlate(licensePlate)
                    .setVehicleModel(model).setVehicleMake(make).setVehicleYear(year).setResidency(residency).build();
                    if(!vehicleCreator.get(4).contains("null")){
                        LocalDate arrivalDate = LocalDate.parse(vehicleCreator.get(4));
                        vehicle.setArrivalDate(arrivalDate);
                    }
                    if(!vehicleCreator.get(5).contains("null")){
                        LocalDate departureDate = LocalDate.parse(vehicleCreator.get(4));
                        vehicle.setDepartureDate(departureDate);
                    }
                    inventory.get().put(vehicle.getLicensePlate(), vehicle);
                    vehicleCreator.clear();
                    reader.readLine();
                    reader.readLine();
                }
            }
            reader.close();  
        } catch(FileNotFoundException e){
            System.err.println("Error! File not found.\n" + e.getStackTrace());
        }
        return inventory;  
    }

}
