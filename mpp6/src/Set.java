import java.util.HashMap;
import java.util.List;

public class Set implements Comparable<Set>
{
    private static int capacity;
    private int number;
    private List<Integer> sizes;
    private List<Integer> values;
    private static final HashMap<Integer,SingleObject> outputObjects = new HashMap<>();

    public  Set() { }

    public static HashMap<Integer, SingleObject> getOutputObject()
    {
        return outputObjects;
    }
    public static void addToOutputMap(SingleObject object)
    {
        outputObjects.put(object.getNumber(),object);
    }

    public static int getCapacity() {
        return capacity;
    }

    public static void setCapacity(int capacity) {
        Set.capacity = capacity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Integer> getSizes() {
        return sizes;
    }
    public int getSize(int n) {
        return sizes.get(n);
    }

    public void setSizes(List<Integer> sizes) {
        this.sizes = sizes;
    }

    public List<Integer> getValues() {
        return values;
    }
    public int getValue(int n) {
        return values.get(n);
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @Override
    public int compareTo(Set o) {
        return this.number-o.number;
    }

    @Override
    public String toString() {
        return "Set{" +
                "number=" + number +
                ", sizes=" + sizes +
                ", values=" + values +
                '}';
    }
}
