package autoboxing;

public class ChallengeMain {

    public static void main(String[] args) {
        Bank leumi = new Bank("Leumi");
        System.out.println(leumi);
        Customer michael = new Customer("Michael", 100);
        System.out.println(michael);

        leumi.addNewCustomer(new Customer("michael",100));

        leumi.addNewCustomer(michael);
        leumi.addTransaction("Michael",200);
        leumi.addTransaction("michael",-40);
        leumi.addTransaction("Alex", 30000);

        leumi.printStatement("michael");

        leumi.addNewCustomer(new Customer("bob", 300));
        leumi.addTransaction("Bob", 100);
        leumi.printStatement("Bob");
    }
}
