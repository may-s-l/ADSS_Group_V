package dev.src.TEST;

import dev.src.Data.DBConnection;
import dev.src.Domain.*;
import dev.src.Controllers.*;
import dev.src.Domain.Enums.ShiftType;
import dev.src.Domain.Repository.ConstraintRep;
import dev.src.Domain.Repository.EJobsRep;
import dev.src.Domain.Repository.EmployeeRep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeConstraintControllerTest {

    private EmployeeRep employeeRep;
    private EmployeeConstraintController controller;

    @BeforeEach
    public void setUp() {
        employeeRep = new EmployeeRep();
        controller = new EmployeeConstraintController(employeeRep);
    }

    private Employee createEmployee(String id, Job job, Branch branch) {
        TermsOfEmployment terms = new TermsOfEmployment(23, LocalDate.now(), 5000.0, "FULL", "GLOBAL");
        Employee employee = new Employee("John Doe", id, "12345678", branch, terms, job);
        terms.setEmp(employee);
        return employee;
    }

    @Test
    public void testReturnEmployeeDetails_Success() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("999999", job, branch);
        employeeRep.add(employee);

        String result = controller.returnEmployeeDetails("999999");
        assertNotNull(result);
        assertTrue(result.contains("John Doe"));
        deleteEmployeeAndConstraints(employee.getID());
    }

    @Test
    public void testAddConstraint_Success() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("123456", job, branch);
        employeeRep.add(employee);

        controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
        assertEquals(1, employee.getConstraintMyMap().size());
        deleteEmployeeAndConstraints(employee.getID());
    }

    @Test
    public void testAddConstraint_InvalidDateFormat() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("321456", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.addConstraint("123456", "01-01-2023", "MORNING");
        });
        assertEquals("Invalid date format. Please use YYYY-MM-DD.", exception.getMessage());
        deleteEmployeeAndConstraints(employee.getID());
    }

    @Test
    public void testAddConstraint_InvalidShiftType() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("789654", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.addConstraint("789654", LocalDate.now().plusDays(7).toString(), "NIGHT");
        });
        assertEquals("Invalid shift type. Valid types are: MORNING, EVENING, FULLDAY.", exception.getMessage());
        deleteEmployeeAndConstraints(employee.getID());
    }

    @Test
    public void testAddConstraint_EmployeeNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.addConstraint("000000", LocalDate.now().plusDays(7).toString(), "MORNING");
        });
        assertEquals("Employee not found.", exception.getMessage());
    }

    @Test
    public void testAddConstraint_PastDate() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("963258", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.addConstraint("963258", LocalDate.now().minusDays(1).toString(), "MORNING");
        });
        assertEquals("Date is in the past.", exception.getMessage());
        deleteEmployeeAndConstraints("963258");
    }

    @Test
    public void testAddConstraint_ConstraintExistsUpdate() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("741258", job, branch);
        employeeRep.add(employee);

        controller.addConstraint("741258", LocalDate.now().plusDays(7).toString(), "MORNING");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.addConstraint("741258", LocalDate.now().plusDays(7).toString(), "EVENING");
        });
        assertEquals("Constraint exists update", exception.getMessage());
        assertEquals(1, employee.getConstraintMyMap().size());
        deleteEmployeeAndConstraints("741258");
    }

    @Test
    public void testRemoveConstraint_Success() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("963147", job, branch);
        employeeRep.add(employee);

        controller.addConstraint("963147", LocalDate.now().plusDays(7).toString(), "MORNING");
        controller.removeConstraint("963147", LocalDate.now().plusDays(7).toString(), "MORNING");
        assertEquals(0, employee.getConstraintMyMap().size());
        deleteEmployeeAndConstraints("963147");
    }

    @Test
    public void testRemoveConstraint_InvalidDateFormat() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("987123", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.removeConstraint("987123", "01-01-2023", "MORNING");
        });
        assertEquals("Invalid date format. Please use YYYY-MM-DD.", exception.getMessage());
        deleteEmployeeAndConstraints("987123");
    }

    @Test
    public void testRemoveConstraint_InvalidShiftType() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("654321", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.removeConstraint("654321", LocalDate.now().plusDays(7).toString(), "NIGHT");
        });
        assertEquals("Invalid shift type. Valid types are: MORNING, EVENING.", exception.getMessage());
        deleteEmployeeAndConstraints("654321");
    }

    @Test
    public void testRemoveConstraint_PastDate() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("111111", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.removeConstraint("111111", LocalDate.now().minusDays(1).toString(), "MORNING");
        });
        assertEquals("A past constraint cannot be deleted", exception.getMessage());
        deleteEmployeeAndConstraints("111111");
    }

    @Test
    public void testRemoveConstraint_EmployeeNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.removeConstraint("000000", LocalDate.now().plusDays(7).toString(), "MORNING");
        });
        assertEquals("Employee not found.", exception.getMessage());
    }

    @Test
    public void testRemoveConstraint_ConstraintNotFound() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("777777", job, branch);
        employeeRep.add(employee);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.removeConstraint("777777", LocalDate.now().plusDays(7).toString(), "MORNING");
        });
        assertEquals("Constraint not found.", exception.getMessage());
        deleteEmployeeAndConstraints("777777");
    }

    @Test
    public void testGetConstraintFromToday_Success() {
        Branch branch = new Branch("Main St.", "123 Main St.");
        Job job = new Job("Engineer");
        Employee employee = createEmployee("989898", job, branch);
        employeeRep.add(employee);

        controller.addConstraint("989898", LocalDate.now().plusDays(7).toString(), "MORNING");
        String result = controller.getConstraintFromToday("989898");
        assertNotNull(result);
        assertTrue(result.contains(LocalDate.now().plusDays(7).toString()));
        deleteEmployeeAndConstraints("989898");
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
