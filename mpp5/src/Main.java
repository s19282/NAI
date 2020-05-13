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

        countIrisTypesCardinality(training,countIrisTypes);
        createConfusionMatrix(confusionMatrix);

        for(Iris iris : test)
            calculateIrisProbability(iris,training,countIrisTypes,confusionMatrix,false);

        printConfusionMatrix(confusionMatrix,test);
        readFromKeyboard(training,countIrisTypes,confusionMatrix);
    }

    public static void countIrisTypesCardinality(List<Iris> training, HashMap<String,Integer> countIrisTypes)
    {
        for(Iris iris : training)
        {
            if(!countIrisTypes.containsKey(iris.getName()))
                countIrisTypes.put(iris.getName(),1);
            else
                countIrisTypes.put(iris.getName(),countIrisTypes.get(iris.getName())+1);
        }
    }

    public static void createConfusionMatrix(HashMap<String,Integer> confusionMatrix)
    {
        confusionMatrix.put("setosasetosa",0);
        confusionMatrix.put("versicolorversicolor",0);
        confusionMatrix.put("virginicavirginica",0);
        confusionMatrix.put("setosaversicolor",0);
        confusionMatrix.put("setosavirginica",0);
        confusionMatrix.put("versicolorsetosa",0);
        confusionMatrix.put("versicolorvirginica",0);
        confusionMatrix.put("virginicasetosa",0);
        confusionMatrix.put("virginicaversicolor",0);
    }

    public static void readFromKeyboard(List<Iris> list,HashMap<String,Integer> countIrisTypes,HashMap<String,Integer> confusionMatrix)
    {
        boolean endLoop=false;
        while(!endLoop)
        {
            try
            {
                System.out.print("Type new Iris values: ");
                Scanner s = new Scanner(System.in);
                String line = s.nextLine();
                line = line.replaceAll(",", ".");
                String[] attributes = line.split("\\s+");

                calculateIrisProbability(new Iris(Arrays.stream(attributes)
                        .mapToDouble(Double::parseDouble)
                        .toArray()), list, countIrisTypes, confusionMatrix, true);
                System.out.println("Do you want to finish (T/F)?");
                s = new Scanner(System.in);
                String end = s.nextLine();
                if(end.equals("T"))
                    endLoop=true;
            }
            catch (Exception e)
            {
                System.out.println("Error! Try again");
            }
        }
    }

    public static void printConfusionMatrix(HashMap<String,Integer> cm,List<Iris> list)
    {
        System.out.println("\t\t\t\t\t\tActual class");
        System.out.println("\t\t\t\tsetosa\tversicolor\tvirginica");
        System.out.println("Predicted setosa \t"+cm.get("setosasetosa")+"\t\t"+cm.get("setosaversicolor")+"\t\t"+cm.get("setosavirginica"));
        System.out.println("Class versicolor\t"+cm.get("versicolorsetosa")+"\t\t"+cm.get("versicolorversicolor")+"\t\t"+cm.get("versicolorvirginica"));
        System.out.println("\t\tvirginica\t"+cm.get("virginicasetosa")+"\t\t"+cm.get("virginicaversicolor")+"\t\t"+cm.get("virginicavirginica"));
        System.out.println();
        int accuracy = (cm.get("setosasetosa")+cm.get("versicolorversicolor")+cm.get("virginicavirginica"))*100/list.size();
        int errorRate = 100-accuracy;
        System.out.println("Accuracy: "+accuracy+"%");
        System.out.println("Error Rate: "+errorRate+"%");
        System.out.println();

        System.out.println("\t\t\tPrecision\tRecall\tF1");
        double setosaPrecision=(double)cm.get("setosasetosa")/(cm.get("setosasetosa")+cm.get("setosaversicolor")+cm.get("setosavirginica"));
        double setosaRecall=(double)cm.get("setosasetosa")/(cm.get("setosasetosa")+cm.get("versicolorsetosa")+cm.get("virginicasetosa"));
        double setosaF1=(2*setosaPrecision*setosaRecall)/(setosaPrecision+setosaRecall);
        System.out.println("setosa\t\t"+setosaPrecision+"\t\t\t"+setosaRecall+"\t\t"+setosaF1);
        double versicolorPrecision=(double)cm.get("versicolorversicolor")/(cm.get("versicolorsetosa")+cm.get("versicolorversicolor")+cm.get("versicolorvirginica"));
        double versicolorRecall=(double)cm.get("versicolorversicolor")/(cm.get("setosaversicolor")+cm.get("versicolorversicolor")+cm.get("virginicaversicolor"));
        double versicolorF1=(2*versicolorPrecision*versicolorRecall)/(versicolorPrecision+versicolorRecall);
        System.out.println("versicolor\t"+versicolorPrecision+"\t\t\t"+versicolorRecall+"\t\t"+versicolorF1);
        double virginicaPrecision=(double)cm.get("virginicavirginica")/(cm.get("virginicasetosa")+cm.get("virginicaversicolor")+cm.get("virginicavirginica"));
        double virginicaRecall=(double)cm.get("virginicavirginica")/(cm.get("setosavirginica")+cm.get("versicolorvirginica")+cm.get("virginicavirginica"));
        double virginicaF1=(2*virginicaPrecision*virginicaRecall)/(virginicaPrecision+virginicaRecall);
        System.out.println("virginica\t"+virginicaPrecision+"\t\t\t"+virginicaRecall+"\t\t"+virginicaF1);
        System.out.println("=================");

    }

    public static void calculateIrisProbability(Iris iris,List<Iris> list,HashMap<String,Integer> countIrisTypes,HashMap<String,Integer> confusionMatrix,boolean checkOne)
    {
        HashMap<String,Probability> allProbabilities = new HashMap<>();
        for(Map.Entry<String,Integer> entry : countIrisTypes.entrySet())
        {
            Probability p = new Probability();

            double probability=0;
            probability = getProbability(iris, list, probability,entry.getKey(),false, false);
            probability*=(double)countIrisTypes.get(entry.getKey())/list.size();
            p.setBeforeSmoothing(probability);

            if(probability==0)
                probability = getProbability(iris, list, probability,entry.getKey(), true, true);
            else
                probability = getProbability(iris, list, probability,entry.getKey(), true, false);
            probability*=(double)countIrisTypes.get(entry.getKey())/list.size();
            p.setAfterSmoothing(probability);
            allProbabilities.put(entry.getKey(),p);
        }
        Map.Entry<String,Probability> theBiggest = Collections.max(allProbabilities.entrySet(),Map.Entry.comparingByValue());

        if(!checkOne)
        {
            fillConfusionMatrix(confusionMatrix, iris.getName(), theBiggest.getKey());
            System.out.println(iris.toString() + " - " + theBiggest.getKey());
        }
        else
            System.out.println(iris.toString() + " --> " + theBiggest.getKey());
        System.out.println("Before smoothing: "+String.format("%2.5f" , theBiggest.getValue().getBeforeSmoothing()));
        System.out.println("After smoothing: "+String.format("%2.5f" , theBiggest.getValue().getAfterSmoothing()));
        System.out.println("=================");
    }

    public static void fillConfusionMatrix(HashMap<String,Integer> confusionMatrix,String real,String predicted)
    {
        switch (real.substring(real.indexOf("-")+1)+predicted.substring(real.indexOf("-")+1))
        {
            case "setosasetosa":
            {
                confusionMatrix.put("setosasetosa",confusionMatrix.get("setosasetosa")+1);
                break;
            }
            case "versicolorversicolor":
            {
                confusionMatrix.put("versicolorversicolor",confusionMatrix.get("versicolorversicolor")+1);
                break;
            }
            case "virginicavirginica":
            {
                confusionMatrix.put("virginicavirginica",confusionMatrix.get("virginicavirginica")+1);
                break;
            }
            case "setosaversicolor":
            {
                confusionMatrix.put("setosaversicolor",confusionMatrix.get("setosaversicolor")+1);
                break;
            }
            case "setosavirginica":
            {
                confusionMatrix.put("setosavirginica",confusionMatrix.get("setosavirginica")+1);
                break;
            }
            case "versicolorsetosa":
            {
                confusionMatrix.put("versicolorsetosa",confusionMatrix.get("versicolorsetosa")+1);
                break;
            }
            case "versicolorvirginica":
            {
                confusionMatrix.put("versicolorvirginica",confusionMatrix.get("versicolorvirginica")+1);
                break;
            }
            case "virginicasetosa":
            {
                confusionMatrix.put("virginicasetosa",confusionMatrix.get("virginicasetosa")+1);
                break;
            }
            case "virginicaversicolor":
            {
                confusionMatrix.put("virginicaversicolor",confusionMatrix.get("virginicaversicolor")+1);
                break;
            }
        }
    }

    private static double getProbability(Iris iris, List<Iris> list, double probability,String name, boolean smoothFirst, boolean smoothOther)
    {
        for (int i = 0; i < iris.getAttributes().length; i++)
        {
            if (i == 0)
                probability += calculateSingleProbability(i, list, iris.getAttributes()[i],name, smoothFirst);
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
        double probability = smoothing ? 1D : 0D;
        for(Iris iris : list)
        {
            if(iris.getAttributes()[position]==value && iris.getName().equals(name))
                probability++;
        }
        probability/= smoothing ? countMyGroupCardinality(position,value,list)+countDiffGroups(position,list) : countMyGroupCardinality(position,value,list);
        if(Double.isNaN(probability))
            probability=0D;
        return probability;
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