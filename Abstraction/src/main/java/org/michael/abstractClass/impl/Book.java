package org.michael.abstractClass.impl;

import org.michael.abstractClass.ProductForSale;
import org.michael.abstractClass.ProductType;

import java.time.LocalDate;

public class Book extends ProductForSale {

    String author;
    LocalDate yearReleased;

    public Book(Long id, ProductType type, String description,
                Double price, String author, LocalDate yearReleased) {
        super(id, type, description, price);
        this.author = author;
        this.yearReleased = yearReleased;
    }

    @Override
    public String showDetails() {
        //return this.toString();
        return
                "This " + type + " is a fantastic book The price is " + price +
                " The description is " + description;

    }

    @Override
    public String toString() {
        return
                super.toString() +
                " author='" + author + '\'' +
                ", yearReleased=" + yearReleased;
    }
}
