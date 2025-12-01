package vccontroller.database;

import java.sql.*;
import job.model.JobOwner;

public class Database {

	static Connection connection = null;
	static String url = "jdbc:mysql://localhost:3306/VC3?useTimezone=true&serverTimezone=UTC";
	static String username = "root";
	static String password = "638$8(5vsug!Fqb";

	public static void jobInsertion(JobOwner job) {
		try {
			connection = DriverManager.getConnection(url, username, password);
			
			String sql = "INSERT INTO jobs (jobName, duration, completionTime, jobDeadline, timestamp) VALUES ('" + job.getJobOwnerName() + "', " + job.getDuration() + ", " + job.getCompletionTime() + ", '" + job.getJobDeadline() + "', NOW())";

			Statement statement = connection.createStatement();
			
			statement.executeUpdate(sql);

			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}