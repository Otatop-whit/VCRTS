package common.model;

import java.util.Objects;

public class JobOwner {
    private int id;
    private String jobOwnerName;
    private String approximateJobDuration;
    private String jobDeadline;

    public JobOwner(){
    }
    public JobOwner(int id, String jobOwnerName, String approximateJobDuration, String jobDeadline) {
        this.id = id;
        this.jobOwnerName = jobOwnerName;
        this.approximateJobDuration = approximateJobDuration;
        this.jobDeadline = jobDeadline;
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

    public String getApproximateJobDuration() {
        return approximateJobDuration;
    }

    public void setApproximateJobDuration(String approximateJobDuration) {
        this.approximateJobDuration = approximateJobDuration;
    }

    public String getJobDeadline() {
        return jobDeadline;
    }

    public void setJobDeadline(String jobDeadline) {
        this.jobDeadline = jobDeadline;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        JobOwner jobOwner = (JobOwner) o;
        return id == jobOwner.id && Objects.equals(jobOwnerName, jobOwner.jobOwnerName) && Objects.equals(approximateJobDuration, jobOwner.approximateJobDuration) && Objects.equals(jobDeadline, jobOwner.jobDeadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jobOwnerName, approximateJobDuration, jobDeadline);
    }
}
