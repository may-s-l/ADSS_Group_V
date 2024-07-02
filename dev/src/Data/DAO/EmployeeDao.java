package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Data.DAO.*;


import java.sql.*;


public class EmployeeDao implements IDao<Employee,String> {

    DBConnection DB;
    private static EmployeeDao instance;


    public static EmployeeDao getInstance() {
        if (instance == null) {
            instance = new EmployeeDao();
        }
        return instance;
    }

    public EmployeeDao() {
        DB=DBConnection.getInstance();
    }

    @Override
    public void insert(Employee obj) {
        String sql ="INSERT INTO Employee VALUES (?,?,?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt =DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,obj.getID());
            pstmt.setInt(2,obj.getEmployeeNum());
            pstmt.setString(3,obj.getName());
            pstmt.setString(4,obj.getBank_account());
            pstmt.setString(5,obj.getBranch().getBranchAddress());
            pstmt.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }

    }

    @Override
    public Employee select(String s) {
        String sql ="SELECT * FROM EmployeeConstraints WHERE  EID=? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Employee employee =null;
        try {
            pstmt=DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,s);
            rs= pstmt.executeQuery();
            if (rs.next()) {
                employee = load(rs);
                return employee;
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void update(Employee obj) {
        delete(obj.getID());
        insert(obj);

    }

    @Override
    public void delete(String s) {
        String sql ="DELETE FROM Employee WHERE EID=?";
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

    private Employee load(ResultSet rs) throws SQLException {
        String EmpID = rs.getString(1);
        int EmpNUM = rs.getInt(2);
        String NAME = rs.getString(3);
        String Bank_account = rs.getString(4);
        Branch Branch_Address = BranchDao.getInstance().select(rs.getString(5));
        return new Employee(NAME,EmpID,EmpNUM,Bank_account,Branch_Address);
    }
}
