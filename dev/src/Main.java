package dev.src;

import dev.src.domain.*;
//import dev.src.presentation.menus.Login_menu;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        //Login_menu  LM= new Login_menu();
        Branch b=new Branch("superlee","neveAshcol");
        Job job=new Job("driver");
        ManagerEmployee ME=new ManagerEmployee("DD","000000","12321232",b,job);
        Employee emp= new Employee("may","207939","65556655",b,job);
        Shift S=new Shift(LocalDate.now(),"MORNNING");
        S.addEmployeeToShift(emp,job);
        System.out.println(S);

    }
}