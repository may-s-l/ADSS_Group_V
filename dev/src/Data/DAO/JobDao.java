package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.Job;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobDao implements IDao<Job,String>{



    private static JobDao instance;
    private DBConnection db;

    private JobDao() {
        db = DBConnection.getInstance();
    }

    public static JobDao getInstance() {
        if (instance == null) {
            instance = new JobDao();
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
    }

    private Job load(ResultSet rs) throws SQLException {
        String name = rs.getString("Name");
        return new Job(name);
    }
}

