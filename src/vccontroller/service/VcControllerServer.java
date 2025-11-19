package vccontroller.service;

import common.service.AccountData;
import common.ui.WelcomePage;
import vccontroller.model.JobsCache;
import vccontroller.ui.ControllerPage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        server.start();

    }

}
