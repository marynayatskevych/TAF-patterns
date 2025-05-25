package model;

public class Product {
    private String name;
    private int price;
    private String brand;

    public Product(String name, int price, String brand) {
        this.name = name;
        this.price = price;
        this.brand = brand;
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return String.format("Product{name='%s', price=%d, brand='%s'}", name, price, brand);
    }
}

