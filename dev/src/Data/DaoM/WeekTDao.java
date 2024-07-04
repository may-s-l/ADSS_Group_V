package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class WeekTDao implements IDao<Week,String> {

    private static WeekTDao instance;
    private DBConnection DB;

    private WeekTDao() {
        DB = DBConnection.getInstance();
    }

    public static WeekTDao getInstance() {
        if (instance == null) {
            instance = new WeekTDao();
        }
        return instance;
    }

    public void insert(Week week) {
        int weeknum= week.getWeekNUM();
        String BranchA = week.getBranch().getBranchAddress();
        String date = week.getStart_date().toString();
        String sql = "INSERT INTO WeekT(WeekNum, BranchAddress, StartDate) VALUES(?,?,?)";
        PreparedStatement ps = null;
        try {
            ps= DB.getConnection().prepareStatement(sql);
            ps.setInt(1, weeknum);
            ps.setString(2, BranchA);
            ps.setString(3, date);
            ps.executeUpdate();
            Set<LocalDate> DAYS =week.getDayInWEEK();
            for (LocalDate day : DAYS) {
                int DO=0;
                if(week.getDayOfWeek(day).isIsdayofrest()){
                    DO=1;
                }
                String date2 = day.toString();
                String sql1 = "INSERT INTO Days(WeekNum, BranchAddress, Date,IsDayOf) VALUES(?,?,?,?)";
                ps = DB.getConnection().prepareStatement(sql1);
                ps.setInt(1, weeknum);
                ps.setString(2, date2);
                ps.setString(3, BranchA);
                ps.setInt(4, DO);
                ps.executeUpdate();
                Shift[] shifts = week.getDayOfWeek(day).getShiftsInDay();
                for (Shift shift : shifts) {
                    int c=0;
                    String st = shift.getShiftType().toString();
                    String STIME = shift.getStart_time().toString();
                    String ETIME = shift.getEnd_time().toString();
                    Set<Job> JOBS = shift.getAllJobInShift();
                    for (Job job : JOBS) {
                        int numemp =shift.getNumberofWorkersPerJob(job);
                        String jobName = job.getJobName();
                        if (c==0){
                            String sql2="INSERT INTO Shift VALUES (?,?,?,?,?,?,?)";
                            ps = DB.getConnection().prepareStatement(sql2);
                            ps.setString(1, date2);
                            ps.setString(2, st);
                            ps.setString(3, BranchA);
                            ps.setString(4, jobName);
                            ps.setString(5, STIME);
                            ps.setString(6, ETIME);
                            ps.setInt(7, numemp);
                            ps.executeUpdate();
                            c++;
                        }
                        List<Employee>EINJS=shift.getallEmployeePerJob(job);
                        for (Employee employee : EINJS) {
                            String sql3="INSERT INTO ShiftEmployees VALUES (?,?,?,?,?)";
                            ps = DB.getConnection().prepareStatement(sql3);
                            ps.setString(1, date2);
                            ps.setString(2,st);
                            ps.setString(3, BranchA);
                            ps.setString(4, employee.getID());
                            ps.setString(5, jobName);
                            ps.executeUpdate();
                        }

                    }

                }


            }



        }
        catch (Exception e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }



    }

    @Override
    public Week select(String s) {
        String[] keys = s.split(",");
        String sqlWeek = "SELECT * FROM Week WHERE WeekNum = ? AND BranchAddress = ?";
        Week week = null;
        PreparedStatement pstmtWeek = null;
        ResultSet rs = null;
        Branch B =BranchTDao.getInstance().select(keys[1]);
        try {
            pstmtWeek = DB.getConnection().prepareStatement(sqlWeek);
            pstmtWeek.setString(1, keys[0]);
            pstmtWeek.setString(2, keys[1]);
            rs = pstmtWeek.executeQuery();
            if(rs.next()){
                week = new Week(LocalDate.parse(rs.getString("StartDate")),B);
                if(week.getWeekNUM()!=rs.getInt("WeekNum")){
                    System.out.println("WEEK NUMBER NOT MATCHED IN DATABASE WEEK SELECT");

                }
                String sqlDays = "SELECT * FROM Days WHERE WeekNum = ? AND BranchAddress = ?";
                pstmtWeek = DB.getConnection().prepareStatement(sqlDays);
                pstmtWeek.setInt(1, week.getWeekNUM());
                pstmtWeek.setString(2, keys[1]);
                rs = pstmtWeek.executeQuery();
                while(rs.next()){
                    LocalDate date = LocalDate.parse(rs.getString("Date"));
                    if(rs.getInt("IsDayOf")==1){
                        week.getDayOfWeek(date).setIsdayofrest(true);
                    }
                    else {
                        String sqlMorningShifts = "SELECT * FROM Shift WHERE ShiftDate = ?  AND BranchAddress = ?";
                        pstmtWeek = DB.getConnection().prepareStatement(sqlMorningShifts);
                        pstmtWeek.setString(1, date.toString());
                        pstmtWeek.setString(2, keys[1]);
                        rs = pstmtWeek.executeQuery();
                        int cm=0;
                        int ce=0;
                        while(rs.next()){
                            if(rs.getString("ShiftType").equals("MORNING")){
                                Shift shift = week.getDayOfWeek(date).getShiftsInDay()[0];
                                if (cm==0){
                                    shift.setStart_time(LocalTime.parse(rs.getString("StartTime")));
                                    shift.setEnd_time(LocalTime.parse(rs.getString("EndTime")));
                                    cm++;
                                }
                                Job job = JobsTDao.getInstance().select(rs.getString("Job"));
                                shift.ChangingTheNumberOfemployeesPerJobInShift(job, rs.getInt("NumEmployeesForjob"));
                                String sqlemloyeesinjob= "SELECT * FROM ShiftEmployees WHERE ShiftDate=? AND ShiftType=MORNING AND EmployeeJob=?";
                                pstmtWeek = DB.getConnection().prepareStatement(sqlemloyeesinjob);
                                pstmtWeek.setString(1, date.toString());
                                pstmtWeek.setString(2, job.getJobName());
                                rs = pstmtWeek.executeQuery();
                                while(rs.next()){
                                    Employee employee = EmployeeTDao.getInstance().select(rs.getString("EID"));
                                    shift.addEmployeeToShift(employee,job);
                                }

                            }
                            else {
                                Shift shift = week.getDayOfWeek(date).getShiftsInDay()[0];
                                if (ce==0){
                                    shift.setStart_time(LocalTime.parse(rs.getString("StartTime")));
                                    shift.setEnd_time(LocalTime.parse(rs.getString("EndTime")));
                                    ce++;
                                }
                                Job job = JobsTDao.getInstance().select(rs.getString("Job"));
                                shift.ChangingTheNumberOfemployeesPerJobInShift(job, rs.getInt("NumEmployeesFoJob"));
                                String sqlemloyeesinjob= "SELECT * FROM ShiftEmployees WHERE ShiftDate=? AND ShiftType=EVENING AND EmployeeJob=?";
                                pstmtWeek = DB.getConnection().prepareStatement(sqlemloyeesinjob);
                                pstmtWeek.setString(1, date.toString());
                                pstmtWeek.setString(2, job.getJobName());
                                rs = pstmtWeek.executeQuery();
                                while(rs.next()){
                                    Employee employee = EmployeeTDao.getInstance().select(rs.getString("EID"));
                                    shift.addEmployeeToShift(employee,job);
                                }
                            }

                        }

                    }
                }

            }
            return week;

        }
        catch (Exception e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void update(Week obj) {

    }

    @Override
    public void delete(String s) {

    }

    public boolean weekExists(String s) {
        String[] keys = s.split(",");
        String sqlWeek = "SELECT * FROM Week WHERE WeekNum = ? AND BranchAddress = ?";
        Week week = null;
        PreparedStatement pstmtWeek = null;
        ResultSet rs = null;
        try {
            pstmtWeek = DB.getConnection().prepareStatement(sqlWeek);
            pstmtWeek.setString(1, keys[0]);
            pstmtWeek.setString(2, keys[1]);
            rs = pstmtWeek.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        }
        catch (Exception e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }


}
