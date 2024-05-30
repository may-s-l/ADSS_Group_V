package dev.src.domain;

import java.sql.Time;
import java.util.Date;
import java.util.Date;
import java.time.*;
import java.util.*;
public class Constraint {
    private Employee emp;
    private LocalDate date;
    private String shiftType;

    public Constraint(Employee emp, LocalDate date, String shiftType) {
        this.emp = emp;
        this.date = date;
        this.shiftType=shiftType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }



}
