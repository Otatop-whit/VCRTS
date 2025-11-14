
import common.service.Client;
import common.ui.WelcomePage;
import vccontroller.ui.ControllerPage;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
//import vccontroller.model.JobInfo;
//import vccontroller.model.JobReq;
//import vccontroller.service.VcControllerServiceImpl;
//import vehicle.ui.vehicle_ui;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{

    public static void main(String args[]) throws IOException {
        new WelcomePage();
        //new ControllerPage().setVisible(true);


        Scanner scanner = new Scanner(System.in);
        System.out.println("What is ur username");
        String request = scanner.nextLine();
        Socket socket = new Socket("localhost",1234);
        Client client = new Client(socket,request);
        client.listenForMessage();
        client.sendMessage();

        //new ControllerPage().setVisible(true);
    }


}
