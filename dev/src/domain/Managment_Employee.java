package dev.src.domain;

import java.time.LocalDate;
import java.util.Date;

public class Managment_Employee extends Employee {

    public Managment_Employee(String name, String bank_accuont, String ID, ManagmantJob job, Float salery, LocalDate start_date) {
        super(name, bank_accuont, ID,job, salery, start_date);
    }
}
