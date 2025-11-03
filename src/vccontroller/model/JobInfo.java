package vccontroller.model;

public class JobInfo {
    private int jobDuration;
    private int jobCompletionTime;

    public JobInfo(int Duration, int completionTime){
        this.jobDuration = Duration;
        this.jobCompletionTime = completionTime;
    }

    public int getJobDuration() {
        return jobDuration;
    }

    public void setJobDuration(int jobDuration) {
        this.jobDuration = jobDuration;
    }

    public int getJobCompletionTime() {
        return jobCompletionTime;
    }

    public void setJobCompletionTime(int jobCompletionTime) {
        this.jobCompletionTime = jobCompletionTime;
    }
}
