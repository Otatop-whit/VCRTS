package vehicle.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * Manage the interaction between client and server
 * Should send request to VController
 * Should receive reply from VController
 */
public class VehicleClient {
    //Allows for the VehicleOwner to send info to Server
    public static void clientRequest(String msg){
        String host = "localhost";
        int serverPort = 1234;
        Socket socket = null;

        try{
            //Initializes the Connection
            socket = new Socket(host, serverPort);
            //Sets up the message to be send to Server
            PrintWriter request = new PrintWriter(socket.getOutputStream(), true);
            //Initilalizes the server's response to the client's message.
            BufferedReader response = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            request.println(msg);
            String reply = response.readLine();
            System.out.println("Server has " + reply + " your request.");

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(socket != null) socket.close();
                System.out.println("Connection closed.");
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
