import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        List<Iris> training = readData("iris_training.txt");
        List<Iris> test = readData("iris_test.txt");
        HashMap<String,Integer> countGroups = new HashMap<>();
        for(Iris iris : training)
        {
            if(!countGroups.containsKey(iris.getName()))
                countGroups.put(iris.getName(),1);
            else
             countGroups.put(iris.getName(),countGroups.get(iris.getName())+1);
        }

        for(Iris iris : test)
        {
            HashMap<String,Double> prob = new HashMap<>();
            for(Map.Entry<String,Integer> entry : countGroups.entrySet())
            {
                prob.put(entry.getKey(),calculateIrisProbability(iris,training,countGroups));
            }
            System.out.println(iris.toString()+" --- "+Collections.min(prob.entrySet(), Map.Entry.comparingByValue()).getKey());
        }

    }

    public static double calculateIrisProbability(Iris iris,List<Iris> list,HashMap<String,Integer> countGroups)
    {
        double result=calculateSingleProbability(0,iris.getAttributes()[0],iris.getName(),list,countGroups);
        for(int i=1; i<iris.getAttributes().length; i++)
        {
            result*=calculateSingleProbability(i,iris.getAttributes()[i],iris.getName(),list,countGroups);
        }
        result*=countGroups.get(iris.getName());
        return result;
    }
    public static double calculateSingleProbability(int pos,Double val,String name,List<Iris> list,HashMap<String,Integer> countGroups)
    {
        double counter = 0;
        for(Iris iris : list)
            if(iris.getAttributes()[pos]==val && name.equals(iris.getName()))
                counter++;
        return (counter+1)/(list.size()+countGroups.size()); //smoothing
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
