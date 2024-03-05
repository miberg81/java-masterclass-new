package autoboxing;

import java.util.ArrayList;
import java.util.Optional;

public class Bank {
    private String name;
    private ArrayList<Customer> customers = new ArrayList<>(5000);

    public Bank(String name) {
        this.name = name;
    }

    public void addNewCustomer(Customer customer) {
        if (!customerExists(customer)) {
            customers.add(customer);
        } else {
            System.out.println("This customer exists!");
        }
    }

    private boolean customerExists(Customer customer) {
        return customers.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(customer.getName()));
    }

    public Customer getCustomer(String name) {
        Optional<Customer> customer = customers.stream().filter(c->c.getName().equalsIgnoreCase(name))
                .findFirst();
        if (customer.isPresent()) {
            return customer.get();
        } else {
            System.out.println("Customer does not exist");
            return null;
        }
    }

    public void addTransaction(String customerName, double amount) {
        Optional<Customer> existing = customers.stream()
                .filter(c ->c.getName().equalsIgnoreCase(customerName))
                        .findFirst();
        if(existing.isPresent()) {
            Customer customer = existing.get();
            customer.getTransactions().add(amount);
        } else {
            System.out.println("Cant add transaction. customer does not exist!");
        }
    }

    void printStatement(String customerName) {
        final Customer customer = getCustomer(customerName);
        if (customer == null) {
            System.out.println("Can't ptint statement. Customer does not exist!");
            return;
        }
        System.out.println("Transaction for customer " + customer.getName());
        System.out.println("-".repeat(50));
        final ArrayList<Double> transactions = customer.getTransactions();
        for (double amount : transactions) {
            System.out.printf("$%10.2f (%s)%n", amount, amount<0 ? "debit" : "credit");
        }
    }


    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", customers=" + customers +
                '}';
    }
}
