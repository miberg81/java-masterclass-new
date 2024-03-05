package org.michael.abstractClass.impl;

import org.michael.abstractClass.ProductForSale;
import org.michael.abstractClass.ProductType;

public class Shoe extends ProductForSale {

    Integer size;

    public Shoe(Long id, ProductType type, String description, Double price, int size) {
        super(id, type, description, price);
        this.size = size;
    }


    @Override
    protected String showDetails() {
        return this.toString();
    }

    @Override
    public String toString() {
        return
                super.toString() +
                " size=" + size;
    }
}
