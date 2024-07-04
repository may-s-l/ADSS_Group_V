package DB_access;

import Supplier.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectRecords {
    private void print_supplier_info(String name, String firm_id, String bank_account, String payment_method) {
        System.out.println("- " + name + ":" + firm_id + ":" + bank_account + ":" + payment_method);
    }

    private void print_supplier(ResultSet rs, int flag) {
        // flag represents which function called for print_supplier()
        // if flag is 1 then display all, if 0 then display only 1 supplier
        if (flag == 0) {
            try {
                if (!rs.next()) {
                    System.out.println("Couldn't find supplier in the system");
                }
                else {
                    System.out.println("Displaying suppliers:\n" +
                            "-----------------------------------");

                    print_supplier_info(rs.getString("name"),
                            rs.getString("firm_id"),
                            rs.getString("bank_account"),
                            rs.getString("payment_method"));

                    System.out.println("-----------------------------------\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                System.out.println("Displaying suppliers:\n" +
                        "-----------------------------------");
                while (rs.next()) {
                    print_supplier_info(rs.getString("name"),
                            rs.getString("firm_id"),
                            rs.getString("bank_account"),
                            rs.getString("payment_method"));
                }
                System.out.println("-----------------------------------");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void displaySupplier(String name) {
        String query = "SELECT * FROM suppliers WHERE name=(?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            print_supplier(result, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayAllSuppliers() {
        String query = "SELECT * FROM suppliers";

        try {
            Connection con = Connect.returnConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            print_supplier(rs, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayAllItems() {
        String query = "SELECT * FROM items";

        try {
            Connection con = Connect.returnConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            System.out.println("Displaying list of all items in the database:");
            while(rs.next()) {
                System.out.println(rs.getString("item_id") + "\t" + rs.getString("item_name"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item itemToObject(String item_name) {
        String query = "SELECT * FROM items WHERE item_name=(?)";
        String id, name;
        Item out = null;

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, item_name);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                System.out.println("Couldn't find item in the system");
            }
            else {
                id = result.getString("item_id");
                item_name = result.getString("item_name");
                out = new Item(item_name, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public Contact contactToObject(String id) {
        Contact out = null;
        String cID, cName, cPhone, supplier_name;
        String query = "SELECT * FROM contacts WHERE contact_id=(?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            cID = rs.getString("contact_id");
            cName = rs.getString("contact_name");
            cPhone = rs.getString("contact_phone");
            supplier_name = rs.getString("supplier_name");
            out = new Contact(cName, cID, cPhone);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public List<Contact> getContactObjectsOfSupplier(String supplier_name) {
        List<Contact> out = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE supplier_name=(?)";


        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, supplier_name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Contact new_contact = new Contact(rs.getString("contact_name"), rs.getString("contact_id"), rs.getString("contact_phone"));
                out.add(new_contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public SupplierCard supplierToObject(String name) {

        String query = "SELECT * FROM suppliers WHERE name=(?)";
        SupplierCard out = null;
        String supplier_name, firm_id, bank_account, payment_method;
        AContract contract = null;
        List<Contact> contacts = new ArrayList<>();

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                System.out.println("Couldn't find supplier in the system");
            }
            else {
                supplier_name = name;
                firm_id = result.getString("firm_id");
                bank_account = result.getString("bank_account");
                payment_method = result.getString("payment_method");
                contract = contractToObject(result.getString("contract_id"));
                contacts = getContactObjectsOfSupplier(supplier_name);
                out = new SupplierCard(supplier_name, firm_id, bank_account, payment_method, contacts, contract);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }

    public AContract contractToObject(String contract_id) {
        AContract out = null;
        String query = "SELECT * FROM contracts WHERE contract_id=(?)";

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, contract_id);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                System.out.println("Couldn't find contract with ID: " + contract_id);
            }
            else {
                if (rs.getString("type").equals("fixed")) {
                    out = new FixedContract(catalogueToObject(rs.getString("supplier")), new String[] {"Sunday"});
                }
                else if (rs.getString("type").equals("temporary")) {
                    out = new TempContract(catalogueToObject(rs.getString("supplier")));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public Catalogue catalogueToObject(String supplier_name) {
        String query = "SELECT * FROM catalogue_items WHERE supplier_name=(?)";
        Catalogue out = new Catalogue();
        QuantityForm temp = new QuantityForm();

        try {
            Connection con = Connect.returnConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, supplier_name);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Item item = new Item(rs.getString("item_name"), rs.getString("item_supplier_id"));
                temp.add(item, rs.getDouble("item_discount_per_100"), rs.getDouble("item_max_discount"));
                out.add(item, rs.getString("item_supplier_id"), rs.getDouble("item_price"));
            }
            out.setQuantityForm(temp);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }
}
