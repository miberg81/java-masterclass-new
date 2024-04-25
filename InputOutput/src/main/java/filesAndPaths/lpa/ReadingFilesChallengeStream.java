package filesAndPaths.lpa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/*
Find the 10 words with the highest frequency in the text
 */
public class ReadingFilesChallengeStream {
    public static void main(String[] args) {
        List<String> excluded = List.of(
                "grand",
                "canyon",
                "retrieved",
                "archived",
                "service",
                "original"
        );

        System.out.println("----------- with stream-------------");
        Pattern pattern = Pattern.compile("\\p{javaWhitespace}+");
        try (BufferedReader br = new BufferedReader(
                new FileReader("InputOutput/article.txt"))) {
//            System.out.printf("%d words in file%n",
//                    br.lines()
//                            .flatMap(pattern::splitAsStream)//.flatMap(line -> pattern.splitAsStream(line))
//                            //.flatMap(line -> Arrays.stream(line.split(pattern.toString())))
//                            .count()
//            );

//            System.out.printf("%d words in file%n",
//                    br.lines()
//                            .mapToLong(line -> line.split(pattern.toString()).length)
//                            //.flatMap(line -> Arrays.stream(line.split(pattern.toString())))
//                            .sum()
//            );

            // get the map of word/word-count-in-article


            var result = br.lines()
                    .flatMap(pattern::splitAsStream) //.flatMap(line -> pattern.splitAsStream(line))
                    .map(word->word.replaceAll("\\p{Punct}","")) // remove all punctuation
                    .filter(word->word.length()>4) // word longer than 4 letters
                    .map(String::toLowerCase) // word->word.toLowerCase()
                    .filter(word -> !excluded.contains(word))
                    .collect(Collectors.groupingBy(
                            w->w, // map key (the word itself)
                            Collectors.counting() // count of each distinct word
                            ));

            // sort the map by the count of words(10 most frequent on top)
            result.entrySet().stream()
                    .sorted(Comparator.comparing(
                            Map.Entry::getValue, // compare by this key (word count)
                            Comparator.reverseOrder()) // extra comparator for reverse order
                    )
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue() + " times"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("---------the same without stream-------------");

        String input = null;
        try {
            input = Files.readString(Path.of("InputOutput/article.txt"));
            input = input.replaceAll("\\p{Punct}",""); // remove all punctuation

            //Pattern pattern1  = Pattern.compile("\\w+"); // one or more word characters
            Pattern pattern1  = Pattern.compile("\\w{5,}"); // words with 5 chars or more
            Matcher matcher = pattern1.matcher(input); // find all words in the article

            Map<String, Long> results = new HashMap<>();
            while (matcher.find()) { // loop through matches(words)
                String word = matcher.group().toLowerCase(); // get next match

                // for the first count of the new word in map put 1 (n=1 initially)
                // then if the entry is not a new-key-entry
                // => the value of this word will be incremented by one (n=1)
                if (!excluded.contains(word)) {
                    results.merge(word, 1L, (count, n) -> count += n);
                }
            }

            var sortedEntries = new ArrayList<>(results.entrySet());
            sortedEntries.sort(
                    Comparator.comparing(Map.Entry::getValue,Comparator.reverseOrder()));
            for(int i=0; i< Math.min(10, sortedEntries.size()); i++) {
                var entry = sortedEntries.get(i);
                System.out.println(entry.getKey() + " - " + entry.getValue() + " times");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
