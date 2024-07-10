package dev.src.Domain.Repository;

import dev.src.Data.DaoM.EmployeeConstraintTDao;
import dev.src.Data.DaoM.EmployeeTDao;
import dev.src.Domain.Constraint;
import dev.src.Domain.Employee;
import dev.src.Domain.MyMap;

import java.time.LocalDate;

public class ConstraintRep implements IRep<Constraint, String> {

    private MyMap<LocalDate, Constraint> map ;
    private EmployeeConstraintTDao constraintDao;


    public ConstraintRep() {
        map = new MyMap<>();
        constraintDao = EmployeeConstraintTDao.getInstance();
    }

    @Override
    public String add(Constraint obj) {
        if(!map.containsKey(obj.getShiftDate())){
            String Key = obj.getEmp().getID()+","+obj.getShiftDate();
            Constraint C =constraintDao.select(Key);
            if(C == null){
                constraintDao.insert(obj);
            }
        }
        map.put(obj.getShiftDate(),obj);
        return "s";
    }

    @Override
    public Constraint find(String s) {
        String[] keys = s.split(",");
        LocalDate date = LocalDate.parse(keys[1]);
        if(!map.containsKey(date)){
            Constraint C =constraintDao.select(s);
            if(C == null){
                return null;
            }
            else {
                map.put(C.getShiftDate(),C);
                return C;
            }
        }
        else {
            return map.get(date);
        }
    }


    @Override
    public String update(Constraint obj) {
        constraintDao.update(obj);
        map.put(obj.getShiftDate(),obj);
        return "S";
    }

    @Override
    public String delete(String s) {
        String[] keys = s.split(",");
        if(!map.containsKey(LocalDate.parse(keys[1]))){
            Constraint C =constraintDao.select(s);
            if(C == null){
                constraintDao.delete(s);
            }
        }
        else {
            map.remove(LocalDate.parse(keys[1]));
            constraintDao.delete(s);
        }
        return "S";
    }

    public int size(){
        return map.size();
    }





}
