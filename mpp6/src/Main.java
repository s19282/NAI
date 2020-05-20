import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException
    {
        List<Set> sets = new ArrayList<>();
        readFromFile(sets);
        int setNR = (int)(Math.random()*10);
        System.out.println("Set nr: "+setNR);
        //doMagic(sets.get(setNR),10);
        System.out.println(knapSack(Set.getCapacity(),new int[]{5,12,4,9,1,5,6,10,8,1},new int[]{9,2,7,6,10,9,4,13,0,7},10));
    }
    static int max(int a, int b)
    {
        return Math.max(a, b);
    }

    // Returns the maximum value that
    // can be put in a knapsack of
    // capacity W
    static int knapSack(
            int W, int wt[],
            int val[], int n)
    {
        // Base Case
        if (n == 0 || W == 0)
            return 0;

        // If weight of the nth item is
        // more than Knapsack capacity W,
        // then this item cannot be included
        // in the optimal solution
        if (wt[n - 1] > W)
            return knapSack(W, wt, val, n - 1);

            // Return the maximum of two cases:
            // (1) nth item included
            // (2) not included
        else
            return max(
                    val[n - 1] + knapSack(W - wt[n - 1],
                            wt, val, n - 1),
                    knapSack(W, wt, val, n - 1));
    }
//    public static void doMagic(Set set,int n)
//    {
//        //https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
//        if(n==0 || Set.getCapacity()==0)
//            return 0;
//
//
//    }

    public static void  readFromFile(List<Set> sets) throws IOException
    {
        Scanner s = new Scanner(Paths.get("plecak_old.txt"));
        Set.setCapacity(Integer.parseInt(s.nextLine().split(" ")[1]));
        s.nextLine();
        List<Integer> sizes = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
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
                    Arrays.stream(m.group(3).split(",")).map(String::trim).mapToInt(Integer::parseInt).forEach(sizes::add);
                    tmpSet.setSizes(sizes);
                }
                else if(m.group(1).equals("values"))
                {
                    Arrays.stream(m.group(3).split(",")).map(String::trim).mapToInt(Integer::parseInt).forEach(values::add);
                    tmpSet.setValues(values);
                    sets.add(tmpSet);
                    tmpSet = new Set();
                    sizes = new ArrayList<>();
                    values = new ArrayList<>();
                }
            }
        }
    }
}
