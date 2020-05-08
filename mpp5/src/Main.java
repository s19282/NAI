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
        HashMap<String,Integer> countIrisTypes = new HashMap<>();
        HashMap<String,Integer> confusionMatrix = new HashMap<>();
        confusionMatrix.put("TP",0);
        confusionMatrix.put("FP",0);
        confusionMatrix.put("FN",0);
        confusionMatrix.put("TN",0);
        for(Iris iris : training)
        {
            if(!countIrisTypes.containsKey(iris.getName()))
                countIrisTypes.put(iris.getName(),1);
            else
             countIrisTypes.put(iris.getName(),countIrisTypes.get(iris.getName())+1);
        }

        for(Iris iris : test)
        {
            calculateIrisProbability(iris,training,countIrisTypes);
        }

    }
    public static void calculateIrisProbability(Iris iris,List<Iris> list,HashMap<String,Integer> countIrisTypes)
    {
        HashMap<String,Probability> allProbabilities = new HashMap<>();
        for(Map.Entry<String,Integer> entry : countIrisTypes.entrySet())
        {
            Probability p = new Probability();
            double probability=0;

            probability = getProbability(iris, list, probability,entry.getKey(),false, false);
            probability*=countIrisTypes.get(entry.getKey());
            p.setBeforeSmoothing(probability);
            if(probability==0)
            {

                probability = getProbability(iris, list, probability,entry.getKey(), true, true);
            }
            else
            {
                probability = getProbability(iris, list, probability,entry.getKey(), true, false);
            }
            probability*=countIrisTypes.get(entry.getKey());
            p.setAfterSmoothing(probability);
            allProbabilities.put(entry.getKey(),p);
        }
        Map.Entry<String,Probability> theBiggest = Collections.max(allProbabilities.entrySet(),Map.Entry.comparingByValue());

        System.out.println(iris.toString()+" - "+theBiggest.getKey());
        System.out.println("Before smoothing: "+theBiggest.getValue().getBeforeSmoothing());
        System.out.println("After smoothing: "+theBiggest.getValue().getAfterSmoothing());
        System.out.println("=================");
    }

    private static double getProbability(Iris iris, List<Iris> list, double probability,String name, boolean smoothFirst, boolean smoothOther)
    {
        for (int i = 0; i < iris.getAttributes().length; i++) {
            if (i == 0) probability += calculateSingleProbability(i, list, iris.getAttributes()[i],name, smoothFirst);
            else
                probability *= calculateSingleProbability(i, list, iris.getAttributes()[i],name, smoothOther);
        }
        return probability;
    }

    public static int countDiffGroups(int position,List<Iris> list)
    {
        List<Double> groups = new ArrayList<>();
        for(Iris iris : list)
            if(!groups.contains(iris.getAttributes()[position]))
                groups.add(iris.getAttributes()[position]);
        return groups.size();
    }
    public static int countMyGroupCardinality(int position,double value,List<Iris> list)
    {
        int cardinality=0;
        for(Iris iris : list)
            if(iris.getAttributes()[position]==value)
                cardinality++;
        return cardinality;
    }
    public static double calculateSingleProbability(int position, List<Iris> list,double value,String name,boolean smoothing)
    {
        double probability = smoothing ? 1 : 0;
        for(Iris iris : list)
        {
            if(iris.getAttributes()[position]==value && iris.getName().equals(name))
                probability++;
        }
        probability/= smoothing ? countMyGroupCardinality(position,value,list)+countDiffGroups(position,list) : countMyGroupCardinality(position,value,list);
        return probability;
    }

//    public static Probability calculateIrisProbability(Iris iris,List<Iris> list,HashMap<String,Integer> countGroups,String name)
//    {
//        List<Double> attributes = new ArrayList<>();
//        Probability p = new Probability();
//        double probability=0d;
//        for(int i=0; i<iris.getAttributes().length; i++)
//        {
//            double tmp = calculateSingleProbability(i,iris.getAttributes()[i],list,countGroups,name);
//            attributes.add(tmp);
//            if(probability==0)
//                probability=tmp;
//            else
//                probability*=tmp;
//        }
//        probability*=((double) countGroups.get(name) /list.size());
//        p.setBeforeSmoothing(probability);
//        probability=0;
//        if(attributes.contains(0D))
//        {
//            for(Double val : attributes)
//            {
//                if()
//            }
//        }
//        else
//        {
//
//        }
//        return p;
//    }
//    public static double calculateSingleProbability(int pos,Double val,List<Iris> list,HashMap<String,Integer> countGroups,String name)
//    {
//        double counter = 0;
//        for(Iris iris : list)
//            if((iris.getAttributes()[pos]==val) && (name.equals(iris.getName())))
//                counter++;
//        return counter/countGroups.get(name);//poprawic
//    }
//
//    public static int countDiffGroup()


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
