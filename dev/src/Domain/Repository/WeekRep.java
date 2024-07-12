package dev.src.Domain.Repository;

import dev.src.Data.DaoM.BranchTDao;
import dev.src.Data.DaoM.EmployeeTDao;
import dev.src.Data.DaoM.WeekTDao;
import dev.src.Domain.Branch;
import dev.src.Domain.MyMap;
import dev.src.Domain.Week;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

public class WeekRep implements IRep<Week,String> {


    private MyMap<String,Week> map;

    private WeekTDao weekTDao;
    private BranchTDao branchTDao;

    public WeekRep() {
        map = new MyMap();
        weekTDao=WeekTDao.getInstance();
        branchTDao=BranchTDao.getInstance();
    }

    //        String sqlWeek = "SELECT * FROM Week WHERE WeekNum = ? AND BranchAddress = ?";

    @Override
    public String add(Week obj) {
        String keysDB = obj.getWeekNUM()+","+obj.getBranch().getBranchAddress();
        String keys = obj.getBranch().getBranchNum()+","+obj.getStart_date();
        if(!map.containsKey(keys)){
            if(!weekTDao.weekExists(keysDB)){
                weekTDao.insertWeek(obj);
                weekTDao.insertDays(obj);
                weekTDao.insertShifts(obj);
                weekTDao.insertShiftEmployees(obj);
                this.map.put(keys,obj);
            }
        }
        return "Week already exist--DB branchrep";

    }

    @Override
    public Week find(String s) {
        if (map.containsKey(s)) {
            return map.get(s);
        } else {
            String[] keys = s.split(",");
            Branch B = branchTDao.getBRANCHbyNum(Integer.parseInt(keys[0]));
            LocalDate date = LocalDate.parse(keys[1]).plusDays(3);
            int weekOfYear = date.get(WeekFields.ISO.weekOfWeekBasedYear());
            String keysDB = weekOfYear+","+B.getBranchAddress();
            if (weekTDao.weekExists(keysDB)) {
                Week w = weekTDao.select(keysDB);
                map.put(s, w);
                return w;
            }
            return null;
        }
    }


    @Override
    public String update(Week obj) {
        return "";
    }

    @Override
    public String delete(String s) {
        return "";
    }
}
