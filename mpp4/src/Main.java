import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        List<Iris> flowers = readData("iris_training.txt");
        for(Iris i : flowers)
            System.out.println(i.toString());
    }

    public static List<Iris> readData(String path)
    {
        List<Iris> outcome = new ArrayList<>();
        try
        {
            List<String> dataFromFile = Files.readAllLines(Paths.get(path));

            for(String line : dataFromFile)
            {
                line=line.replaceAll(",",".");
                String[] tmp = line.split("\\s+");
                tmp= Arrays.copyOfRange(tmp,1,tmp.length);
                outcome.add(new Iris(Arrays.stream(Arrays.copyOfRange(tmp, 0, tmp.length - 1))
                        .mapToDouble(Double::parseDouble)
                        .toArray()));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outcome;
    }
}
