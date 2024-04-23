package studentsChallenge.michael;

import java.util.List;
import java.util.stream.Stream;

public class MichaelMain {

    public static void main(String[] args) {

        Course pymc= new Course("PYMC", "Python Masterclass");
        Course jmc= new Course("JMC", "Java Masterclass");
//        Student tim = new Student("AU", 2019, 30, "M",
//                true, jmc, pymc);
//        System.out.println(tim);
//
//        tim.watchLecture("JMC", 10, 5, 2019);
//        tim.watchLecture("PYMC", 7, 7, 2020);
//        System.out.println(tim);

//        Stream.generate(() -> Student.getRandomStudent(jmc, pymc))
//                .limit(10)
//                .forEach(System.out::println);

        final List<Student> students = Stream.generate(
                () -> Student.getRandomStudent(
                        new Course("1","C++"),
                        new Course("2","Math")
                        ))
                .limit(1000)
                .toList();

        long mailsFemailsNum = students.stream()
                .filter(s -> s.getGender().equals("M") || s.getGender().equals("F"))
                //.peek(System.out::println)
                .count();
        System.out.println(mailsFemailsNum);

        long under30 = students.stream()
                .filter(s -> s.getAge() < 30)
                .count();
        System.out.println(under30);

        long over30 = students.stream()
                .filter(s -> s.getAge() > 30)
                .count();
        System.out.println(over30);

        long range30to60 = students.stream()
                .filter(s -> s.getAge() > 30 && s.getAge() <60)
                .count();
        System.out.println(range30to60);

        System.out.println("age statistics are " +
                students.stream().mapToInt(Student::getAge).summaryStatistics());

        students.stream()
                .map(Student::getCountryCode)
                .distinct()
                .reduce((a, b) -> a.concat(" ").concat(b))
                .ifPresentOrElse(System.out::println,
                        () -> System.out.println("no country codes found"));

        final long activeEnrolledOver7Years = students.stream()
                .filter(s -> s.getYearEnrolled() > 7 && s.getMonthsSinceActive() < 12)
                .limit(5)
                        .peek(System.out::println)
                                .count();
        System.out.println("enrolled for more than 7 years and active: " + activeEnrolledOver7Years);


    }
}
