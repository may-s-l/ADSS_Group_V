//package dev.src.TEST;
//import dev.src.Controllers.*;
//import dev.src.Domain.*;
//import dev.src.Domain.Repository.BranchRep;
//import dev.src.Domain.Repository.EmployeeRep;
//import dev.src.Domain.Repository.JobRep;
//import org.junit.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//
//import java.util.*;
////import static org.junit.Assert.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class SystemTest {
//
//    private EmployeeRep employeeDatabase= new EmployeeRep();
//    private JobRep jobDatabase= new JobRep();;
//    private BranchRep branchDatabase=new BranchRep();
//    private HRManagerEmployeeController controller = new HRManagerEmployeeController(jobDatabase, branchDatabase, employeeDatabase);
//    Job J1=new Job("Cashier");
//    Job J2=new Job("Stock");
//    Job J3=new ManagementJob("HRManager");
//    Branch B = new Branch("Main Branch", "123 Main St");
//
//    @BeforeEach
//    public  void set(){
//        this.jobDatabase.add(J1);
//        this.jobDatabase.add(J2);
//        this.jobDatabase.add(J3);
//        this.branchDatabase.put(B.getBranchAddress(),B);
//    }
//
//    @Test
//    public void testCreateEmployeeSuccess() {
//        String result = controller.createEmployee("John Doe", "123456", "12345678", "Main Branch", 14, "2022-01-01", 3000, "FULL", "GLOBAL", "Cashier");
//        assertEquals("Employee successfully created", result);
//        Assertions.assertNotNull(employeeDatabase.get("123456"));
//    }
//
//    @Test
//    public void testCreateEmployeeDuplicateID() {
//        controller.createEmployee("John Doe", "123456", "12345678", "Main Branch", 14, "2022-01-01", 3000, "FULL", "GLOBAL", "Cashier");
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> controller.createEmployee("Jane Doe", "123456", "87654321", "Main Branch", 14, "2022-01-01", 3000, "FULL", "GLOBAL", "Stock")
//                ,"Employee is already exist");
//        assertEquals("Employee is already exist", exception.getMessage());
//    }
//
//    @Test
//    public void testUpdateEmployeeNameSuccess() {
//        controller.createEmployee("John Doe", "123456", "12345678", "Main Branch", 14, "2022-01-01", 3000, "FULL", "GLOBAL", "Cashier");
//        String result = controller.updateEmployeeNAME("123456", "John Smith");
//        assertEquals("The employee name has been successfully changed", result);
//        assertEquals("John Smith", employeeDatabase.get("123456").getName());
//    }
//
//    @Test
//    public void testUpdateEmployeeNameInvalidID() {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> controller.updateEmployeeNAME("999999", "John Smith")
//                ,"Employee is already exist");
//        assertEquals("Employee is NOT exist", exception.getMessage());
//
//    }
//}
