package filesAndPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.System.out;

public class MainWordsChallengeMichaelMain {

    public static void main(String[] args) {

        Path path = Path.of("InputOutput/ai.txt");

        String content;
        try {
             content = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.println(content);
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Stream.of(content.split(" "))
                .filter(word -> pattern.matcher(word).matches())
                .filter(word -> word.length() > 5)
                .forEach(System.out::println);
//                .sorted(Comparator.)
//                .collect(Collectors.groupingBy(
//                        word -> word,
//                        Collectors.counting()
//                ));

        //result.entrySet().forEach(System.out::println);
        //.forEach(s-> System.out.println(s));
    }
}
