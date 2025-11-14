package vccontroller.model;

import job.model.JobOwner;

import java.util.ArrayList;

public class JobsCache {
    private static JobsCache instance;
    private ArrayList<JobOwner> jobCache = new ArrayList<>();
    private JobsCache(){

    }

    public static JobsCache getInstance(){
        if(instance == null){
            instance = new JobsCache();
        }
        return instance;
    }
    public JobOwner getJob(int index){
        return jobCache.get(index);
    }
    public void addJob(JobOwner job){
        jobCache.add(job);
        System.out.println(jobCache.size());

    }
    public void removeJob(int index){
        jobCache.remove(index);
    }
    public int length(){
        return jobCache.size();
    }


}
