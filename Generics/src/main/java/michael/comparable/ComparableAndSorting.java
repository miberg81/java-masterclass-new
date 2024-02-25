package michael.comparable;

import java.util.Arrays;
import java.util.Comparator;

public class ComparableAndSorting {
    public static void main(String[] args) {
        Integer five = 5;
        Integer[] others = {0, 5, 10, -50, 50};

        for (Integer other : others) {
            int res = five.compareTo(other);
            System.out.printf("%d %s %d: compareToResult=%d%n",
                    five,
                    (res == 0 ? "==" :
                            (res<0) ? "<" :
                                    ">"),
                    other,
                    res
            );
        }

        System.out.println("------------------------------------------------");

        String banana = "banana";
        String[] fruits = {"apple", "banana", "pear", "BANANA"};

        for (String fruit : fruits) {
            int res = banana.compareTo(fruit);
            System.out.printf("%s %s %s: compareToResult=%d%n",
                    banana,
                    (res == 0 ? "==" :
                            (res<0) ? "<" :
                                    ">"),
                    fruit,
                    res
            );
        }

        Arrays.sort(fruits);
        System.out.println(Arrays.toString(fruits));

        System.out.println("A:" +(int)'A' + " " + "a:"+(int)'a');
        System.out.println("B:" +(int)'B' + " " + "b:"+(int)'b');
        System.out.println("P:" +(int)'P' + " " + "p:"+(int)'p');

        Student tim = new Student("Tim");
        Student[] students = {
                new Student("Zach"),
                new Student("Tim"),
                new Student("Ann")
        };
        Arrays.sort(students);
        System.out.println(Arrays.toString(students));

        // example of class cast exception when using raw type student
        // String cannot be cast to class Student
        //System.out.println("result = " + tim.compareTo("Mary"));

        System.out.println("tim compared to new Tim result = " +
                tim.compareTo(new Student("TIM")));

        // compare by gpa with custom comparator(highest to lowest),
        // which will be used INSTEAD of comparable
        System.out.println("-------------------------------------");
        Comparator<Student> gpaSorter = new StudentGPAComparator();
        Arrays.sort(students, gpaSorter.reversed());
        System.out.println(Arrays.toString(students));

    }
}

