package lpa.StreamingStudents;

import lpa.StreamingStudents.model.Course;
import lpa.StreamingStudents.model.Student;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.*;

public class MainMapping {

    public static void main(String[] args) {

        Course pymc= new Course("PYMC", "Python Masterclass", 50);
        Course jmc= new Course("JMC", "Java Masterclass", 100);
        Course jgames = new Course("JGAME", "Creating games in Java");

        // 1.STREAMS TO MAPS
        List<Student> students = IntStream
                .rangeClosed(1, 5000)
                .mapToObj(s -> Student.getRandomStudent(jmc, pymc))
                .toList();

        var mappedStudents = students.stream()
                .collect(Collectors.groupingBy(Student::getCountryCode));

        mappedStudents.forEach((k, v) -> System.out.println(k + " " + v.size()));

        System.out.println("-----------------------");
        int minAge = 25;
        var youngerSet = students.stream()
                .collect(groupingBy(Student::getCountryCode,
                        filtering(s -> s.getAge() <= minAge , toList())));

        youngerSet.forEach((k, v) -> System.out.println(k + " " + v.size()));

        var experienced = students.stream()
                .collect(partitioningBy(Student::hasProgrammingExperience));
        System.out.println("Experienced Students = " + experienced.get(true).size());

        var expCount = students.stream()
                .collect(partitioningBy(Student::hasProgrammingExperience, counting()));
        System.out.println("Experienced Students = " + expCount.get(true));

        var experiencedAndActive = students.stream()
                .collect(partitioningBy(
                        s -> s.hasProgrammingExperience()
                        && s.getMonthsSinceActive() == 0,
                        counting()));
        System.out.println("Experienced and Active Students = " +
                experiencedAndActive.get(true));

        // get a map by country and within each country a map by gender
        // Multilevel map type Map<String, Map<String,List<Student>
        var multiLevel = students.stream()
                .collect(groupingBy(Student::getCountryCode,
                        groupingBy(Student::getGender)));

        // print multilevel map
        multiLevel.forEach((key, value) -> {
            System.out.println(key);
            // value is also a map!
            value.forEach((key1, value1) ->
                    System.out.println("\t" + key1 + " " + value1.size()));
        });


        // 2.MAPS TO STREAMS (using flat map)

        // given a map of experienced/not-experienced students Map<Boolean, List<Student>>
        // count the total amount of student (without streams)
        long studentBodyCount = 0;
        for (var list : experienced.values()) {
            studentBodyCount += list.size();
        }
        System.out.println("studentBodyCount no stream = " + studentBodyCount);

        // count using streams
        // the stream here is stream of lists
        studentBodyCount = experienced.values().stream()
                .mapToInt(l -> l.size())
                .sum();
        System.out.println("studentBodyCount with stream = " + studentBodyCount);

        // count of student who were active in the last 4 months
        // (streams without flat map = ugly code example)
        studentBodyCount = experienced.values().stream()
                .map(l -> l.stream() // Stream<Stream<Student> returned here (map is stile one to one!)
                        .filter(s -> s.getMonthsSinceActive() <= 3)
                        .count()
                )
                .mapToLong(l -> l)
                .sum();
        System.out.println("Active Students with streams WITHOUT flat map= " + studentBodyCount);

        // count of student who were active in the last 4 months
        // (streams with flat map = pretty code example)
        long count = experienced.values().stream()
                .flatMap(l -> l.stream())
                .filter(s -> s.getMonthsSinceActive() <= 3)
                .count();
        System.out.println("Active Students with streams WITH flat map= " + count);

        // Multilevel map = Map<String, Map<String,List<Student>
        // Active Students in multiLevel UGLY code
        count = multiLevel.values().stream()
                .flatMap(map -> map.values().stream()
                        .flatMap(l -> l.stream())
                )
                .filter(s -> s.getMonthsSinceActive() <= 3)
                .count();
        System.out.println("Active Students in multiLevel UGLY code= " + count);

        // Active Students in multiLevel PRETTY code
        count = multiLevel.values().stream()
                .flatMap(map -> map.values().stream())
                .flatMap(list -> list.stream())
                .filter(s -> s.getMonthsSinceActive() <= 3)
                .count();
        System.out.println("Active Students in multiLevel PRETTY code= " + count);

    }
}
