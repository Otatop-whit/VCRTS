package vccontroller.service;

import common.model.Account;
import job.model.JobOwner;
import vccontroller.model.JobsCache;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
        VcControllerServiceImpl vccontroller = new VcControllerServiceImpl();
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String clientRequest;
        private JobsCache jobCache = JobsCache.getInstance();

    public  ClientHandler(Socket socket){
        try{

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientRequest = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Hello "+ clientRequest);

        }catch (IOException e){

            System.out.println(e);
            closeEverything(socket,bufferedReader,bufferedWriter);
        }

    }

    @Override
    public void run() {
        String messageFromClient = clientRequest;

        while (socket.isConnected()){
            try{
                if(messageFromClient != null && !messageFromClient.isEmpty()){
                    String[] instructions = splitMessage(messageFromClient);
                     String response = (String) executeRequest(instructions[0], instructions[1]);
                    System.out.println(response);
                }
                messageFromClient = bufferedReader.readLine();
                
                
                //JobOwner job = convertToJobObj(messageFromClient);
                //System.out.println(messageFromClient);
                //jobCache.addJob(job);
                //broadcastMessage(messageFromClient);
            } catch (IOException e) {
                System.out.println(e);
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
        }
    }
    //Prints out the message
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

    public void acceptJob(int idx){
        String message = "Accepted";
        for(ClientHandler clientHandler: clientHandlers){
            try{
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
                System.out.println(message);
            }catch(IOException e){
                System.err.println(e);
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        } // End of For
        vccontroller.submitJob(jobCache.getJob(idx));
    }

    public void rejectJob(int idx){
        String message = "Rejected";
        for(ClientHandler clientHandler : clientHandlers){
            try{
                clientHandler.bufferedWriter.write(message);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
                System.out.println(message);
            }catch(IOException e){
                System.err.println(e);
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
        jobCache.removeJob(idx);
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
                System.out.println("Job successfully added to cache.");
                jobCache.addJob(job);
                return "Job has been added to cache.";
            default:
                return "Unknown task: " + instruction;
        }
    }
    public JobOwner convertToJobObj(String stringJob){
        String[]parts = stringJob.split("/",4);
        if(parts.length ==4) {
            int jobId =Integer.parseInt(parts[0]);
            int jobDuration =Integer.parseInt(parts[2]);
            int jobCompletionTime =Integer.parseInt(parts[3]);
            return new JobOwner(jobId,parts[1],jobDuration,jobCompletionTime);
        }
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
