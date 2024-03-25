package filesAndPaths;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathsMain1 {
    public static void main(String[] args) {

        // IO (LEGACY)
        // ====================

        // working directory, where the application is running from
        // current working der: cwd(linux),pdw(mac),cd(win)
        // absolute path is relative to root directory
        System.out.println("****Current working directory " + new File("").getAbsolutePath());
        // C:\Michael\Dev\Workspaces\Training\Java\JavaMasterClassNew

        // discovering root directories
        System.out.println("****Roots are: ");
        for (File f : File.listRoots()) {
            System.out.println(f); // prints:   C:\    G:\   H:\
        }

        // relative path(relative to project root = JavaMasterClassNew)
        String filename1 = "InputOutput/files/testing.csv";
        File file1 = new File(filename1);
        System.out.println("file from module directory " + file1.getAbsolutePath());
        // file from module directory C:\Michael\Dev\Workspaces\Training\Java\JavaMasterClassNew\InputOutput\files\testing.csv

        // specify absolute (from root) path by adding forward slash in the filename.
        String filename2 = "/files/testing.csv";
        File file2 = new File(filename2);
        System.out.println("file from root location " + file2.getAbsolutePath());
        // file from root location C:\files\testing.csv

        // specify absolute (from root) path by adding "/" = root parent  (file name without slash!)
        String filename3 = "files/testing.csv";
        File file3 = new File("/", filename3);
        System.out.println("file from root location " + file3.getAbsolutePath());
        // file from root location C:\files\testing.csv

        // specify relative to work dir  path by adding "." = working dir  (file name without slash!)
        String filename4 = "InputOutput/files/testing.csv";
        File file4 = new File(".", filename4);
        System.out.println("file from root location " + file4.getAbsolutePath());
        // file is found, but path is printed with REDUNDANT name element
        // file from root location C:\Michael\Dev\Workspaces\Training\Java\JavaMasterClassNew\.\InputOutput\files\testing.csv

        // specify relative to work dir path with new File("").getAbsolutePath() (file name without slash!)
        String filename5 = "InputOutput/files/testing.csv";
        File file5 = new File(new File("").getAbsolutePath(), filename5);
        System.out.println("file from root location " + file5.getAbsolutePath());
        // C:\Michael\Dev\Workspaces\Training\Java\JavaMasterClassNew\InputOutput\files\testing.csv

        if (!file4.exists()) {
            System.out.println("File doesn't exist");
            return;
        }
        System.out.println("File exists");

        // NEO2
        // ====================
        Path path = Paths.get("InputOutput/files/testing.csv");
        if (!Files.exists(path)) {
            System.out.println("Neo 2: File doesn't exist");
            return;
        }
        System.out.println("Neo2: File exists");

    }
}