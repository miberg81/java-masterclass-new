package michael.comparable;

import java.util.Comparator;
import java.util.Random;

class Student implements Comparable<Student> {

    private static int LAST_ID = 1000;
    private static Random random = new Random();

    protected String name;
    private int id;
    protected double gpa;

    public Student(String name) {
        this.name = name;
        id = LAST_ID++;

        // GPA(great pint evarage) from 0 to 4.0
        // 4.0 = A, 3.0 = B, 3.0 = C....
        gpa = random.nextDouble(1.0, 4.0);
    }

    @Override
    public String toString() {
        return "%d - %s (%.2f)".formatted(id, name, gpa);
    }

    // compare by unique student id (sorts form lowest to highest)
    @Override
    public int compareTo(Student other) {
        // better user Integers method than manual calculation x-y, less error prone
        return Integer.valueOf(id).compareTo(Integer.valueOf(other.id));
    }

    // compare by name (not perfect as it compare first letters only)
    // and capitalLetter-small letter always gives 32.
//    @Override
//    public int compareTo(Student other) {
//        return name.compareTo(other.name);
//    }

// Method to use with raw type comparable(not recommended!)
// Throw exceptions
//    @Override
//    public int compareTo(Object o) {
//        Student other  = (Student) o;
//        return name.compareTo(other.name);
//    }
}

// Compare by gpa (will sort from lowest to highest),
// but in case of equality, then sort alphabetically by name
class StudentGPAComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        // possible, but NOT recommended to do o2.compareTo(o1)
        // to reverse order
        return (o1.gpa + o1.name).compareTo(o2.gpa + o2.name);
    }
}

