import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException
    {
        List<Set> sets = new ArrayList<>();
        readFromFile(sets);
        int setNR = (int)(Math.random()*10);
        System.out.println("Set nr: "+setNR);
        doMagic(sets.get(setNR));
    }
    public static void doMagic(Set set)
    {

    }

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
