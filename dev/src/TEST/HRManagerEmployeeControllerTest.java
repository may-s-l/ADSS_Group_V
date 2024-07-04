package dev.src.TEST;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;
import dev.src.Controllers.HRManagerEmployeeController;
import dev.src.Domain.Enums.ShiftType;
import dev.src.Domain.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class HRManagerEmployeeControllerTest {

    private HRManagerEmployeeController controller;
    private JobRep jobRep;
    private BranchRep branchRep;
    private EmployeeRep employeeRep;

    @BeforeEach
    public void setUp() {
        jobRep = new JobRep();
        branchRep = new BranchRep();
        employeeRep = new EmployeeRep();
        controller = new HRManagerEmployeeController(jobRep, branchRep, employeeRep);
    }

    private Employee createEmployee(String id, Job job, Branch branch) {
        TermsOfEmployment terms = new TermsOfEmployment(23, LocalDate.now(), 5000.0, "FULL", "GLOBAL");
        Employee employee = new Employee("John Doe", id, "12345678", branch, terms, job);
        terms.setEmp(employee);
        return employee;
    }

    @Test
    public void testCreateEmployee_Success() {
        String id = "852258";
        String jobName = "ENGINEER";
        String branchName = "Main Branch";

        jobRep.add(new Job(jobName));
        branchRep.add(new Branch(branchName, "123 Main St"));

        String result = controller.createEmployee("John Doe", "852258", "12345678", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        assertEquals("Employee successfully created", result);
        assertNotNull(employeeRep.find(id));
        deleteEmployeeAndConstraints(id);
    }

    @Test
    public void testCreateEmployee_EmployeeAlreadyExists() {
        String id = "111111";
        String jobName = "ENGINEER";
        String branchName = "Main Branch";

        jobRep.add(new Job(jobName));
        branchRep.add(new Branch(branchName, "123 Main St"));
        employeeRep.add(createEmployee(id, new Job(jobName), new Branch(branchName, "123 Main St")));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEmployee("John Doe", id, "111111", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        });

        assertEquals("Employee is already exist", exception.getMessage());
        assertNotNull(employeeRep.find(id));
        deleteEmployeeAndConstraints(id);
    }

    @Test
    public void testCreateEmployee_InvalidName() {
        String id = "222222";
        String jobName = "ENGINEER";
        String branchName = "Main Branch";

        jobRep.add(new Job(jobName));
        branchRep.add(new Branch(branchName, "123 Main St"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEmployee("123", id, "12345678", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        });

        assertEquals("Name contain only alphabetic characters", exception.getMessage());
        assertNull(employeeRep.find(id));
    }

    @Test
    public void testCreateEmployee_InvalidID() {
        String id = "12345A";
        String jobName = "ENGINEER";
        String branchName = "Main Branch";

        jobRep.add(new Job(jobName));
        branchRep.add(new Branch(branchName, "123 Main St"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEmployee("John Doe", id, "12345678", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        });

        assertEquals("ID must be 6 numeric characters long", exception.getMessage());
        assertNull(employeeRep.find(id));
    }

    @Test
    public void testCreateEmployee_InvalidBankAccount() {
        String id = "333333";
        String jobName = "ENGINEER";
        String branchName = "Main Branch";

        jobRep.add(new Job(jobName));
        branchRep.add(new Branch(branchName, "123 Main St"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEmployee("John Doe", id, "1234567A", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        });

        assertEquals("ID must be 8 numeric characters long", exception.getMessage());
        assertNull(employeeRep.find(id));
    }

    @Test
    public void testCreateEmployee_InvalidJob() {
        String id = "444444";
        String jobName = "UNKNOWN_JOB";
        String branchName = "Main Branch";

        branchRep.add(new Branch(branchName, "123 Main St"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEmployee("John Doe", id, "12345678", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        });

        assertEquals("Job does not exist", exception.getMessage());
        assertNull(employeeRep.find(id));
    }

    @Test
    public void testCreateEmployee_InvalidBranch() {
        String id = "555555";
        String jobName = "ENGINEER";
        String branchName = "UNKNOWN_BRANCH";

        jobRep.add(new Job(jobName));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.createEmployee("John Doe", id, "12345678", branchName, 15, LocalDate.now().toString(), 5000.0, "FULL", "GLOBAL", jobName);
        });

        assertEquals("Branch does not exist", exception.getMessage());
        assertNull(employeeRep.find(id));
    }

    @Test
    public void testUpdateEmployeeNAME_Success() {
        String id = "666666";
        Employee employee = createEmployee(id, new Job("ENGINEER"), new Branch("Main Branch", "123 Main St"));
        employeeRep.add(employee);

        String result = controller.updateEmployeeNAME(id, "Jane Doe");
        assertEquals("The employee name has been successfully changed", result);
        assertEquals("Jane Doe", employee.getName());
        deleteEmployeeAndConstraints(id);
    }

    @Test
    public void testUpdateEmployeeNAME_EmployeeNotFound() {
        String id = "777777";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.updateEmployeeNAME(id, "Jane Doe");
        });

        assertEquals("Employee is NOT exist", exception.getMessage());
    }

    @Test
    public void testUpdateEmployeeENDINGDATE_Success() {
        String id = "888888";
        Employee employee = createEmployee(id, new Job("ENGINEER"), new Branch("Main Branch", "123 Main St"));
        employeeRep.add(employee);

        String result = controller.updateEmployeeENDINGDATE(id, LocalDate.now().plusDays(1).toString());
        assertEquals("departure date has been successfully updated for this employee - " + id, result);
        assertEquals(LocalDate.now().plusDays(1), employee.getTerms().getEnd_date());
        deleteEmployeeAndConstraints(id);
    }

    @Test
    public void testUpdateEmployeeENDINGDATE_EmployeeNotFound() {
        String id = "999999";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.updateEmployeeENDINGDATE(id, LocalDate.now().plusDays(1).toString());
        });

        assertEquals("Employee is NOT exist", exception.getMessage());
    }

    @Test
    public void testUpdateEmployeeSALERY_Success() {
        String id = "123654";
        Employee employee = createEmployee(id, new Job("ENGINEER"), new Branch("Main Branch", "123 Main St"));
        employeeRep.add(employee);

        String result = controller.updateEmployeeSALERY(id, 6000.0);
        assertEquals("Salary is successfully updated for employee - " + id, result);
        assertEquals(6000.0, employee.getTerms().getSalary());
        deleteEmployeeAndConstraints(id);
    }

    @Test
    public void testUpdateEmployeeSALERY_EmployeeNotFound() {
        String id = "987456";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.updateEmployeeSALERY(id, 6000.0);
        });

        assertEquals("Employee is NOT exist", exception.getMessage());
    }

    public void deleteEmployeeAndConstraints(String employeeId) {
        String sqlDeleteConstraints = "DELETE FROM EmployeeConstraints WHERE EID = ?";
        String sqlDeleteEmployee = "DELETE FROM Employee WHERE EID = ?";

        try (Connection connection = DBConnection.getInstance().getConnection()) {
            try (PreparedStatement pstmtConstraints = connection.prepareStatement(sqlDeleteConstraints);
                 PreparedStatement pstmtEmployee = connection.prepareStatement(sqlDeleteEmployee)) {
                connection.setAutoCommit(false);

                pstmtConstraints.setString(1, employeeId);
                pstmtConstraints.executeUpdate();

                pstmtEmployee.setString(1, employeeId);
                pstmtEmployee.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Failed to delete employee and constraints", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete employee and constraints", e);
        }
    }
}

