package Supplier;

import java.util.List;

public class SupplierCard implements IPrintable {
    private String name;
    private String firmID;
    private String bankAccount;
    private String paymentMethod;
    private List<Contact> contacts;
    private AContract contract;

    public SupplierCard(String name, String firmID, String bankAccount, String paymentMethod, List<Contact> contacts, AContract contract) {
        this.name = name;
        this.firmID = firmID;
        this.bankAccount = bankAccount;
        this.paymentMethod = paymentMethod;
        this.contacts = contacts;
        this.contract = contract;
    }

    // getters setters:

    public String getName() {
        return name;
    }

    public String getFirmID() {
        return firmID;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public AContract getContract() {
        return contract;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setFirmID(String firmID) {
        this.firmID = firmID;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void setContract(AContract contract) {
        this.contract = contract;
    }

    @Override
    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Displaying supplier info..\n" +
                "Name: " + name + "\n" +
                "Firm ID: " + firmID + "\n" +
                "Bank account: " + bankAccount + "\n" +
                "Payment method: " + paymentMethod + "\n" +
                "Fixed/Temporary: " + contract.getType() + "\n";
    }
}
