package model;

public class ProductBuilder {

    private String name;
    private int price;
    private String brand;

    private ProductBuilder() {
        this.name = "Default Product";
        this.price = 0;
        this.brand = "Generic";
    }

    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withPrice(int price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Product build() {
        return new Product(name, price, brand);
    }
}

