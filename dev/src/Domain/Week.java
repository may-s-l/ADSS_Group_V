package dev.src.Domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;


public class Week {
    private int weekNUM;
    private Branch branch;
    private LocalDate start_date;
    private LocalDate end_date;
    private MyMap<LocalDate, Day> DayInWEEK;


    public Week(LocalDate start_date,Branch B) {
        this.branch=B;
        this.start_date = start_date;
        this.DayInWEEK = new MyMap<LocalDate, Day>();
        LocalDate date = start_date;
        Day d = null;
        for (int i = 0; i < 7; i++) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY) {
                d = new Day(false, date);
                this.DayInWEEK.put(date, d);
                if (date.getDayOfWeek() == DayOfWeek.TUESDAY) {
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    this.weekNUM = date.get(weekFields.weekOfWeekBasedYear());
                }
            } else {
                d = new Day(true, date);
                this.DayInWEEK.put(date, d);
                this.end_date = date;
            }
            date = date.plusDays(1);
        }
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public int getWeekNUM() {
        return weekNUM;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public Set<LocalDate> getDayInWEEK() {
        return DayInWEEK.getKeys();
    }

    public void setDayInWEEK(MyMap<LocalDate, Day> dayInWEEK) {
        DayInWEEK = dayInWEEK;
    }

    public Day getDayOfWeek(LocalDate date) {
        return this.DayInWEEK.get(date);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Week number: ").append(weekNUM)
                .append(" ").append(start_date).append(" - ").append(end_date).append("\n");

        for (LocalDate date : DayInWEEK.getKeys()) {
            sb.append(DayInWEEK.get(date)).append("\n");
        }

        return sb.toString();
    }


    public String weekInTableToShow() {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        int jobWidth = 13;
        int shiftWidth = 19;
        int daydateWidth = 43;
        int rowsPerJob = 3;

        sb1.append("Week number: ").append(weekNUM).append("\n");
        sb1.append("Start date: ").append(start_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
        sb1.append("End date: ").append(end_date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n\n");

        // Header Row: Job | Day of Week Date | Day of Week Date | ...
        sb1.append(String.format("| %-" + jobWidth + "s |", "Job"));
        sb2.append(String.format("| %-" + jobWidth + "s |", "Job"));
        sb3.append(String.format("| %-" + jobWidth + "s |", "Job"));

        for (LocalDate date : DayInWEEK.getKeys()) {
            Day day = DayInWEEK.get(date);
            if(day.getDayOfWeek()==DayOfWeek.SUNDAY||day.getDayOfWeek()==DayOfWeek.MONDAY||day.getDayOfWeek()==DayOfWeek.THURSDAY){
                sb1.append(String.format("%-" + daydateWidth + "s|", day.getDayOfWeek() + " " + day.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            }
            if(day.getDayOfWeek()==DayOfWeek.SATURDAY){
                sb3.append(String.format("%-" + daydateWidth + "s|", day.getDayOfWeek() + " " + day.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            }
            if(day.getDayOfWeek()==DayOfWeek.WEDNESDAY||day.getDayOfWeek()==DayOfWeek.TUESDAY||day.getDayOfWeek()==DayOfWeek.FRIDAY){
                sb2.append(String.format("%-" + daydateWidth + "s|", day.getDayOfWeek() + " " + day.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            }
        }
        sb1.append("\n");
        sb2.append("\n");
        sb3.append("\n");

        // Header Row: Morning (Start-End) | Evening (Start-End) | ...
        sb1.append(String.format("| %-" + jobWidth + "s |", ""));
        sb2.append(String.format("| %-" + jobWidth + "s |", ""));
        sb3.append(String.format("| %-" + jobWidth + "s |", ""));
        Set<Job> jobsToFilluplod=null;
        for (LocalDate date : DayInWEEK.getKeys()) {
            Day day = DayInWEEK.get(date);
            if (!day.isIsdayofrest()) {
                Shift morningShift = day.getShiftsInDay()[0];
                Shift eveningShift = day.getShiftsInDay()[1];

                String morningShiftHeader = String.format("Morning(%s-%s)",
                        morningShift.getStart_time().toString(),
                        morningShift.getEnd_time().toString());
                        jobsToFilluplod=morningShift.getAllJobInShift();

                String eveningShiftHeader = String.format("Evening(%s-%s)",
                        eveningShift.getStart_time().toString(),
                        eveningShift.getEnd_time().toString());

                if(day.getDayOfWeek()==DayOfWeek.SUNDAY||day.getDayOfWeek()==DayOfWeek.MONDAY||day.getDayOfWeek()==DayOfWeek.THURSDAY){
                    sb1.append(String.format(" %-" + shiftWidth + "s| %-" + shiftWidth + "s|", morningShiftHeader, eveningShiftHeader));
                }
                if(day.getDayOfWeek()==DayOfWeek.SATURDAY){
                    sb3.append(String.format(" %-" + shiftWidth + "s| %-" + shiftWidth + "s|", morningShiftHeader, eveningShiftHeader));
                }
                if(day.getDayOfWeek()==DayOfWeek.WEDNESDAY||day.getDayOfWeek()==DayOfWeek.TUESDAY||day.getDayOfWeek()==DayOfWeek.FRIDAY){
                    sb2.append(String.format(" %-" + shiftWidth + "s| %-" + shiftWidth + "s|", morningShiftHeader, eveningShiftHeader));
                }
            } else {
                if(day.getDayOfWeek()==DayOfWeek.SUNDAY||day.getDayOfWeek()==DayOfWeek.MONDAY||day.getDayOfWeek()==DayOfWeek.THURSDAY){
                    sb1.append(String.format(" %-" + shiftWidth + "s| %-" + shiftWidth + "s|", "Day off", "Day off"));
                }
                if(day.getDayOfWeek()==DayOfWeek.SATURDAY){
                    sb3.append(String.format(" %-" + shiftWidth + "s| %-" + shiftWidth + "s|", "Day off", "Day off"));
                }
                if(day.getDayOfWeek()==DayOfWeek.WEDNESDAY||day.getDayOfWeek()==DayOfWeek.TUESDAY||day.getDayOfWeek()==DayOfWeek.FRIDAY){
                    sb2.append(String.format(" %-" + shiftWidth + "s| %-" + shiftWidth + "s|", "Day off", "Day off"));
                }
            }
        }
        sb1.append("\n");
        sb2.append("\n");
        sb3.append("\n");

        // Separator line
        sb1.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * 3+shiftWidth+3 , "-"))).append("\n");
        sb2.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * 3+shiftWidth+3 , "-"))).append("\n");
        sb3.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * 1+shiftWidth-9 , "-"))).append("\n");

        Set<Job> jobsToFill = Shift.getNumberofWorkersPerPositionDifult().getKeys(); // Assuming all days have the same jobs to fill
        if(jobsToFill.size()==0&&jobsToFilluplod.size()>0){
            jobsToFill=jobsToFilluplod;
        }

        for (Job job : jobsToFill) {
            for (int row = 0; row < rowsPerJob; row++) {
                if (row == 0) {
                    sb1.append(String.format("| %-" + jobWidth + "s |", job.getJobName()));
                    sb2.append(String.format("| %-" + jobWidth + "s |", job.getJobName()));
                    sb3.append(String.format("| %-" + jobWidth + "s |", job.getJobName()));
                } else {
                    sb1.append(String.format("| %-" + jobWidth + "s |", ""));
                    sb2.append(String.format("| %-" + jobWidth + "s |", ""));
                    sb3.append(String.format("| %-" + jobWidth + "s |", ""));
                }

                for (LocalDate date : DayInWEEK.getKeys()) {
                    Day day = DayInWEEK.get(date);

                    String morningEmployee = "";
                    String eveningEmployee = "";
                    String morningRequired = "";
                    String eveningRequired = "";

                    if (!day.isIsdayofrest()) {
                        Shift morningShift = day.getShiftsInDay()[0];
                        Shift eveningShift = day.getShiftsInDay()[1];

                        List<Employee> morningShiftEmployees = new ArrayList<>();
                        for (Employee emp : morningShift.getEmployeeinshiftSet()) {
                            if (morningShift.getEmployeeinshiftMap().get(emp) == job) {
                                morningShiftEmployees.add(emp);
                            }
                        }
                        List<Employee> eveningShiftEmployees = new ArrayList<>();
                        for (Employee emp : eveningShift.getEmployeeinshiftSet()) {
                            if (eveningShift.getEmployeeinshiftMap().get(emp) == job) {
                                eveningShiftEmployees.add(emp);
                            }
                        }

                        morningRequired = String.valueOf(morningShift.getNumberofWorkersPerJob(job));
                        eveningRequired = String.valueOf(eveningShift.getNumberofWorkersPerJob(job));

                        if (row < morningShiftEmployees.size()) {
                            morningEmployee = morningShiftEmployees.get(row).getName() + "-" + morningShiftEmployees.get(row).getEmployeeNum();
                        }
                        if (row < eveningShiftEmployees.size()) {
                            eveningEmployee = eveningShiftEmployees.get(row).getName() + "-" + eveningShiftEmployees.get(row).getEmployeeNum();
                        }
                    } else {
                        if (row == 0) {
                            morningEmployee = "Day off";
                            eveningEmployee = "Day off";
                            morningRequired = "0";
                            eveningRequired = "0";
                        }
                    }

                    if (row == 0) {
                        if(day.getDayOfWeek()==DayOfWeek.SUNDAY||day.getDayOfWeek()==DayOfWeek.MONDAY||day.getDayOfWeek()==DayOfWeek.THURSDAY){
                            sb1.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", "(Req:" + morningRequired + ")" + morningEmployee, "(Req:" + eveningRequired + ")" + eveningEmployee));
                        }
                        if(day.getDayOfWeek()==DayOfWeek.SATURDAY){
                            sb3.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", "(Req:" + morningRequired + ")" + morningEmployee, "(Req:" + eveningRequired + ")" + eveningEmployee));
                        }
                        if(day.getDayOfWeek()==DayOfWeek.WEDNESDAY||day.getDayOfWeek()==DayOfWeek.TUESDAY||day.getDayOfWeek()==DayOfWeek.FRIDAY){
                            sb2.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", "(Req:" + morningRequired + ")" + morningEmployee, "(Req:" + eveningRequired + ")" + eveningEmployee));
                        }
                    } else {
                        if(day.getDayOfWeek()==DayOfWeek.SUNDAY||day.getDayOfWeek()==DayOfWeek.MONDAY||day.getDayOfWeek()==DayOfWeek.THURSDAY){
                            sb1.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", morningEmployee, eveningEmployee));
                        }
                        if(day.getDayOfWeek()==DayOfWeek.SATURDAY){
                            sb3.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", morningEmployee, eveningEmployee));
                        }
                        if(day.getDayOfWeek()==DayOfWeek.WEDNESDAY||day.getDayOfWeek()==DayOfWeek.TUESDAY||day.getDayOfWeek()==DayOfWeek.FRIDAY){
                            sb2.append(String.format(" %-" + shiftWidth + "s | %-" + shiftWidth + "s |", morningEmployee, eveningEmployee));
                        }
                    }
                }
                sb1.append("\n");
                sb2.append("\n");
                sb3.append("\n");
            }
            // Separator line after each job row
            sb1.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * 3+shiftWidth+3, "-"))).append("\n");
            sb2.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * 3+shiftWidth+3, "-"))).append("\n");
            sb3.append(String.join("", Collections.nCopies(jobWidth + shiftWidth * 2 * 1+shiftWidth-9, "-"))).append("\n");
        }

        return sb1.toString() + "\n" + sb2.toString() + "\n" + sb3.toString();
    }


}

