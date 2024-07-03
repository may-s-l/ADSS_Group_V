package dev.src.Data.DaoM;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;
import dev.src.Domain.Repository.EmployeeRep;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "INSERT INTO branch VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1, obj.getBranchAddress());
            ps.setInt(2, obj.getBranchNum());
            ps.setString(3, obj.getBranchName());
            if (obj.getManagerEmployee()!=null) {
                ps.setString(4, obj.getManagerEmployee().getID());
            } else {
                ps.setNull(4, java.sql.Types.VARCHAR);
            }
            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Insertion failed");
        }
    }

    @Override
    public Branch select(String s) {
        String sql = "SELECT * FROM Branch WHERE Address = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Branch obj = null;
        Branch branch=null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, s);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString("Manager")!=null) {
                    ManagerEmployee Memp = (ManagerEmployee) employeeTDao.select(rs.getString("Manager"));
                    branch=new Branch(rs.getString("Name"),rs.getString("Address"),Memp,rs.getInt(2));
                    return branch;
                } else {
                    branch=new Branch(rs.getString("Name"),rs.getString("Address"),rs.getInt(2));
                    return branch;
                }
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
        String sql = "DELETE FROM Branch WHERE branchAddress = ?";
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

    private Branch load(ResultSet rs) throws SQLException {
        Branch branch=null;
        if (rs.getString("Manager")!=null) {
            ManagerEmployee Memp = (ManagerEmployee) employeeTDao.select(rs.getString("Manager"));
            branch=new Branch(rs.getString("Name"),rs.getString("Address"),Memp,rs.getInt(2));
            return branch;
        } else {
            branch=new Branch(rs.getString("Name"),rs.getString("Address"),rs.getInt(2));
            return branch;
        }
    }

    public MyMap<String, Branch> selectAllBranchs() {
        String sql = "SELECT * FROM Branch";
        Branch branch=null;
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            MyMap<String, Branch> branchMyMap=new MyMap<String, Branch>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                branch=load((rs));
                branchMyMap.put(branch.getBranchAddress(),branch);
            }
            return branchMyMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Branch getBRANCHbyNum(int num) {
        String sql = "SELECT * FROM Branch WHERE BranchNum = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Branch branch=null;
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setInt(1, num);
            rs = ps.executeQuery();
            if (rs.next()) {
                branch=load(rs);
            }
            return branch;

        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Get Branch failed");
        }
    }




}