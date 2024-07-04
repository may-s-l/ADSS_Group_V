package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.Job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeJobsTDao implements IDao<String,String> {


    private static EmployeeJobsTDao instance;
    private DBConnection DB;
    private JobsTDao jobsTDao;

    private EmployeeJobsTDao() {
        DB = DBConnection.getInstance();
        jobsTDao = JobsTDao.getInstance();
    }

    public static EmployeeJobsTDao getInstance() {
        if (instance == null) {
            instance = new EmployeeJobsTDao();
        }
        return instance;
    }

    @Override
    public void insert(String obj) {
        String[] keys = obj.split(",");
        String sql = "INSERT INTO EmployeeJobs VALUES (?,?)";
        PreparedStatement ps = null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, keys[1]);
            ps.setString(2, keys[0]);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Insertion failed");

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
    public String select(String s) {
        String[] keys = s.split(",");
        String sql = "SELECT * FROM EmployeeJobs WHERE EID =? AND Job =?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, keys[0]);
            ps.setString(2, keys[1]);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(2);
            }
            return null;

        }
        catch (Exception e) {
            throw new IllegalArgumentException("Select failed");
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
    public void update(String obj) {

    }

    @Override
    public void delete(String s) {

    }


}
