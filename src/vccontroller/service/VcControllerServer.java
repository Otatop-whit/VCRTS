package vccontroller.service;

import common.service.AccountData;
import common.ui.WelcomePage;
import vccontroller.model.JobsCache;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class VcControllerServer {
    private ServerSocket serverSocket;
    public VcControllerServer(ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
    }
    public void start(){
        try {
            System.out.println("Starting server...");
            while(!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();

                System.out.println("Connected!");
            ClientHandler clientHandler = new ClientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();
            }

        } catch (IOException e) {
            System.out.println("Server Error: " + e);
        }
    }
    public void closeServer(){
        try{

            if(serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
        AccountData accountData = new AccountData();
        new WelcomePage();
        JobsCache jobCache = JobsCache.getInstance();
        ServerSocket socket = new ServerSocket();
        socket.setReuseAddress(true);
        socket.bind(new java.net.InetSocketAddress(1234));
        VcControllerServer server = new VcControllerServer(socket);
        startVehicleTable();
        server.start();   

    }

    public static void startVehicleTable(){
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/VC3?useTimezone=true&serverTimezone=UTC";
        String username = "root";
        String password = "638$8(5vsug!Fqb";
        try {
            connection = DriverManager.getConnection(url,username,password);
            String query = "CREATE TABLE IF NOT EXISTS vc3.Vehicles ("
           + "vehicleid INT NOT NULL AUTO_INCREMENT, "
           + "vo_email VARCHAR(45) NOT NULL, "
           + "license_plate VARCHAR(45) NOT NULL, "
           + "model VARCHAR(45) NOT NULL, "
           + "make VARCHAR(45) NOT NULL, "
           + "year YEAR NOT NULL, "
           + "computingpower VARCHAR(6) NOT NULL, "
           + "arrivaldate DATE NOT NULL, "
           + "departuredate DATE NOT NULL, "
           + "residency VARCHAR(45) NOT NULL, "
           + "timestamp DATETIME NOT NULL, "
           + "lastmodified DATETIME NOT NULL, "
           + "PRIMARY KEY (vehicleid), "
           + "UNIQUE INDEX vehiclescol_UNIQUE (license_plate ASC) VISIBLE"
           + ")";
           Statement statement = connection.createStatement();
           statement.executeUpdate(query); // Executes the query

        } catch (SQLException e) {
            
            e.printStackTrace();
        }
            //Sets up the query as a java string
    }
}
