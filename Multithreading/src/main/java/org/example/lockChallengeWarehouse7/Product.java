package org.example.lockChallengeWarehouse7;


public class Product {
    private Integer productId;
    private ShoeType shoeType;

    public Product(Integer productId, ShoeType shoeType) {
        this.productId = productId;
        this.shoeType = shoeType;
    }

    public  enum ShoeType {
        MALE,
        FEMALE,
        UNISEX;
    }
}
