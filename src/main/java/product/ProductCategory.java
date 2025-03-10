package product;

public enum ProductCategory {
    TSHIRT("Футболки"),
    HOODIE("Толстовки"),
    HUDI("Худи"),
    UNDERWEAR("Трусы"),
    SUIT("Костюмы"),
    PANTS("Штаны"),
    COMFORTER("Одеяла");

    private final String name;

    ProductCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
