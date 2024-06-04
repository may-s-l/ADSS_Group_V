package Supplier;

import java.util.Scanner;

public class FixedContractGenerator implements IObjectGenerator {
    @Override
    public FixedContract generate() {
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        CatalogueGenerator catalogueGenerator = new CatalogueGenerator();
        String[] daysOfDelivery = new String[7];
        String input;
        System.out.println("--Fixed Contract Generator--");
        System.out.println("Please enter days of delivery, when finished input 'stop':");
        while (true) {
            input = scanner.next();
            if (input.equals("stop"))
                break;
            daysOfDelivery[i++] = input;
        }
        return new FixedContract(catalogueGenerator.generate(), daysOfDelivery);
    }
}
