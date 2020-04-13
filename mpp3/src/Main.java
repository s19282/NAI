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
        String path = "training";
        processDir(path, languages);
        simpleLearning(languages);
        //checkAll(languages);
        checkFromTxt("test",languages);
    }
    static void checkAll(List<Language> languages)
    {
        for(Language lang : languages)
        {
            System.out.println("---------");
            System.out.println(lang.getName());
            System.out.println("---------");
            for(Language l : languages)
            {
                for(int[] v : l.getTrainingVectors())
                {
                    System.out.println(l.getName()+" "+lang.check(v));
                }
            }
        }
    }

    static void checkFromTxt(String path,List<Language> languages) throws IOException
    {
        Files.walkFileTree(Paths.get(path), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
                new SimpleFileVisitor<>()
                {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
                    {
                        System.out.println("------------");
                        System.out.println(file.toString());
                        System.out.println("------------");
                        for(Language language : languages)
                        {
                            System.out.println(language.getName()+" "+language.check(Files.lines(file, StandardCharsets.UTF_8).map(String::toUpperCase).collect(Collectors.joining(" "))));
                        }
                        return CONTINUE;
                    }
                });
    }
    static void simpleLearning(List<Language> languages)
    {
        for(Language lang : languages)
            lang.simple(languages);
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
