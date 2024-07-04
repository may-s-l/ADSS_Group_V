package DB_access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateRecords {
    private void updateSupplierData(String supplier_name, String firm_id, String bank_account, String payment_method) {
        String query = "UPDATE suppliers SET firm_id=(?), bank_account=(?), payment_method=(?) WHERE name=" + supplier_name;

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, firm_id);
            statement.setString(2, bank_account);
            statement.setString(3, payment_method);
            statement.executeUpdate();
            System.out.println("Supplier " + supplier_name + "was updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplier() {
        String name, firm_id, bank_account, payment_method;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of the supplier you wish to update:");
        name = scanner.next();
        // todo
    }
}
