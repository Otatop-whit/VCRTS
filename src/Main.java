
import common.service.AccountData;
import common.service.Client;
import common.ui.WelcomePage;
import vccontroller.ui.ControllerPage;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
//import vccontroller.model.JobInfo;
//import vccontroller.model.JobReq;
//import vccontroller.service.VcControllerServiceImpl;
//import vehicle.ui.vehicle_ui;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{


    static Connection connection = null;
    //this part is the address and name of your database server: jdbc:mysql://localhost:3306/VC3
    //this part of the string is for time adjustment: ?useTimezone=true&serverTimezone=UTC
    static String url = "jdbc:mysql://localhost:3306/VC3?useTimezone=true&serverTimezone=UTC";
    static String username = "root";
    static String password = "Yourpassword";


    public static void main(String args[]) throws IOException {

        try {
            //declares a connection to your database
            connection = DriverManager.getConnection(url, username, password);
            //creates an insert query
            String sql = "INSERT INTO table1" + "(ClientID , name)" + "VALUES (23, 'David Cruise')";
            //establishes the connection session
            Statement statement = connection.createStatement();
            //executes the query
            int row = statement.executeUpdate(sql);
            //the return value is the indication of success or failure of the query execution
            if (row > 0)
                System.out.println("Data was inserted!");

            connection.close();

        } catch (SQLException e) {
            e.getMessage();

        }




















//        AccountData accountData = new AccountData();
//        new WelcomePage();
        //new ControllerPage().setVisible(true);


//        Scanner scanner = new Scanner(System.in);
//        System.out.println("What is ur username");
//        String request = scanner.nextLine();
//        Socket socket = new Socket("localhost",1234);
//        Client client = new Client(socket,request);
//        client.listenForMessage();
//        client.sendMessage();

        //new ControllerPage().setVisible(true);
    }


}
