package dev.src.TEST;

import static org.junit.jupiter.api.Assertions.*;

import dev.src.Controllers.*;
import dev.src.Domain.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;

public class HRManagerShiftControllerTest {
    private HRManagerShiftController shiftController;
    private MasterController masterController;
    private MyMap<String, Branch> branchDatabase;
    private MyMap<String, Employee> employeeDatabase;
    private List<Job> jobDatabase;
    private MyMap<Integer, MyMap<LocalDate, String>> historyDatabase;

    @Before
    public void setUp() {
        branchDatabase = new MyMap<>();
        employeeDatabase = new MyMap<>();
        jobDatabase = new ArrayList<>();
        historyDatabase = new MyMap<>();
        masterController = new MasterController();

        // Adding branches, jobs, and employees
        branchDatabase.put("Main Branch", new Branch("Main Branch", "123 Main St"));
        jobDatabase.add(new Job("Cashier"));
        jobDatabase.add(new Job("Stock"));
        TermsOfEmployment terms= new TermsOfEmployment(14,LocalDate.now(),456,"PART","GLOBAL");
        Employee employee1 = new Employee("John Doe", "123456", "12345678", branchDatabase.get("Main Branch"), terms, jobDatabase.get(0));
        Employee employee2 = new Employee("Jane Smith", "654321", "87654321", branchDatabase.get("Main Branch"), terms, jobDatabase.get(1));

        employeeDatabase.put("123456", employee1);
        employeeDatabase.put("654321", employee2);

        shiftController = new HRManagerShiftController(jobDatabase, branchDatabase, employeeDatabase, historyDatabase);
    }

    @Test
    public void testMakeScheduleForNextWeek() {
        String result = shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        assertTrue(result.contains("Schedule Successfully created"));
    }

    @Test
    public void testMakeScheduleForNextWeekInvalidDate() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            shiftController.MakeScheduleforNextWeek(1, "2024-06-10"); // Not a Sunday
        });
        assertEquals("Date of start must be SUNDAY", thrown.getMessage());
    }

    @Test
    public void testAddEmployeeToShift() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        List<Integer> employeeNums = Arrays.asList(0);
        List<Object> cleanInfo = shiftController.checkaddEmployeesToShiftsByDateANDJob(employeeNums, "Cashier", "MORNING", "2024-06-09");
        String result = shiftController.addEmployeetoshift(cleanInfo);
        assertTrue(result.contains("successfully added"));
    }

    @Test
    public void testAddEmployeeToShiftInvalidJob() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        List<Integer> employeeNums = Arrays.asList(0);
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            shiftController.checkaddEmployeesToShiftsByDateANDJob(employeeNums, "InvalidJob", "MORNING", "2024-06-09");
        });
        assertEquals("Job does not exist in this Shift", thrown.getMessage());
    }

    @Test
    public void testRemoveEmployeeFromShift() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        List<Integer> employeeNums = Arrays.asList(0);
        List<Object> cleanInfo = shiftController.checkaddEmployeesToShiftsByDateANDJob(employeeNums, "Cashier", "MORNING", "2024-06-09");
        shiftController.addEmployeetoshift(cleanInfo);

        String result = shiftController.removeEmployeefromShift(0, "MORNING", "2024-06-09");
        assertTrue(result.contains("successfully removed"));
    }

    @Test
    public void testRemoveEmployeeFromShiftInvalidEmployee() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            shiftController.removeEmployeefromShift(999, "MORNING", "2024-06-09");
        });
        assertEquals("Employee not exist", thrown.getMessage());
    }

    @Test
    public void testChangeDefaultValuesInSpecificShift() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        String result = shiftController.ChangingdefaultvaluesinSpecificShiftNUMworkertoJob("2024-06-09", "MORNING", "Cashier", 5);
        assertEquals("The number of worker for Cashier is change to 5", result);
    }

    @Test
    public void testChangeDefaultValuesInSpecificShiftWorkHours() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        String result = shiftController.ChangingdefaultvaluesinSpecificShiftWORKHoursStart_End("2024-06-09", "MORNING", "08:00", "14:00");
        assertEquals("Shift Hours is change to 08:00-14:00", result);
    }

    @Test
    public void testChangeDayOff() {
        shiftController.MakeScheduleforNextWeek(1, "2024-06-09");
        String result = shiftController.ChangingdefaultvaluesinSpecificDayDAY_OFF("2024-06-09", "T");
        assertEquals("Day 2024-06-09 change to day off", result);
    }

    @Test
    public void testChangeDefaultValuesForAllShifts() {
        String result = shiftController.ChangingdefaultvaluesforALLShiftNUMworkertoJob("Cashier", 5);
        assertEquals("The number of worker for Cashier is change to 5", result);
    }

    @Test
    public void testChangeDefaultValuesForAllShiftWorkHours() {
        String result = shiftController.ChangingdefaultvaluesforALLshiftWORKHoursStart_End("MORNING", "08:00", "14:00");
        assertEquals("MORNING Shift Hours is change to 08:00-14:00", result);
    }
}