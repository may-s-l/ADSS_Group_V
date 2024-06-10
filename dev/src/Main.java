package dev.src;

import dev.src.Controllers.*;
import dev.src.Domain.*;
import dev.src.presentation.menus.Login_menu;
//import dev.src.presentation.menus.Login_menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                new Login_menu();
            } catch (Exception e) {

                System.err.println("An unexpected error occurred: " + e.getMessage());


                System.out.println("Restarting the system...");
            }
        }
    }
}