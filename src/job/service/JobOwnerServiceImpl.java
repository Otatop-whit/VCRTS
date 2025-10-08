package job.service;

import job.model.JobOwner;

import java.io.*;

public class JobOwnerServiceImpl implements JobOwnerService {
    private int entryNumber;

        public void addJobOwner(JobOwner jobOwner) {


            try {
                BufferedReader reader = new BufferedReader(new FileReader("src/job/repo/Data.txt"));
                entryNumber = Integer.valueOf(reader.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("src/job/repo/JobOwnerData.txt", true));

                writer.write("\n");
                writer.write("Data entry " + entryNumber + ":");
                writer.write("\n");
                writer.write("\nJobOwnerID: " + jobOwner.getId());
                writer.write("\nJobOwnerName: " + jobOwner.getJobOwnerName());
                writer.write("\nApproximateDuration: " + jobOwner.getApproximateJobDuration());
                writer.write("\nJobDeadline: " + jobOwner.getJobDeadline());
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
