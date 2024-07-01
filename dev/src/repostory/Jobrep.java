package dev.src.repostory;

import dev.src.Domain.Job;

import java.util.ArrayList;
import java.util.List;

public class Jobrep {
    private List<Job> jobrep;

    public Jobrep() {
        jobrep = new ArrayList<Job>();
    }
    public void addJob(Job j) {
        jobrep.add(j);
    }
    public void removeJob(Job j) {
        jobrep.remove(j);
    }
    public List<Job> getJobrep() {
        return jobrep;
    }
    public void setJobrep(List<Job> jobrep) {
        this.jobrep = jobrep;
    }
    public Job getJob(String jobName) {
        for (Job j : jobrep) {
            if (j.getJobName().equals(jobName)){
                return j;
            }
        }
        return null;
    }
}
