package filesAndPaths.lpa;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReadingWithNeo2Main {

    public static void main(String[] args) {

        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());

        Path path = Path.of("InputOutput/fixedWidth.txt");
        try {
            System.out.println(new String(Files.readAllBytes(path)));
            System.out.println("----------------");
            System.out.println(Files.readString(path));

            // pattern to group text by constant column legth: name/age/dept/salary/state
            // length of 15(any symbol), length of 3(any symbol) etc
            Pattern pattern = Pattern.compile("(.{15})(.{3})(.{12})(.{8})(.{2}).*");
            Set<String> values = new TreeSet<>();
            // extract list of unique departments
            // go line by line. apply pattern to each line, if matches -> extract group 3 (department)
            Files.readAllLines(path).forEach(s -> {
                if (!s.startsWith("Name")) { // skip header
                    Matcher m = pattern.matcher(s);
                    if (m.matches()) {
                        values.add(m.group(3).trim());
                    }
                }
            });
            System.out.println("-------------result with foreach-------------------");
            System.out.println(values);

            // do the same with stearm of lines
            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1)
                        //.map(line ->pattern.matcher(line))
                        //.filter(matcher -> matcher.matches())
                        .map(pattern::matcher)
                        .filter(Matcher::matches)
                        .map(matcher -> matcher.group(3).trim())
                        .distinct()
                        .sorted()
                        .toArray(String[]::new);
                System.out.println("-------------result with stream of lines-------------------");
                System.out.println(Arrays.toString(results));
            }

            // extract a map of department/department count
            // Finance=5, HR=4, IT=6, Marketing=5
            try (var stringStream = Files.lines(path)) {
                var results = stringStream
                        .skip(1)
                        //.map(line ->pattern.matcher(line))
                        //.filter(matcher -> matcher.matches())
                        .map(pattern::matcher)
                        .filter(Matcher::matches)
                        .collect(Collectors.groupingBy(
                                matcher -> matcher.group(3).trim(),
                                Collectors.counting())
                        );

                System.out.println("-----------map of dep/dep count--------------");
                results.entrySet().forEach(System.out::println);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
