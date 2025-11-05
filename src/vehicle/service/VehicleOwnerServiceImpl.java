package vehicle.service;

import vehicle.model.VehicleOwner;
import vehicle.model.VehicleInventory;
import vehicle.model.Vehicle;
import java.io.*;
//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;


import common.model.User;



public class VehicleOwnerServiceImpl{
     /** OLD Code:
    private int entryNumber;
    private int vehicleEntryNumber = 0;

    //Old File Code for recording Entry Amount as well as Vehicle Owner Data
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
            writer.write("\nVehicleOwnerID: " + vehicleOwner.getEmail());
            writer.write("\nVehicleOwnerName: " + vehicleOwner.getUsername());
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
    // Old File Writer Code
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
                    writer.write("VehicleOwnerID Assigned: " + vehicles.getVehicleOwnerEmail() + "\n");
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

    //Old File Reader Code
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
                vehicleInfo = vehicleInfo.substring(vehicleInfo.indexOf(":") + 1).trim();
                vehicleCreator.add(vehicleInfo);
                
                if(vehicleCreator.size() == 8){
                    String licensePlate = vehicleCreator.get(0).trim();
                    String model = vehicleCreator.get(1).trim();
                    String make = vehicleCreator.get(2).trim();
                    Year year = Year.parse(vehicleCreator.get(3).trim());
                    String arrivalDate = vehicleCreator.get(4).trim();
                    String departureDate = vehicleCreator.get(5).trim();
                    String residency = vehicleCreator.get(6).trim();
                    String vehicleOwnerEmail = vehicleCreator.get(7).trim();

                    vehicle = new Vehicle.VehicleBuilder().setVehicleOwnerEmail(vehicleOwnerEmail).setLicensePlate(licensePlate)
                    .setVehicleModel(model).setVehicleMake(make).setVehicleYear(year).setResidency(residency).build();
                    if(!arrivalDate.contains("null")){
                        LocalDate arrDate = LocalDate.parse(arrivalDate);
                        vehicle.setArrivalDate(arrDate);
                        
                    }
                    if(!departureDate.contains("null")){
                        LocalDate departDate = LocalDate.parse(departureDate);
                        vehicle.setDepartureDate(departDate);
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
    
    */
    
    //New File Writer Code
    public static void writeFile(VehicleOwner owner){
        VehicleInventory userInventory = owner.getInventory();
        try{
              //Creates a file based on the user's email address
              BufferedWriter writer;
              if(owner.getFilename().isEmpty()){
                owner.setFilename("src/vehicle/repo/" + owner.getEmail() + "_VehicleInfo.txt");
              }
                String filename = owner.getFilename();
                File file = new File(filename);
                if(file.createNewFile()){
                    System.out.println("File: " + filename + " was Created!");  
                }
                

                writer = new BufferedWriter(new FileWriter(filename));
                if(userInventory.isEmpty() == false){
                    try {
                        //Confirms who owns the vehicle information
                        writer.write("Username: " + owner.getUsername() + "\n");
                        writer.write("Email: " + owner.getEmail() + "\n");
                        writer.write("Vehicles Registered: " + owner.getNumOfVehicles() + "\n");
                        LocalDateTime fileTimestamp = LocalDateTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        writer.write("Time of File Created: " + fileTimestamp.format(formatter) + "\n\n");
                        

                        int entryNumber = 1;
                        for(Vehicle vehicles: userInventory.sortByEntryDate()){
                            writer.write("\n");
                            writer.write("Vehicle Entry: " + entryNumber + "\n");
                            writer.write("Vehicle License: " + vehicles.getLicensePlate() + "\n");
                            writer.write("Vehicle Model: " + vehicles.getModel() + "\n");
                            writer.write("Vehicle Make: " + vehicles.getMake() + "\n");
                            writer.write("Vehicle Manufacture Year: " + vehicles.getYear() + "\n");
                            writer.write("Vehicle Computing Power: " + vehicles.getComputingPower() + "\n");
                            writer.write("Vehicle Arrival Date: " + vehicles.getArriveDate() + "\n");
                            writer.write("Vehicle Departure Date: " + vehicles.getDepartDate() + "\n");
                            writer.write("Vehicle Residency: " + vehicles.getResidency() + "\n");
                            writer.write("Vehicle Entry Date: " + vehicles.getTimestamp().format(formatter) + "\n");
                            writer.write("Recent Vehicle Data Modified Date: " + vehicles.getLastModified().format(formatter) + "\n");
                            entryNumber++;
                        }
                        writer.write("\n End of File \n");
                        writer.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    } catch (IOException e) {
                        System.err.println("System Failed to Load Vehicle Info to file.");
                        e.printStackTrace();
                    }
                }
            writer.close();
        }
        catch(IOException e){
            System.err.println("Problem Occured When Writing File \n");
            e.printStackTrace();
        }
    }

