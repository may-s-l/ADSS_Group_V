package dev.src;

//import dev.src.presentation.menus.Login_menu;

import dev.src.Data.DBConnection;

import java.sql.*;

public class Main {
//    public static void main(String[] args) {
//        DBConnection db = DBConnection.getInstance();
//
//        while (true) {
//            try {
//                new Login_menu();
//            } catch (Exception e) {
//
//                System.err.println("An unexpected error occurred: " + e.getMessage());
//
//
//                System.out.println("Restarting the system...");
//            }
//        }
//    }
    public static void main(String[] args) {
        try {
            // קבלת מופע של DBConnection
            DBConnection db = DBConnection.getInstance();

            // יצירת חיבור למסד הנתונים
            try (Connection conn = db.getConnection()) {
                // הכנסת נתונים לדוגמה לטבלה "Branch"
                String insertBranch = "INSERT INTO Branch (Address, Name, Manager) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertBranch)) {
                    pstmt.setString(1, "123 Main St");
                    pstmt.setString(2, "Main Branch");
                    pstmt.setString(3, "John Doe");
                    pstmt.executeUpdate();
                }

                // קריאת נתונים מטבלה "Branch"
                String selectBranch = "SELECT * FROM Branch";
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery(selectBranch)) {
                    while (rs.next()) {
                        System.out.println("Address: " + rs.getString("Address"));
                        System.out.println("Name: " + rs.getString("Name"));
                        System.out.println("Manager: " + rs.getString("Manager"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}