package dev.src.domain;

public class Job {
    String JobName;
    public Job(String jobName) {
        this.JobName=jobName;
    }

    public String getJobName() {
        return JobName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || ! (o instanceof Job)) {
            return false;
        }
        Job other = (Job) o;
        return this.getJobName()==other.getJobName();
    }
}
