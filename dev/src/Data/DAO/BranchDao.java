package dev.src.Data.DAO;

import dev.src.Data.DBConnection;
import dev.src.Domain.Branch;

import java.sql.PreparedStatement;
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
    public void insert(Branch obj) {
        String sql ="INSERT INTO Branch VALUES (?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt =DB.getConnection().prepareStatement(sql);
//            pstmt.setString(1,obj.getEmp().getID());
//            pstmt.setString(2,obj.getShiftDate().toString());
//            pstmt.setString(3,obj.getShiftType().toString().toUpperCase());
//            pstmt.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException();
        }
    }


    @Override
    public Branch select(String s) {
        return null;
    }

    @Override
    public void update(Branch obj) {

    }

    @Override
    public void delete(String s) {

    }
}
