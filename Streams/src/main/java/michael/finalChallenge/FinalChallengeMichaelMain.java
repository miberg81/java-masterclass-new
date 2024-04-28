package michael.finalChallenge;

import michael.CourseEngagement;
import michael.Student;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
//import static java.util.stream.Collectors.groupingBy;

/*
In this challenge, you'll again use streams with the Student Engagement Code.
Before you start, first change the getRandomStudent method on Student, to select a random number and random selection of courses.
Every student should be enrolled, and have activity in at least one class.

Set up three or four courses, use the lecture count version of the constructor on several of these, to pass lecture counts greater than 40.
Generate a list of 10,000 students, who've enrolled in the past 4 years.
Pass the supplier code three or four courses.

Next, answer the following questions.
How many of the students are enrolled in each class?
How many students are taking 1, 2, or 3 classes?
Determine the average percentage complete, for all courses, for this group of students.  Hint, try Collectors.averagingDouble to get this information.
For each course, get activity counts by year, using the last activity year field.
Think about how you'd go about answering these questions, using some of the stream operations you've learned, especially the collect terminal operation in conjunction, with the Collectors helper class methods.

 */
public class FinalChallengeMichaelMain {
    public static void main(String[] args) {
        final List<Student> students = Stream
                .generate(() -> Student.getRandomStudent())
                .limit(1000)
                .toList();

        //How many of the students are enrolled in each class?
        var enrolledInEachClass = students.stream()
                .map(Student::getEngagementMap)
                .flatMap(map->map.values().stream())
                .collect(groupingBy(CourseEngagement::getCourseCode, counting()));

        enrolledInEachClass.forEach(
                (key, value) -> System.out.println(key + " : " + value));

        var studentsPerClassCount = students.stream()
                .map(Student::getEngagementMap)
                .flatMap(map->map.values().stream())
                //.collect(groupingBy(CourseEngagement::getCo))

    }
}
