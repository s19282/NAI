import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException
    {
        List<Set> sets = new ArrayList<>();
        readFromFile(sets);

        int setNR = (int)(Math.random()*10);
        System.out.println("Set nr: "+setNR);
        ArrayList<Integer> selectedItems = new ArrayList<>();
        long executionTime = System.nanoTime();
        knapSack(Set.getCapacity(),sets.get(setNR).getWeights(),sets.get(setNR).getValues(),sets.get(0).getValues().length,selectedItems);
        executionTime = System.nanoTime()-executionTime;
        for(Integer i : selectedItems)
            System.out.println("Item nr="+i+", size="+sets.get(setNR).getSize(i)+", value="+sets.get(setNR).getValue(i));

        System.out.println("Execution time: "+ TimeUnit.SECONDS.convert(executionTime,TimeUnit.NANOSECONDS) +"s");
    }

    //https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
    //https://stackoverflow.com/questions/20342386/printing-out-result-in-0-1-knapsack-recursive-brute-force
    static int knapSack(int capacity, int[] weights, int[] values, int n,ArrayList<Integer> taken)
    {
        if (n == 0 || capacity == 0)
            return 0;

        if (weights[n - 1] > capacity)
            return knapSack(capacity, weights, values, n - 1,taken);
        else
        {
            final int preTookSize = taken.size();
            final int took = values[n - 1] + knapSack(capacity - weights[n - 1], weights, values, n - 1,taken);
            final int preLeftSize = taken.size();
            final int left = knapSack(capacity, weights, values, n - 1,taken);

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

    public static void  readFromFile(List<Set> sets) throws IOException
    {
        Scanner s = new Scanner(Paths.get("plecak_old.txt"));
        Set.setCapacity(Integer.parseInt(s.nextLine().split(" ")[1]));
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
