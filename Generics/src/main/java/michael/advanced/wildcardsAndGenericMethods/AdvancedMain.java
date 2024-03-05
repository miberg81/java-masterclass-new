package michael.advanced.wildcardsAndGenericMethods;

import michael.advanced.wildcardsAndGenericMethods.query.QueryItem;
import michael.advanced.wildcardsAndGenericMethods.query.QueryList;

import java.util.ArrayList;
import java.util.List;

record Employee(String name) implements QueryItem {
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        return false;
    }
}

public class AdvancedMain {
    public static void main(String[] args) {

        int studentCount = 10;
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) {
            students.add(new Student());
        }
        students.add(new LPAStudent());
        //printList(students);
        printMoreLists(students);


        List<LPAStudent> lpaStudents = new ArrayList<>();
        for (int i = 0; i < studentCount; i++) {
            lpaStudents.add(new LPAStudent());
        }
        //printList(lpaStudents);
        printMoreLists(lpaStudents);

        testList(new ArrayList<String>(List.of("Able", "Barry", "Charlie")));
        testList(new ArrayList<Integer>(List.of(1, 2, 3)));

        // use query functionality by using an object the Query service class;
        var queryList = new QueryList<>(lpaStudents);
        var matches = queryList.getMatches("Course", "Python");
        printMoreLists(matches);

        // use the same functionality without creating QueryList object,
        // instead just use a static generic method on this class
        var students2021 = QueryList.getMatches(
                students, "YearStared", "2021");
        printMoreLists(students2021);

        // Example how to specify a type argument
        // for a generic method which is a static method of a class,
        // in cases when the type can't be inferred from the argument passed to it
        var students2022 =
                QueryList.<Student>getMatches(
                        new ArrayList<>(), "YearStared", "2022");
        printMoreLists(students2022);

        // Test multiple upper bounds with wrong upper bound
        //QueryList<Employee> employeeList = new QueryList<>();
    }

    public static void printMoreLists(List<? extends Student> students) {
        // can't add element because compiler does not know exactly the type of element.
//        Student last = students.get(students.size() - 1);
//        students.set(0, last);

        for (var student : students) {
            System.out.println(student);
        }
        System.out.println();
    }

/*
    public static <T extends Student> void printList(List<T> students) {
        for (var student : students) {
            System.out.println(student.getYearStarted() + ": " + student);
        }
        System.out.println();
    }
*/

    public static void testList(List<?> list) {
        for (var element : list) {
            if (element instanceof String s) {
                System.out.println("String: " + s.toUpperCase());
            } else if (element instanceof Integer i) {
                System.out.println("Integer: " + i.floatValue());
            }
        }
        System.out.println();
    }

    // Type erasure when overloading : List<String> ---compile --> List<Object>
//    public static void testList(List<String> list) {
//        for (var element : list) {
//            System.out.println("String: " + element.toUpperCase());
//        }
//        System.out.println();
//    }

    // Type erasure when overloading: List<Integer> ---compile --> List<Object>
//    public static void testList(List<Integer> list) {
//        for (var element : list) {
//            System.out.println("Integer: " + element.floatValue());
//        }
//        System.out.println();
//    }
}
