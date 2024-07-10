package dev.src.Domain.Repository;

import dev.src.Data.DaoM.*;
import dev.src.Domain.*;

import java.util.Set;

public class EmployeeRep implements IRep<Employee,String> {

    private EmployeeJobsTDao employeeJobsTDao;
    private MyMap<String, Employee> map ;
    private EmployeeConstraintTDao constraintDao;
    private EmployeeTermsTDao termsOfEmploymentDao;
    private EmployeeTDao employeeDao;
    private JobsTDao jobDao;


    public EmployeeRep() {
        this.map = new MyMap<String,Employee>();
        this.constraintDao = EmployeeConstraintTDao.getInstance();
        this.termsOfEmploymentDao = EmployeeTermsTDao.getInstance();
        this.employeeDao = EmployeeTDao.getInstance();
        this.employeeJobsTDao = EmployeeJobsTDao.getInstance();
        this.jobDao = JobsTDao.getInstance();
    }

    @Override
    public String add(Employee obj) {
        String Key;
        if(!map.containsKey(obj.getID())){
            Key = obj.getID();
            Employee C = employeeDao.select(Key);
            if(C == null){
                employeeDao.insert(obj);
                map.put(Key, obj);
                return "employee added";
            }
            EJobsRep J = employeeJobsTDao.selectAllJobs(Key);
            C.setJobs(J);
            TermsOfEmployment t=termsOfEmploymentDao.select(Key);
            C.setTerms(t);
            ConstraintRep D= constraintDao.selectALLFEUTREconstrain(Key);
            C.setConstraintMyMap(D);
            map.put(Key, C);
            return "employee added";
        }
        return "employee already exist";
    }

    @Override
    public Employee find(String s) {
        if(map.containsKey(s)){
            return map.get(s);
        }
        Employee E =employeeDao.select(s);
        if(E == null){
            return null;
        }
        EJobsRep J = employeeJobsTDao.selectAllJobs(s);
        E.setJobs(J);
        TermsOfEmployment t=termsOfEmploymentDao.select(s);
        E.setTerms(t);
        t.setEmp(E);
        ConstraintRep D= constraintDao.selectALLFEUTREconstrain(s);

        E.setConstraintMyMap(D);
        map.put(s, E);
        return E;
    }

    @Override
    public String update(Employee obj) {
        if(map.containsKey(obj.getID())){
            Employee C = employeeDao.select(obj.getID());
            if(C == null){
                return null;
            }
            try {
                employeeDao.update(obj);
            }
            catch(Exception e){
                new IllegalArgumentException("employee update failed");
            }
            map.put(obj.getID(), obj);
            return "employee updated";
        };
        return "employee updated --emp rep update";
    }

    @Override
    public String delete(String s) {
        if(map.containsKey(s)){
            map.remove(s);
            return "employee deleted";
        }
        return "employee not delete from --rep employee";
    }

    public EmployeeRep getALLEmpActiveByBranch(String A){
        return employeeDao.getALLEmpActiveByBranch(A);
    }

    public Set<String> getKeys(){
        return map.getKeys();
    }



}
