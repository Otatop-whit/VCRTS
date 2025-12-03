package vccontroller.service;

import common.model.Account;
import job.model.JobOwner;
import vccontroller.model.JobsCache;
import vccontroller.model.VehicleCache;
import vccontroller.ui.ControllerPage;
import vehicle.model.Vehicle;
import vehicle.model.VehicleOwner;
import java.sql.*;
import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.time.LocalDateTime;
import javax.print.attribute.standard.JobSheets;
import java.time.format.DateTimeFormatter;
import vccontroller.database.Database;

public class ClientHandler implements Runnable{
        VcControllerServiceImpl vccontroller = new VcControllerServiceImpl();
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private JobsCache jobCache = JobsCache.getInstance();
        private VehicleCache vehicleCache = VehicleCache.getInstance();

    public  ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientHandlers.add(this);
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        try {
            String messageFromClient = bufferedReader.readLine().trim();

            while (socket.isConnected() && !socket.isClosed()){
                try{
                    if(messageFromClient != null && !messageFromClient.isEmpty()){
                        System.out.println(messageFromClient);
                        if(messageFromClient.startsWith("JOB|")) {
                            JobOwner job = convertToJobObj(messageFromClient);
                            if (job != null) {
                                jobCache.addJob(job);
                                System.out.println("Job successfully added to cache.");
                                javax.swing.SwingUtilities.invokeLater(() -> {
                                    ControllerPage.refreshIfOpen();
                                });
                            }
                        } else if (messageFromClient.startsWith("VEHICLE|")) {
                            Vehicle vehicle = convertTovehicleObj(messageFromClient);
                            if (vehicle != null) {
                                vehicleCache.addVehicle(vehicle);
                                System.out.println("Vehicle successfully added to cache.");
                                javax.swing.SwingUtilities.invokeLater(() -> {
                                    ControllerPage.refreshIfOpen();
                                });
                            }
                        } else {
                            String[] instructions = splitMessage(messageFromClient);
                            String response = (String) executeRequest(instructions[0], instructions[1]);
                            System.out.println(response);
                        }
                    }
                    messageFromClient = bufferedReader.readLine();
                } catch (IOException e) {
                    System.out.println(e);
                    closeEverything(socket,bufferedReader,bufferedWriter);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }          
    

    public void broadcastMessage(String message){
        for (ClientHandler clientHandler: clientHandlers){
            try{
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
                System.out.println(message);
            }catch (IOException e){

                System.out.println(e);
                closeEverything(socket,bufferedReader,bufferedWriter);
            }
        }

    }
    public void removeClient(){
        clientHandlers.remove(this);

    }
    public void closeEverything(Socket socket,  BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        removeClient();
        try{
           if(bufferedWriter != null) {
              bufferedWriter.close();
           }
            if(bufferedReader != null) {

                bufferedReader.close();
            }
            if(socket != null) {

                socket.close();
            }
        }catch (IOException e){

            System.out.println(e);
        }
    }

    public static void acceptJob(int idx){
        String message = "Accepted";
        JobsCache jobCache = JobsCache.getInstance();
        VcControllerServiceImpl vcController = new VcControllerServiceImpl();
        ArrayList<ClientHandler> handlersToClose = new ArrayList<>(clientHandlers);
        for(ClientHandler clientHandler: handlersToClose){
            try {
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
            }
        } 

        for(ClientHandler handler: handlersToClose) {
            handler.closeEverything(handler.socket, handler.bufferedReader, handler.bufferedWriter);
        }

        if (idx >= 0 && idx < jobCache.length()) {
            JobOwner job = jobCache.getJob(idx);
            if (job != null) {
                vcController.submitJob(job);
                Database.jobInsertion(job);
                jobCache.removeJob(idx);
            }
        }
    }
    public static void acceptVehicle(int idx){
        String message = "Accepted";
        VehicleCache vehicleCache = VehicleCache.getInstance();
        ArrayList<ClientHandler> handlersToClose = new ArrayList<>(clientHandlers);
        for(ClientHandler clientHandler : handlersToClose){
            try{
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            }catch(IOException e){

            }
        }
        for(ClientHandler handler: handlersToClose){
            handler.closeEverything(handler.socket, handler.bufferedReader, handler.bufferedWriter);
        }

        if(idx >= 0 && idx < vehicleCache.length()){
            Vehicle vehicle = vehicleCache.getVehicle(idx);
            if(vehicle != null){
                Database.vehicleInsertion(vehicle);
                String vehInfo = vehicle.toString();
                saveVehicle(vehInfo);
                vehicleCache.removeVehicle(idx);
            }
        }
    }
    public static void rejectJob(int idx){
        String message = "Rejected";
        JobsCache jobCache = JobsCache.getInstance();
        ArrayList<ClientHandler> handlersToClose = new ArrayList<>(clientHandlers);
        for(ClientHandler clientHandler : handlersToClose){
            try {
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
            }
        }

        for(ClientHandler handler: handlersToClose){
            handler.closeEverything(handler.socket, handler.bufferedReader, handler.bufferedWriter);
        }

        if (idx >= 0 && idx < jobCache.length()) {
            jobCache.removeJob(idx);
        }
    }
    public static void rejectVehicle(int idx){
        String message = "Rejected";
        VehicleCache vehicleCache = VehicleCache.getInstance();
        ArrayList<ClientHandler> handlersToClose = new ArrayList<>(clientHandlers);
        for(ClientHandler clientHandler : handlersToClose){
            try {
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            } catch (IOException e) {
            }
        }

        for(ClientHandler handler: handlersToClose){
            handler.closeEverything(handler.socket, handler.bufferedReader, handler.bufferedWriter);
        }

        if (idx >= 0 && idx < vehicleCache.length()) {
            vehicleCache.removeVehicle(idx);
        }
    }
    //Splits up the client message for the client request method to work
    public String[] splitMessage(String message){
        String[] taskRequest = new String[2];
        if(!message.isEmpty() && message != null){
                    String[] parts = message.split("/");
                    taskRequest[0] = parts[0]; // Stores the method it wants to use.
                    StringBuilder infoBuilder = new StringBuilder();
                    for(int i = 1; i < parts.length; i++){
                    infoBuilder.append(parts[i]);
                    if(i < parts.length - 1) infoBuilder.append("/");
                }
                    taskRequest[1] = infoBuilder.toString(); // Stores the information that it wants to use for the method.
                    System.out.println(taskRequest[1]);
        }
        return taskRequest;
    }
    //Allows specific actions to execute depending on the task and information provided
    public Object executeRequest(String instruction, String info){
        switch(instruction){
            //Placeholder code names. Feel free to change them.
            case "V_StF":
                saveVehicle(info);
                return "Vehicle has been saved to File.";
            case "J_Convert":
                JobOwner job = convertToJobObj(info);
                if (job != null) {
                    jobCache.addJob(job);
                    System.out.println("Job successfully added to cache.");
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        ControllerPage.refreshIfOpen();
                    });
                    return "Job has been added to cache.";
                }
                return "Failed to convert job.";
            default:
                return "Unk nown task: " + instruction;
        }
    }
    public JobOwner convertToJobObj(String stringJob){
        if (stringJob == null || stringJob.trim().isEmpty()) {
            return null;
        }

        String[] parts = stringJob.split("\\|", 5);
        if (parts.length == 5 && parts[0].equals("JOB")) {
                String jobId = parts[1].trim();
                String jobName = parts[2].trim();
                int jobDuration = Integer.parseInt(parts[3].trim());
                String jobDeadline = parts[4].trim();

                JobOwner job = new JobOwner();
                job.setJobId(jobId);
                job.setJobOwnerName(jobName);
                job.setDuration(jobDuration);
                job.setJobDeadline(jobDeadline);
                job.setCompletionTime(0);
                return job;
            }

        parts = stringJob.split("/", 4);
        if (parts.length == 4) {
                int jobId = Integer.parseInt(parts[0]);
                int jobDuration = Integer.parseInt(parts[2]);
                int jobCompletionTime = Integer.parseInt(parts[3]);
                return new JobOwner(jobId, parts[1], jobDuration, jobCompletionTime);
            }
        return null;
    }

