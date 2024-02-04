package arrayList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class ArrayListsChallenge {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean flag =  true;
        ArrayList<String> groceries = new ArrayList<>();
        while (flag) {
            printActions();
            String inputFromUser  = scanner.nextLine();// read input
            Integer choice = Integer.parseInt(inputFromUser);

            switch (choice) {
                case 1 -> addItems(groceries);
                case 2 -> removeItems(groceries);
                default -> flag = false;
            }
            groceries.sort(Comparator.naturalOrder());
            System.out.println(groceries);
        }

    }

    private static void printActions() {
        // text block - new java feature
        String textBlock = """
                Available actions:

                0 - to shutdown

                1 - to add item(s) to list (comma delimited list)

                2 - to remove any items (comma delimited list)

                Enter a number for which action you want to do:"""; //the quotes come right after text so no new line will be included

        System.out.println(textBlock + " "); // we add space because text block removes all trailing spaces
    }

    private static void addItems(ArrayList<String> groceries) {
        System.out.println("Add item(s) [separate items by comma]:");
        String[] items = scanner.nextLine().split(",");
        //groceries.addAll(List.of(items));

        for (String i : items) {
            String trimmed = i.trim();
            if (groceries.indexOf(trimmed) < 0) { // not a duplecate
                groceries.add(trimmed);
            }
        }
    }

    private static void removeItems(ArrayList<String> groceries) {
        System.out.println("Remove item(s) [separate items by comma]:");
        String[] items = scanner.nextLine().split(",");

        for (String i : items) {
            String trimmed = i.trim();
            groceries.remove(trimmed);
        }
    }
}
