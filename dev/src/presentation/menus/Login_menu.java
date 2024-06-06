package dev.src.presentation.menus;
import dev.src.Controllers.MasterController;
import dev.src.Domain.Branch;
import dev.src.Domain.Employee;
import dev.src.Domain.Job;
import dev.src.Domain.ManagerEmployee;
import dev.src.service.EmployeeService;
import dev.src.service.HRManagerService;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Login_menu {

    public Login_menu() {
        MasterController MC = new MasterController();
        EmployeeService ES = new EmployeeService(MC);
        HRManagerService HRS=new HRManagerService(MC);
        dataupload(MC);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Login Menu");
            System.out.println("1. Login");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            String res;
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String password = scanner.nextLine();
                    res=MC.checkLoginEmployee(password);
                    if(Objects.equals(res, "HRManager")){
                        new SystemManagerMenu(HRS);
                    }
                    if(Objects.equals(res, "Employee")){
                        new EmployeeMenu(ES,password);
                    }
                    else{
                        System.out.println(res);
                    }
                    break;
                case 2:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void dataupload(MasterController MC){
        String file = "dev.src.DataToupload\\Branchs.csv";
        BufferedReader reader = null;
        String line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                MC.getHR_Employee().createBranch(row[0],row[1]);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file = "dev.src.DataToupload\\Jobs.csv";
        reader = null;
        line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if(row[0]=="HRManager"){
                    MC.getHR_Employee().createManagementJob(row[0]);
                }
                else {
                    MC.getHR_Employee().createJob(row[0]);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file = "dev.src.DataToupload\\Employees.csv";
        reader = null;
        line = "";

        try {
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                if(row[9]=="HRManager"){
                    MC.getHR_Employee().createManagmentEmployee(row[0],row[1],row[2],row[3],Double.parseDouble(row[4]),row[5],Double.parseDouble(row[6]),row[7],row[8],row[9]);
                }
                else {
                    MC.getHR_Employee().createEmployee(row[0],row[1],row[2],row[3],Double.parseDouble(row[4]),row[5],Double.parseDouble(row[6]),row[7],row[8],row[9]);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MC.getHR_Employee().getAllBranch().get(0).setManagerEmployee((ManagerEmployee) MC.getHR_Employee().getEmployeeByID("111111"));
    }

}