    public static VehicleOwner loadOwner(User user){
        try{
            String filename = "src/vehicle/repo/" + user.getEmail() + "_VehicleInfo.txt";
            VehicleOwner owner = new VehicleOwner();
            owner.setFilename(filename);
            File file = new File(filename);
            if(!file.exists()){
                return owner;
            }
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            System.out.println("File Successfully Read.");
            reader.readLine();
            reader.readLine();
            String vehicleNum = reader.readLine().substring(reader.readLine().indexOf(": ") + 1).trim();
            if(!vehicleNum.isEmpty()){
                int numOfVehicles = Integer.parseInt(vehicleNum);
                owner.setNumOfVehicles(numOfVehicles);
            }
                
            
            
            
            
            reader.close();
            return owner;
        }catch(Exception e){
            System.err.println("File failed to load Vehicle Owner information.");
            e.printStackTrace();
            return null;
        }
    }

    public static VehicleOwner loadVehicles(VehicleOwner owner){
        try{
            String filename = owner.getFilename();
            File file = new File(filename);
            if(!file.exists()){
                return owner;
            }
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            
            //Skips to Vehicle Information
            for(int i = 0; i < 7; i++){
                reader.readLine();
            }
            
            
            //Reads the Vehicle Information
            Queue<String> vehicleCreator = new LinkedList<String>(); // Stores values for single execution in builder
            String vehicleInfo;
            while((vehicleInfo = reader.readLine()) != null){
                vehicleInfo = vehicleInfo.substring(vehicleInfo.indexOf(":") + 1).trim();
                vehicleCreator.add(vehicleInfo);

                //Limit set based on the number of variables the vehicle has (excludes vehicle owner id)
                if(vehicleCreator.size() == 10){
                    String vehicleOwnerEmail = owner.getEmail();
                    //Empties out the queue
                    String licensePlate = vehicleCreator.poll();
                    String model = vehicleCreator.poll();
                    String make = vehicleCreator.poll();
                    Year year = Year.parse(vehicleCreator.poll());
                    String computingPower = vehicleCreator.poll();
                    String arrivalDate = vehicleCreator.poll();
                    String departureDate = vehicleCreator.poll();
                    String residency = vehicleCreator.poll();
                    String timestamp = vehicleCreator.poll();
                    String lastModified = vehicleCreator.poll();
                    
                    Vehicle vehicle = new Vehicle.VehicleBuilder().setVehicleOwnerEmail(vehicleOwnerEmail).setLicensePlate(licensePlate)
                    .setVehicleModel(model).setVehicleMake(make).setVehicleYear(year).setComputingPower(computingPower)
                    .setArrivalDate(arrivalDate).setDepatureDate(departureDate).setResidency(residency).setTimestamp(timestamp)
                    .setLastModified(lastModified).build();

                    owner.importVehicle(vehicle);//Sends Vehicle to Vehicle Owner data
                    owner.storeVehicle();
                    reader.readLine();// Skips empty space in file
                    reader.readLine();// Skips Vehicle Entry Number in file
                }
            } // End of while (vehicleInfo)
            reader.close();
            return owner;
        }catch(IOException e){
            System.err.println("Failed to Load Vehicle Information From File");
            return null;
        }
        
    }
}
