package dev.src.Domain.Repository;

import dev.src.Data.DaoM.BranchTDao;
import dev.src.Data.DaoM.EmployeeJobsTDao;
import dev.src.Data.DaoM.EmployeeTDao;
import dev.src.Data.DaoM.JobsTDao;
import dev.src.Domain.Branch;
import dev.src.Domain.Job;
import dev.src.Domain.MyMap;

import java.util.List;
import java.util.Set;

public class BranchRep implements IRep<Branch,String>{

    MyMap<String, Branch> Branchs;
    private EmployeeTDao EmployeeDao;
    private BranchTDao BranchDao;

    public BranchRep() {
        Branchs = new MyMap();
        EmployeeDao=EmployeeTDao.getInstance();
        BranchDao=BranchTDao.getInstance();
    }


    @Override
    public String add(Branch obj) {
        if (!Branchs.containsKey(obj.getBranchAddress())) {
            Branch B=BranchDao.select(obj.getBranchAddress());
            if(B==null){
                BranchDao.insert(obj);
            }
            Branchs.put(obj.getBranchAddress(),obj);
        }
        return "S-BranchRep Added Successfully";
    }

    @Override
    public Branch find(String s) {
        if (!Branchs.containsKey(s)) {
            Branch B=BranchDao.select(s);
            if(B==null){
                return null;
            }
            BranchDao.branchHaveManager(B);
            Branchs.put(s,B);
            return B;
        }
        return Branchs.get(s);
    }

    @Override
    public String update(Branch obj) {
        if(Branchs.containsKey(obj.getBranchAddress())) {
            if(!Branchs.get(obj.getBranchAddress()).equals(obj)) {
                BranchDao.update(obj);
            }
        }
        return "";
    }

    @Override
    public String delete(String s) {
        return "";
    }

    public Set<String> getKeys(){
        if (Branchs.getKeys().isEmpty()){
            Branchs=BranchDao.selectAllBranchs();
        }
        return Branchs.getKeys();
    }

}