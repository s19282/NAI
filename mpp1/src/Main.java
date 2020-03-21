import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        List<Iris> training = readData("iris_training.txt");
        List<Iris> test = readData("iris_test.txt");
        int correctAnswer=0;
        for(Iris iris : test)
        {
            String name = predictName(training,iris,4);
            if(name.equals(iris.name))
                correctAnswer++;
        }
        System.out.println("Prawidłowo zaklasyfikowanych: "+correctAnswer+" co daje: "+(correctAnswer*100/test.size())+"%");
    }
    public static String predictName(List<Iris> training,Iris iris,int attrs)
    {
        TreeMap<Double,Iris> tm = new TreeMap<>();
        for(Iris iris1: training)
            tm.put(iris1.calculateDistance(iris,attrs-1),iris1);
        String[] names = new String[5];
        for(int i=0; i<5; i++)
        {
            Map.Entry<Double,Iris> entry = tm.firstEntry();
            names[i]=entry.getValue().name;
            tm.remove(entry.getKey());
        }
        return getPopularElement(names);
    }

    public static String getPopularElement(String[] a)
    {
        int count = 1, tempCount;
        String popular = a[0];
        String temp ;
        for (int i = 0; i < (a.length - 1); i++)
        {
            temp = a[i];
            tempCount = 0;
            for (int j = 1; j < a.length; j++)
            {
                if (temp == a[j])
                    tempCount++;
            }
            if (tempCount > count)
            {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
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
}
