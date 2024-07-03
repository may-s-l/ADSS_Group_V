package dev.src.Domain;

import dev.src.Domain.Enums.*;

import java.time.LocalDate;
public class Constraint {
    private Employee emp;
    private LocalDate date;
    private ShiftType shiftType;

    public Constraint(Employee emp, LocalDate date, ShiftType shiftType) {
        this.emp = emp;
        this.date = date;
        this.shiftType=shiftType;
    }

    public LocalDate getShiftDate() {
        return date;
    }

    public void setShiftDate(LocalDate date) {
        this.date = date;
    }

    public ShiftType getShiftType() {
        return shiftType;
    }

    public Employee getEmp() {
        return emp;
    }

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public void setShiftType(ShiftType shiftType) {
        this.shiftType = shiftType;
    }


    @Override
    public String toString() {
        return "can't work on "+this.date+" "+this.date.getDayOfWeek()+" "+this.shiftType.toString();
    }

    /////FOR DATABASE////

    public Constraint(LocalDate date, ShiftType shiftType) {
        this.date = date;
        this.shiftType = shiftType;
    }
}