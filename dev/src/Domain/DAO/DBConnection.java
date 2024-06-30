package dev.src.Domain.DAO;

import org.sqlite.SQLiteConfig;
import java.io.File;
import java.sql.*;


public class DBConnection {
    private static final DBConnection dbConnection = new DBConnection();
    private static final String dbFileName = "Superlee.db";

    private DBConnection()
    {
        try {
            boolean newDb = isNewDb();
            if (newDb) {
                try (Connection conn = getConnection()) {
                    createTables(conn);
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DBConnection getInstance() {
        return dbConnection;
    }

    public Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        return DriverManager.getConnection("jdbc:sqlite:" + dbFileName, config.toProperties());
    }

    private boolean isNewDb() {
        boolean newDb = false;
        File fdb = new File(dbFileName);
        if (!fdb.exists()) {
            newDb = true;
        }
        return newDb;
    }

    public int getGeneratedKey(PreparedStatement ps) throws SQLException {
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (int)generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    private void createTables(Connection conn) {
        String script =
                "CREATE TABLE IF NOT EXISTS \"Branch\" (\n" +
                        "    \"Address\"\tVARCHAR(25),\n" +
                        "    \"Name\"\tVARCHAR(25),\n" +
                        "    \"Manager\"\tVARCHAR(25),\n" +
                        "    PRIMARY KEY(\"Address\")\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"Jobs\" (\n" +
                        "    \"Name\"\tVARCHAR(25),\n" +
                        "    PRIMARY KEY(\"Name\")\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"Shift\" (\n" +
                        "    \"ShiftDate\"\tTEXT,\n" +
                        "    \"ShiftType\"\tVARCHAR(25),\n" +
                        "    \"BranchAddress\"\tVARCHAR(25),\n" +
                        "    \"StartTime\"\tVARCHAR(25),\n" +
                        "    \"EndTime\"\tVARCHAR(25),\n" +
                        "    \"Job\"\tVARCHAR(25),\n" +
                        "    \"NumEmployeesForjob\"\tINTEGER,\n" +
                        "    PRIMARY KEY(\"ShiftType\",\"ShiftDate\",\"BranchAddress\"),\n" +
                        "    FOREIGN KEY(\"BranchAddress\") REFERENCES \"Branch\"(\"Address\")\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"Employee\" (\n" +
                        "    \"ID\"\tVARCHAR(6),\n" +
                        "    \"EmpNUM\"\tINTEGER,\n" +
                        "    \"Name\"\tVARCHAR(25),\n" +
                        "    \"BankAccountNumber\"\tVARCHAR(8),\n" +
                        "    \"BranchID\"\tVARCHAR(25),\n" +
                        "    \"Terms\"\tVARCHAR(25),\n" +
                        "    \"EmployeeJob\"\tVARCHAR(25),\n" +
                        "    PRIMARY KEY(\"ID\")\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"EmployeeJobs\" (\n" +
                        "    \"EID\"\tVARCHAR(6),\n" +
                        "    \"Job\"\tVARCHAR(25),\n" +
                        "    PRIMARY KEY(\"EID\",\"Job\"),\n" +
                        "    FOREIGN KEY(\"EID\") REFERENCES \"Employee\"(\"ID\") ON UPDATE CASCADE ON DELETE CASCADE\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"EmployeeConstraints\" (\n" +
                        "    \"EID\"\tVARCHAR(6),\n" +
                        "    \"ConstraintDate\"\tTEXT,\n" +
                        "    \"ConstraintShiftType\"\tVARCHAR(10),\n" +
                        "    PRIMARY KEY(\"EID\",\"ConstraintDate\"),\n" +
                        "    FOREIGN KEY(\"EID\") REFERENCES \"Employee\"(\"ID\") ON UPDATE CASCADE ON DELETE CASCADE\n" +
                        ");\n" +
                        "CREATE TABLE IF NOT EXISTS \"ShiftEmployees\" (\n" +
                        "    \"ShiftDate\"\tTEXT,\n" +
                        "    \"ShiftType\"\tVARCHAR(25),\n" +
                        "    \"BranchAddress\"\tVARCHAR(25),\n" +
                        "    \"EID\"\tINTEGER,\n" +
                        "    \"EmployeeJob\"\tVARCHAR(25),\n" +
                        "    FOREIGN KEY(\"ShiftDate\",\"ShiftType\",\"BranchAddress\") REFERENCES \"Shift\"(\"ShiftDate\",\"ShiftType\",\"BranchAddress\"),\n" +
                        "    PRIMARY KEY(\"ShiftDate\",\"ShiftType\",\"EID\")\n" +
                        ");\n";
        String[] queries = script.split(";\n");
        for (String query : queries) {
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
