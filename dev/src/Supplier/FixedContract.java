package Supplier;

import java.util.Map;

public class FixedContract extends AContract {
    private String[] daysOfDelivery;

    public FixedContract(Catalogue itemCatalogue, String[] days) {
        super(itemCatalogue);
        daysOfDelivery = days;
    }

    @Override
    public void print() {
        StringBuilder builder = new StringBuilder();
        for (String day : daysOfDelivery) {
            if (day == null)
                break;
            builder.append(day).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        System.out.println("Contract type: " + getType() + "\n" +
                "Days of delivery: " + builder);
        itemCatalogue.print();
    }

    @Override
    public String getType() {
        return "Fixed";
    }
}
