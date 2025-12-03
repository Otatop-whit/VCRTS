package vccontroller.database;

import java.sql.*;
import job.model.JobOwner;
import vehicle.model.Vehicle;

public class Database {

	static Connection connection = null;
	static String url = "jdbc:mysql://localhost:3306/VCRTS?useTimezone=true&serverTimezone=UTC";
	static String username = "root";
	static String password = "638$8(5vsug!Fqb";

	public static void jobInsertion(JobOwner job) {
		try {
			connection = DriverManager.getConnection(url, username, password);
			
			String sql = "INSERT INTO vcrts.jobs (ID , jobName, duration , completionTime , jobDeadline , timestamp) VALUES ('" + job.getJobId() + "', '" + job.getJobOwnerName() + "', " + job.getDuration() + ", " + job.getCompletionTime() + ", '" + job.getJobDeadline() + "', NOW())";

			Statement statement = connection.createStatement();
			
			statement.executeUpdate(sql);

			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void vehicleInsertion(Vehicle vehicle){
		try{
            Connection connection = null;
            connection = DriverManager.getConnection(url,username,password);
            //Sets up the query as a java string
            String sqlquery = 
            "INSERT INTO vcrts.vehicles (vehicle_ID , vo_email, license_plate , model , make" +
            ", year , computingpower , arrivaldate , departuredate , residency , timestamp , lastmodified)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            //PreparedStatement allows for java variables to be utilized
            PreparedStatement ps = connection.prepareStatement(sqlquery);
			ps.setInt(1, vehicle.getVehicleId());
			ps.setString(2, vehicle.getVehicleOwnerEmail());
			ps.setString(3, vehicle.getLicensePlate());
            ps.setString(4, vehicle.getModel());
            ps.setString(5, vehicle.getMake());
            ps.setInt(6, vehicle.getYear().getValue());
            ps.setString(7, vehicle.getComputingPower());
            ps.setObject(8, vehicle.getArriveDate());
            ps.setObject(9, vehicle.getDepartDate());
            ps.setString(10, vehicle.getResidency());
            ps.setObject(11, vehicle.getTimestamp());
            ps.setObject(12, vehicle.getLastModified());

            //Returns the number of rows modified
            int response = ps.executeUpdate();
			if (response >= 1){
				System.out.println("Vehicle inserted successfully!");
			}else{
				System.out.println("Vehicle insertion failed.");
			}
			ps.close();
            connection.close();
         }catch (SQLException e){
			System.err.println("Error! Vehicle insertion failed.");
            e.getMessage();
			
         }
	}

	public static void startVehiclesTable() {
    try {
        connection = DriverManager.getConnection(url, username, password);
		Statement statement = connection.createStatement();
        String createQuery =
            "CREATE TABLE IF NOT EXISTS vehicles ("
          + "vehicle_ID INT NOT NULL, "
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
          + "PRIMARY KEY (vehicle_ID), "
          + "UNIQUE KEY vehiclescol_UNIQUE (vehicle_ID), "
          + "UNIQUE KEY license_plate_UNIQUE (license_plate)"
          + ") ENGINE=InnoDB "
          + "DEFAULT CHARSET=utf8mb4 "
          + "COLLATE=utf8mb4_0900_ai_ci";

        
        statement.executeUpdate(createQuery);

        System.out.println("Vehicles table created successfully.");

    } catch (SQLException e) {
		System.err.println(" Failed to create Vehicles table.");
        e.getMessage();
    }
}

	public static void startJobsTable(){
		try {
        connection = DriverManager.getConnection(url, username, password);

        String query =
            "CREATE TABLE IF NOT EXISTS jobs ("
          + "jobNum INT AUTO_INCREMENT PRIMARY KEY, "
          + "ID VARCHAR(100), "
          + "jobName VARCHAR(100), "
          + "duration INT, "
          + "completionTime INT, "
          + "jobDeadline VARCHAR(100), "
          + "`timestamp` DATETIME"
          + ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(query);

        System.out.println("Jobs table created successfully.");

    } catch (SQLException e) {
        System.err.println("Failed to create Jobs table.");
        e.getMessage();
    }
	}
}