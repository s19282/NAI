import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        List<Iris> flowers = readData("iris_training.txt");
        int k;
        double[][] centroids;
        System.out.println("Podaj k: ");
        Scanner s = new Scanner(System.in);
        k=s.nextInt();
        centroids = new double[k][flowers.get(0).getAttributes().length];
        generateValues(centroids);
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
            for(Iris iris : flowers)
                for(int i=0; i<iris.getAttributes().length; i++)
                    centroids[iris.getGroup()][i]+=iris.getAttributes()[i];
            for(int i=0; i<centroids.length; i++) {
                for (int j = 0; j < centroids[i].length; j++)
                    centroids[i][j] /= flowers.size();
            }
            if(Arrays.deepEquals(oldCentroids,centroids))
                enough=true;
            //suma kwadratów odległości punktów do środka grupy
            double[] tmp = new double[k];
            for(Iris iris : flowers)
                tmp[iris.getGroup()]+=iris.calculateDistance(centroids[iris.getGroup()]);
            for(int i=0; i<k; i++)
                System.out.println("Group: "+i+" sum: "+tmp[i]);
        }
        flowers.sort(Comparator.comparingInt(Iris::getGroup));
        System.out.println("========================");
        for(Iris iris : flowers)
            System.out.println("Group: "+iris.getGroup()+" "+iris.toString());
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
        {
           for(int j=0; j<v[i].length; j++)
               v[i][j]=Math.random()*10;
        }
    }
}
