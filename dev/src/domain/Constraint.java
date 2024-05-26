package dev.src.domain;

import java.sql.Time;
import java.util.Date;
import java.util.Date;
import java.time.*;
import java.util.*;
public class Constraint {
    private Employee emp;
    private LocalDate date;
    private Time start_t;
    private Time end_t;

    public Constraint(Employee emp, LocalDate date, Time start_t, Time end_t) {
        this.emp = emp;
        this.date = date;
        this.start_t = start_t;
        this.end_t = end_t;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Time getStart_t() {
        return start_t;
    }

    public void setStart_t(Time start_t) {
        this.start_t = start_t;
    }

    public Time getEnd_t() {
        return end_t;
    }

    public void setEnd_t(Time end_t) {
        this.end_t = end_t;
    }


}
