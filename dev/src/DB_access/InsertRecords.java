package DB_access;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertRecords {
    public void insertSupplier(String name, String firm_id, String bank_account, String payment_method, int contract_id) {
        String query = "INSERT INTO suppliers(name, firm_id, bank_account, payment_method, contract_id) VALUES(?,?,?,?,?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, firm_id);
            statement.setString(3, bank_account);
            statement.setString(4, payment_method);
            statement.setInt(5, contract_id);
            statement.executeUpdate();
            System.out.println("Supplier " + name + " contract #" + contract_id + " was added successfully");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertItem(String item_id, String item_name) {
        String query = "INSERT INTO items(item_id, item_name) VALUES (?,?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1 ,item_id);
            statement.setString(2, item_name);
            statement.executeUpdate();
            System.out.println(item_name + ":" + item_id + " has been added successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertContact(String id, String name, String phone, String supplier) {
        String query = "INSERT INTO contacts(contact_id, contact_name, contact_phone, supplier_name) VALUES(?,?,?,?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, phone);
            statement.setString(4, supplier);
            statement.executeUpdate();
            System.out.println("Contact for supplier " + supplier + " has been added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertItemToCatalogue(String supplier, String item_name, String item_id, double item_price, double item_discount, double item_max_discount) {
        String query = "INSERT INTO catalogue_items(supplier_name, item_name, item_supplier_id, item_price, item_discount_per_100, item_max_discount)" +
                "VALUES (?,?,?,?,?,?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, supplier);
            statement.setString(2, item_name);
            statement.setString(3, item_id);
            statement.setDouble(4, item_price);
            statement.setDouble(5, item_discount);
            statement.setDouble(6, item_max_discount);
            statement.executeUpdate();
            System.out.println("Item has been added to supplier " + supplier + " catalogue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertContract(String id, String type, String supplier_name, String[] days_of_delivery) {
        String query = "INSERT INTO contracts(contract_id, type, supplier, days_of_delivery)" +
                "VALUES (?,?,?,?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, type);
            statement.setString(3, supplier_name);
            statement.setString(4, days_of_delivery.toString());
            statement.executeUpdate();
            System.out.println("Contract id. " + id + " for supplier " + supplier_name + " has been added");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
