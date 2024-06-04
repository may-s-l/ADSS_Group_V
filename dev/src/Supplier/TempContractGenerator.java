package Supplier;

public class TempContractGenerator implements IObjectGenerator {
    @Override
    public TempContract generate() {
        CatalogueGenerator catalogueGenerator = new CatalogueGenerator();
        System.out.println("--Temporary Contract Generator--");
        return new TempContract(catalogueGenerator.generate());
    }
}
