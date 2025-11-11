package common.service;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String request;

    public Client(Socket socket, String request){
        try {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.request = request;

        }catch (IOException e){
            closeEverything(socket,bufferedReader,bufferedWriter);

        }

    }
    public void sendMessage(){
        try{
            bufferedWriter.write(request);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(request+ ":"+ messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){

            closeEverything(socket,bufferedReader,bufferedWriter);
        }

    }
    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()){
                    try{
                        message = bufferedReader.readLine();
                        System.out.println(message);
                    }catch (IOException e){

                        closeEverything(socket,bufferedReader,bufferedWriter);
                    }
                }
            }
        }).start();
    }
    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        try {
        if(bufferedReader != null){
            bufferedReader.close();
        }
        if (bufferedWriter != null){
            bufferedWriter.close();
        }
        if (socket != null){
            socket.close();
        }
        }catch (IOException e){
            System.out.println(e);
        }

    }
}
