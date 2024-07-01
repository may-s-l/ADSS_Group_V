package dev.src.Data.DAO;

import dev.src.Domain.Job;
import dev.src.repostory.Jobrep;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobDAO implements IDao<Job,String> {
    DBConnection DB;
    Jobrep JR;

    public JobDAO(DBConnection DB, Jobrep JR) {
        this.DB = DB;
        this.JR = JR;
    }


    @Override
    public void insert(Job job) {
        PreparedStatement ps = null;
        String sql = "INSERT INTO Job VALUES(?)";
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, job.getJobName());
            ps.execute();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Job select(String jobname)throws SQLException  {
        String sql = "SELECT * FROM Job WHERE JobName=?";
        Job job = (Job) JR.getJob(jobname);
        if(job != null) {
            return job;
        }
        ResultSet rs=null;
        PreparedStatement ps = null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, jobname);
            rs = ps.executeQuery();
            rs.next();
            job=load(rs);
            JR.addJob(job);
            return job;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void update(Job obj) {

    }

    @Override
    public void delete(String s) {

    }

    @Override
    public Job load(ResultSet rs) throws SQLException {
        Job job = null;
        //
        job=new Job(rs.getString(1));
        return job;
    }
}
