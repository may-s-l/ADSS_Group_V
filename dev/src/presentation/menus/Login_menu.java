package dev.src.presentation.menus;
import dev.src.Controllers.MasterController;
import dev.src.Data.DaoM.JobsTDao;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.Job;
import dev.src.Domain.ManagerEmployee;
import dev.src.service.EmployeeService;
import dev.src.service.HRManagerService;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.Objects;

public class Login_menu {

    public Login_menu() {
        MasterController MC = new MasterController();
        EmployeeService ES = new EmployeeService(MC);
        HRManagerService HRS = new HRManagerService(MC);
        System_manger_set_up(MC);
        Scanner scanner = new Scanner(System.in);
        int flag_can_uplodata=0;
        while (true) {

            System.out.println("Login Menu");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            if(flag_can_uplodata==0) {
                System.out.println("3. Lode demo data");
            }
            System.out.print("Enter your choice: ");
            int choice = getValidChoice(scanner);

            String res;
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String password = scanner.nextLine();
                    res = MC.checkLoginEmployee(password);//password=id
                    if (Objects.equals(res, "HR-MANAGER")) {
                        new SystemManagerMenu(HRS);
                    } else if (Objects.equals(res, "Employee")) {
                        new EmployeeMenu(ES, password);
                    } else {
                        System.out.println(res);
                    }
                    break;
                case 2:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);

                case 3:
                    if(flag_can_uplodata==0) {
                        dataupload(MC);
                        flag_can_uplodata += 1;
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private int getValidChoice(Scanner scanner) {
        int choice = -1;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                System.out.print("Enter your choice: ");
            }
        }
        return choice;
    }

    public static void main(String[] args) {
        new Login_menu();
    }



    private void dataupload(MasterController MC) {
        String branchFile = "C:\\Users\\mayal\\Downloads\\לימודים מאי לוי\\ניתוץ - ניתוך ותכנון מערכות\\adss_v02\\dev\\src\\DataToupload\\Branchs.csv";
        String jobFile = "C:\\Users\\mayal\\Downloads\\לימודים מאי לוי\\ניתוץ - ניתוך ותכנון מערכות\\adss_v02\\dev\\src\\DataToupload\\Jobs.csv";
        String employeeFile = "C:\\Users\\mayal\\Downloads\\לימודים מאי לוי\\ניתוץ - ניתוך ותכנון מערכות\\adss_v02\\dev\\src\\DataToupload\\Employees.csv";
        //uploadBranchData(MC, branchFile);
        uploadJobData(MC, jobFile);
        uploadEmployeeData(MC, employeeFile);

        // Verify there are branches and employees before accessing them
        if (!MC.getHR_Employee().getAllBranch().isEmpty() && MC.getHR_Employee().getEmployeeByID("111111") instanceof ManagerEmployee) {
            MC.getHR_Employee().getAllBranch().get(0).setManagerEmployee((ManagerEmployee) MC.getHR_Employee().getEmployeeByID("111111"));
        }
    }

    private void uploadBranchData(MasterController MC, String filePath) {
        if (!new File(filePath).exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                i+=1;
                if(i==1){
                    continue;
                }
                else {
                    String[] row = line.split(",");
                    MC.getHR_Employee().createBranch(row[0], row[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadJobData(MasterController MC, String filePath) {
        if (!new File(filePath).exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                i+=1;
                if(i==1){
                    continue;
                }
                else {
                    String[] row = line.split(",");
                    if (row[0].equals("HR-MANAGER")) {
                        MC.getHR_Employee().createManagementJob(row[0]);
                    } else {
                        MC.getHR_Employee().createJob(row[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void uploadEmployeeData(MasterController MC, String filePath) {
        if (!new File(filePath).exists()) {
            System.out.println("File not found: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                i+=1;
                if(i==1){
                    continue;
                }
                else {
                    String[] row = line.split(",");
                    try {
                        double salary = Double.parseDouble(row[4]);
                        double hours = Double.parseDouble(row[6]);

                        if (row[9].equals("HR-MANAGER")) {
                            MC.getHR_Employee().createManagmentEmployee(row[0], row[1], row[2], row[3], salary, row[5], hours, row[7], row[8], row[9]);
                        } else {
                            MC.getHR_Employee().createEmployee(row[0], row[1], row[2], row[3], salary, row[5], hours, row[7], row[8], row[9]);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in row: " + String.join(", ", row));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void System_manger_set_up(MasterController MC){
        if(JobsTDao.getInstance().select("HR-MANAGER")==null) {
            MC.getHR_Employee().createManagementJob("HR-MANAGER");
            MC.getHR_Employee().createBranch("SuperLee Main", "BGU");
            MC.getHR_Employee().createManagmentEmployee("SHLAT", "111111", "95135748", "SuperLee Main", 14, "2024-06-06", 10000, "FULL", "GLOBAL", "HR-MANAGER");
        }
    }

}


