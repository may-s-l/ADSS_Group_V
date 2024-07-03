package dev.src.Domain.Repository;


import dev.src.Data.DaoM.JobsTDao;
import dev.src.Domain.Job;

import java.util.ArrayList;
import java.util.List;

public class JobRep implements IRep<Job,String>{

    private List<Job> jobs;
    private JobsTDao JobDao;


    public JobRep() {
        jobs = new ArrayList<Job>();
        JobDao = JobDao.getInstance();
    }

    @Override
    public String add(Job obj) {
        if(!jobs.contains(obj)) {
            jobs.add(obj);
            if (JobDao.select(obj.getJobName()) == null) {
                JobDao.insert(obj);
            }
            return "S";
        }
        return "S";
    }


    @Override
    public Job find(String s) {//jabName
        if (jobs.contains(s)) {
            for (int i=0;i>=jobs.size();i++) {
                if (jobs.get(i).getJobName().equals(s)){
                    return jobs.get(i);
                }
            }
        }
        Job job=JobDao.select(s);
        if (job == null){
            return null;
        }
        jobs.add(job);
        return job;
    }

    public Job getJobByIndex(int i) {
        Job j=jobs.get(i);
        if (jobs.contains(j)) {
            return j;
        }
        Job j2=JobDao.select(j.getJobName());
        if (j2 == null) {
            return null;
        }
        return j;
    }

    @Override
    public String update(Job obj) {
        if (jobs.contains(obj)) {
            Job job=JobDao.select(obj.getJobName());
            if (job==null) {
                return null;
            }
            try {
                JobDao.update(obj);
            } catch (Exception e) {
                new IllegalArgumentException("Job update failed");
            }
            jobs.add(obj);
            return "Job update";
        }
        return "Job updated --job rep update";
    }

    @Override
    public String delete(String s) {
        for (int i=0;i>=jobs.size();i++) {
            if (jobs.get(i).getJobName().equals(s)){
                jobs.remove(i);
                return "job deleted";
            }
        }
        return "job not delete from --rep job";
    }

    public int getsize() {
        if (jobs.size()==0){
            this.jobs=JobDao.selectAllJobs();
        }
        return jobs.size();
    }

}