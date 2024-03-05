package org.michael.abstractClass;

import java.util.ArrayList;
import java.util.List;

public class StoreMain {
    public static void main(String[] args) {

        List<ProductForSale> products = Store.getProductsForSale();

        for (ProductForSale product: products) {
            System.out.println("-".repeat(30));
            System.out.println(product.showDetails());
        }

        List<OrderItem> order1 = new ArrayList<>(List.of(
                new OrderItem(products.get(0), 2),
                new OrderItem(products.get(2), 1)
        ));
        addItemToOrder(order1, products, 3, 2);
        addItemToOrder(order1, products, 5, 4);
        printReceipt(order1);
    }

    private static void addItemToOrder(List<OrderItem> order, List<ProductForSale> products, int itemIndex, int quantity) {
        order.add(new OrderItem(products.get(itemIndex), quantity));
    }

    private static void printReceipt(List<OrderItem> order) {
        System.out.println("***** Receipt *****");
        double total = 0d;
        for (var orderItem : order) {
            orderItem.getProductForSale().printPricedLineItem(orderItem.getQuantity());
            total += orderItem.getProductForSale().getSalesPrice(orderItem.getQuantity());
        }
        System.out.println("Total is $ " + total);
    }
}