import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Language> languages = new ArrayList<>();
        String path = "data";
        processDir(path, languages);
        learn(languages);
    }

    static void learn(List<Language> languages)
    {
        for (Language lang : languages)
        {
            System.out.println("lang");
            boolean stop;
            do
            {
                System.out.println("do");
                stop = false;
                for (Language language : languages)
                {
                    System.out.println("language");
                    stop = lang.learn(language, stop);
                }
            }
            while (stop);
        }
        System.out.println("Done");
    }

    static void processDir(String path, List<Language> languages) throws IOException
    {
        Files.walkFileTree(Paths.get(path), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<>()
                {
                    Language tmp;

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                    {
                        if (dir.toString().equals(path))
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
                        tmp.addTrainingFile(Files.lines(file, StandardCharsets.UTF_8).map(String::toUpperCase).collect(Collectors.joining(" ")));
                        tmp.addTrainingVector(Files.lines(file, StandardCharsets.UTF_8).map(String::toUpperCase).collect(Collectors.joining(" ")));
                        return CONTINUE;
                    }
                });
    }
}