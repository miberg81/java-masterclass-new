package linkedList;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

/*
It's now time for a LinkedList challenge.
I'm going to ask you to  use LinkedList functionality, to create a list of places,
ordered by distance from the starting point.
And we want to use a ListIterator, to move, both backwards and forwards,
through this ordered itinerary of places.
 */

record Place(String name, int distance){
    @Override
    public String toString() {
        return String.format("%s (%d)", name, distance);
    }
}

public class LinkedListChallengeMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LinkedList<Place> trip = new LinkedList<>();

    public static void main(String[] args) {
        addPlaces();
        System.out.println(trip);

        printMenu();
        boolean quitLoop = false;
        boolean forward = true;
        System.out.println("Enter value: ");
        final ListIterator<Place> iterator = trip.listIterator(0);
        while (!quitLoop) {
            //valid inputs are F,B,L,M,Q
            if (!iterator.hasPrevious()) {    // we are at the start!
                System.out.println("Originating: " + iterator.next());
                forward = true;
            }
            if (!iterator.hasNext()) {    // we are at the end!
                System.out.println("Final: " + iterator.previous());
                forward = false;
            }
            String inputFromUser = scanner.nextLine().toUpperCase().substring(0,1 );
            switch (inputFromUser) {
                case "F":
                    System.out.println("User wants to go forward");
                    // WITHOUT THIS FIRST WILL BE PRINTED TWICE
                    if (!forward) { // reverse the cursor
                        forward = true;
                        if (iterator.hasNext()) {
                            iterator.next(); // adjust position without printing!
                        }
                    }
                    if(iterator.hasNext()){
                        System.out.println(iterator.next());
                    }
                    break;
                case "B" :
                    System.out.println("User wants to go forward");
                    // WITHOUT THIS LAST WILL BE PRINTED TWICE
                    if (forward) { // reverse the direction
                        forward = false;
                        if (iterator.hasPrevious()) {
                            iterator.previous(); // adjust position without printing!
                        }
                    }
                    if(iterator.hasPrevious()){
                        System.out.println(iterator.previous());
                    }
                    break;
                case "L" :
                    listPlaces();
                    break;
                case "M" :
                    printMenu();
                    break;
                default:
                    quitLoop=true;
                    break;
            }
        }
    }

    private static void addPlaces() {
        addPlace(trip, new Place ("Adelaide", 1374));
        addPlace(trip, new Place ("adelaide", 1374));//duplecate

        addPlace(trip, new Place ("Brisbane", 917));
        addPlace(trip, new Place ("Perth", 3923));
        addPlace(trip, new Place ("Alice Springs", 2771));
        addPlace(trip, new Place ("Darwin", 3972));
        addPlace(trip, new Place ("Melbourne", 877));

        addPlace(trip, new Place ("Sidney", 0));
    }

    private static void extracted() {
        addPlace(trip, new Place("Sidney", 0));
    }

    /*
    This contains will only work with Records!
    On record getName()-->name()!
    But it will miss same city with lower case
     */
    private static void addPlace(LinkedList<Place> list, Place place) {
        //checks exact duplecate!
        if (list.contains(place)) {
            System.out.println("Found duplicate: " + place);
            return;
        }

        // check case
        for (Place p : list) {
            if (p.name().equalsIgnoreCase(place.name())) {
                System.out.println("Found duplicate: " + place);
                return;
            }
        }

        // add place sorted from the lowest distance
        //  (from Sidney which is zero) to highest.
        int matchedIndex = 0;
        for (var listPlace : list) {
            // if the new record is closer to Sidney , we add this record at the beginning
            if (place.distance() < listPlace.distance()) {
                list.add(matchedIndex, place);
                return;
            }
            matchedIndex++;
        }
        list.add(place);
    }

    private static void moveToPreviousCity(ListIterator<Place> listIter) {
        if(listIter.hasPrevious()){
            listIter.previous();
            System.out.println("\n Currently at " + trip.get(listIter.nextIndex() - 1));
        } else {
            System.out.println(
                    "Can't move back! You are at the beginning = "
                            + trip.getFirst());
        }
    }

    private static void moveToNextCity(ListIterator<Place> listIter) {
        if (listIter.hasNext()) {
            listIter.next();
            System.out.println("\n Currently at " + trip.get(listIter.previousIndex() + 1));
        } else {
            System.out.println(
                    "Can't move forward! You are at the end = "
                            + trip.getLast());
        }
    }

    private static void quit() {
        System.exit(1);
    }

    private static void printMenu() {
        String menu = """
                (F)orward
                (B)ackward
                (L)ist Places
                (M)enu
                (Q)uit
                """;
        System.out.println(menu);
    }

    private static void listPlaces() {
        System.out.println(trip);
    }
    
}
