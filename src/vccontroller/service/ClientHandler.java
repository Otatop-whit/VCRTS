package vccontroller.service;

import common.model.Account;
import job.model.JobOwner;
import vccontroller.model.JobsCache;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String clientRequest;
        private JobsCache jobCache = JobsCache.getInstance();
        private VcControllerServiceImpl vcController = new VcControllerServiceImpl();

    public  ClientHandler(Socket socket){
        try{

            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientRequest = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("Hello"+ clientRequest);

        }catch (IOException e){

            System.out.println(e);
            closeEverything(socket,bufferedReader,bufferedWriter);
        }

    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                JobOwner job = convertToJobObj(messageFromClient);
                System.out.println(messageFromClient);
                jobCache.addJob(job);
                //broadcastMessage(messageFromClient);
            } catch (IOException e) {
                System.out.println(e);
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;
            }
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
    public void acceptJob(int idx){
        String message = "Accepted";
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
        vcController.submitJob(jobCache.getJob(idx));
    }
    public void rejectJob(int idx){

        String message = "Rejected";
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
        jobCache.removeJob(idx);

    }
}
