package bankChallenge;

import java.util.ArrayList;

/*
   -  It has two fields, A String called name and an ArrayList
    that holds objects of type Double called transactions.
    -  A constructor that takes a  String (name of customer)
    and a double (initial transaction). It initialises name and instantiates transactions.
    -  And three methods, they are (their functions are in their names):
        -  getName(), getter for name.
        -  getTransactions(), getter for transactions.
        -  addTransaction(), has one parameter of type double
        (transaction) and doesn't return anything.
 */
public class Customer {
    private String name;
    private ArrayList<Double> transactions;

    public Customer(String name, double initialTransaction) {
        this.name = name;
        this.transactions = new ArrayList<>();
        this.transactions.add(initialTransaction);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Double> getTransactions() {
        return transactions;
    }

    public void addTransaction(double transaction) {
        this.transactions.add(transaction);
    }
}
