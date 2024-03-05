package michael.advanced.wildcardsAndGenericMethodsChallenge;

import michael.advanced.wildcardsAndGenericMethodsChallenge.query.QueryItem1;

import java.util.Random;

public class Student1 implements QueryItem1, Comparable<Student1> {
    protected String name;
    protected String course;
    protected int yearStarted;
    protected int studentId;

    protected static Random random = new Random();
    private static String[] firstNames = {"Ann", "Bill", "Cathy", "John", "Tim"};
    private static String[] courses = {"C++", "Java", "Python"};

    private static int LAST_ID = 10_000;

    /**
     * Create a big number of different names, like Ann A, Cathy Z etc..
     * The first name is randomly picked from exciting five.
     * The last name is a random single letter from A to Z
     */
    public Student1() {
        // randomly generate single character for last name from A to Z
        // 65 is the integer for capital A, 90 iz "Z" (91 is because upper bound is exclusive)
        int lastNameIndex = random.nextInt(65, 91);

        final String randomFirstName = firstNames[random.nextInt(5)];
        name = randomFirstName + " " + (char) lastNameIndex;

        // random course from existing
        course = courses[random.nextInt(3)];

        // random year from 2018-2022
        yearStarted = random.nextInt(2018, 2023);

        studentId = LAST_ID++;
    }

    @Override
    public String toString() {
        // minus sign indicate "left justify" and 15 is the allotted width
        return "%d %-15s %-15s %d".formatted(studentId, name, course, yearStarted);
    }

    public int getYearStarted() {
        return yearStarted;
    }

    /**
     * If a fieldName to match is Course, values maybe Java, Python etc
     * If a fieldName to match is Course, values maybe Java, Python etc
     *
     * @param fieldName
     * @param value
     * @return boolean
     */
    @Override
    public boolean matchFieldValue(String fieldName, String value) {
        String fName = fieldName.toUpperCase();
        return switch (fName) {
            case "NAME" -> name.equalsIgnoreCase(value);
            case "COURSE" -> course.equalsIgnoreCase(value);
            case "YEARSTARTED" -> yearStarted == (Integer.parseInt(value));
            default -> false;
        };
    }

    @Override
    public int compareTo(Student1 o) {
        return Integer.valueOf(studentId).compareTo(o.studentId);
    }
}
