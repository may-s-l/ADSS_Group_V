package dev.src.presentation.menus;

import dev.src.service.HRManagerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SystemManagerMenu{
    private HRManagerService managerService;

    public SystemManagerMenu(HRManagerService managerService) {
        this.managerService = managerService;
        displayMenu();
    }

    private void displayMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to manager menu!");
            System.out.println("1. Create employee");
            System.out.println("2. Update employee details");
            System.out.println("3. Create job");
            System.out.println("4. Go to Shift scheduling log menu");
            System.out.println("5. Back to Login menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createEmployee(scanner);
                    break;
                case 2:
                    updateEmployeeDetailsMenu(scanner);
                    break;
                case 3:
                    createJob(scanner);
                    break;
                case 4:
                    shiftScheduleMenu(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createEmployee(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter bank account: ");
        String bankAccount = scanner.nextLine();
        System.out.print("Enter branch: ");
        String branch = scanner.nextLine();
        System.out.print("Enter vacation days: ");
        double vacationDays = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter job type (FULL/PART): ");
        String jobType = scanner.nextLine();
        System.out.print("Enter salary type (GLOBAL/HOURLY): ");
        String salaryType = scanner.nextLine();
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        String result = managerService.createEmployee(name, ID, bankAccount, branch, vacationDays, startDate, salary, jobType, salaryType, jobName);
        System.out.println(result);
    }

    private void updateEmployeeDetailsMenu(Scanner scanner) {
        while (true) {
            System.out.println("Update Employee Details Menu:");
            System.out.println("1. Update employee name");
            System.out.println("2. Update employee end date");
            System.out.println("3. Update employee salary");
            System.out.println("4. Update employee bank account");
            System.out.println("5. Update employee branch");
            System.out.println("6. Add job for employee");
            System.out.println("7. Update employee vacation days");
            System.out.println("8. Update employee job type");
            System.out.println("9. Update employee salary type");
            System.out.println("10. Back to manager menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    updateEmployeeName(scanner);
                    break;
                case 2:
                    updateEmployeeEndDate(scanner);
                    break;
                case 3:
                    updateEmployeeSalary(scanner);
                    break;
                case 4:
                    updateEmployeeBankAccount(scanner);
                    break;
                case 5:
                    updateEmployeeBranch(scanner);
                    break;
                case 6:
                    addJobForEmployee(scanner);
                    break;
                case 7:
                    updateEmployeeVacationDays(scanner);
                    break;
                case 8:
                    updateEmployeeJobType(scanner);
                    break;
                case 9:
                    updateEmployeeSalaryType(scanner);
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateEmployeeName(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        String result = managerService.updateEmployeeName(ID, name);
        System.out.println(result);
    }

    private void updateEmployeeEndDate(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();
        String result = managerService.updateEmployeeEndDate(ID, endDate);
        System.out.println(result);
    }

    private void updateEmployeeSalary(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();
        String result = managerService.updateEmployeeSalary(ID, salary);
        System.out.println(result);
    }

    private void updateEmployeeBankAccount(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new bank account: ");
        String bankAccount = scanner.nextLine();
        String result = managerService.updateEmployeeBankAccount(ID, bankAccount);
        System.out.println(result);
    }

    private void updateEmployeeBranch(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new branch number: ");
        int branchNum = scanner.nextInt();
        scanner.nextLine();
        String result = managerService.updateEmployeeBranch(ID, branchNum);
        System.out.println(result);
    }

    private void addJobForEmployee(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        String result = managerService.addJobForEmployee(ID, jobName.toUpperCase());
        System.out.println(result);
    }

    private void updateEmployeeVacationDays(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new vacation days: ");
        double vacationDays = scanner.nextDouble();
        scanner.nextLine();
        String result = managerService.updateEmployeeVacationDays(ID, vacationDays);
        System.out.println(result);
    }

    private void updateEmployeeJobType(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new job type (FULL/PART): ");
        String jobType = scanner.nextLine();
        String result = managerService.updateEmployeeJobType(ID, jobType.toUpperCase());
        System.out.println(result);
    }

    private void updateEmployeeSalaryType(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String ID = scanner.nextLine();
        System.out.print("Enter new salary type (GLOBAL/HOURLY): ");
        String salaryType = scanner.nextLine();
        String result = managerService.updateEmployeeSalaryType(ID, salaryType.toUpperCase());
        System.out.println(result);
    }

    private void createJob(Scanner scanner) {
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        String result = managerService.createJob(jobName.toUpperCase());
        System.out.println(result);
    }

    private void shiftScheduleMenu(Scanner scanner) {
        while (true) {
            System.out.println("welcome to shift scheduling log menu! ");
            System.out.println("1. Go to changing default for shifts menu");
            System.out.println("2. Show shifts history");
            System.out.println("3. Go to create new shift week menu");
            System.out.println("4. Back to manager menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    changingDefaultMenu(scanner);
                    break;
                case 2:
                    showShiftsHistory(scanner);
                    break;
                case 3:
                    shiftSchedulingLogMenu(scanner);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void changingDefaultMenu(Scanner scanner) {
        while (true) {
            System.out.println("Welcome to changing default for shifts menu! ");
            System.out.println("1. Defining the number of workers from each position in the shift");
            System.out.println("2. Setting shifts hours");
            System.out.println("3. Back to shift scheduling log menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    defineNumberOfWorkers(scanner);
                    break;
                case 2:
                    setShiftsHours(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void defineNumberOfWorkers(Scanner scanner) {
        System.out.println("Defining the number of workers from each position in the shift:");
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        System.out.print("Enter the number of worker for this job: ");
        int numWorkers = scanner.nextInt();
        scanner.nextLine();
        String result = managerService.changingDefaultValuesInShiftNumWorkersToJob(jobName.toUpperCase(), numWorkers);
        System.out.println(result);
    }

    private void setShiftsHours(Scanner scanner) {
        System.out.println("Setting shifts hours:");
        System.out.print("Enter shift type (MORNING/EVENING): ");
        String shiftType = scanner.nextLine();
        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();
        String result = managerService.changingDefaultValuesInShiftWorkHours(shiftType.toUpperCase(),startTime, endTime);
        System.out.println(result);
    }

    private void showShiftsHistory(Scanner scanner) {
        System.out.print("Enter branch number: ");
        int branchNum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter the start date of the week (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        String result = managerService.printingWeekHistory(branchNum, dateStr);
        System.out.println(result);
    }


    private void shiftSchedulingLogMenu(Scanner scanner) {
        while (true) {
            System.out.println("Welcome to shift scheduling log menu!");
            System.out.println("1. Make schedule for next week");
            System.out.println("2. Add employee to shift");
            System.out.println("3. Add employee to all __ shifts in week");
            System.out.println("4. Remove employee from shift");
            System.out.println("5. Change default number of workers for a job in a specific shift");
            System.out.println("6. Change work hours for a specific shift");
            System.out.println("7. Change day off setting for a specific day");
            System.out.println("8. Close scheduling");
            System.out.println("9. Back to shift scheduling log menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    makeScheduleForNextWeek(scanner);
                    break;
                case 2:
                    addEmployeeToShift(scanner);
                    break;
                case 3:
                    addEmployeeToAll__Shift(scanner);
                    break;
                case 4:
                    removeEmployeeFromShift(scanner);
                    break;
                case 5:
                    changeDefaultNumWorkers(scanner);
                    break;
                case 6:
                    changeWorkHours(scanner);
                    break;
                case 7:
                    changeDayOff(scanner);
                    break;
                case 8:
                    closeSchedul();
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void makeScheduleForNextWeek(Scanner scanner) {
        System.out.print("Enter branch number: ");
        int branchNum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter start date of the week (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        String result = managerService.makeScheduleForNextWeek(branchNum, date);
        System.out.println(result);
    }

    private void addEmployeeToShift(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter employee numbers (comma-separated): ");
        String[] empNums = scanner.nextLine().split(",");
        List<Integer> empNumList = new ArrayList<>();
        for (String num : empNums) {
            empNumList.add(Integer.parseInt(num.trim()));
        }
        System.out.print("Enter shift type (MORNING/EVENING): ");
        String shiftType = scanner.nextLine();
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        String result = managerService.addEmployeeToShift( date, empNumList, shiftType.toUpperCase(), jobName.toUpperCase());
        System.out.println(result);
    }


    private void addEmployeeToAll__Shift(Scanner scanner) {
        System.out.print("Enter employee number: ");
        int empNum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter shift type (MORNING/EVENING): ");
        String shiftType = scanner.nextLine();
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        String result = managerService.addemployeetoallshiftsserver(empNum,jobName.toUpperCase(), shiftType.toUpperCase());
        System.out.println(result);
    }

    private void removeEmployeeFromShift(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter employee number: ");
        int workNum = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter shift type (MORNING/EVENING): ");
        String shiftType = scanner.nextLine();
        String result = managerService.removeEmployeeFromShiftService(date, workNum, shiftType.toUpperCase());
        System.out.println(result);
    }

    private void changeDefaultNumWorkers(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter shift type (MORNING/EVENING): ");
        String shiftType = scanner.nextLine();
        System.out.print("Enter job name: ");
        String jobName = scanner.nextLine();
        System.out.print("Enter number of workers: ");
        int numWorkers = scanner.nextInt();
        scanner.nextLine();
        String result = managerService.changingDefaultValuesInSpecificShiftNumWorkersToJob( date, shiftType.toUpperCase(), jobName.toUpperCase(), numWorkers);
        System.out.println(result);
    }

    private void changeWorkHours(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter shift type (MORNING/EVENING): ");
        String shiftType = scanner.nextLine();
        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();
        String result = managerService.changingDefaultValuesInSpecificShiftWorkHours(date, shiftType.toUpperCase(), startTime, endTime);
        System.out.println(result);
    }

    private void changeDayOff(Scanner scanner) {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter 'T' for true (day off) or 'F' for false (work day): ");
        String bool = scanner.nextLine();
        String result = managerService.changingDefaultValuesInSpecificDayOff(date, bool);
        System.out.println(result);
    }

    private void closeSchedul(){
        String res =managerService.closeSchedulserver();
        System.out.println(res);

    }





}