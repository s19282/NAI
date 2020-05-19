import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws IOException
    {
        int capacity = 40;
        List<Set> sets = new ArrayList<>();
        readFromFile(capacity,sets);
        int setNR = (int)(Math.random()*10);
        System.out.println("Set nr: "+setNR);
        doMagic(sets.get(setNR),capacity);
    }
    public static void doMagic(Set set,int capacity)
    {
        int maxVal=0;
        int maxSize=0;
        List<String> res = new ArrayList<>();

        for(int i1=0; i1<=1; i1++)
            for(int i2=0; i2<=1; i2++)
                for(int i3=0; i3<=1; i3++)
                    for(int i4=0; i4<=1; i4++)
                        for(int i5=0; i5<=1; i5++)
                            for(int i6=0; i6<=1; i6++)
                                for(int i7=0; i7<=1; i7++)
                                    for(int i8=0; i8<=1; i8++)
                                        for(int i9=0; i9<=1; i9++)
                                            for(int i10=0; i10<=1; i10++)
                                            {
                                                int size=set.getSize(0)*i1
                                                        +set.getSize(1)*i2
                                                        +set.getSize(2)*i3
                                                        +set.getSize(3)*i4
                                                        +set.getSize(4)*i5
                                                        +set.getSize(5)*i6
                                                        +set.getSize(6)*i7
                                                        +set.getSize(7)*i8
                                                        +set.getSize(8)*i9
                                                        +set.getSize(9)*i10;
                                                if(size<=capacity)
                                                {
                                                    int val=set.getValue(0)*i1
                                                            +set.getValue(1)*i2
                                                            +set.getValue(2)*i3
                                                            +set.getValue(3)*i4
                                                            +set.getValue(4)*i5
                                                            +set.getValue(5)*i6
                                                            +set.getValue(6)*i7
                                                            +set.getValue(7)*i8
                                                            +set.getValue(8)*i9
                                                            +set.getValue(9)*i10;
                                                    if(val>maxVal)
                                                    {
                                                        res.clear();
                                                        maxVal=val;
                                                        maxSize=size;
                                                        if(i1==1)
                                                            res.add("number=1 size="+set.getSize(0)+" value="+set.getValue(0));
                                                        if(i2==1)
                                                            res.add("number=2 size="+set.getSize(1)+" value="+set.getValue(1));
                                                        if(i3==1)
                                                            res.add("number=3 size="+set.getSize(2)+" value="+set.getValue(2));
                                                        if(i4==1)
                                                            res.add("number=4 size="+set.getSize(3)+" value="+set.getValue(3));
                                                        if(i5==1)
                                                            res.add("number=5 size="+set.getSize(4)+" value="+set.getValue(4));
                                                        if(i6==1)
                                                            res.add("number=6 size="+set.getSize(5)+" value="+set.getValue(5));
                                                        if(i7==1)
                                                            res.add("number=7 size="+set.getSize(6)+" value="+set.getValue(6));
                                                        if(i8==1)
                                                            res.add("number=8 size="+set.getSize(7)+" value="+set.getValue(7));
                                                        if(i9==1)
                                                            res.add("number=9 size="+set.getSize(8)+" value="+set.getValue(8));
                                                        if(i10==1)
                                                            res.add("number=10 size="+set.getSize(9)+" value="+set.getValue(9));
                                                    }
                                                }
                                            }
        System.out.println("Max val: "+maxVal);
        System.out.println("Max size: "+maxSize);
        for(String s : res)
            System.out.println(s);
    }

    public static void  readFromFile(int capacity, List<Set> sets) throws IOException
    {
        Scanner s = new Scanner(Paths.get("plecak_old.txt"));
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
