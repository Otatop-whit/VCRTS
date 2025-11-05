package job.service;

import common.model.User;
import job.model.JobOwner;

import java.io.*;

//import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JobOwnerServiceImpl implements JobOwnerService {
    private int entryNumber;
    User user = User.getInstance();

        public void addJobOwner(JobOwner jobOwner) {


            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/job/repo/Data.txt"));
                entryNumber = Integer.valueOf(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("src/job/repo/"+user.getEmail()+".txt", true));

                writer.write("\n");
                writer.write("Data entry " + entryNumber + ":");
                writer.write("\n");
                writer.write("Timestamp: " + timestamp);
                writer.write("\nJobName: " + jobOwner.getJobOwnerName());
                writer.write("\nDuration: " + jobOwner.getDuration());
                writer.write("\nCompletiontime: " + jobOwner.getCompletionTime());
                writer.write("\nRequirements: " + jobOwner.getRequirements());

                writer.write("\n");
                writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                entryNumber++;
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/job/repo/Data.txt"));
                bufferedWriter.write(String.valueOf(entryNumber));
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }

}
