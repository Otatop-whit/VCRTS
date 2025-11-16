package vccontroller.service;
import job.model.JobOwner;
import vccontroller.model.JobInfo;
import vccontroller.model.JobReq;

import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;

public class VcControllerServiceImpl {
    Queue<JobOwner> queue = new Queue<JobOwner>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<JobOwner> iterator() {
            return null;
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return null;
        }

        @Override
        public boolean add(JobOwner jobOwner) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends JobOwner> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean offer(JobOwner jobOwner) {
            return false;
        }

        @Override
        public JobOwner remove() {
            return null;
        }

        @Override
        public JobOwner poll() {
            return null;
        }

        @Override
        public JobOwner element() {
            return null;
        }

        @Override
        public JobOwner peek() {
            return null;
        }
    };


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
            if (jobOwner.getCompletionTime() == 0) {
                int currentTotal = 0;
                try (BufferedReader reader = new BufferedReader(new FileReader("src/vccontroller/repo/Data.txt"))) {
                    String line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        currentTotal = Integer.parseInt(line.trim());
                    } 
                    
                } catch (IOException ignore) {

                }
                jobOwner.setCompletionTime(currentTotal + jobOwner.getDuration());
            }


            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            queue.add(jobOwner);
            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter("src/vccontroller/repo/JobData.txt", true));

                writer.write("\n");
                writer.write("Timestamp: " + timestamp);
                writer.write("\nJob Name: " + jobOwner.getJobOwnerName());
                writer.write("\nDuration: " + jobOwner.getDuration());
                writer.write("\nJob Completion Time: " + jobOwner.getCompletionTime());
                writer.write("\nJob Deadline: " + jobOwner.getJobDeadline() );

                writer.write("\n");
                writer.write("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                writer.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        try {
            int completionTime = jobOwner.getCompletionTime();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/vccontroller/repo/Data.txt"));
            bufferedWriter.write(String.valueOf(completionTime));
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }

}
