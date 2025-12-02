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
			
			String sql = "INSERT INTO vcrts.jobs (jobId, jobName, duration, completionTime, jobDeadline, timestamp) VALUES ('" + job.getJobId() + "', '" + job.getJobOwnerName() + "', " + job.getDuration() + ", " + job.getCompletionTime() + ", '" + job.getJobDeadline() + "', NOW())";

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
            "INSERT INTO vcrts.vehicles (vo_email , license_plate , model , make" +
            ", year , computingpower , arrivaldate , departuredate , residency , timestamp , lastmodified)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            //PreparedStatement allows for java variables to be utilized
            PreparedStatement ps = connection.prepareStatement(sqlquery);
            ps.setString(1, vehicle.getVehicleOwnerEmail());
            ps.setString(2, vehicle.getLicensePlate());
            ps.setString(3, vehicle.getModel());
            ps.setString(4, vehicle.getMake());
            ps.setInt(5, vehicle.getYear().getValue());
            ps.setString(6, vehicle.getComputingPower());
            ps.setObject(7, vehicle.getArriveDate());
            ps.setObject(8, vehicle.getDepartDate());
            ps.setString(9, vehicle.getResidency());
            ps.setObject(10, vehicle.getTimestamp());
            ps.setObject(11, vehicle.getLastModified());

            //Returns the number of rows modified
            ps.executeUpdate();
			ps.close();
            connection.close();
         }catch (SQLException e){
            e.getMessage();
         }
	}

	public static void startVehicleTable(){
        try {
            connection = DriverManager.getConnection(url,username,password);
            String query = "CREATE TABLE IF NOT EXISTS vcrts.Vehicles ("
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