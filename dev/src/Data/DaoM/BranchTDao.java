package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.ManagerEmployee;
import dev.src.Domain.Repository.EmployeeRep;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

public class BranchTDao implements IDao<Branch,String>{

    private static BranchTDao instance;
    private DBConnection DB;
    private EmployeeTDao employeeTDao;

    private BranchTDao() {
        DB = DBConnection.getInstance();
        employeeTDao = EmployeeTDao.getInstance();
    }

    public static BranchTDao getInstance() {
        if (instance == null) {
            instance = new BranchTDao();
        }
        return instance;
    }


    @Override
    public void insert(Branch obj) {
        String sql = "insert into branch_t values(?,?,?)";
        PreparedStatement ps = null;
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1, obj.getBranchAddress());
            ps.setString(2, obj.getBranchName());
            ps.setString(3, obj.getManagerEmployee().getID());
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Insertion failed");
        }
    }

    @Override
    public Branch select(String s) {
        String sql = "select * from Branch where branchAddress = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Branch obj = null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, s);
            rs = ps.executeQuery();
            if (rs.next()) {
                ManagerEmployee Memp =(ManagerEmployee)employeeTDao.select(rs.getString("Manager"));
                Branch branch=new Branch(rs.getString("Name"),rs.getString("Address"),Memp);
                return branch;
            }
            return null;        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Selection failed");
        }
    }

    @Override
    public void update(Branch obj) {
        try {
            delete(obj.getBranchAddress());
            insert(obj);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Update failed");
        }
    }

    @Override
    public void delete(String s) {
        String sql = "delete from Branch where branchAddress = ?";
        PreparedStatement ps = null;
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1, s);
            ps.executeUpdate();

        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Deletion failed");
        }
    }

    private void getALLEmpByBranch(Branch branch){
        String sql = "select * from Employee where BranchID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1,branch.getBranchAddress());
            rs = ps.executeQuery();
            while (rs.next()) {
                if(branch.getEmployeesInBranch().find(rs.getString("ID"))==null){
                    Employee emp = employeeTDao.select.(rs.getString("ID"));
                    branch.getEmployeesInBranch().add(emp);
                }
            }
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Selection failed");
        }

    }
}
