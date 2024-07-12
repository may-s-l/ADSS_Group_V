package dev.src;

//import dev.src.presentation.menus.Login_menu;

import dev.src.Data.DBConnection;
import dev.src.presentation.menus.Login_menu;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();

        while (true) {
           // try {
                new Login_menu();
          //  } catch (Exception e) {

           //     System.err.println("An unexpected error occurred: " + e.getMessage());


                System.out.println("Restarting the system...");
            }
        }
    }
