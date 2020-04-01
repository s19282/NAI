import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main
{
    static final double alpha = 0.25;
    static double[] weights;
    static double threshold = 0.7;

    public static void main(String[] args)
    {
        List<Iris> training = readData("iris_training.txt");
        List<Iris> test = readData("iris_test.txt");
        generateValues(weights);
        boolean stop;
        do
        {
            stop = false;
            for(Iris iris : training)
            {
                stop=learn(iris,stop);
            }
        }
        while (stop);

        int counter = 0;
        for(Iris iris : test)
        {
            String result = check(iris);
            if(iris.name.equals("Iris-setosa")&&result.equals("Iris-setosa")||result.equals("Inny")&&(!iris.name.equals("Iris-setosa")))
                counter++;
        }
        System.out.println("Prawidłowo zaklasyfikowanych: "+test.size()+" Dokadność: "+counter*100/test.size()+"%");

        while (true)
        {
            Scanner scanner=new Scanner(System.in);
            System.out.print("Podaj wektor: ");
            String[] v = scanner.nextLine().split(" ");
            double[] tmp = new double[v.length];
            for(int i=0; i<v.length; i++)
                tmp[i]=Double.parseDouble(v[i]);
            Iris myIrys = new Iris(tmp);
            int s=0;
            for (int i=0; i<weights.length; i++)
                s+=weights[i]*myIrys.attributes[i];
            System.out.println(s>=threshold?"Setosa":"inny");
        }

    }
    public static String check(Iris iris)
    {
        double s =0;
        for (int i=0; i<weights.length; i++)
            s+=weights[i]*iris.attributes[i];
        return s>=threshold?"Iris-setosa":"Inny";
    }
    public static boolean learn(Iris iris, boolean stop)
    {
        double s;
        while (true)
        {
            s=0;
            for (int i=0; i<weights.length; i++)
                s+=weights[i]*iris.attributes[i];
            if((iris.value==1&&s<threshold)||(iris.value==0&&s>=threshold))
            {
                stop=true;
                for (int i=0; i<weights.length; i++)
                    weights[i]=weights[i] + alpha*(iris.value-(s>=threshold?1:0))*(iris.attributes[i]);
            }
            else
                break;
        }
        return stop;
    }


    public static List<Iris> readData(String path)
    {
        List<Iris> outcome = new ArrayList<>();
        try
        {
            List<String> dataFromFile = Files.readAllLines(Paths.get(path));
            weights = new double[dataFromFile.get(0).split("\\s+").length-2];
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
