package dev.src.services;
import dev.src.Temp_DataBase;
import dev.src.domain.*;

import java.time.LocalDate;
import java.util.List;

public class HRManagerEmployeeController {

//    private List<Branch> Branch_temp_database;
//    private List<Job> Employeejobs_temp_database;
//    private List<Employee> Employees_temp_database;

    private Temp_DataBase Temp_Database;
    public void HRManagerEmployeeService() {
        this.Temp_Database=new Temp_DataBase();
    }
    public Branch createBranch(String name){
        if(name==null){
            return null;
        }
        for (Branch b: this.Temp_Database.getBranch_temp_database()){
            if(b.getBranchName()==name){
                return null;
            }
        }
        Branch newbranch=new Branch(name);
        this.Temp_Database.getBranch_temp_database().add(newbranch);
        return newbranch;
    }

    public Job createJob(String name){
        if(name==null){
            return null;
        }
        for (Job j: this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==name){
                return null;
            }
        }
        Job newJob=new Job(name);
        this.Temp_Database.getEmployeejobs_temp_database().add(newJob);
        return newJob;
    }

    public ManagmantJob createManagingJob(String name){
        if(name==null){
            return null;
        }
        for (Job j: this.Temp_Database.getEmployeejobs_temp_database()){
            if(j.getJobName()==name){
                return null;
            }
        }
        ManagmantJob newJob=new ManagmantJob(name);
        this.Temp_Database.getEmployeejobs_temp_database().add(newJob);
        return newJob;
    }
    public Employee createEmployee(String name, String bank_accuont, String ID, Job job, Float salery,Branch branch){
        //NULL
        if(name==null|name.contains("[0-9]+")|bank_accuont==null|ID==null|job==null|salery==null||branch==null)
            return null;
        if(bank_accuont.length()!=12|bank_accuont.contains("[a-z,A-Z]+")){
            return null;
        }
        if(!this.Temp_Database.getEmployeejobs_temp_database().contains(job)){
            return null;
        }
        if(!this.Temp_Database.getBranch_temp_database().contains(job)){
            return null;
        }
        if (job instanceof ManagmantJob){
            return null;
        }
        if(salery<=0){
            return null;
        }
        if(ID.length()!=6|ID.contains("[a-z,A-Z]+")){
            return null;
        }
        for (Employee emp : this.Temp_Database.getEmployees_temp_database()) {
            if (emp.getID() == ID) {
                return null;
            }
        }

        LocalDate today=LocalDate.now();
        Employee NEWemployee=new Employee(name,bank_accuont,ID,job,salery,today,branch);
        this.Temp_Database.getEmployees_temp_database().add(NEWemployee);
        return NEWemployee;
    }

    public ManagementEmployee createManagmentEmployee(String name, String bank_accuont, String ID, Job job, Float salery, Branch branch){
        //NULL
        if(name==null|name.contains("[0-9]+")|bank_accuont==null|ID==null|job==null|salery==null|branch==null)
            return null;
        if(bank_accuont.length()!=12|bank_accuont.contains("[a-z,A-Z]+")){
            return null;
        }
        if(!this.Temp_Database.getEmployeejobs_temp_database().contains(job)){
            return null;
        }
        if(!this.Temp_Database.getBranch_temp_database().contains(branch)){
            return null;
        }
        if(salery<=0){
            return null;
        }
        if(ID.length()!=6|ID.contains("[a-z,A-Z]+")){
            return null;
        }
        for (Employee emp : this.Temp_Database.getEmployees_temp_database()) {
            if (emp.getID() == ID) {
                return null;
            }
        }
        if (job instanceof ManagmantJob) {
            LocalDate today = LocalDate.now();
            ManagementEmployee NEWemployee = new ManagementEmployee(name, bank_accuont, ID, (ManagmantJob)job, salery, today,branch);
            this.Temp_Database.getEmployees_temp_database().add(NEWemployee);
            return NEWemployee;
        }
        return null;
    }

    public Boolean addEmployee(Employee employee) {
        for (Employee emp : this.Temp_Database.getEmployees_temp_database()){
            if (emp==employee){
                return false;
            }
        }
        this.Temp_Database.getEmployees_temp_database().add(employee);
        return true;
    }

    public Employee getEmployeeByID(String ID) {
        for (Employee emp:this.Temp_Database.getEmployees_temp_database()){
            if(ID==emp.getID()){
                return emp;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return this.Temp_Database.getEmployees_temp_database();

    }

    public List<Job> getAlljobs() {
        return this.Temp_Database.getEmployeejobs_temp_database();
    }


    //UPDATE all (String name, String bank_accuont, Job job, Float salery,Date end_date) for all employees
    public boolean updateEmployeeNAME(String ID,String name) {
        if(ID==null|name==null|name.contains("[0-9]+")){
            return false;
        }
        Employee empToUpdate;
        for (Employee emp:this.Temp_Database.getEmployees_temp_database()){
            if(ID==emp.getID()){
                empToUpdate=emp;
                empToUpdate.setName(name);
                return true;
            }
        }
        return false;
    }

    public boolean updateEmployeeENDINGDATE(String ID,LocalDate end_date) {
        if(ID==null|end_date==null){
            return false;
        }
        Employee empToUpdate;
        for (Employee emp:this.Temp_Database.getEmployees_temp_database()){
            if(ID==emp.getID()){
                empToUpdate=emp;
                LocalDate start_date=empToUpdate.getStart_date();
                if(end_date.isAfter(start_date)){
                    if (empToUpdate.getEnd_date()==null){
                        empToUpdate.setEnd_date(end_date);
                        return true;
                    }
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public boolean updateEmployeeSALERY(String ID,float salery) {
        if(ID==null|salery<=0){
            return false;
        }
        Employee empToUpdate;
        for (Employee emp:this.Temp_Database.getEmployees_temp_database()){
            if(ID==emp.getID()){
                empToUpdate=emp;
                empToUpdate.setSalery(salery);
                return true;
            }
        }
        return false;
    }

    public boolean updateEmployeeJob(String ID,Job job) {
        if(ID==null|job==null){
            return false;
        }
        Employee empToUpdate;
        for (Employee emp:this.Temp_Database.getEmployees_temp_database()){
            if(ID==emp.getID()){
                empToUpdate=emp;
                if (empToUpdate instanceof ManagementEmployee){
                    if (job instanceof ManagmantJob){
                         return empToUpdate.AddJob(job);
                    }else {
                        return false;
                    }
                }
                if (job instanceof ManagmantJob){
                    return false;
                }
                empToUpdate.AddJob(job);
                return true;
            }
        }
        return false;
    }

    public boolean updateEmployeeBANK(String ID,String bank_accuont) {
        if(ID==null|bank_accuont==null|bank_accuont.length()!=12|bank_accuont.contains("[a-z,A-Z]+")){
            return false;
        }
        Employee empToUpdate;
        for (Employee emp:this.Temp_Database.getEmployees_temp_database()){
            if(ID==emp.getID()){
                empToUpdate=emp;
                empToUpdate.setBank_accuont(bank_accuont);
                return true;
            }
        }
        return false;
    }

    //UPDATE Employee TO ManagmantEmployee

    public boolean UPDATEemployeeToManagmantEmployee(String ID,ManagmantJob job){
        if(ID==null|job==null){
            return false;
        }
        Employee empToUpdate =getEmployeeByID(ID);
        if (empToUpdate!=null){
            if(empToUpdate instanceof ManagementEmployee){
               return false;
            }
            this.Temp_Database.getEmployees_temp_database().remove(empToUpdate);
            ManagementEmployee empToUpdateASmaneger =createManagmentEmployee(empToUpdate.getName(), empToUpdate.getBank_accuont(), empToUpdate.getID(), job, empToUpdate.getSalery(),empToUpdate.getBranch());
            if (empToUpdateASmaneger==null){
                return false;
            }
            return this.addEmployee(empToUpdateASmaneger);
        }
        return false;
    }

    //UPDATE Employee TO ManagmantEmployee with salery
    public boolean UPDATEemployeeToManagmantEmployee(String ID,ManagmantJob job,float salery){
        if(ID==null|job==null|salery<=0){
            return false;
        }
        Employee empToUpdate =getEmployeeByID(ID);
        if (empToUpdate!=null){
            if(empToUpdate instanceof ManagementEmployee){
                return false;
            }
            this.Temp_Database.getEmployees_temp_database().remove(empToUpdate);
            ManagementEmployee empToUpdateASmaneger =createManagmentEmployee(empToUpdate.getName(), empToUpdate.getBank_accuont(), empToUpdate.getID(), job, salery,empToUpdate.getBranch());
            if (empToUpdateASmaneger==null){
                return false;
            }
            return this.addEmployee(empToUpdateASmaneger);
        }
        return false;
    }


}


