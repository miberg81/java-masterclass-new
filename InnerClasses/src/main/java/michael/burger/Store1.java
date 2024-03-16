package michael.burger;

public class Store1 {

    public static void main(String[] args) {

//        Meal1 regularMeal = new Meal1();
//        System.out.println(regularMeal);

        Meal1 USRegularMeal = new Meal1(1);
//        System.out.println(USRegularMeal);
        System.out.println("toppings added");
        USRegularMeal.addToppings("mustard","mayonnaise","ketchup");
        System.out.println(USRegularMeal);

    }
}
