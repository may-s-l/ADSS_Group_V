package dev.src.Domain.Repository;

import dev.src.Data.DAONotToUes.ConstraintDao;
import dev.src.Domain.Constraint;
import dev.src.Domain.MyMap;

import java.time.LocalDate;

public class ConstraintRep implements IRep<Constraint, String> {

    private MyMap<LocalDate, Constraint> map ;
    private ConstraintDao constraintDao;


    public ConstraintRep() {
        map = new MyMap<>();
        constraintDao = ConstraintDao.getInstance();
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
        return "s";
    }

    @Override
    public String delete(String s) {
        if(!map.containsKey(LocalDate.parse(s))){
            Constraint C =constraintDao.select(s);
            if(C == null){
                constraintDao.delete(s);
            }
        }
        else {
            map.remove(LocalDate.parse(s));
            constraintDao.delete(s);
        }
        return "s";
    }

    public int size(){
        return map.size();
    }





}
