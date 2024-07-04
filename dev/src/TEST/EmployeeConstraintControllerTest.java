//package dev.src.TEST;
//
//import dev.src.Domain.*;
//import dev.src.Controllers.*;
//import dev.src.Domain.Enums.ShiftType;
//import dev.src.Domain.Repository.ConstraintRep;
//import dev.src.Domain.Repository.EJobsRep;
//import dev.src.Domain.Repository.EmployeeRep;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class EmployeeConstraintControllerTest {
//
//    private Employee createEmployee(String id, Job job) {
//        Branch branch = new Branch("Main St.", "SuperLee Main", "Main Branch", "Manager");
//        TermsOfEmployment terms = new TermsOfEmployment(10.0, LocalDate.now(), 5000.0, "Full Time", "Monthly");
//        return new Employee("John Doe", id, "12345678", branch, terms, job);
//    }
//
//    @Test
//    public void testReturnEmployeeDetails_Success() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        String result = controller.returnEmployeeDetails("123456");
//        assertNotNull(result);
//        assertTrue(result.contains("John Doe"));
//    }
//
//    @Test
//    public void testAddConstraint_Success() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
//        assertEquals(1, employee.getConstraintMyMap().size());
//    }
//
//    @Test
//    public void testAddConstraint_InvalidDateFormat() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.addConstraint("123456", "01-01-2023", "MORNING");
//        });
//        assertEquals("Invalid date format. Please use YYYY-MM-DD.", exception.getMessage());
//    }
//
//    @Test
//    public void testAddConstraint_InvalidShiftType() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "NIGHT");
//        });
//        assertEquals("Invalid shift type. Valid types are: MORNING, EVENING, FULLDAY.", exception.getMessage());
//    }
//
//    @Test
//    public void testAddConstraint_EmployeeNotFound() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.addConstraint("000000", LocalDate.now().plusDays(7).toString(), "MORNING");
//        });
//        assertEquals("Employee not found.", exception.getMessage());
//    }
//
//    @Test
//    public void testAddConstraint_PastDate() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.addConstraint("123456", LocalDate.now().minusDays(1).toString(), "MORNING");
//        });
//        assertEquals("Date is in the past.", exception.getMessage());
//    }
//
//    @Test
//    public void testAddConstraint_ConstraintExistsUpdate() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "EVENING");
//        });
//        assertEquals("Constraint exists update", exception.getMessage());
//        assertEquals(1, employee.getConstraintMyMap().size());
//    }
//
//    @Test
//    public void testRemoveConstraint_Success() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
//        controller.removeConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
//        assertEquals(0, employee.getConstraintMyMap().size());
//    }
//
//    @Test
//    public void testRemoveConstraint_InvalidDateFormat() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.removeConstraint("123456", "01-01-2023", "MORNING");
//        });
//        assertEquals("Invalid date format. Please use YYYY-MM-DD.", exception.getMessage());
//    }
//
//    @Test
//    public void testRemoveConstraint_InvalidShiftType() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.removeConstraint("123456", LocalDate.now().plusDays(7).toString(), "NIGHT");
//        });
//        assertEquals("Invalid shift type. Valid types are: MORNING, EVENING.", exception.getMessage());
//    }
//
//    @Test
//    public void testRemoveConstraint_PastDate() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.removeConstraint("123456", LocalDate.now().minusDays(1).toString(), "MORNING");
//        });
//        assertEquals("A past constraint cannot be deleted", exception.getMessage());
//    }
//
//    @Test
//    public void testRemoveConstraint_EmployeeNotFound() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.removeConstraint("000000", LocalDate.now().plusDays(7).toString(), "MORNING");
//        });
//        assertEquals("Employee not found.", exception.getMessage());
//    }
//
//    @Test
//    public void testRemoveConstraint_ConstraintNotFound() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            controller.removeConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
//        });
//        assertEquals("Constraint not found.", exception.getMessage());
//    }
//
//    @Test
//    public void testGetConstraintFromToday_Success() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        controller.addConstraint("123456", LocalDate.now().plusDays(7).toString(), "MORNING");
//        String result = controller.getConstraintFromToday("123456");
//        assertNotNull(result);
//        assertTrue(result.contains(LocalDate.now().plusDays(7).toString()));
//    }
//
//    @Test
//    public void testGetConstraintFromToday_NoConstraints() {
//        EmployeeRep employeeRep = new EmployeeRep();
//        Job job = new Job("Engineer");
//        Employee employee = createEmployee("123456", job);
//        employeeRep.add(employee);
//        EmployeeConstraintController controller = new EmployeeConstraintController(employeeRep);
//
//        String result = controller.getConstraintFromToday("123456");
//        assertEquals("No constraints found for this employee.", result);
//    }
//}
