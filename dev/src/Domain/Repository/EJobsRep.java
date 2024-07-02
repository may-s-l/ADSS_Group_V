package dev.src.Domain.Repository;

import dev.src.Data.DAO.EmployeeDao;
import dev.src.Data.DAO.JobDao;
import dev.src.Domain.Employee;
import dev.src.Domain.Job;

import java.util.ArrayList;
import java.util.List;

public class EJobsRep implements IRep<Job, Employee>{

    String EDI;
    List<Job> jobs;
    private JobDao JobDao;
    private EmployeeDao EmployeeDao;


    public void EJobRep(String EID) {
        EDI = EID;
        jobs = new ArrayList<Job>();
        JobDao = JobDao.getInstance();
        EmployeeDao = EmployeeDao.getInstance();
    }

    @Override
    public String add(Job obj) {
        if(!jobs.contains(obj)) {
            jobs.add(obj);
            EmployeeDao.getAlljobsforemployee(EDI);
            if()
            return "s";
        }
        return "s";
    }


    @Override
    public Job find(String s) {
        return null;
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

}
