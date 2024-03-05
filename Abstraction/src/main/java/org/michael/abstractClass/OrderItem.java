package org.michael.abstractClass;

public class OrderItem {
    ProductForSale productForSale;
    int quantity;

    public OrderItem(ProductForSale productForSale, int quantity) {
        this.quantity = quantity;
        this.productForSale = productForSale;
    }

    public ProductForSale getProductForSale() {
        return productForSale;
    }

    public int getQuantity() {
        return quantity;
    }
}
