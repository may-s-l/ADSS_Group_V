package dev.src.Controllers;
import dev.src.Data.*;
import dev.src.Domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class HRManagerEmployeeController {

    private MyMap<String, Branch> Branch_temp_database;//String address
    private List<Job> Employeejobs_temp_database;
    private MyMap<String, Employee> Employees_temp_database;//String ID

    public HRManagerEmployeeController(List<Job>Joblist,MyMap<String,Branch>Branchs,MyMap<String,Employee>employeeMyMap) {
        this.Employees_temp_database=employeeMyMap;
        this.Branch_temp_database=Branchs;
        this.Employeejobs_temp_database=Joblist;
    }


    public String createJob(String name) throws IllegalArgumentException {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be NULL");
        }
        for (Job j : this.Employeejobs_temp_database) {
            if (j.getJobName() == name) {
                throw new IllegalArgumentException("Job is already exists");
            }
        }
        Job newJob = new Job(name);
        this.Employeejobs_temp_database.add(newJob);
        return "Job successfully created";
    }
    public String createManagementJob(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name can't be NULL");
        }
        for (Job j : this.Employeejobs_temp_database) {
            if (j.getJobName() == name) {
                throw new IllegalArgumentException("Job is already exists");
            }
        }
        Job newJob = (ManagementJob) new ManagementJob(name);
        this.Employeejobs_temp_database.add(newJob);
        return "Managemet Job successfully created";
    }

    public Employee createEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type, String jobname) throws IllegalArgumentException {
        //NULL
        if (name == null || bank_account == null || ID == null || jobname == null || salary < 30 || vacationDay < 14 || branch == null || salary_type == null || job_type == null || start_date == null)
            throw new IllegalArgumentException("Argument's can't be NULL");
        //-------Name-------//
        if(name.contains("[0-9]+")){
            throw new IllegalArgumentException("Name ame cannot contain non-alphabetic characters");
        }
        //-------ID-------//
        if (ID.length() != 6 || this.Temp_Database.getEmployeeByID(ID) != null) {
            return null;
        }
        //-------Bank-------//
        if (bank_account.length() != 8 || bank_account.contains("[a-z,A-Z]+")) {
            return null;
        }
        //-------job-------//
        Job job_to_emp = null;
        for (Job j : this.Temp_Database.getEmployeejobs_temp_database()) {
            if (j.getJobName() == jobname) {
                job_to_emp = j;
                break;
            }
        }
        if (job_to_emp == null) {
            return null;
        }
        //-------Branch-------//
        Branch branch_to_emp = null;
        for (Branch b : this.Temp_Database.getBranch_temp_database()) {
            if (b.getBranchName() == branch) {
                branch_to_emp = b;
                break;
            }
        }
        if (branch_to_emp == null) {
            return null;
        }
        //-------StartDay-------//"2018-05-05"
        LocalDate date = LocalDate.parse(start_date);
        if (LocalDate.now().getMonth() != date.getMonth()) {
            return null;
        }
        //-------Enums-------//
        if (job_type.toUpperCase(Locale.ROOT) != "FULL" && job_type.toUpperCase(Locale.ROOT) != "PART") {
            return null;
        }
        if (salary_type.toUpperCase() != "GLOBAL" && salary_type.toUpperCase() != "HOURLY") {
            return null;
        }
        //-------------------create------------------//
        TermsOfEmployment terms = new TermsOfEmployment(vacationDay, date, salary, job_type, salary_type);
        Employee NEWemployee = new Employee(name, ID, bank_account, branch_to_emp, terms, job_to_emp);
        this.Temp_Database.getEmployees_temp_database().put(ID, NEWemployee);
        return NEWemployee;
    }

    public ManagerEmployee createManagmentEmployee(String name, String ID, String bank_account, String branch, double vacationDay, String start_date, double salary, String job_type, String salary_type, String jobname) {
        //NULL
        if (name == null || name.contains("[0-9]+") || bank_account == null || ID == null || jobname == null || salary < 30 || vacationDay < 14 || branch == null || salary_type == null || jobname == null)
            return null;
        //-------ID-------//
        if (ID.length() != 6 || this.Temp_Database.getEmployeeByID(ID) != null) {
            return null;
        }
        //-------Bank-------//
        if (bank_account.length() != 8 || bank_account.contains("[a-z,A-Z]+")) {
            return null;
        }
        //-------job-------//
        Job job_to_emp = null;
        for (Job j : this.Temp_Database.getEmployeejobs_temp_database()) {
            if (j.getJobName() == jobname) {
                if (j instanceof ManagementJob) {
                    job_to_emp = j;
                    break;
                }
            }
        }
        if (job_to_emp == null) {
            return null;
        }
        //-------Branch-------//
        Branch branch_to_emp = null;
        for (Branch b : this.Temp_Database.getBranch_temp_database()) {
            if (b.getBranchName() == branch) {
                branch_to_emp = b;
                break;
            }
        }
        if (branch_to_emp == null) {
            return null;
        }
        //-------StartDay-------//"2018-05-05"
        LocalDate date = LocalDate.parse(start_date);
        if (LocalDate.now().getMonth() != date.getMonth()) {
            return null;
        }
        //-------Enums-------//
        if (job_type.toUpperCase(Locale.ROOT) != "FULL" && job_type.toUpperCase(Locale.ROOT) != "PART") {
            return null;
        }
        if (salary_type.toUpperCase() != "GLOBAL" && salary_type.toUpperCase() != "HOURLY") {
            return null;
        }
        TermsOfEmployment terms = new TermsOfEmployment(vacationDay, date, salary, job_type, salary_type);
        ManagerEmployee NEWemployee = new ManagerEmployee(name, ID, bank_account, branch_to_emp, terms, (ManagementJob) job_to_emp);
        this.Temp_Database.getEmployees_temp_database().put(ID, NEWemployee);
        return NEWemployee;

    }

    // UPDATE all String name, String bank_account, Branch branch, double vacationDay, LocalDate start_date, double salary, String job_type, String Salary_type,Job job) for all employees
    public boolean updateEmployeeNAME(String ID, String name) {
        if (ID == null | name == null | name.contains("[0-9]+")) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        if (empToUpdate != null) {
            empToUpdate.setName(name);
            return true;
        }

        return false;
    }

    public boolean updateEmployeeENDINGDATE(String ID, String end_date) {
        if (ID == null | end_date == null) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        if (empToUpdate != null) {
            LocalDate end_date_toUP = LocalDate.parse(end_date);
            LocalDate start_date = empToUpdate.getTerms().getStart_date();
            if (end_date_toUP.isAfter(start_date)) {
                if (empToUpdate.getTerms().getEnd_date() == null) {
                    empToUpdate.getTerms().setEnd_date(end_date_toUP);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public boolean updateEmployeeSALERY(String ID, double salery) {
        if (ID == null | salery <= 0) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        if (empToUpdate != null) {
            empToUpdate.getTerms().setSalary(salery);
            return true;
        }
        return false;
    }

    public boolean updateORaddEmployeeJob(String ID, String job) {
        if (ID == null | job == null) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        Job jobToUpdate = this.Temp_Database.getJobByName(job);
        if (empToUpdate == null || jobToUpdate == null) {
            return false;
        }
        if (jobToUpdate instanceof ManagementJob && empToUpdate instanceof ManagerEmployee) {
            return empToUpdate.addJob(jobToUpdate);
        }
        if (!(jobToUpdate instanceof ManagementJob) && !(empToUpdate instanceof ManagerEmployee)) {
            return empToUpdate.addJob(jobToUpdate);
        }
        return false;
    }

    public boolean updateEmployeeBANK(String ID, String bank_accuont) {
        if (ID == null || bank_accuont == null || bank_accuont.length() != 8 || bank_accuont.contains("[a-z,A-Z]+")) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        if (empToUpdate == null) {
            return false;
        }
        return empToUpdate.setBank_account(bank_accuont);
    }

    public boolean updateEmployeeBranch(String ID, int branchnum) {
        if (ID == null || branchnum <= 0) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        Branch branchToUpdate = this.Temp_Database.getBranchByNUM(branchnum);
        if (empToUpdate == null || branchToUpdate == null) {
            return false;
        }
        return empToUpdate.setBranch(branchToUpdate);
    }

    public boolean updateEmployeeTermsSalary(String ID, double salary, String job_type, String Salary_type) {
        if (ID == null || salary <= 30 || job_type == null || Salary_type == null || (job_type.toUpperCase() != "PART" && job_type.toUpperCase() != "FULL") || (Salary_type.toUpperCase() != "GLOBAL" && Salary_type.toUpperCase() != "HOURLY")) {
            return false;
        }
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(ID);
        if (empToUpdate == null) {
            return false;
        }
        TermsOfEmployment term = empToUpdate.getTerms();
        term.setSalary(salary);
        term.setJt(job_type);
        term.setSt(Salary_type);
        return true;
    }

    //UPDATE Employee TO ManagmantEmployee
    public boolean UPDATEemployeeToManagmantEmployee(String ID, String job) {
        if (ID == null | job == null) {
            return false;
        }
        Employee empToUpdate =this.Temp_Database.getEmployeeByID(ID);
        Job jobToUpdat = this.Temp_Database.getJobByName(job);
        if (empToUpdate != null && jobToUpdat != null) {
            if (empToUpdate instanceof ManagerEmployee || !(jobToUpdat instanceof ManagementJob)) {
                return false;
            }
            this.Temp_Database.getEmployees_temp_database().remove(ID);
            ManagerEmployee empToUpdateASmaneger = new ManagerEmployee(empToUpdate.getName(), ID, empToUpdate.getBank_account(), empToUpdate.getBranch(), empToUpdate.getTerms(), (ManagementJob) jobToUpdat);
            if (empToUpdateASmaneger == null) {
                return false;
            }
            return this.addEmployee(empToUpdateASmaneger);
        }
        return false;
    }

    public Boolean addEmployee(Employee employee) {
        Employee empToUpdate = this.Temp_Database.getEmployeeByID(employee.getID());
        if (empToUpdate != null) {
            return false;
        }
        this.Temp_Database.getEmployees_temp_database().put(employee.getID(), employee);
        return true;
    }


    public Temp_DataBase getTemp_Database() {
        return Temp_Database;
    }

    public void setTemp_Database(Temp_DataBase temp_Database) {
        Temp_Database = temp_Database;
    }




    public String createBranch(String name, String address, String ManagerID) throws IllegalArgumentException {
        if (name == null||address == null || ManagerID == null){
            throw new IllegalArgumentException("Argument's can't be NULL");
        }
        Branch branch=this.Temp_Database.getBranchByAddress(address);
        if(branch!=null){
            throw new IllegalArgumentException("There is already a branch at this address");
        }
        Employee employee=this.Temp_Database.getEmployeeByID(ManagerID);
        if(employee==null){
            throw new IllegalArgumentException("This employee does not exist in the system");
        }
        if (employee instanceof ManagerEmployee){
            Branch newbranch = new Branch(name,address,(ManagerEmployee) employee);
            this.Temp_Database.getBranch_temp_database().put(address,newbranch);
            return "Branch successfully created";
        }
        throw new IllegalArgumentException("Branch must be managed by a managerial employee");

    }
    public String createBranch(String name, String address) throws IllegalArgumentException {
        if (name == null||address == null){
            throw new IllegalArgumentException("Argument's can't be NULL");
        }
        Branch branch=this.Temp_Database.getBranchByAddress(address);
        if(branch!=null){
            throw new IllegalArgumentException("There is already a branch at this address");
        }
        Branch newbranch = new Branch(name, address);
        this.Temp_Database.getBranch_temp_database().put(address,newbranch);
        return "Branch successfully created";
    }
}
