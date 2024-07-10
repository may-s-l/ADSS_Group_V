package dev.src.Controllers;
import dev.src.Domain.*;
import dev.src.Domain.Enums.ShiftType;
import dev.src.Domain.Repository.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static dev.src.Domain.Shift.ChangingtheDifultNumberOfemployeesPerJob;
import static java.time.DayOfWeek.*;


public class HRManagerShiftController {

    private BranchRep Branch_temp_database;//String key address
    private JobRep Employeejobs_temp_database;
//    private MyMap<String, Employee> Employees_temp_database;//String key ID
    private EmployeeRep Employees_temp_database;
    private MyMap<Integer,MyMap<LocalDate, Week>> BranchWeek_temp_database;//INT keys BranchNUM
    private WeekRep History_Shifts_temp_database;
    private MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>> CurrentSchedule;
//    public HRManagerShiftController(List<Job> Employeejobs_temp_database,MyMap<String, Branch> Branch_temp_database,MyMap<String, Employee> Employees_temp_database,MyMap<Integer,MyMap<LocalDate, String>> History_Shifts_temp_database) {
//        this.Employees_temp_database=Employees_temp_database;
//        this.Branch_temp_database=Branch_temp_database;
//        this.Employeejobs_temp_database=Employeejobs_temp_database;
//        this.BranchWeek_temp_database=new MyMap<Integer,MyMap<LocalDate,Week>>();
//        this.History_Shifts_temp_database=History_Shifts_temp_database;
//    }

    public HRManagerShiftController(JobRep Employeejobs_temp_database, BranchRep Branch_temp_database, EmployeeRep Employees_temp_database, WeekRep History_Shifts_temp_database) {
        this.Employees_temp_database=Employees_temp_database;
        this.Branch_temp_database=Branch_temp_database;
        this.Employeejobs_temp_database=Employeejobs_temp_database;
        this.BranchWeek_temp_database=new MyMap<Integer,MyMap<LocalDate,Week>>();
        this.History_Shifts_temp_database=History_Shifts_temp_database;
    }


