import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        List<Set> sets = new ArrayList<>();
        readFromNewFile(sets);
        int setNR = (int)(Math.random()*15);

        long time = System.nanoTime();
        iterationKnapSack(sets.get(setNR));
        time = System.nanoTime()-time;

//        System.out.println("Set nr: "+setNR);
//        System.out.println("Capacity: "+Set.getCapacity());
//        ArrayList<Integer> selectedItems = new ArrayList<>();
//        long executionTime = System.nanoTime();
//
//        System.out.println(recursionKnapSack(Set.getCapacity(),sets.get(setNR).getWeights(),sets.get(setNR).getValues(),Set.getN(),selectedItems));
//
//        executionTime = System.nanoTime()-executionTime;
//        int finalSize=0;
//        int finalValue=0;
//        System.out.println("------------------------------");
//        for(Integer i : selectedItems)
//        {
//            int size = sets.get(setNR).getSize(i);
//            int value = sets.get(setNR).getValue(i);
//            finalSize+=size;
//            finalValue+=value;
//            System.out.println("Item nr="+i+", size="+size+", value="+value);
//        }
//        System.out.println("------------------------------");
//        System.out.println("Final items size: "+finalSize);
//        System.out.println("Final items value: "+finalValue);
//
        System.out.println("Execution time: "+ TimeUnit.SECONDS.convert(time,TimeUnit.NANOSECONDS) +"s");
    }

    static void iterationKnapSack(Set set)
    {
        int maxWeight=0;
        int maxValue=0;
        List<SingleObject> finalObjects = new ArrayList<>();

        for(int i=0; i<Math.pow(2,Set.getN()); i++)
        {
            List<SingleObject> tmpList = new ArrayList<>();
            int tmpWeight = 0;
            int tmpValue = 0;
            String singleCombination = Integer.toBinaryString(i);
            for (int j = 0; j < singleCombination.length(); j++)
            {
                if (singleCombination.charAt(j) == '1')
                {
                    tmpWeight += set.getSize(j);
                    tmpValue += set.getValue(j);
                    tmpList.add(new SingleObject(j,set.getSize(j),set.getValue(j)));
                }
            }
            if (tmpWeight <= Set.getCapacity() && tmpValue > maxValue)
            {
                maxValue = tmpValue;
                maxWeight = tmpWeight;
                finalObjects.clear();
                finalObjects.addAll(tmpList);
            }
        }
        for(SingleObject so : finalObjects)
            System.out.println(so.toString());
        System.out.println("-------------------");
        System.out.println("Weight: "+maxWeight);
        System.out.println("Value: "+maxValue);
    }

    //https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
    //https://stackoverflow.com/questions/20342386/printing-out-result-in-0-1-knapsack-recursive-brute-force
    static int recursionKnapSack(int capacity, int[] weights, int[] values, int n,ArrayList<Integer> taken)
    {
        if (n == 0 || capacity == 0)
            return 0;

        if (weights[n - 1] > capacity)
            return recursionKnapSack(capacity, weights, values, n - 1,taken);
        else
        {
            final int preTookSize = taken.size();
            final int took = values[n - 1] + recursionKnapSack(capacity - weights[n - 1], weights, values, n - 1,taken);
            final int preLeftSize = taken.size();
            final int left = recursionKnapSack(capacity, weights, values, n - 1,taken);

            if(took>left)
            {
                if (taken.size() > preLeftSize)
                    taken.subList(preLeftSize, taken.size()).clear();
                taken.add(n - 1);
                return took;
            }
            else
            {
                if(preLeftSize>preTookSize)
                    taken.subList(preTookSize,preLeftSize).clear();
                return left;
            }
        }
    }

    public static void  readFromNewFile(List<Set> sets) throws IOException
    {
        Scanner s = new Scanner(Paths.get("plecak.txt"));
        Pattern p = Pattern.compile("length - (\\d+), capacity (\\d+)");
        Matcher m = p.matcher(s.nextLine());

        if(m.matches())
        {
            Set.setCapacity(Integer.parseInt(m.group(2)));
            Set.setN(Integer.parseInt(m.group(1)));
        }
        int[] sizes;
        int[] values;
        Set tmpSet = new Set();
        p = Pattern.compile("(sizes|vals|dataset) ((\\d+)+:|=\\s+\\{(((\\d+),\\s*)+(\\d)+)}\\s+)");
        while (s.hasNext())
        {
            String line = s.nextLine();
            m = p.matcher(line);
            if(m.matches())
            {
                switch (m.group(1))
                {
                    case "sizes":
                        sizes = Arrays.stream(m.group(4).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
                        tmpSet.setWeights(sizes);
                        break;
                    case "vals":
                        values = Arrays.stream(m.group(4).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
                        tmpSet.setValues(values);
                        sets.add(tmpSet);
                        tmpSet = new Set();
                        s.nextLine();
                        break;
                    case "dataset":
                        tmpSet.setNumber(Integer.parseInt(m.group(3)));
                        break;
                }
            }
        }
    }

    public static void  readFromOldFile(List<Set> sets) throws IOException
    {
        Scanner s = new Scanner(Paths.get("plecak_old.txt"));
        Set.setCapacity(Integer.parseInt(s.nextLine().split(" ")[1]));
        Set.setN(10);
        s.nextLine();
        int[] sizes;
        int[] values;
        Set tmpSet = new Set();
        while (s.hasNext())
        {
            String line = s.nextLine();
            Pattern p = Pattern.compile("(sizes|values)(\\d+): \\{((\\d+,)+\\d+)};");
            Matcher m = p.matcher(line);
            if(m.matches())
            {
                if(m.group(1).equals("sizes"))
                {
                    tmpSet.setNumber(Integer.parseInt(m.group(2)));
                    sizes=Arrays.stream(m.group(3).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
                    tmpSet.setWeights(sizes);
                }
                else if(m.group(1).equals("values"))
                {
                    values=Arrays.stream(m.group(3).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
                    tmpSet.setValues(values);
                    sets.add(tmpSet);
                    tmpSet = new Set();
                }
            }
        }
    }
}
