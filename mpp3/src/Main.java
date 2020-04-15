import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
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

    static String verdict(List<Language> languages,String text)
    {
        List<String> result = new ArrayList<>();
        for(Language l : languages)
        {
            if (l.getName().equals(l.check(text)))
                result.add(l.getName());
        }
        int random;
        try{random=new Random().nextInt(result.size());}
        catch (IllegalArgumentException e) {random=-1;}
        if(random==-1)
            return "\nNotFound";
        else
            return "("+result.size()+"):\n"+result.get(random);
    }

    static void checkFromTxt(List<Language> languages) throws IOException
    {
        Files.walkFileTree(Paths.get("test"), EnumSet.of(FileVisitOption.FOLLOW_LINKS), Integer.MAX_VALUE,
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

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        List<Language> languages = new ArrayList<>();
        String path = "training";
        processDir(path, languages);
        simpleLearning(languages);
        checkAll(languages);
        checkFromTxt(languages);
        for(Language l : languages)
            System.out.println(l.getName()+" "+Arrays.toString(l.getWeights()));

        primaryStage.setTitle("Predict Language");
        HBox hBox = new HBox();
        VBox vBox = new VBox();
        TextArea ta = new TextArea();
        ta.setWrapText(true);

        StringBuilder labelText= new StringBuilder();
        for(Language l : languages)
            labelText.append(l.name).append("\n");
        Label label = new Label("Available languages:\n"+labelText.toString());
        label.setFont(Font.font(15));

        Label result = new Label();
        result.setFont(Font.font("verdana", FontWeight.BOLD,18));
        result.setStyle("-fx-text-fill: red;");

        Button btn = new Button();
        btn.setText("Check text");
        btn.setOnAction(event -> result.setText("Verdict"+verdict(languages,ta.getText())));

        vBox.getChildren().addAll(btn,label,result);
        hBox.getChildren().addAll(ta,vBox);
        primaryStage.setScene(new Scene(hBox, 640, 480));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
