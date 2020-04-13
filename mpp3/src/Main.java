import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        List<Language> languages = new ArrayList<>();
        String path = "data";
        processDir(path, languages);
        System.out.println(languages.toString());
    }

    static void processDir(String path, List<Language> languages) throws IOException
    {
        Files.walkFileTree(Paths.get(path), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<>() {
                    Language tmp;
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    {
                        if(dir.toString().equals(path))
                            return CONTINUE;
                        else
                        {
                            tmp = new Language(dir.getFileName().toString());
                            languages.add(tmp);
                        }
                        return CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                    {
                        tmp.addTrainingFile(Files.lines(file, StandardCharsets.UTF_8).collect(Collectors.joining(System.lineSeparator())));
                        return CONTINUE;
                    }
                });

    }
//    static void processDir(List<Language> languages)
//    {
//        try
//        {
//            for(Path path : Files.walk(Paths.get("data")).filter(path -> path.toString().endsWith(".txt")).collect(Collectors.toList()))
//            {
//
//                System.out.println(path);
//            }
//        }
//        catch (IOException ex)
//        {
//            ex.printStackTrace();
//        }
//    }
}
