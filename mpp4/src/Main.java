import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        List<Iris> flowers = readData("iris_test.txt");
        int k;
        double[][] centroids;
        System.out.print("Podaj k: ");
        Scanner s = new Scanner(System.in);
        k=s.nextInt();
        System.out.println("========================");
        centroids = new double[k][flowers.get(0).getAttributes().length];
        //generateValues(centroids);
        getValuesFromList(flowers,centroids);
        doMagic(flowers,k,centroids);
    }
    public static void doMagic(List<Iris> flowers,int k,double[][] centroids)
    {
        LinkedHashMap<Integer,Double> distances = new LinkedHashMap<>();
        boolean enough = false;
        while (!enough)
        {
            //przypisanie klasy
            for(Iris iris : flowers)
            {
                for(int i=0; i<k; i++)
                    distances.put(i,iris.calculateDistance(centroids[i]));

                iris.setGroup(Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey());
                distances.clear();
            }
            //nowe wartości centroidów
            double[][] oldCentroids = centroids;
            centroids = new double[k][flowers.get(0).getAttributes().length];
            int[] groupCounter = new int[k];

            for(Iris iris : flowers)
            {
                for (int i = 0; i < iris.getAttributes().length; i++)
                    centroids[iris.getGroup()][i] += iris.getAttributes()[i];

                groupCounter[iris.getGroup()]++;
            }
            for(int i=0; i<centroids.length; i++)
            {
                for (int j = 0; j < centroids[i].length; j++)
                    if(groupCounter[i]==0)
                        centroids[i][j]=0;
                    else
                        centroids[i][j] /= groupCounter[i];
            }
            //System.out.println("centroids: "+Arrays.deepToString(centroids));

            if(Arrays.deepEquals(oldCentroids,centroids))
                enough=true;
            //suma kwadratów odległości punktów do środka grupy
            double[] tmp = new double[k];

            for(Iris iris : flowers)
                tmp[iris.getGroup()]+=iris.calculateDistance(centroids[iris.getGroup()]);

            for(int i=0; i<k; i++)
                System.out.println("Group: " + i + " sum: " + tmp[i]);

            System.out.println("------------------------");
        }
        flowers.sort(Comparator.comparingInt(Iris::getGroup));
        System.out.println("========================");
        for(Iris iris : flowers)
            System.out.println("Group: "+iris.getGroup()+" "+iris.toString());

        System.out.println("========================");
        calculateEntropy(flowers,k);
        System.out.println("========================");
    }

    public static void calculateEntropy(List<Iris> flowers, int k)
    {
        for(int i=0; i<k; i++)
        {
            double entropy=0;
            double[] probability=new double[3];
            int actualGroup=0;
            for(Iris iris : flowers)
            {
                if(iris.getGroup()==i)
                    actualGroup++;
                if(iris.getGroup()==i&&iris.getName().equals("Iris-setosa"))
                    probability[0]++;
                else if(iris.getGroup()==i&&iris.getName().equals("Iris-versicolor"))
                    probability[1]++;
                else if(iris.getGroup()==i&&iris.getName().equals("Iris-virginica"))
                    probability[2]++;
            }

            System.out.print("Group: " + i);
            if(probability[0]==0 && probability[1]==0 && probability[2]==0 && actualGroup==0)
                System.out.println(" Empty");
            else
            {
                probability[0] /= actualGroup;
                probability[1] /= actualGroup;
                probability[2] /= actualGroup;
                for (int j = 0; j < 3; j++)
                {
                    if (probability[j] != 0 && probability[j] != 1)
                        entropy += log2(probability[j]) * probability[j];
                }
                if (entropy != 0)
                    entropy *= -1;
                System.out.format(" Entropy: %.10f", entropy);
                System.out.println();
            }


        }
    }

    public static double log2(double x)
    {
        return (Math.log(x)/Math.log(2) +1e-10);
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
                        .toArray(),tmp[tmp.length-1]));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return outcome;
    }

    public static void generateValues(double[][] v)
    {
        for(int i=0; i<v.length; i++)
           for(int j=0; j<v[i].length; j++)
               v[i][j]=Math.random()*10;
    }
    public static void getValuesFromList(List<Iris> flowers,double[][]v)
    {
        for(int i=0; i<v.length; i++)
            v[i]=flowers.get((int)(Math.random()*flowers.size())).getAttributes();
    }
}