    public Vehicle convertTovehicleObj(String stringVehicle){
        if (stringVehicle == null || stringVehicle.trim().isEmpty()) {
            return null;
        }
        int limit = 11; //Indicates the number of expected parts
        String[] parts = stringVehicle.split("\\|", limit);
        if (parts.length == limit && parts[0].equals("VEHICLE")) {
            int vehicleId = Integer.parseInt(parts[1].trim());
            String vehicleModel = parts[2].trim();
            String vehicleMake = parts[3].trim();
            Year vehicleYear = Year.parse(parts[4].trim());
            String vehicleLicensePlate = parts[5].trim();
            String computingPower = parts[6].trim();
            String arrivateDate = parts[7].trim();
            String depatureDate = parts[8].trim();
            String residency = parts[9].trim();
            String email = parts[10].trim();
            Vehicle vehicle = new Vehicle.VehicleBuilder().setVehicleId(vehicleId).setVehicleOwnerEmail(email).setVehicleModel(vehicleModel).setVehicleMake(vehicleMake).setVehicleYear(vehicleYear).setLicensePlate(vehicleLicensePlate).setComputingPower(computingPower).setArrivalDate(arrivateDate).setDepatureDate(depatureDate).setResidency(residency).build();
            return vehicle;
        }

//
//        parts = stringVehicle.split("/", 4);
////        if (parts.length == 4) {
////            int jobId = Integer.parseInt(parts[0]);
////            int jobDuration = Integer.parseInt(parts[2]);
////            int jobCompletionTime = Integer.parseInt(parts[3]);
////            return new Vehicle(jobId, parts[1], jobDuration, jobCompletionTime);
////        }
        return null;
    }

    //Saves vehicle information to the vehicle repo
    public static void saveVehicle(String vehicleInfo){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/vccontroller/repo/VehicleData.txt", true)); // ,true allows for adding to file without rewriting
            writer.write(vehicleInfo);
            writer.newLine();
            writer.close();
        }catch(IOException e){
            System.err.println("Failed to write to file:" + e.getMessage());
        }
    }

}
