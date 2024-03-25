package filesAndPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateDeleteFileMain {
    public static void main(String[] args) {
        // useFileIOLegacy("tempFile.txt");
        usePathNEO("pathFile.txt");
    }

    public static void useFileIOLegacy(String fileName) {
        File file = new File(fileName);
        boolean fileExists = file.exists();

        System.out.printf("File '%s' %s%n", fileName, fileExists ? "exists." : "doesn't exist");

        if (fileExists) {
            System.out.println("Deleting file: " + fileName);
            fileExists = !file.delete();
        }

        if (!fileExists) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong");
            }
            System.out.println("Created file: " + fileName);
            if (file.canWrite()) {
                System.out.println("Writing permission exists...will write");
            }
        }
    }

    public static void usePathNEO(String fileName) {
        Path path = Path.of(fileName);
        boolean fileExists = Files.exists(path);

        System.out.printf("File '%s' %s%n", fileName, fileExists ? "exists." : "doesn't exist");

        if (fileExists) {
            System.out.println("Deleting file: " + fileName);
            try {
                Files.delete(path);
                fileExists = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!fileExists) {
            try {
                Files.createFile(path);
                System.out.println("Created file: " + fileName);
                if (Files.isWritable(path)) {
                    System.out.println("Writing permission exists...writing to file...");
                    Files.writeString(path, """
                            Here is some data,
                            for my file
                            """);
                }
                System.out.println("----------------------");
                System.out.println("And I can read to too. The lines are: ");
                Files.readAllLines(path).forEach(System.out:: println);
            } catch (IOException e) {
                throw new RuntimeException("Something went wrong");
            }
        }
    }

}
