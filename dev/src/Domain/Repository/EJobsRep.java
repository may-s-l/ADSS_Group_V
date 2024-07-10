package dev.src.Domain.Repository;


import dev.src.Data.DaoM.EmployeeJobsTDao;
import dev.src.Data.DaoM.EmployeeTDao;
import dev.src.Data.DaoM.JobsTDao;
import dev.src.Domain.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class EJobsRep implements IRep<Job, String>{

    String EDI;
    List<Job> jobs;
    private JobsTDao JobDao;
    private EmployeeTDao EmployeeDao;
    private EmployeeJobsTDao EmployeeJobsDao;



    public EJobsRep (String EID) {
        EDI = EID;
        jobs = new ArrayList<Job>();
        JobDao = JobDao.getInstance();
        EmployeeDao = EmployeeDao.getInstance();
        EmployeeJobsDao = EmployeeJobsDao.getInstance();
    }

    @Override
    public String add(Job obj) {
        String key = EDI+","+obj.getJobName();
        if(!jobs.contains(obj)) {
            /////
            String J = EmployeeJobsDao.select(key) ;
            if(J == null) {
                EmployeeJobsDao.insert(key);
            }
            jobs.add(obj);
            return "S";
        }
        return "S";
    }


    @Override
    public Job find(String s) {
        String Key = EDI+","+s;
        Job J = JobDao.select(s);
        if(J != null) {
            if(!jobs.contains(J)){
                if (EmployeeJobsDao.select(Key)==null) {
                    return null;
                } else {
                    jobs.add(J);
                    return J;
                }
            }
            return J;
        }
        throw new IllegalArgumentException("job not exist");
    }

    public String toString() {
        return this.jobs.toString();
    }

    @Override
    public String update(Job obj) {
        return "";
    }

    @Override
    public String delete(String s) {
        return "";
    }

    public int getsize() {
        return jobs.size();
    }

    public boolean isEmpty(){
        return jobs.isEmpty();
    }


    public Stream<Job> stream(){
        return jobs.stream();
    }


}
