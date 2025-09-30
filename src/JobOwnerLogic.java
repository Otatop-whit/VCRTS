import java.io.*;

public class JobOwnerLogic {
    private int entryNumber;
    public JobOwnerLogic(JobOwner jobOwner){

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Data.txt"));
            entryNumber = Integer.valueOf(reader.readLine());
            System.out.println(entryNumber);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("JobOwnerData.txt",true));

            writer.write("\n");
            writer.write("Data entry " + entryNumber + ":");
            writer.write("\n");
            writer.write("\nJobOwnerID: " + jobOwner.getId());
            writer.write("\nJobOwnerName: " + jobOwner.getJobOwnerName());
            writer.write("\nApproximateDuration: " + jobOwner.getApproximateJobDuration());
            writer.write("\nJobDeadline: " + jobOwner.getJobDeadline());

            writer.write("\n");
            writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try{
            entryNumber++;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("Data.txt"));
            bufferedWriter.write(String.valueOf(entryNumber));
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
