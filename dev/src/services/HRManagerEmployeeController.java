package services;

import domain.Employee;
import domain.Job;
import domain.ManagmantJob;
import domain.Managment_Employee;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

public class HRManagerEmployeeController {
    private List<Job> Employeejobs_temp_database;
    private List<Employee> Employees_temp_database;

    public void HRManagerEmployeeService() {
        this.Employees_temp_database = new ArrayList<Employee>();
        this.Employeejobs_temp_database=new ArrayList<Job>();

    }
    public Job createJob(String name){
        if(name==null){
            return null;
        }
        for (Job j: Employeejobs_temp_database){
            if(j.getJobName()==name){
                return null;
            }
        }
        Job newJob=new Job(name);
        Employeejobs_temp_database.add(newJob);
        return newJob;
    }

    public Job createManagingJob(String name){
        if(name==null){
            return null;
        }
        for (Job j: Employeejobs_temp_database){
            if(j.getJobName()==name){
                return null;
            }
        }
        ManagmantJob newJob=new ManagmantJob(name);
        Employeejobs_temp_database.add(newJob);
        return newJob;
    }
    public Employee createEmployee(String name, String bank_accuont, String ID, Job job, Float salery){
        //NULL
        if(name==null|name.contains("[0-9]+")|bank_accuont==null|ID==null|job==null|salery==null)
            return null;
        if(bank_accuont.length()!=12|bank_accuont.contains("[a-z,A-Z]+")){
            return null;
        }
        if(!Employeejobs_temp_database.contains(job)){
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
        for (Employee emp : Employees_temp_database) {
            if (emp.getID() == ID) {
                return null;
            }
        }
        LocalDate today=LocalDate.now();
        Employee NEWemployee=new Employee(name,bank_accuont,ID,job,salery,today);
        this.Employees_temp_database.add(NEWemployee);
        return NEWemployee;
    }

    public Managment_Employee createManagmentEmployee(String name, String bank_accuont, String ID, Job job, Float salery){
        //NULL
        if(name==null|name.contains("[0-9]+")|bank_accuont==null|ID==null|job==null|salery==null)
            return null;
        if(bank_accuont.length()!=12|bank_accuont.contains("[a-z,A-Z]+")){
            return null;
        }
        if(!Employeejobs_temp_database.contains(job)){
            return null;
        }
        if(salery<=0){
            return null;
        }
        if(ID.length()!=6|ID.contains("[a-z,A-Z]+")){
            return null;
        }
        for (Employee emp : Employees_temp_database) {
            if (emp.getID() == ID) {
                return null;
            }
        }
        if (job instanceof ManagmantJob) {
            LocalDate today = LocalDate.now();
            Managment_Employee NEWemployee = new Managment_Employee(name, bank_accuont, ID, (ManagmantJob)job, salery, today);
            this.Employees_temp_database.add(NEWemployee);
            return NEWemployee;
        }
        return null;
    }

    public Boolean addEmployee(Employee employee) {
        for (Employee emp : Employees_temp_database){
            if (emp==employee){
                return false;
            }
        }
        Employees_temp_database.add(employee);
        return true;
    }

    public Employee getEmployeeByID(String ID) {
        for (Employee emp:Employees_temp_database){
            if(ID==emp.getID()){
                return emp;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return Employees_temp_database;

    }

    public List<Job> getAlljobs() {
        return this.Employeejobs_temp_database;
    }


    //UPDATE all (String name, String bank_accuont, Job job, Float salery,Date end_date) for all employees
    public boolean updateEmployeeNAME(String ID,String name) {
        if(ID==null|name==null|name.contains("[0-9]+")){
            return false;
        }
        Employee empToUpdate;
        for (Employee emp:Employees_temp_database){
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
        for (Employee emp:Employees_temp_database){
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
        for (Employee emp:Employees_temp_database){
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
        for (Employee emp:Employees_temp_database){
            if(ID==emp.getID()){
                empToUpdate=emp;
                if (empToUpdate instanceof Managment_Employee){
                    if (job instanceof ManagmantJob){
                        empToUpdate.setJob(job);
                    }else {
                        return false;
                    }
                }
                if (job instanceof ManagmantJob){
                    return false;
                }
                empToUpdate.setJob(job);
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
        for (Employee emp:Employees_temp_database){
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
            if(empToUpdate instanceof Managment_Employee){
               return false;
            }
            Employees_temp_database.remove(empToUpdate);
            Managment_Employee empToUpdateASmaneger =createManagmentEmployee(empToUpdate.getName(), empToUpdate.getBank_accuont(), empToUpdate.getID(), job, empToUpdate.getSalery());
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
            if(empToUpdate instanceof Managment_Employee){
                return false;
            }
            Employees_temp_database.remove(empToUpdate);
            Managment_Employee empToUpdateASmaneger =createManagmentEmployee(empToUpdate.getName(), empToUpdate.getBank_accuont(), empToUpdate.getID(), job, salery);
            if (empToUpdateASmaneger==null){
                return false;
            }
            return this.addEmployee(empToUpdateASmaneger);
        }
        return false;
    }
}


