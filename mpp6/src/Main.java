import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException
    {
        int capacity = 0;
        List<Set> sets = new ArrayList<>();
        readFromFile(capacity,sets);

        for(Set s : sets)
            System.out.println(s.toString());

    }

    public static void  readFromFile(int capacity, List<Set> sets) throws IOException
    {
        Scanner s = new Scanner(Paths.get("plecak.txt"));
        capacity = Integer.parseInt(s.nextLine().split(" ")[1]);
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
