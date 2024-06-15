package michael.terminalOperationsChallenge;

import michael.Course;
import michael.finalChallenge.ChallengeStudent;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class TerminalOperationsChallenge2Main {

    public static void main(String[] args) {

        Course pymc = new Course("PYMC", "Python Masterclass", 50);
        Course jmc = new Course("JMC", "Java Masterclass", 100);

        Course javaGames = new Course("JGAME", "Creating games in Java");

        final int countOfStudents = 10000;

        // create many students with generate
//        final List<ChallengeStudent> students = Stream.generate(
//                        () -> ChallengeStudent.getRandomStudent(pymc, jmc))
//                .limit(countOfStudents)
//                .collect(toList());

        // create many students with iterate (Integers) - boxing!
//        final List<ChallengeStudent> students =
//                Stream.iterate(1, s -> s < countOfStudents, s -> s + 1)
//                .map(s -> ChallengeStudent.getRandomStudent(jmc, pymc))
//                .toList();

        // with intStream we can use primitives in stream (ints)
        List<ChallengeStudent> students = IntStream
                .rangeClosed(1, countOfStudents)
                // this changes stream type from IntStream(integers) to Stream<T>(objects)
                .mapToObj(s -> ChallengeStudent.getRandomStudent(jmc, pymc))
                .toList();

        // Stream<Double> - boxing! not efficient
//        var average = students.stream()
//                .map(student -> student.getPercentComplete("JMC"))
//                //.peek(System.out::println)
//                .reduce((a, b) -> a + b)// sum of percentages
//                .map(result -> result / countOfStudents)// average of percentages
//                .get();

        // student who completed more than 3/4 of average to offer new course
//        List<ChallengeStudent> completedMoreThanAverage = students.stream()
//                .filter(s -> s.getPercentComplete("JMC") > 1.25 * average)
//                .sorted(Comparator.comparing(ChallengeStudent::getYearEnrolled))
//                .filter(student -> student.getMonthsSinceActive()>0)
//                .limit(10)
//                .peek(System.out::println)
//                .toList();


        double totalPercent = students.stream()
                .mapToDouble(student -> student.getPercentComplete("JMC"))
                //.peek(System.out::println)
                .reduce(0, Double::sum);// 0 is the seed(initial) value for the sum, then we add to it

        double avePercent = totalPercent/ students.size();
        System.out.println("avePercent complete " + avePercent);

        // minimum percent for eligible for binus student
        int topPercent = (int)(avePercent*1.25);
        System.out.println("topPercent complete " + topPercent);

        // this comparator is not good to return list, but not treeSet,
        // treeSet code uses this to sort, but also determine uniqueness of student,
        // but this comparator doesn't define uniqueness(because many students enroll in the same year)
        Comparator<ChallengeStudent> longTermStudentComparator =
                Comparator.comparing(ChallengeStudent::getYearEnrolled);

        List<ChallengeStudent> hardWorkers = students.stream()
                        .filter(s->s.getMonthsSinceActive("JMC")==0) // are active in last month
                        .filter(s->s.getPercentComplete("JMC")>=topPercent) // have percentage more than threshold
                        .sorted(longTermStudentComparator)
                        .limit(10)
                        .toList();

        System.out.println("hardWorkers = " + hardWorkers.size());


        hardWorkers.forEach(s->{
            s.addCourse(javaGames, LocalDate.now());
            //System.out.print(s);
            System.out.print(s.getStudentId() + " ");
        });
        System.out.println();
        System.out.println("--------------------------");

        // will make students unique,because for those with same year, id will give uniqueness
        Comparator<ChallengeStudent> uniqueSorted = longTermStudentComparator
                .thenComparing(ChallengeStudent::getStudentId);

        // same code with long chain!
        students.stream()
                        .filter(s->s.getMonthsSinceActive("JMC")==0) // are active in last month
                        .filter(s->s.getPercentComplete("JMC")>=topPercent) // have percentage more than threshold
                        .sorted(longTermStudentComparator)
                        .limit(10)
                        //.toList()
                        //.collect(Collectors.toList()) // same as toList() but collection is modifiable

                        // toSet() gives back hashset which has no order!
                        // sorted(0 is still needed to pick up the 10 which fit creteria,
                        // than the order of this group is lost
                        //.collect(Collectors.toSet())

                        // returns only 1 result with the old non-unique prop. comparator
//                        .collect(
//                                ()-> new TreeSet<>(uniqueSorted), // supplier
//                                TreeSet::add, // accumulator
//                                TreeSet::addAll // combiner
//                        )

                        .forEach(s->{
                            s.addCourse(javaGames, LocalDate.now());
                            System.out.print(s.getStudentId() + " ");
                        });

    }
}
