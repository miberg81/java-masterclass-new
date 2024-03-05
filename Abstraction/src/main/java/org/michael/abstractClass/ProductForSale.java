package org.michael.abstractClass;

public abstract class ProductForSale {
    protected Long id;
    protected ProductType type;
    protected String description;
    protected Double price;

    public ProductForSale(Long id, ProductType type, String description, Double price) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
    }

    double getSalesPrice(int quantity) {
        return quantity*price;
    }

    void printPricedLineItem(int quantity) {
        //System.out.println(description + "   " + quantity + "   " + price + "  " + getSalesPrice(quantity));

        // %2d  - prints integer with 2 digits, right justified
        // $%8.2f  - $=usd dollar, decimal number(double,float), total width=8, 2=digits after the dot
        // %-15s  -print string, dash=left justified, 15=allow minimum 15 spaces
        System.out.printf("%2d qty at $%8.2f each, %-15s %-35s %n",
                quantity, price, type, description);
    }

    protected abstract String showDetails();

    @Override
    public String toString() {
        return
                " id " + id +
                " type=" + type +
                ", description='" + description + '\'' +
                ", price=" + price;
    }
}
