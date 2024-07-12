package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.Constraint;
import dev.src.Domain.Employee;
import dev.src.Domain.Enums.ShiftType;
import dev.src.Domain.Job;
import dev.src.Domain.ManagementJob;
import dev.src.Domain.Repository.ConstraintRep;
import dev.src.Domain.Repository.EJobsRep;

import java.sql.*;
import java.time.LocalDate;

public class EmployeeConstraintTDao implements IDao<Constraint,String> {

    private static EmployeeConstraintTDao instance;
    private DBConnection DB;
    private EmployeeTDao employeeTDao;

    private EmployeeConstraintTDao() {
        DB = DBConnection.getInstance();
        employeeTDao = EmployeeTDao.getInstance();
    }

    public static EmployeeConstraintTDao getInstance() {
        if (instance == null) {
            instance = new EmployeeConstraintTDao();
        }
        return instance;
    }



    @Override
    public void insert(Constraint obj) {
        String sql ="INSERT INTO EmployeeConstraints VALUES (?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt =DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,obj.getEmp().getID());
            pstmt.setString(2,obj.getShiftDate().toString());
            pstmt.setString(3,obj.getShiftType().toString().toUpperCase());
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
    public Constraint select(String KEY) {
        String[] KEYS=KEY.split(",");
        String sql ="SELECT * FROM EmployeeConstraints WHERE EID=? AND ConstraintDate=? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Constraint constraint =null;
        try {
            pstmt=DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,KEYS[0]);
            pstmt.setString(2,KEYS[1]);
            rs= pstmt.executeQuery();
            ResultSet rs1 = rs;
            if (rs.next()) {
                constraint = load(rs1);
                return constraint;
            } else {
                return null;
            }
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
    public void update(Constraint obj) {
        String sql = "UPDATE EmployeeConstraints SET ConstraintShiftType = ? WHERE EID = ? AND ConstraintDate = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setString(1, obj.getShiftType().toString().toUpperCase());
            pstmt.setString(2, obj.getEmp().getID());
            pstmt.setString(3, obj.getShiftDate().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
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
    public void delete(String KEY) {
        String[] KEYS=KEY.split(",");
        String sql ="DELETE FROM EmployeeConstraints WHERE EID=? AND ConstraintDate=?";
        PreparedStatement pstmt = null;
        try {
            pstmt=DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,KEYS[0]);
            pstmt.setString(2,KEYS[1]);
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

    private Constraint load(ResultSet rs) throws SQLException {
    //    Employee Emp = EmployeeTDao.getInstance().select(rs.getString(1));
        LocalDate ShiftDate = LocalDate.parse(rs.getString(2));
        ShiftType shiftType = ShiftType.valueOf(rs.getString(3));
        return new Constraint(ShiftDate,shiftType);
    }



    public ConstraintRep selectALLFEUTREconstrain(String EID){
        Employee E = employeeTDao.select(EID);
        String sql ="SELECT * FROM EmployeeConstraints WHERE EID=? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ConstraintRep constraints =new ConstraintRep();
        LocalDate today = LocalDate.now();
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, EID);
            rs = ps.executeQuery();
            while (rs.next()) {
                LocalDate date = LocalDate.parse(rs.getString(2));
                if(date.isAfter(today)) {
                    Constraint constraint = load(rs);
                    constraint.setEmp(E);
                    constraints.add(constraint);
                }
            }
            return constraints;
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
                if (ps != null) {
                    ps.close();
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
