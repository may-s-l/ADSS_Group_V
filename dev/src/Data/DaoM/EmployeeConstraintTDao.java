package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.Constraint;
import dev.src.Domain.Enums.ShiftType;

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
            if (rs.next()) {
                constraint = load(rs);
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
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }


    @Override
    public void update(Constraint obj) {
        String sql = "UPDATE EmployeeConstraints SET ConstraintShiftType = ? WHERE EID = ? AND ShiftDate = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setString(1, obj.getShiftType().toString().toUpperCase());
            pstmt.setString(2, obj.getEmp().getID());
            pstmt.setString(3, obj.getShiftDate().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Update failed", e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
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
    public void delete(String KEY) {
        String[] KEYS=KEY.split(",");
        String sql ="DELETE FROM users WHERE EID=? AND ShiftDate=?";
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
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Constraint load(ResultSet rs) throws SQLException {
        //Employee Emp = EmployeeTDao.getInstance().select(rs.getString(1));
        LocalDate ShiftDate = LocalDate.parse(rs.getString(2));
        ShiftType shiftType = ShiftType.valueOf(rs.getString(3));
        return new Constraint(ShiftDate,shiftType);
    }


}
