package dev.src.Data.DaoM;


import dev.src.Data.DBConnection;
import dev.src.Domain.*;
import dev.src.Domain.Repository.EmployeeRep;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeTDao implements IDao<Employee,String> {

    private static EmployeeTDao instance;
    private DBConnection DB;
    private EmployeeJobsTDao employeeJobsTDao;
    private EmployeeTermsTDao  employeeTermsTDao;

    private EmployeeTDao() {
        DB = DBConnection.getInstance();
        employeeJobsTDao = EmployeeJobsTDao.getInstance();
        employeeTermsTDao = EmployeeTermsTDao.getInstance();
    }

    public static EmployeeTDao getInstance() {
        if (instance == null) {
            instance = new EmployeeTDao();
        }
        return instance;
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
            employeeTermsTDao.insert(obj.getTerms());
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
    public Employee select(String s) {
        String sql ="SELECT * FROM Employee WHERE  EID=? ";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Employee employee =null;
        try {
            pstmt=DB.getConnection().prepareStatement(sql);
            pstmt.setString(1,s);
            rs= pstmt.executeQuery();
            ResultSet rs1 = rs;
            if(rs.next()) {
                if (EmployeeJobsTDao.getInstance().selectAllJobs(s).find("HR-MANAGER")!=null) {
                    employee=loadM(rs1);
                }
                else {
                    employee = load(rs1);
                }
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

        return employee;
    }

    @Override
    public void update(Employee obj) {
        String sql = "UPDATE Employee SET EmpNUM = ?, Name = ?, BankAccountNumber = ?, BranchID = ? WHERE EID = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = DB.getConnection().prepareStatement(sql);
            pstmt.setInt(1, obj.getEmployeeNum());
            pstmt.setString(2, obj.getName());
            pstmt.setString(3, obj.getBank_account());
            pstmt.setString(4, obj.getBranch().getBranchAddress());
            pstmt.setString(5, obj.getID());
            pstmt.executeUpdate();

            // Update terms of employment
            EmployeeTermsTDao.getInstance().update(obj.getTerms());

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


    private Employee load(ResultSet rs) throws SQLException {
        String EmpID = rs.getString(1);
        int EmpNUM = rs.getInt(2);
        String NAME = rs.getString(3);
        String Bank_account = rs.getString(4);
        Branch Branch_Address = BranchTDao.getInstance().select(rs.getString(5));
        TermsOfEmployment terms = employeeTermsTDao.select(rs.getString(1));
        return new Employee(NAME,EmpID,Bank_account,Branch_Address,EmpNUM,terms);
    }

    private ManagerEmployee loadM(ResultSet rs) throws SQLException {
        String EmpID = rs.getString(1);
        int EmpNUM = rs.getInt(2);
        String NAME = rs.getString(3);
        String Bank_account = rs.getString(4);
        Branch Branch_Address = BranchTDao.getInstance().select(rs.getString(5));
        TermsOfEmployment terms = employeeTermsTDao.select(rs.getString(1));
        return new ManagerEmployee(NAME,EmpID,Bank_account,Branch_Address,EmpNUM,terms);
    }

//    public EJobsRep getAlljobsforemployee(String EID) {
//        EJobsRep EJobRep = new EJobsRep();
//        String sql ="SELECT * FROM EmployeeJobs WHERE  EID=? ";
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        try {
//            pstmt = DB.getConnection().prepareStatement(sql);
//            pstmt.setString(1,EID);
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                EJobRep.add(JobDao.getInstance().select(rs.getString(1)));
//            }
//        }
//        catch (SQLException e) {
//            throw new RuntimeException();
//        }
//        return EJobRep;1
//    }
    public EmployeeRep getALLEmpActiveByBranch(String A,EmployeeRep EPR){

        String sql = "SELECT * FROM Employee WHERE BranchID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        EmployeeRep employeeRep =EPR;
        if (employeeRep==null) {
            employeeRep= new EmployeeRep();
        }
        try {
            ps=DB.getConnection().prepareStatement(sql);
            ps.setString(1,A);
            rs = ps.executeQuery();
            while (rs.next()) {
                if(employeeRep.find(rs.getString("ID"))==null){
                    Employee emp = select(rs.getString("ID"));
                    if(emp.getTerms().getEnd_date()==null){
                        employeeRep.add(emp);
                    }
                }
            }
            if(EPR==null){}
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Selection failed");
        }


        return employeeRep;
    }





}
