package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;
import dev.src.Domain.Enums.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class ConstraintDao implements IDao<Constraint,String> {


    DBConnection DB;
    private static ConstraintDao instance;



    public static ConstraintDao getInstance() {
        if (instance == null) {
            instance = new ConstraintDao();
        }
        return instance;
    }

    public ConstraintDao() {
        DB=DBConnection.getInstance();
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
    }

    @Override
    public Constraint select(String KEY) {
        String[] KEYS=KEY.split(",");
        String sql ="SELECT * FROM EmployeeConstraints WHERE  EID=? AND ShiftDate=? ";
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

    }


    @Override
    public void update(Constraint obj) {
        String keys = obj.getShiftDate().toString()+","+obj.getShiftType().toString().toUpperCase();
        delete(keys);
        insert(obj);
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
    }

    private Constraint load(ResultSet rs) throws SQLException {
        Employee Emp = EmployeeDao.getInstance().select(rs.getString(1));
        LocalDate ShiftDate = LocalDate.parse(rs.getString(2));
        ShiftType shiftType = ShiftType.valueOf(rs.getString(3));
        return new Constraint(Emp,ShiftDate,shiftType);
    }
}
