package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Data.DAO.*;
import dev.src.Domain.Job;
import dev.src.Domain.Repository.Jobrep;
import dev.src.Domain.TermsOfEmployment;


import java.sql.*;
import java.util.List;


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
        String sql1 ="INSERT INTO EmployeeJobs VALUES (?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt =DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,obj.getID());
            pstmt.setInt(2,obj.getEmployeeNum());
            pstmt.setString(3,obj.getName());
            pstmt.setString(4,obj.getBank_account());
            pstmt.setString(5,obj.getBranch().getBranchAddress());
            pstmt.execute();
            pstmt =DB.getConnection().prepareStatement(sql1);
            pstmt.setString(1,obj.getID());


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


//(String name, String ID, String bank_account, Branch branch, TermsOfEmployment terms,int employeeNum
    private Employee load(ResultSet rs) throws SQLException {
        String EmpID = rs.getString(1);
        int EmpNUM = rs.getInt(2);
        String NAME = rs.getString(3);
        String Bank_account = rs.getString(4);
        Branch Branch_Address = BranchDao.getInstance().select(rs.getString(5));
        TermsOfEmployment terms = TermsOfEmploymentDao.getInstance().select(EmpID);
        EJobRep JR = getAlljobsforemployee(EmpID);
        return new Employee(NAME,EmpID,Bank_account,Branch_Address,terms,EmpNUM,JR);
    }

//?//
    public EJobRep getAlljobsforemployee(String EID) {
        EJobRep EJobRep = new EJobRep();
        String sql ="SELECT * FROM EmployeeJobs WHERE  EID=? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,EID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                jobrep.add(JobDao.getInstance().select(rs.getString(1)));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
        return jobrep;
    }
}
