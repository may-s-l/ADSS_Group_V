package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.Constraint;
import dev.src.Domain.Employee;
import dev.src.Domain.Enums.JobType;
import dev.src.Domain.Enums.SalaryType;
import dev.src.Domain.Enums.ShiftType;
import dev.src.Domain.TermsOfEmployment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TermsOfEmploymentDao implements IDao<TermsOfEmployment,String> {

    DBConnection DB;
    private static TermsOfEmploymentDao instance;



    public static TermsOfEmploymentDao getInstance() {
        if (instance == null) {
            instance = new TermsOfEmploymentDao();
        }
        return instance;
    }

    public TermsOfEmploymentDao() {
        DB=DBConnection.getInstance();
    }


    @Override
    public void insert(TermsOfEmployment obj) {
        String sql ="INSERT INTO EmployeesTerm VALUES (?,?,?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt =DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,obj.getEmp().getID());
            pstmt.setDouble(2,obj.getVacationDay());
            pstmt.setString(3,obj.getStart_date().toString());
            pstmt.setString(4,obj.getEnd_date().toString());
            pstmt.setDouble(5,obj.getSalary());
            pstmt.setString(6,obj.getJt().toString());
            pstmt.setString(7,obj.getSt().toString());
            pstmt.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }



    @Override
    public TermsOfEmployment select(String s) {
        String sql ="SELECT * FROM EmployeesTerm WHERE  EID=?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TermsOfEmployment Terms =null;
        try {
            pstmt =DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,s);
            rs= pstmt.executeQuery();
            rs.next();
            Terms=load(rs);
            return Terms;
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(TermsOfEmployment obj) {
        this.delete(obj.getEmp().getID());
        this.insert(obj);

    }

    @Override
    public void delete(String s) {
        String sql ="DELETE FROM EmployeesTerm WHERE EID=?";
        PreparedStatement pstmt = null;
        try {
            pstmt=DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,s);
            pstmt.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }


    }

    private TermsOfEmployment load(ResultSet rs) throws SQLException {
        // Employee Emp = EmployeeDao.getInstance().select(rs.getString(1));
        TermsOfEmployment terms;
        Double VacationDay = rs.getDouble(2);
        LocalDate start_date = LocalDate.parse(rs.getString(3));
        String end = rs.getString(4);
        Double salary = rs.getDouble(5);
        String jt = rs.getString(6);
        String st = rs.getString(7);
        if (end.isEmpty()){
            terms = new TermsOfEmployment(VacationDay, start_date, salary, jt, st);
        }
        else {
            LocalDate end_date = LocalDate.parse(end);
            terms = new TermsOfEmployment(VacationDay, start_date,end_date, salary, jt, st);
        }
        return terms;
    }
}
