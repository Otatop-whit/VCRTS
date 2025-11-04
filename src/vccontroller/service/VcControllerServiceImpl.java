package vccontroller.service;
import job.model.JobOwner;
import vccontroller.model.JobInfo;
import vccontroller.model.JobReq;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class VcControllerServiceImpl {

    public JobInfo jobCalculations(JobReq rec){

        Random rand = new Random();

        int duration = switch (rec) {
            case High -> rand.nextInt(7, 10);     // 7–9
            case Medium -> rand.nextInt(3, 7);     // 3–6
            case Low -> rand.nextInt(1, 3);     // 1–3
        };
        int completionTime;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/vccontroller/repo/Data.txt"));
             completionTime = Integer.valueOf(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        completionTime += duration;

        JobInfo jobinfo = new JobInfo(duration,completionTime);

        return  jobinfo;


    }
    public void submitJob(JobOwner jobOwner){

//
//            try {
//                BufferedReader reader = new BufferedReader(new FileReader("src/job/repo/Data.txt"));
//                entryNumber = Integer.valueOf(reader.readLine());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("src/vccontroller/repo/JobData.txt", true));

                writer.write("\n");
                writer.write("Timestamp: " + timestamp);
                writer.write("\nDuration: " + jobOwner.getDuration());
                writer.write("\nJobCompletionTime: " + jobOwner.getCompletionTime());
                writer.write("\nRequirements: " + jobOwner.getRequirements());

                writer.write("\n");
                writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/vccontroller/repo/Data.txt"));
            bufferedWriter.write(String.valueOf(jobOwner.getCompletionTime()));
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
