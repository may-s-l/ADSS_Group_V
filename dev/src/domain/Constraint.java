package dev.src.domain;

import java.time.*;

public class Constraint {
    private Employee emp;
    private LocalDate date;
    private Enums.Shift_type shiftType;

    public Constraint(Employee emp, LocalDate date, Enums.Shift_type shiftType) {
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

    public Enums.Shift_type getShiftType() {
        return shiftType;
    }

    public void setShiftType(Enums.Shift_type shiftType) {
        this.shiftType = shiftType;
    }
    @Override
    public String toString() {
        return "constrain for Employee ID:" + emp.getID() +
                "at date: '" + date + "in " + shiftType +
                "shift";
    }
}
