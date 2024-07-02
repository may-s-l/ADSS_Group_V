package dev.src.Domain.Repository;

import dev.src.Data.DAO.ConstraintDao;
import dev.src.Data.DAO.EmployeeDao;
import dev.src.Data.DAO.TermsOfEmploymentDao;
import dev.src.Domain.Constraint;
import dev.src.Domain.Employee;
import dev.src.Domain.MyMap;

import java.time.LocalDate;

public class EmployeeRep implements IRep<Employee,String> {


    private MyMap<String, Employee> map ;
    private ConstraintDao constraintDao;
    private TermsOfEmploymentDao termsOfEmploymentDao;
    private EmployeeDao employeeDao;


    public EmployeeRep() {
        map = new MyMap<>();
        constraintDao = ConstraintDao.getInstance();
        termsOfEmploymentDao = TermsOfEmploymentDao.getInstance();
        employeeDao = EmployeeDao.getInstance();
    }

    @Override
    public String add(Employee obj) {
        String Key;
        if(!map.containsKey(obj.getID())){
            Key = obj.getID();
            Employee C =EmployeeDao.getInstance().select(Key);
            if(C == null){
                EmployeeDao.getInstance().insert(obj);
                TermsOfEmploymentDao.getInstance().insert(obj.getTerms());

            }
        }

        return "s";


        return "";
    }

    @Override
    public Employee find(String s) {
        return null;
    }

    @Override
    public String update(Employee obj) {
        return "";
    }

    @Override
    public String delete(String s) {
        return "";
    }
}
