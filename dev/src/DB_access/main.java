package DB_access;

import Supplier.SupplierCard;
import Supplier.SupplierCardGenerator;

public class main {
    public static void main(String[] args) {
        Connect.connect();
        SupplierCardGenerator gen = new SupplierCardGenerator();
        InsertRecords insert = new InsertRecords();
        SelectRecords select = new SelectRecords();
        SupplierCard s1 = gen.generate();

        SupplierCard supplier = select.supplierToObject("Michael");
        System.out.println(supplier);
        Connect.close();
    }
}
