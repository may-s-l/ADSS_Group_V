package dev.src.domain;

public class Job {
    String name;

    public Job(String name) {
        this.name = name;
    }

    public String getJobName() {
        return name;
    }

    public void setJobName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Job: " + name ;
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