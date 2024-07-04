package dev.src.Domain.Repository;

import dev.src.Data.DaoM.*;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.MyMap;

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
            }
            map.put(Key, obj);
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
        E.setJobs(employeeJobsTDao.selectAllJobs(s));
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

    public void getALLEmpActiveByBranch(String A,EmployeeRep rep){
        employeeDao.getALLEmpActiveByBranch(A,rep);
    }

    public Set<String> getKeys(){
        return map.getKeys();
    }



}
