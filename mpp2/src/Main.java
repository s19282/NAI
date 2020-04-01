import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main
{
    static final double alpha = 0.5;
    //static final double alpha = Math.random();
    static double[] weights;
    static double threshold = 0;

    public static void main(String[] args)
    {
        List<Iris> training = readData("iris_training.txt");
        List<Iris> test = readData("iris_test.txt");
        generateValues(weights);
        boolean wagesChanged= false;
        while (!wagesChanged)
        {
            System.out.println("training");
            for(Iris iris : training)
            {
                wagesChanged=learn(iris);
            }
        }

    }
    public static boolean learn(Iris iris)
    {
        int s;
        boolean changed=false;
        while (true)
        {
            s=0;
            for (int i=0; i<weights.length; i++)
                s+=weights[i]*iris.attributes[i];
            if((iris.value==1&&s<threshold)||(iris.value==0&&s>=threshold))
            {
                changed=true;
                for (int i=0; i<weights.length; i++)
                    weights[i]+=alpha*(iris.value-(s>=threshold?1:0))*(iris.attributes[i]);

            }
            else
                break;
        }
        return changed;
    }


    public static List<Iris> readData(String path)
    {
        List<Iris> outcome = new ArrayList<>();
        try
        {
            List<String> dataFromFile = Files.readAllLines(Paths.get(path));
            weights = new double[dataFromFile.get(0).split("\\s+").length-2];//upewnic sie ze dziala
            for(String line : dataFromFile)
            {
                line=line.replaceAll(",",".");
                String[] tmp = line.split("\\s+");
                tmp=Arrays.copyOfRange(tmp,1,tmp.length);
                outcome.add(new Iris(Arrays.stream(Arrays.copyOfRange(tmp, 0, tmp.length - 1))
                        .mapToDouble(Double::parseDouble)
                        .toArray(),tmp[tmp.length-1]));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outcome;
    }

    public static void generateValues(double[] v)
    {
        for(int i=0; i<v.length; i++)
        {
            double val = 0;
            while (val==0)
                val=Math.random()*10-5;
            v[i]=val;
        }
    }
}
