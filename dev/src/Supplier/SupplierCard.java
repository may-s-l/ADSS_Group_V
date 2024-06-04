package Supplier;

public class SupplierCard implements IPrintable {
    private String name;
    private long firmID;
    private long bankAccount;
    private String paymentMethod;
    private Contact[] contacts;
    private AContract contract;

    public SupplierCard(String name, int firmID, int bankAccount, String paymentMethod, Contact[] contacts, AContract contract) {
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

    public long getFirmID() {
        return firmID;
    }

    public long getBankAccount() {
        return bankAccount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public AContract getContract() {
        return contract;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setFirmID(int firmID) {
        this.firmID = firmID;
    }

    public void setBankAccount(int bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setContacts(Contact[] contacts) {
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
