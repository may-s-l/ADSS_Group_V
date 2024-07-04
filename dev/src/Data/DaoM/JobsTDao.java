package dev.src.Data.DaoM;


import dev.src.Data.DBConnection;
import dev.src.Domain.Job;
import dev.src.Domain.ManagementJob;
import dev.src.Domain.Repository.JobRep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobsTDao implements IDao<Job,String>{


    private static JobsTDao instance;
    private DBConnection db;

    private JobsTDao() {
        db = DBConnection.getInstance();
    }

    public static JobsTDao getInstance() {
        if (instance == null) {
            instance = new JobsTDao();
        }
        return instance;
    }

    @Override
    public void insert(Job obj) {
        String sql = "INSERT INTO Jobs (Name) VALUES (?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, obj.getJobName());
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (db.getConnection() != null) {
                    db.getConnection().setAutoCommit(true);
                    db.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public Job select(String id) {
        String sql = "SELECT * FROM Jobs WHERE Name = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return load(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (db.getConnection() != null) {
                    db.getConnection().setAutoCommit(true);
                    db.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void update(Job obj) {
        delete(obj.getJobName());
        insert(obj);
    }

    @Override
    public void delete(String id) {
        String sql = "DELETE FROM Jobs WHERE Name = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (db.getConnection() != null) {
                    db.getConnection().setAutoCommit(true);
                    db.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Job load(ResultSet rs) throws SQLException {
        String name = rs.getString("Name");
        return new Job(name);
    }

    private ManagementJob loadMJob(ResultSet rs) throws SQLException {
        String name = rs.getString("Name");
        return new ManagementJob(name);
    }

    public ArrayList<Job> selectAllJobs() {
        String sql = "SELECT * FROM Jobs";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            ArrayList<Job> jobs=new ArrayList<Job>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1).equals("HR-MANAGER")){
                    jobs.add(loadMJob(rs));
                } else {
                    jobs.add(load(rs));
                }
            }
            return jobs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (db.getConnection() != null) {
                    db.getConnection().setAutoCommit(true);
                    db.getConnection().close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}