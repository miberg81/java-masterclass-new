package michael.advanced.wildcardsAndGenericMethodsChallenge;

import michael.advanced.wildcardsAndGenericMethodsChallenge.query.QueryItem1;
import michael.advanced.wildcardsAndGenericMethodsChallenge.query.QueryList1;

import java.util.Comparator;
import java.util.List;

record Employee1(String name) implements QueryItem1 {
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}

public class AdvancedMainChallenge {
    public static void main(String[] args) {
        int studentCount = 25;
        QueryList1<LPAStudent1> queryList1 = new QueryList1<>();
        for (int i = 0; i < studentCount; i++) {
            queryList1.add(new LPAStudent1());
        }

//        QueryList1<LPAStudent1> result = queryList1
//                .getMatches("percentComplete", "50");
//        Arrays.sort(result.toArray(), new StudentIdComparator());
//        Arrays.toString(result.toArray());

        System.out.println("Ordered items");
        queryList1.sort(Comparator.naturalOrder());
        printList(queryList1);

        System.out.println("------Matches----------");
        var matches = queryList1
                .getMatches("PercentComplete", "50")
                .getMatches("Course", "Python");
        //matches.sort(Comparator.naturalOrder()); // must implement on element comparable fot this!
        matches.sort(new PercentCompleteComparator());
        printList(matches);

        System.out.println("------Ordered again--------");
        matches.sort(null);//when passing null Comparator, natural order will be used;
        printList(matches);
    }

    public static void printList(List<?> students){
        for (var student : students) {
            System.out.println(student);
        }
    }
}

/*
Custom comparator is used if we want to sort differently
 from the most common(natural order) sort,
 implemented by Comparable, without changing it
 */
class PercentCompleteComparator implements Comparator<LPAStudent1> {
    @Override
    public int compare(LPAStudent1 student1, LPAStudent1 student2) {
        return (int)(student1.getPercentComplete()-student2.getPercentComplete());
    }
}


