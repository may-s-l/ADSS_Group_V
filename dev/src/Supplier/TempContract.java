package Supplier;

public class TempContract extends AContract {
    TempContract(Catalogue itemCatalogue) {
        super(itemCatalogue);
    }

    @Override
    public void print() {
        System.out.println("Contract type: " + getType() + "\n");
        itemCatalogue.print();
    }

    @Override
    public String getType() {
        return "Temporary";
    }
}
