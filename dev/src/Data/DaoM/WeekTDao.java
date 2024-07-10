package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;

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
//        Connection connection = null;
//
//        try {
//            connection = DB.getConnection();
//            connection.setAutoCommit(false); // התחלת טרנזקציה
//
//            insertWeek(week, connection);
//            insertDays(week, connection);
//            insertShifts(week, connection);
//            insertShiftEmployees(week, connection);
//
//            connection.commit(); // סיום טרנזקציה
//
//        } catch (Exception e) {
//            if (connection != null) {
//                try {
//                    connection.rollback(); // ביטול טרנזקציה במקרה של שגיאה
//                } catch (SQLException ex) {
//                    throw new IllegalArgumentException("Rollback Error: " + ex.getMessage());
//                }
//            }
//            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
//        } finally {
//            try {
//                if (connection != null) {
//                    connection.setAutoCommit(true); // החזרת מצב טרנזקציה לאוטומטי
//                    connection.close(); // סגירת החיבור לבסיס הנתונים
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
//        }
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
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void insertDays(Week week)  {
        String sql = "INSERT INTO Days(WeekNum, BranchAddress, Date, IsDayOf) VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try  {
            ps =DB.getConnection().prepareStatement(sql);
            for (LocalDate day : week.getDayInWEEK()) {
                ps.setInt(1, week.getWeekNUM());
                ps.setString(2, week.getBranch().getBranchAddress());
                ps.setString(3, day.toString());
                ps.setInt(4, week.getDayOfWeek(day).isIsdayofrest() ? 1 : 0);
                ps.executeUpdate();
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
                        ps.executeUpdate();
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
                            ps.executeUpdate();
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
        Branch B = BranchTDao.getInstance().select(keys[1]);
        Connection connection = null;

        try {
            connection = DB.getConnection();
            pstmtWeek = connection.prepareStatement(sqlWeek);
            pstmtWeek.setString(1, keys[0]);
            pstmtWeek.setString(2, keys[1]);
            rs = pstmtWeek.executeQuery();

            if (rs.next()) {
                week = new Week(LocalDate.parse(rs.getString("StartDate")), B);
                if (week.getWeekNUM() != rs.getInt("WeekNum")) {
                    return null;
                }

                String sqlDays = "SELECT * FROM Days WHERE WeekNum = ? AND BranchAddress = ?";
                pstmtWeek = connection.prepareStatement(sqlDays);
                pstmtWeek.setInt(1, week.getWeekNUM());
                pstmtWeek.setString(2, keys[1]);
                rs = pstmtWeek.executeQuery();

                while (rs.next()) {
                    LocalDate date = LocalDate.parse(rs.getString("Date"));
                    if (rs.getInt("IsDayOf") == 1) {
                        week.getDayOfWeek(date).setIsdayofrest(true);
                    } else {
                        String sqlShifts = "SELECT * FROM Shift WHERE ShiftDate = ? AND BranchAddress = ?";
                        pstmtWeek = connection.prepareStatement(sqlShifts);
                        pstmtWeek.setString(1, date.toString());
                        pstmtWeek.setString(2, keys[1]);
                        ResultSet rsShifts = pstmtWeek.executeQuery();

                        while (rsShifts.next()) {
                            Shift shift;
                            if (rsShifts.getString("ShiftType").equals("MORNING")) {
                                shift = week.getDayOfWeek(date).getShiftsInDay()[0];
                            } else {
                                shift = week.getDayOfWeek(date).getShiftsInDay()[1];
                            }

                            shift.setStart_time(LocalTime.parse(rsShifts.getString("StartTime")));
                            shift.setEnd_time(LocalTime.parse(rsShifts.getString("EndTime")));

                            Job job = JobsTDao.getInstance().select(rsShifts.getString("Job"));
                            shift.ChangingTheNumberOfemployeesPerJobInShift(job, rsShifts.getInt("NumEmployeesForjob"));

                            String sqlEmployees = "SELECT * FROM ShiftEmployees WHERE ShiftDate = ? AND ShiftType = ? AND EmployeeJob = ?";
                            PreparedStatement pstmtEmployees = connection.prepareStatement(sqlEmployees);
                            pstmtEmployees.setString(1, date.toString());
                            pstmtEmployees.setString(2, rsShifts.getString("ShiftType"));
                            pstmtEmployees.setString(3, job.getJobName());
                            ResultSet rsEmployees = pstmtEmployees.executeQuery();

                            while (rsEmployees.next()) {
                                Employee employee = EmployeeTDao.getInstance().select(rsEmployees.getString("EID"));
                                shift.addEmployeeToShift(employee, job);
                            }
                            rsEmployees.close();
                            pstmtEmployees.close();
                        }
                        rsShifts.close();
                    }
                }
            }
            return week;

        } catch (Exception e) {
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
            pstmt.setString(1, keys[1]);
            pstmt.setString(2, keys[0]);
            rs = pstmt.executeQuery();
            ResultSet rs1 = rs;
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new IllegalArgumentException("SQL Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
