package dev.src.service;

import dev.src.Controllers.*;

public class HRManagerService {
    private MasterController masterController;

    public HRManagerService(MasterController masterController) {
        this.masterController = masterController;
    }

    public String createEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type, String jobname) {
        try {
            return masterController.getHR_Employee().createEmployee(name, ID, bank_account, branch, vacationDay, start_date, salary, job_type, salary_type, jobname);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String createManagementEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type, String jobname) {
        try {
            return masterController.getHR_Employee().createManagmentEmployee(name, ID, bank_account, branch, vacationDay, start_date, salary, job_type, salary_type, jobname);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeName(String ID, String name) {
        try {
            return masterController.getHR_Employee().updateEmployeeNAME(ID, name);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeEndDate(String ID, String end_date) {
        try {
            return masterController.getHR_Employee().updateEmployeeENDINGDATE(ID, end_date);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeSalary(String ID, double salary) {
        try {
            return masterController.getHR_Employee().updateEmployeeSALERY(ID, salary);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeJobType(String ID, String jobType) {
        try {
            masterController.getHR_Employee().updateEmployeeJobType(ID, jobType);
            return "Job type updated successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeSalaryType(String ID, String salaryType) {
        try {
            masterController.getHR_Employee().updateEmployeeSalaryType(ID, salaryType);
            return "Salary type updated successfully.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeBankAccount(String ID, String bank_account) {
        try {
            return masterController.getHR_Employee().updateEmployeeBANK(ID, bank_account);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeBranch(String ID, int branchNum) {
        try {
            return masterController.getHR_Employee().updateEmployeeBranch(ID, branchNum);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String addJobForEmployee(String ID, String jobName) {
        try {
            return masterController.getHR_Employee().updateORaddEmployeeJob(ID, jobName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String updateEmployeeVacationDays(String ID, double vacationDays) {
        try {
            return masterController.getHR_Employee().updateVacationDays(ID,vacationDays);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String createJob(String name) {
        try {
            return masterController.getHR_Employee().createJob(name);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    public String createManagementJob(String name) {
        try {
            return masterController.getHR_Employee().createManagementJob(name);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

//    public String makeScheduleForNextWeek(int branchNum, String date) {
//        try {
//            return masterController.getHR_Shift().MakeScheduleforNextWeek(branchNum, date);
//        } catch (IllegalArgumentException e) {
//            return e.getMessage();
//        }
//    }
}
