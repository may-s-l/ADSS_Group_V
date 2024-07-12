package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.TermsOfEmployment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeTermsTDao implements IDao<TermsOfEmployment,String>{

    DBConnection DB;
    private static EmployeeTermsTDao instance;


    public static EmployeeTermsTDao getInstance() {
        if (instance == null) {
            instance = new EmployeeTermsTDao();
        }
        return instance;
    }

    public EmployeeTermsTDao() {
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
            if(obj.getEnd_date()==null){
                pstmt.setNull(4,java.sql.Types.DATE);
            }
            else {
                pstmt.setString(4,obj.getEnd_date().toString());
            }
            pstmt.setDouble(5,obj.getSalary());
            pstmt.setString(6,obj.getJt().toString());
            pstmt.setString(7,obj.getSt().toString());
            pstmt.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
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
        finally {
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

    @Override
    public void update(TermsOfEmployment obj){
        String sql = "UPDATE EmployeesTerm SET vacationDay=?, Start_date=?, End_date=?, Salary=?, JobType=?, SalaryType=? WHERE EID=?";
        PreparedStatement pstmt = null;
        try {
            pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setDouble(1, obj.getVacationDay());
            pstmt.setString(2, obj.getStart_date().toString());
            if (obj.getEnd_date() == null) {
                pstmt.setNull(3, java.sql.Types.DATE);
            } else {
                pstmt.setString(3, obj.getEnd_date().toString());
            }
            pstmt.setDouble(4, obj.getSalary());
            pstmt.setString(5, obj.getJt().toString());
            pstmt.setString(6, obj.getSt().toString());
            pstmt.setString(7, obj.getEmp().getID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating terms of employment for employee ID: " + obj.getEmp().getID(), e);
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
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
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
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
        if (end==null){
            terms = new TermsOfEmployment(VacationDay, start_date, salary, jt, st);
        }
        else {
            LocalDate end_date = LocalDate.parse(end);
            terms = new TermsOfEmployment(VacationDay, start_date,end_date, salary, jt, st);
        }
        return terms;
    }
}


