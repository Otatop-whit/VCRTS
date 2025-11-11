package vccontroller.service;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
        public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String clientRequest;

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
                System.out.println(messageFromClient);
                broadcastMessage(messageFromClient);
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

}
