
import common.service.AccountData;
import common.service.Client;
import common.ui.WelcomePage;
import job.model.JobOwner;
import vccontroller.database.Database;
import vccontroller.ui.ControllerPage;

import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
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
    static String password = "638$8(5vsug!Fqb"; //Varies per user so if you wanna test, insert your root password here.


    public static void main(String args[]) throws IOException {

        AccountData accountData = new AccountData();
        new WelcomePage();
    }

    
}
