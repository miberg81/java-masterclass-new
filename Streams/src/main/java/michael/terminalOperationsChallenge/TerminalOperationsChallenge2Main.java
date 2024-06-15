package michael.terminalOperationsChallenge;

import michael.Course;
import michael.finalChallenge.ChallengeStudent;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class TerminalOperationsChallenge2Main {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass", 50);
        Course jmc = new Course("JMC", "Java Masterclass", 100);

        Course javaGames = new Course("CGIJ", "Creating games in Java");

        final int countOfStudents = 10000;

        // create many students with generate
//        final List<ChallengeStudent> students = Stream.generate(
//                        () -> ChallengeStudent.getRandomStudent(pymc, jmc))
//                .limit(countOfStudents)
//                .collect(toList());

        final List<ChallengeStudent> students =
                Stream.iterate(1, s -> s < countOfStudents, s -> s + 1)
                .map(s -> ChallengeStudent.getRandomStudent(jmc, pymc))
                .toList();

        var average = students.stream()
                .map(student -> student.getPercentComplete("JMC"))
                .peek(System.out::println)
                .reduce((a, b) -> a + b)// sum of percentages
                .map(result -> result / countOfStudents)// average of percentages
                .get();

        // student who completed more than 3/4 of average to offer new course
        List<ChallengeStudent> completedMoreThanAverage = students.stream()
                .filter(s -> s.getPercentComplete("JMC") > 1.25 * average)
                .sorted(Comparator.comparing(ChallengeStudent::getYearEnrolled))
                .filter(student -> student.getMonthsSinceActive()>0)
                .limit(10)
                .peek(System.out::println)
                .toList();

        students.forEach(s->s.addCourse(javaGames, LocalDate.now()));

    }
}
