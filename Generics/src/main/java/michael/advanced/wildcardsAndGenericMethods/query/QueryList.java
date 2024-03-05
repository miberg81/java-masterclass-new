package michael.advanced.wildcardsAndGenericMethods.query;

import michael.advanced.wildcardsAndGenericMethods.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a query service which get a list to query from(like a table in DB)
 * It gets a field from Student class on which we want to match,
 * like name, course , yearStarted and the value of this field
 * like Bill, Python, 2022
 * it returns all student from the list which match.
 * @param <T>
 */
public class QueryList<T extends Student & QueryItem> {

    private List<T> items;

    public QueryList(List<T> items) {
        this.items = items;
    }

    /*
    This code won't compile
    Generic type of the class T doen't wotk on static method.
    class level type parameters work ONLY on INSTANCES,
    when class is loaded into memory, they dont exists,
    so T can't be refernced from static method.
    But a generic method can be used instead of generic class, see below.
     */
//    public static List<T> getMatches(List<T> items, String field, String value) {
//        List<T> matches = new ArrayList<>();
//
//        for (var item : items) {
//            if (item.matchFieldValue(field, value)) {
//                matches.add(item);
//            }
//        }
//        return matches;
//    }

    /*
    This S type parameter is completely different from the class param T
    it belongs to the method only, it gets specified or inferred,
    when this method is invoked. If we omit "static", the method
    will belong to class, and S will not be allowed because S
    was not declared on the class level
     */
    public static <S extends QueryItem> List<S> getMatches(
            List<S> items, String field, String value) {
        List<S> matches = new ArrayList<>();

        for (var item : items) {
            if (item.matchFieldValue(field, value)) {
                matches.add(item);
            }
        }
        return matches;
    }

    public List<T> getMatches(String field, String value) {
        List<T> matches = new ArrayList<>();

        for (var item : items) {
            if (item.matchFieldValue(field, value)) {
                matches.add(item);
            }
        }
        return matches;
    }
}
