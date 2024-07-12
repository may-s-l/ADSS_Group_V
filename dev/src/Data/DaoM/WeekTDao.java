package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;
import dev.src.Domain.Enums.ShiftType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class WeekTDao implements IDao<Week, String> {

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

    @Override
    public void insert(Week week) {
    }

    public void insertWeek(Week week) {
        String sql = "INSERT INTO Week(WeekNum, BranchAddress, StartDate) VALUES(?,?,?)";
        PreparedStatement ps = null;
        try  {
            ps =DB.getConnection().prepareStatement(sql);
            ps.setInt(1, week.getWeekNUM());
            ps.setString(2, week.getBranch().getBranchAddress());
            ps.setString(3, week.getStart_date().toString());
            ps.execute();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void insertDays(Week week)  {
        String sql = "INSERT INTO Days(WeekNum,Date, BranchAddress,IsDayOf) VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try  {
            ps =DB.getConnection().prepareStatement(sql);
            for (LocalDate day : week.getDayInWEEK()) {
                ps.setInt(1, week.getWeekNUM());
                ps.setString(2, day.toString());
                ps.setString(3, week.getBranch().getBranchAddress());
                ps.setInt(4, week.getDayOfWeek(day).isIsdayofrest() ? 1 : 0);
                ps.execute();
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void insertShifts(Week week){
        String sql = "INSERT INTO Shift (ShiftDate, ShiftType, BranchAddress, Job, StartTime, EndTime, NumEmployeesForjob) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = null;
        try  {
            ps =DB.getConnection().prepareStatement(sql);
            for (LocalDate day : week.getDayInWEEK()) {
                if (week.getDayOfWeek(day).isIsdayofrest()) continue;

                for (Shift shift : week.getDayOfWeek(day).getShiftsInDay()) {
                    for (Job job : shift.getAllJobInShift()) {
                        ps.setString(1, day.toString());
                        ps.setString(2, shift.getShiftType().toString());
                        ps.setString(3, week.getBranch().getBranchAddress());
                        ps.setString(4, job.getJobName());
                        ps.setString(5, shift.getStart_time().toString());
                        ps.setString(6, shift.getEnd_time().toString());
                        ps.setInt(7, shift.getNumberofWorkersPerJob(job));
                        ps.execute();
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void insertShiftEmployees(Week week){
        String sql = "INSERT INTO ShiftEmployees (ShiftDate, ShiftType, BranchAddress, EID, EmployeeJob) VALUES (?,?,?,?,?)";
        PreparedStatement ps = null;
        try  {
            ps =DB.getConnection().prepareStatement(sql);
            for (LocalDate day : week.getDayInWEEK()) {
                if (week.getDayOfWeek(day).isIsdayofrest()) continue;

                for (Shift shift : week.getDayOfWeek(day).getShiftsInDay()) {
                    for (Job job : shift.getAllJobInShift()) {
                        for (Employee employee : shift.getallEmployeePerJob(job)) {
                            ps.setString(1, day.toString());
                            ps.setString(2, shift.getShiftType().toString());
                            ps.setString(3, week.getBranch().getBranchAddress());
                            ps.setString(4, employee.getID());
                            ps.setString(5, job.getJobName());
                            ps.execute();
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps != null) {
                    ps.close();
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
        Connection connection = null;

        try {
            connection = DB.getConnection();
            pstmtWeek = connection.prepareStatement(sqlWeek);
            pstmtWeek.setString(1, keys[0]);
            pstmtWeek.setString(2, keys[1]);
            rs = pstmtWeek.executeQuery();

            if (rs.next()) {
                week = new Week(LocalDate.parse(rs.getString("StartDate")), BranchTDao.getInstance().select(keys[1]));
                if (week.getWeekNUM() != rs.getInt("WeekNum")) {
                    return null;
                }

                String sqlDays = "SELECT * FROM Days WHERE WeekNum = ? AND BranchAddress = ?";
                pstmtWeek = connection.prepareStatement(sqlDays);
                pstmtWeek.setInt(1, week.getWeekNUM());
                pstmtWeek.setString(2, keys[1]);
                ResultSet rsDays = pstmtWeek.executeQuery();

                while (rsDays.next()) {
                    LocalDate date = LocalDate.parse(rsDays.getString("Date"));
                    boolean isDayOf = rsDays.getInt("IsDayOf") == 1;
                    week.getDayOfWeek(date).setIsdayofrest(isDayOf);

                    if (!isDayOf) {
                        String sqlMShifts = "SELECT * FROM Shift WHERE ShiftDate = ? AND BranchAddress = ? AND ShiftType = ?";
                        PreparedStatement pstmtMShifts = connection.prepareStatement(sqlMShifts);
                        pstmtMShifts.setString(1, date.toString());
                        pstmtMShifts.setString(2, keys[1]);
                        pstmtMShifts.setString(3, "MORNING");
                        ResultSet rsShifts = pstmtMShifts.executeQuery();
                        int flag =0;
                        Shift Mshift=null;
                        while (rsShifts.next()){
                            if(flag==0){
                                LocalTime startTime = LocalTime.parse(rsShifts.getString("StartTime"));
                                LocalTime endTime = LocalTime.parse(rsShifts.getString("EndTime"));
                                Mshift = new MorningShift(startTime, endTime,date);
                                flag=1;
                            }
                            Job job = JobsTDao.getInstance().select(rsShifts.getString("Job"));
                            Mshift.ChangingTheNumberOfemployeesPerJobInShift(job, rsShifts.getInt("NumEmployeesForjob"));
                            String sqlEmployees = "SELECT * FROM ShiftEmployees WHERE ShiftDate = ? AND ShiftType = ? AND EmployeeJob = ?";
                            PreparedStatement pstmtEmployees = connection.prepareStatement(sqlEmployees);
                            pstmtEmployees.setString(1, date.toString());
                            pstmtEmployees.setString(2, "MORNING");
                            pstmtEmployees.setString(3, job.getJobName());
                            ResultSet rsEmployees = pstmtEmployees.executeQuery();
                            while (rsEmployees.next()) {
                                Employee employee = EmployeeTDao.getInstance().select(rsEmployees.getString("EID"));
                                Mshift.addEmployeeToShift(employee, job);
                            }
                            if(rsEmployees!=null){
                                rsEmployees.close();
                            }
                            if (pstmtEmployees!=null){
                                pstmtEmployees.close();
                            }
                        }
                        if (rsShifts!=null){
                            rsShifts.close();
                        }
                        if (pstmtMShifts != null){
                            pstmtMShifts.close();
                        }
                        String sqlEShifts = "SELECT * FROM Shift WHERE ShiftDate = ? AND BranchAddress = ? AND ShiftType = ?";
                        PreparedStatement pstmtEShifts = connection.prepareStatement(sqlEShifts);
                        pstmtEShifts.setString(1, date.toString());
                        pstmtEShifts.setString(2, keys[1]);
                        pstmtEShifts.setString(3, "EVENING");
                        ResultSet rsEShifts = pstmtEShifts.executeQuery();
                        flag =0;
                        Shift Eshift=null;
                        while (rsEShifts.next()){
                            if(flag==0){
                                LocalTime startTime = LocalTime.parse(rsEShifts.getString("StartTime"));
                                LocalTime endTime = LocalTime.parse(rsEShifts.getString("EndTime"));
                                Eshift = new EveningShift(startTime, endTime,date);
                                flag=1;
                            }
                            Job job = JobsTDao.getInstance().select(rsEShifts.getString("Job"));
                            Eshift.ChangingTheNumberOfemployeesPerJobInShift(job, rsEShifts.getInt("NumEmployeesForjob"));
                            String sqlEmployees = "SELECT * FROM ShiftEmployees WHERE ShiftDate = ? AND ShiftType = ? AND EmployeeJob = ?";
                            PreparedStatement pstmtEmployees = connection.prepareStatement(sqlEmployees);
                            pstmtEmployees.setString(1, date.toString());
                            pstmtEmployees.setString(2, "EVENING");
                            pstmtEmployees.setString(3, job.getJobName());
                            ResultSet rsEmployees = pstmtEmployees.executeQuery();
                            while (rsEmployees.next()) {
                                Employee employee = EmployeeTDao.getInstance().select(rsEmployees.getString("EID"));
                                Eshift.addEmployeeToShift(employee, job);
                            }
                            if(rsEmployees!=null){
                                rsEmployees.close();
                            }
                            if (pstmtEmployees!=null){
                                pstmtEmployees.close();
                            }
                        }
                        if (rsEShifts!=null){
                            rsEShifts.close();
                        }
                        if (pstmtEShifts != null){
                            pstmtEShifts.close();
                        }
                        Shift[] Shifts = new Shift[]{Mshift,Eshift};
                        week.getDayOfWeek(date).setShiftsInDay(Shifts);
                    }
                }
                rsDays.close();
            }
            return week;

        } catch (SQLException e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmtWeek != null) pstmtWeek.close();
                if (connection != null) connection.close();
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

    public boolean weekExists(String key) {
        String[] keys = key.split(",");
        String sql = "SELECT * FROM Week WHERE WeekNum = ? AND BranchAddress = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try  {
            pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setInt(1,Integer.parseInt(keys[0]));
            pstmt.setString(2, keys[1]);
            rs = pstmt.executeQuery();
            ResultSet rs1 = rs;
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        }finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
