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
        String sql = "INSERT INTO Branch VALUES(?,?,?,?)";
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
            ps.execute();
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Insertion failed");
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps !=null){
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
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
//                if (rs.getString("Manager")!=null) {
//                    ManagerEmployee Memp = (ManagerEmployee) employeeTDao.select(rs.getString("Manager"));
//                    branch=new Branch(rs.getString("Name"),rs.getString("Address"),Memp,rs.getInt(2));
//                    return branch;
//                } else {
                    branch=new Branch(rs.getString("Name"),rs.getString("Address"),rs.getInt(2));
                    return branch;
//                }
            }
            return branch;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Selection failed");
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps !=null){
                    ps.close();
                }
                if (rs !=null){
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void update(Branch obj) {
        String sql = "UPDATE Branch SET BranchNum = ?, Name = ?, Manager = ? WHERE Address = ?";
        PreparedStatement ps = null;
        Connection conn = null;
        try {
            conn = DB.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, obj.getBranchNum());
            ps.setString(2, obj.getBranchName());
            if (obj.getManagerEmployee() != null) {
                ps.setString(3, obj.getManagerEmployee().getID());
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR);
            }
            ps.setString(4, obj.getBranchAddress());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Update failed", e);
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps !=null){
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    @Override
    public void delete(String s) {
        String sql = "DELETE FROM Branch WHERE Address = ?";
        PreparedStatement ps = null;
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1, s);
            ps.execute();

        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Deletion failed");
        }
        finally {
            try {
                if (DB.getConnection() != null) {
                    DB.getConnection().setAutoCommit(true);
                    DB.getConnection().close();
                }
                if (ps !=null){
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
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
        Branch branch = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DB.getConnection().prepareStatement(sql);
            rs = ps.executeQuery();
            MyMap<String, Branch> branchMyMap = new MyMap<String, Branch>();
            while (rs.next()) {
                branch = load(rs);
                branchMyMap.put(branch.getBranchAddress(), branch);
            }
            return branchMyMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
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

    public void branchHaveManager(Branch branch){
        String sql = "SELECT * FROM Branch WHERE Address = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = DB.getConnection().prepareStatement(sql);
            ps.setString(1, branch.getBranchAddress());
            rs = ps.executeQuery();
            rs.next();
            if (rs.getString("Manager") != null) {
                ManagerEmployee Memp = (ManagerEmployee) employeeTDao.select(rs.getString("Manager"));
                branch.setManagerEmployee(Memp);

            }

        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Selection failed");
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