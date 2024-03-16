package michael.burger;

import java.util.ArrayList;
import java.util.List;

public class Meal1 {

    private double price = 5.0;
    private Item drink;
    private Item side;
    private Burger burger;

    private double conversionRate;

    public Meal1() {
        this(1);
    }

    public Meal1(double conversionRate) {
        this.conversionRate = conversionRate;
        drink = new Item("coke", "drink", 1.5);
        burger = new Burger("MacRoyal");
        System.out.println(drink.name);
        side = new Item("fries", "side", 2.0);
    }

    public double getTotal() {
        double total =
                burger.getPrice() +  drink.price + side.price;
        return Item.getPrice(total, conversionRate);
    }

    public void addToppings(String... toppings) {
        this.burger.addToppings(toppings);
    }

    @Override
    public String toString() {
        return "%s%n%s%n%s%n%26s$%.2f".formatted( burger,drink, side,
                "Total Due: ", getTotal());
    }

    private class Item {

        private String name;
        private String type;
        private double price;

        public Item(String name, String type) {
            this(name, type, type.equals("burger") ? Meal1.this.price : 0);
        }

        public Item(String name, String type, double price) {
            this.name = name;
            this.type = type;
            this.price = price;
        }

        @Override
        public String toString() {
            return "%10s%15s $%.2f".formatted(
                    type, name, getPrice(price, conversionRate)
            );
        }

        private static double getPrice(double price, double rate) {
            return price * rate;
        }
    }

    private class Burger extends Item {

        // enum can be added since JDK16
        // (it is implicitly static when used as innner type
        private enum Extra {
            KETCHUP(0.5d),
            MUSTARD(1.5d),
            MAYONAISE(2.5d);

            double price;

            Extra(double price) {
                this.price = price;
            }
        }

        private List<Item> toppings;

        public Burger(String name) {
            super(name, "burger");
            toppings = new ArrayList<>();
        }

        private void addToppings(String... toppingsNames ){

            for (String toppingName : toppingsNames) {
                Extra extra;
                try {
                    extra = Extra.valueOf(toppingName.toUpperCase());
                    Item topping = new Item(
                            extra.name(), "topping", extra.price);
                    toppings.add(topping);
                } catch (IllegalArgumentException e){
                    System.out.println("no such extra found: " + toppingName);
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder(super.toString());
            for (Item topping : toppings) {
                output.append(
                        "%n%10s%15s $%10.2f%n".formatted(
                        topping.type,
                        topping.name,
                        Item.getPrice(topping.price, conversionRate)
                ));
            }
            return output.toString();
        }

        public double getPrice() {
            double total =  super.price;
            for (Item topping : toppings) {
                total += topping.price;
            }
            return total;
        }
    }
}
