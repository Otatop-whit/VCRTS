package job.model;

import java.util.Objects;

public class JobOwner {
    private int id;
    private String jobOwnerName;
    private int duration;
    private int completionTime;

    private String requirements;

    public JobOwner(){
    }
    public JobOwner(int id, String jobOwnerName, int duration, int completionTime) {
        this.id = id;
        this.jobOwnerName = jobOwnerName;
        this.duration = duration;
        this.completionTime = completionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobOwnerName() {
        return jobOwnerName;
    }

    public void setJobOwnerName(String jobOwnerName) {
        this.jobOwnerName = jobOwnerName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setJobDeadline(int completionTime) {
        this.completionTime = completionTime;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JobOwner jobOwner = (JobOwner) o;
        return id == jobOwner.id && Objects.equals(jobOwnerName, jobOwner.jobOwnerName) && Objects.equals(duration, jobOwner.duration) && Objects.equals(completionTime, jobOwner.completionTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobOwnerName, duration, completionTime);
    }
}
