package arrayList;

public class ContactsMain {

    private static Contact bob;

    public static void main(String[] args) {
        MobilePhone phone = new MobilePhone("0507688534");

        Contact bob = new Contact("Bob", "31415926");

        Contact newBob = new Contact("Bob1", "31415926");

        phone.addNewContact(bob);
        phone.addNewContact(new Contact("Bob","31415926"));
        phone.addNewContact(new Contact("Tom","11235813"));
        phone.addNewContact(new Contact("Jane","23571113"));
        phone.addNewContact(new Contact("Jane","23571113"));

        // contact must exist and have the same name
        phone.updateContact(bob, newBob);
        phone.printContacts();
    }
}