    //======================Functions for working on weekly employee placement=====================================================//
    public String MakeScheduleforNextWeek(int branchNum,String date){
//        if(!isItTheTIMEtoAssignmenttToShifts()){
//            throw new IllegalArgumentException("You can start placement on Thursday and Friday");
//        }
        LocalDate Ldate = LocalDate.parse(date);
        if(!istenextSunday(Ldate)){
            throw new IllegalArgumentException("Placement can be started for the next week only");
        }
        Branch branch =getBranchByBranchNUM(branchNum);
        MyMap<Integer, Employee>BranchemployeeBYemployeeNUM = new MyMap<Integer, Employee>();
        List<List<Object>> TableofEmployeeandConstrin =createEmployeeConstraintJobTable(branch.getEmployeesInBranch(),date,BranchemployeeBYemployeeNUM);
        Week Weekforassignment = createWeekforassignment(date,branch);
        if(this.BranchWeek_temp_database.containsKey(branchNum)){
            MyMap<LocalDate,Week> dateWeek= this.BranchWeek_temp_database.get(branchNum);
            dateWeek.put(Weekforassignment.getStart_date(),Weekforassignment);
        }
        else {
            MyMap<LocalDate, Week> dateWeekMyMap = new MyMap<LocalDate, Week>();
            dateWeekMyMap.put(Weekforassignment.getStart_date(), Weekforassignment);
            this.BranchWeek_temp_database.put(branchNum,dateWeekMyMap);
        }
        MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>> pair = new MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>>(Weekforassignment,TableofEmployeeandConstrin,BranchemployeeBYemployeeNUM);
        this.CurrentSchedule=pair;
        return "Schedule Successfully created:) \n"+ toStringforweekANDemlpoyeeinbanc(pair.getFirst(),pair.getSecond());
    }
    public List<Object> checkaddEmployeesToShiftsByDateANDJob(List<Integer> employeeNum,String jobname,String shiftype,String date) throws IllegalArgumentException{
        Week week=this.getWeek();
        if(employeeNum==null||employeeNum.isEmpty()||jobname==null||shiftype==null||date==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
            throw new IllegalArgumentException("Date must be in week of work");
        }
        if(week.getDayOfWeek(dateToCheck).isIsdayofrest()){
            throw new IllegalArgumentException("THIS day is day off");
        }

        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Shift type must be morning or evening");
        }
        //-------Job-------//
        int i=1;
        if(shiftype.equals("MORNING")){
            i=0;
        }
        Shift shiftassignment=week.getDayOfWeek(dateToCheck).getShiftsInDay()[i];
        jobname=jobname.toUpperCase();
        Job job = null;
        for (Job j : shiftassignment.getAllJobInShift()) {
            if (j.getJobName().equals(jobname)) {
                job = j;
                break;
            }
        }
        if (job == null) {
            throw new IllegalArgumentException("Job does not exist in this Shift");
        }
        if(shiftassignment.isJobInShiftisFull(job)){
            throw new IllegalArgumentException("Job is in max capacity for this Shift");
        }
        List<Object> CleanInformation =new ArrayList<Object>() ;
        CleanInformation.add(employeeNum);
        CleanInformation.add(shiftassignment);
        CleanInformation.add(job);
        return CleanInformation;
    }
    public String addEmployeetoshift(List<Object> empsNUM_shift_job)throws IllegalArgumentException{
        MyTripel<Week,List<List<Object>>,MyMap<Integer, Employee>> WeekAndConstrainAndMAPemployee= this.CurrentSchedule;
        if(empsNUM_shift_job==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        Week week =WeekAndConstrainAndMAPemployee.getFirst();
        MyMap<Integer, Employee> empMAP=WeekAndConstrainAndMAPemployee.getThird();
        List<Integer> employeeNum=(List<Integer>)empsNUM_shift_job.get(0);
        Shift shift=(Shift) empsNUM_shift_job.get(1);
        Job job=(Job) empsNUM_shift_job.get(2);
        String s="";
        for(int e:employeeNum){
            Employee emp_to_workon=null;
            if(!(empMAP.containsKey(e))){
                s+= e+"- Employee NUMBER is not in this branch or DATA\n";
                continue;
            }
            emp_to_workon=empMAP.get(e);
            if(!emp_to_workon.employeeCanbe(job)){
                s+=emp_to_workon.toString()+" Can't work as "+job.getJobName()+"\n";
                continue;
            }
            if(emp_to_workon.getConstraintByDate(shift.getDate())!=null&&(emp_to_workon.getConstraintByDate(shift.getDate()).getShiftType().equals(shift.getShiftType())||emp_to_workon.getConstraintByDate(shift.getDate()).getShiftType()== ShiftType.FULLDAY)){
                s+=emp_to_workon.toString()+" there is a shift constraint "+emp_to_workon.getConstraintByDate(shift.getDate()).toString()+"\n";
                continue;
            }
            if(shift.isJobInShiftisFull(job)){
                s+=emp_to_workon.toString()+" the number of worker needed is already full \n";
                continue;
            }
            shift.addEmployeeToShift(emp_to_workon,job);
        }
        return s + "\n"+ toStringforweekANDemlpoyeeinbanc(WeekAndConstrainAndMAPemployee.getFirst(),WeekAndConstrainAndMAPemployee.getSecond());

    }
    public String removeEmployeefromShift(int employeenum, String shiftype,String date){
        Week week=this.getWeek();
        if(employeenum<=0||shiftype==null||date==null){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
            throw new IllegalArgumentException("Date must be in week of work");
        }
        if(week.getDayOfWeek(dateToCheck).isIsdayofrest()){
            throw new IllegalArgumentException("THIS day is day off");
        }

        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Shift type must be morning or evening");
        }
        //-------Employee------//
        MyMap<Integer, Employee> empMAP=this.CurrentSchedule.getThird();
        if(!empMAP.containsKey(employeenum)){
            throw  new IllegalArgumentException("Employee not exist");
        }
        int i=1;
        if(shiftype.equals("MORNING")){
            i=0;
        }
        Employee emp=empMAP.get(employeenum);
        MyMap<Employee,Job> MAP=week.getDayOfWeek(dateToCheck).getShiftsInDay()[i].getEmployeeinshiftMap();
        if(!MAP.containsKey(emp)){
            throw new IllegalArgumentException("Employee is not work in this shift");
        }
        MAP.remove(emp);
        return emp+" successfully removed\n" +  toStringforweekANDemlpoyeeinbanc(week, this.CurrentSchedule.getSecond());

    }
    public Week getWeek() {
        return this.CurrentSchedule.getFirst();
    }
    public String toStringforweekANDemlpoyeeinbanc(Week week,List<List<Object>> employeeTable){
        return toStringEmployeeConstraintJobTable(employeeTable)+week.weekInTableToShow();
    }
    private List<List<Object>> createEmployeeConstraintJobTable(EmployeeRep employeeInBranch, String date,MyMap<Integer, Employee>BranchemployeeBYemployeeNUM) {
        List<List<Object>> employeesWithConstraints = new ArrayList<>();
        List<List<Object>> employeesWithoutConstraints = new ArrayList<>();
        Set<String> employeeIDs = employeeInBranch.getKeys();
        LocalDate startDay = LocalDate.parse(date);

        if (startDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            for (String id : employeeIDs) {
                List<Constraint> constraintList = new ArrayList<>();
                Employee emp = employeeInBranch.find(id);

                for (int i = 0; i < 7; i++) {
                    LocalDate constraintDate = startDay.plusDays(i);
                    Constraint constraint = emp.getConstraintByDate(constraintDate);

                    if (constraint != null) {
                        constraintList.add(constraint);
                    }
                }

                List<Object> employeeData = new ArrayList<>();
                BranchemployeeBYemployeeNUM.put(emp.getEmployeeNum(),emp);
                employeeData.add(emp);
                employeeData.add(emp.getJobs());
                employeeData.add(constraintList);

                if (constraintList.isEmpty()) {
                    employeesWithoutConstraints.add(employeeData);
                } else {
                    employeesWithConstraints.add(employeeData);
                }
            }
        }

        List<List<Object>> Table = new ArrayList<>(employeesWithConstraints);
        Table.addAll(employeesWithoutConstraints);
        return Table;
    }
    private Week createWeekforassignment(String date,Branch branch) {
        LocalDate Ldate = LocalDate.parse(date);
        if (Ldate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            Week week = new Week(Ldate,branch);
            Day day;
            for (int i = 0; i < 7; i++) {
                day = week.getDayOfWeek(Ldate.plusDays(i));
                if (!day.isIsdayofrest()) {
                    for (int z=0;z<Employeejobs_temp_database.getsize();z++) {
                        Job j=Employeejobs_temp_database.getJobByIndex(z);
                        if (!(j instanceof ManagementJob)) {
                            day.getShiftsInDay()[0].addJobToShift(j);
                            day.getShiftsInDay()[1].addJobToShift(j);
                        }
                    }
                }
            }
            return week;
        }
        throw new IllegalArgumentException("Date of start must be SUNDAY");
    }
    private Branch getBranchByBranchNUM(int branchnum)throws IllegalArgumentException{
        List<Branch> allBranch=getAllBranch();
        Branch branch=null;
        for(Branch b:allBranch){
            if(b.getBranchNum()==branchnum){
                branch=b;
                break;
            }
        }
        if (branch==null){
            throw new IllegalArgumentException("Branch is NOT exist");
        }
        return branch;
    }
    private List<Branch> getAllBranch() {
        Set<String>Brenchskey=this.Branch_temp_database.getKeys();
        List<Branch> Allbranch=new ArrayList<Branch>();
        for(String b:Brenchskey){
            Allbranch.add(this.Branch_temp_database.find(b));
        }
        return Allbranch;
    }
    public String toStringEmployeeConstraintJobTable(List<List<Object>> employeeTable) {

        int idWidth = 25;
        int jobsWidth = 40;
        int constraintsWidth = 40;

        StringBuilder tableBuilder = new StringBuilder();

        tableBuilder.append(String.format("| %-" + idWidth + "s | %-" + jobsWidth + "s | %-" + constraintsWidth + "s |%n",
                "Employee ID", "Jobs", "Constraints"));
        tableBuilder.append(String.join("", Collections.nCopies(idWidth + jobsWidth + constraintsWidth + 8, "-"))).append("\n");

        for (List<Object> employeeData : employeeTable) {
            Employee employee = (Employee) employeeData.get(0);
            EJobsRep jobs = (EJobsRep) employeeData.get(1);
            List<Constraint> constraints = (List<Constraint>) employeeData.get(2);

            String jobsString =  jobs.isEmpty() ? "None" : jobs.stream()
                    .map(Job::toString)
                    .collect(Collectors.joining(", "));
            String constraintsString = constraints.isEmpty() ? "None" : constraints.stream()
                    .map(Constraint::toString)
                    .collect(Collectors.joining(", "));

            tableBuilder.append(String.format("| %-" + idWidth + "s | %-" + jobsWidth + "s | %-" + constraintsWidth + "s |%n",
                    employee, jobsString, constraintsString));
        }

        return tableBuilder.toString();
    }
    public boolean isItTheTIMEtoAssignmenttToShifts(){
        LocalDate today= LocalDate.now();
        DayOfWeek week_day=today.getDayOfWeek();
        if(week_day.equals(THURSDAY)||week_day.equals(FRIDAY)){
            return true;
        }
        return false;
    }
    public String isWeekcanbeclose(){
        Week week=this.getWeek();
        LocalDate strat_day=week.getStart_date();
        String S="";
        for (int i=0 ;i<7;i++){
            Day day = week.getDayOfWeek(strat_day.plusDays(i));
            if(day.isIsdayofrest()){
                continue;
            }
            Shift[] shifts = day.getShiftsInDay();
            for (int j=0;j<=shifts.length-1;j++){
                Shift shift=shifts[j];
                for(Job job:shift.getAllJobInShift()){
                    if(shift.isJobInShiftisFull(job)){
                        continue;
                    }
                    else {
                        S += "In " + day.getDate() + " " + job + "is not full \n";
                    }

                }
            }
        }
        if(S.isEmpty()&&isDriverandStorkeeperInShift()){
            String weekstring=week.weekInTableToShow();
            Set<Employee> EMP=week.getDayOfWeek(week.getStart_date()).getShiftsInDay()[0].getEmployeeinshiftSet();
            List<Employee> myList = new ArrayList<>(EMP);
            int branchNum= myList.get(0).getBranch().getBranchNum();
            String s=branchNum+","+week.getStart_date();
            if(this.History_Shifts_temp_database.find(s)==null){
//                Week dateWeek= this.History_Shifts_temp_database.find(s);
//                dateWeek.put(week.getStart_date(),weekstring);
                this.History_Shifts_temp_database.add(week);
            }
//            else {
//                MyMap<LocalDate, String> dateWeekMyMap = new MyMap<LocalDate, String>();
//                dateWeekMyMap.put(week.getStart_date(),weekstring);
//                this.History_Shifts_temp_database.put(branchNum,dateWeekMyMap);
//            }

            return "All positions have been filled \n" +toStringforweekANDemlpoyeeinbanc(week,CurrentSchedule.getSecond());
        }
        if(!isDriverandStorkeeperInShift()){
            S+="A driver MUST receive service from STOREKEEPER. Please schedule warehousemen on days when there are drivers \n";

        }
        return S+=toStringforweekANDemlpoyeeinbanc(week,CurrentSchedule.getSecond());
    }
    public String addEmployeetoall_Shiftinweek(Integer employeeNum,String jobname,String shiftype) throws IllegalArgumentException{
        Week week=this.CurrentSchedule.getFirst();
        if(employeeNum==null||jobname==null||shiftype==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Shift type must be morning or evening");
        }
        //-------Job-------//
        int i=1;
        if(shiftype.equals("MORNING")){
            i=0;
        }
        String S="";
        Set<LocalDate> dayofweek=week.getDayInWEEK();
        for(LocalDate dateToCheck :dayofweek ) {
            if(!week.getDayOfWeek(dateToCheck).isIsdayofrest()) {
                Shift shiftassignment = week.getDayOfWeek(dateToCheck).getShiftsInDay()[i];
                jobname = jobname.toUpperCase();
                Job job = null;
                for (Job j : shiftassignment.getAllJobInShift()) {
                    if (j.getJobName().equals(jobname)) {
                        job = j;
                        break;
                    }
                }
                if (job == null) {
                    S+="Job "+jobname+ " does not exist in this Shift "+dateToCheck+"\n";
                    continue;
                }
                if (shiftassignment.isJobInShiftisFull(job)) {
                    S+="Job "+job +" is in max capacity for this Shift " +dateToCheck+"\n";
                    continue;
                }
                if (!this.CurrentSchedule.getThird().containsKey(employeeNum)) {
                    throw new IllegalArgumentException("Employee NOT exist");
                }
                Employee emp_to_workon = this.CurrentSchedule.getThird().get(employeeNum);
                if (!emp_to_workon.employeeCanbe(job)) {
                    S+= emp_to_workon.toString() + " Can't work as " + job.getJobName();
                    continue;

                }
                if (emp_to_workon.getConstraintByDate(shiftassignment.getDate()) != null && (emp_to_workon.getConstraintByDate(shiftassignment.getDate()).getShiftType().equals(shiftassignment.getShiftType()) || emp_to_workon.getConstraintByDate(shiftassignment.getDate()).getShiftType() == ShiftType.FULLDAY)) {
                    S+=" there is a shift constraint " + emp_to_workon.getConstraintByDate(shiftassignment.getDate()).toString();
                    continue;
                }
                shiftassignment.addEmployeeToShift(emp_to_workon, job);
            }
        }
        return S += "\n"+toStringforweekANDemlpoyeeinbanc(week,this.CurrentSchedule.getSecond());

    }
    private boolean istenextSunday(LocalDate startday_sc){
        LocalDate today=LocalDate.now();
        if(today.getDayOfWeek()==SUNDAY){
            today=today.plusDays(1);
        }
        while (today.getDayOfWeek()!=SUNDAY){
            today=today.plusDays(1);
        }
        if(startday_sc.isEqual(today)){
            return true;
        }
        return false;
    }
    private boolean isDriverandStorkeeperInShift() {
        Week week = this.CurrentSchedule.getFirst();
        LocalDate strat_day = week.getStart_date();
        for (int i = 0; i < 7; i++) {
            Day day = week.getDayOfWeek(strat_day.plusDays(i));
            if (day.isIsdayofrest()) {
                continue;
            }
            Shift[] shifts = day.getShiftsInDay();
            for (int j = 0; j <= shifts.length - 1; j++) {
                Shift shift = shifts[j];
                for (Job job : shift.getAllJobInShift()) {
                    if (job.getJobName().equals("DRIVER") && shift.getNumberofWorkersPerJob(job)>=1){
                        for (Job jobpassto : shift.getAllJobInShift()) {
                            if(jobpassto.getJobName().equals("STOREKEEPER")){
                                if (shift.getNumberofWorkersPerJob(jobpassto) <1) {
                                    return false;
                                }
                            }
                            continue;
                        }
                    }
                }
            }
        }
        return true;
    }

    //---------------------------Functions for changing default values for a specific shift-----------------------------------------//
    public String ChangingdefaultvaluesinSpecificShiftNUMworkertoJob(String date,String shiftype,String jobname,int numworker)throws IllegalArgumentException{
        Week week=this.getWeek();
        if(jobname==null||shiftype==null||date==null||numworker<0){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
            throw new IllegalArgumentException("Date must be in week of work");
        }
        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Job type must be morning or evening");
        }
        //-------Job-------//
        int i=1;
        if(shiftype.equals("MORNING")){
            i=0;
        }
        if(week.getDayOfWeek(dateToCheck).isIsdayofrest()){
            throw new IllegalArgumentException("Day must be work day");
        }
        Shift shift=week.getDayOfWeek(dateToCheck).getShiftsInDay()[i];
        Job job = null;
        for (int z=0;z<Employeejobs_temp_database.getsize();z++) {
            Job j=Employeejobs_temp_database.getJobByIndex(z);
            if (Objects.equals(j.getJobName(), jobname)) {
                job = j;
                break;
            }
        }
        if (job == null) {
            throw new IllegalArgumentException("Job does not exist");
        }
        if(numworker==0){
            if (Objects.equals(job.getJobName(), "SHIFT MANAGER")){
                throw new IllegalArgumentException("Must be minimum 1 SHIFT MANAGER");
            }
        }
        shift.ChangingTheNumberOfemployeesPerJobInShift(job,numworker);
        return "The number of worker for "+jobname+" is change to "+numworker+"\n"+toStringforweekANDemlpoyeeinbanc(week,CurrentSchedule.getSecond());

    }

    public String ChangingdefaultvaluesinSpecificShiftWORKHoursStart_End(String date,String shiftype,String start_time,String end_time){
        Week week=this.getWeek();
        if(start_time==null||end_time==null||shiftype==null||date==null||week==null){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
            throw new IllegalArgumentException("Date must be in week of work");
        }
        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Job type must be morning or evening");
        }
        //-------Time-------//
        LocalTime start = LocalTime.parse(start_time);
        LocalTime end = LocalTime.parse(end_time);
        if(start.isAfter(end)){
            throw new IllegalArgumentException("Ending shift time must be after Start of shift");
        }
        int i=1;
        if(shiftype.equals("MORNING")){
            i=0;
        }
        if(week.getDayOfWeek(dateToCheck).isIsdayofrest()){
            throw new IllegalArgumentException("Day must be work day");
        }
        Shift shift=week.getDayOfWeek(dateToCheck).getShiftsInDay()[i];
        shift.setStart_time(start);
        shift.setEnd_time(end);
        return "Shift Hours is change to "+start+"-"+end;
    }

    public String ChangingdefaultvaluesinSpecificDayDAY_OFF(String date,String bool){
        Week week=this.getWeek();
        if(date==null||week==null||bool==null){
            throw new IllegalArgumentException("Arguments can not be NULL");
        }
        //-------date------//
        LocalDate dateToCheck= LocalDate.parse(date);
        if(!((dateToCheck.isEqual(week.getStart_date()) || dateToCheck.isAfter(week.getStart_date())) && (dateToCheck.isEqual(week.getEnd_date()) || dateToCheck.isBefore(week.getEnd_date())))){
            throw new IllegalArgumentException("Date must be in week of work");
        }
        Day day=week.getDayOfWeek(dateToCheck);
        boolean b=true;
        if(!(bool.toUpperCase().equals("F")||bool.toUpperCase().equals("T"))){
            throw new IllegalArgumentException("is a day of must be F/T");
        }
        if(bool.toUpperCase().equals("F")){
            b=false;
        }
        if(day.isIsdayofrest()!=b){
            day.setIsdayofrest(b);
            if(b) {
                return "Day " + dateToCheck + " change to day off";
            }
            return "Day " + dateToCheck + " change to work day";
        }
        if(b) {
            throw new IllegalArgumentException("Day " + dateToCheck + " IS ALREADY day off");
        }
        throw new IllegalArgumentException("Day " + dateToCheck +" IS ALREADY work day");

    }


    //---------------------------Changing default values for the placement system---------------------------------------------------//

    public String ChangingdefaultvaluesforALLShiftNUMworkertoJob(String jobname,int numworker){
        if(jobname==null||numworker<0){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }

        Job job = null;
        for (int z=0;z<Employeejobs_temp_database.getsize();z++) {
            Job j=Employeejobs_temp_database.getJobByIndex(z);
            if (j.getJobName().equals(jobname)) {
                job = j;
                break;
            }
        }
        if (job == null) {
            throw new IllegalArgumentException("Job does not exist");
        }
        if(numworker==0){
            if (job.getJobName().equals("SHIFT MANAGER")){
                throw new IllegalArgumentException("Must be minimum 1 SHIFT MANAGER");
            }
        }
        ChangingtheDifultNumberOfemployeesPerJob(job,numworker);
        return "The number of worker for "+jobname+" is change to "+numworker;


    }

    public String ChangingdefaultvaluesforALLshiftWORKHoursStart_End(String shiftype,String start_time,String end_time){
        if(start_time==null||end_time==null||shiftype==null){
            throw new IllegalArgumentException("Argumets can not be NULL");
        }
        //-------EnumShiftType-------//
        shiftype=shiftype.toUpperCase();
        if (!shiftype.equals("MORNING") && !shiftype.equals("EVENING")) {
            throw new IllegalArgumentException("Job type must be morning or evening");
        }
        //-------Time-------//
        LocalTime start = LocalTime.parse(start_time);
        LocalTime end = LocalTime.parse(end_time);
        if(start.isAfter(end)){
            throw new IllegalArgumentException("Ending shift time must be after Start of shift");
        }
        if(shiftype.equals("MORNING")){
            MorningShift.setDs_time(start);
            MorningShift.setDe_time(end);
        }
        if(shiftype.equals("EVENING")){
            EveningShift.setDs_time(start);
            EveningShift.setDe_time(end);
        }

        return shiftype+" Shift Hours is change to "+start+"-"+end;
    }


}