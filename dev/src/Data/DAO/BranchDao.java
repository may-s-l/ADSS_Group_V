package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.Branch;
import dev.src.Domain.ManagerEmployee;
import jdk.internal.icu.text.UCharacterIterator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchDao implements IDao<Branch,String>{

    DBConnection DB;
    private static BranchDao instance;

    public static BranchDao getInstance() {
        if (instance == null) {
            instance = new BranchDao();
        }
        return instance;
    }

    public BranchDao() {
        DB=DBConnection.getInstance();
    }

    @Override
    public void insert(Branch branch) {
        String sql = "INSERT INTO Branch (Address, Name, Manager) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, branch.getBranchAddress());
            pstmt.setString(2, branch.getBranchName());
            pstmt.setString(3, branch.getManagerEmployee() != null ? branch.getManagerEmployee().getID() : null);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Branch select(String address) {
        String sql = "SELECT * FROM Branch WHERE Address = ?";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, address);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return load(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Branch branch) {
        delete(branch.getBranchAddress());
        insert(branch);
    }

    @Override
    public void delete(String address) {
        String sql = "DELETE FROM Branch WHERE Address = ?";
        try (PreparedStatement pstmt = DB.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, address);
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Branch load(ResultSet rs) throws SQLException {
        String name = rs.getString("Name");
        String address = rs.getString("Address");
        String managerId = rs.getString("Manager");

        // Load manager employee
        ManagerEmployee managerEmployee = managerId != null ? loadManager(managerId) : null;

        return new Branch(name, address, managerEmployee);
    }

    private ManagerEmployee loadManager(String managerId) {
        // Load ManagerEmployee from database using managerId
        // This method should be implemented if ManagerEmployee has its own DAO or can be loaded similarly
        // Here we assume a method to fetch ManagerEmployee exists in its own DAO
        return ManagerEmployeeDao.getInstance().select(managerId);
    }
}
