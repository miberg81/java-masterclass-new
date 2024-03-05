package org.michael.abstractClass;

import org.michael.abstractClass.impl.Book;
import org.michael.abstractClass.impl.Shoe;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Store {
    private static List<ProductForSale> productsForSale;
    private static Long lastId = 0L;

    static {
        ProductForSale shoe1 = new Shoe(++lastId, ProductType.SHOE, "shoe 1", 200d, 49);
        ProductForSale shoe2 = new Shoe(++lastId, ProductType.SHOE, "shoe 2", 100d, 39);
        ProductForSale shoe3 = new Shoe(++lastId, ProductType.SHOE, "shoe 3", 300d, 19);

        ProductForSale book1 = new Book(++lastId, ProductType.BOOK, "book 1", 234d, "author1", LocalDate.of(2022,11,1));
        ProductForSale book2 = new Book(++lastId, ProductType.BOOK, "book 2", 23d, "author2", LocalDate.of(2022,11,1));
        ProductForSale book3 = new Book(++lastId, ProductType.BOOK, "book 3", 244d, "author3", LocalDate.of(2022,11,1));

        productsForSale = Arrays.asList(shoe1,shoe2,shoe3,book1,book2,book3);
    }

    public static List<ProductForSale> getProductsForSale() {
        return productsForSale;
    }
}
