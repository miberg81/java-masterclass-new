package michael.advanced.wildcardsAndGenericMethodsChallenge.query;

import michael.advanced.wildcardsAndGenericMethodsChallenge.Student1;

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
public class QueryList1<T extends Student1 & QueryItem1>  extends ArrayList<T>{

    public QueryList1() {
    }

    public QueryList1(List<T> items) {
        super(items);
    }

    public QueryList1<T> getMatches(String field, String value) {
        QueryList1<T> matches = new QueryList1<>();
        for (var item : this) {
            if (item.matchFieldValue(field, value)) {
                matches.add(item);
            }
        }
        return matches;
    }

}
