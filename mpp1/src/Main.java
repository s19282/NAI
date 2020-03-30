import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static int k = 0;
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Podaj k: ");
        k=scanner.nextInt();

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

        while (true)
        {
            scanner=new Scanner(System.in);
            System.out.print("Podaj wektor: ");
            String[] v = scanner.nextLine().split(" ");
            double[] tmp = new double[v.length];
            for(int i=0; i<v.length; i++)
                tmp[i]=Double.parseDouble(v[i]);
            System.out.println(predictName(training,new Iris(tmp),4));
        }
    }
    public static String predictName(List<Iris> training,Iris iris,int attrs)
    {
        for(Iris iris1: training)
            iris1.calculateDistance(iris,attrs-1);
        training.sort(Comparator.comparingDouble(Iris::getDistance));
        String[] names = new String[k];
        for(int i=0; i<k; i++)
            names[i]=training.get(i).name;
        return getPopularElementv2(names);
    }
    public static String getPopularElementv2(String[] names)
    {
        int IrisSetosa=0;
        int IrisVersicolor=0;
        int IrisVirginica=0;
        for (String name : names) {
            if (name.equals("Iris-setosa"))
                IrisSetosa++;
            if (name.equals("Iris-versicolor"))
                IrisVersicolor++;
            if (name.equals("Iris-virginica"))
                IrisVirginica++;
        }
        if(IrisSetosa>IrisVersicolor)
        {
            if(IrisSetosa>IrisVirginica)
                return "Iris-setosa";
            else
                return "Iris-virginica";
        }
        else
        {
            if(IrisVersicolor>IrisVirginica)
                return "Iris-versicolor";
            else
                return "Iris-virginica";
        }
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
                if (temp.equals(a[j]))
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
