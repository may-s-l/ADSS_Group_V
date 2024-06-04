package Supplier;

public class Contact implements IPrintable {
    private String name;
    private long ID;
    private String phoneNumber;

    Contact(String name, long ID, String phoneNumber) {
        this.name = name;
        this.ID = ID;
        this.phoneNumber = phoneNumber;
    }

    // getters & setters:

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void print() {
        System.out.println("Contact Name: " + name + "\n" +
                "Contact ID: " + ID + "\n" +
                "Contact phone number: " + phoneNumber + "\n");
    }
}
