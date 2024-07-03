package dev.src.Data.DaoM;


import dev.src.Data.DBConnection;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.Repository.EmployeeRep;
import dev.src.Domain.TermsOfEmployment;


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
        EmployeeTermsTDao.getInstance().update(obj.getTerms());
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
        Branch Branch_Address = BranchTDao.getInstance().select(rs.getString(5));
        TermsOfEmployment terms = employeeTermsTDao.select(rs.getString(1));
        return new Employee(NAME,EmpID,Bank_account,Branch_Address,EmpNUM,terms);
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
//        return EJobRep;
//    }
    public EmployeeRep getALLEmpActiveByBranch(String A,EmployeeRep EPR){

        String sql = "select * from Employee where BranchID = ?";
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
        }
        catch (SQLException e) {
            throw new IllegalArgumentException("Selection failed");
        }

        return employeeRep;
    }





}
