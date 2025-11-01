package vccontroller.service;
import vccontroller.model.JobInfo;
import vccontroller.model.JobReq;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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


}
