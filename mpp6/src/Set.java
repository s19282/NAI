import java.util.Arrays;
import java.util.List;

public class Set implements Comparable<Set>
{
    private static int capacity;
    private int number;
    private int[] weights;
    private int[] values;

    public  Set() { }

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

    public int[] getWeights() {
        return weights;
    }
    public int getSize(int n) {
        return weights[n];
    }

    public void setWeights(int[] weights) {
        this.weights = weights;
    }

    public int[] getValues() {
        return values;
    }
    public int getValue(int n) {
        return values[n];
    }

    public void setValues(int[] values) {
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
                ", sizes=" + Arrays.toString(weights) +
                ", values=" + Arrays.toString(values) +
                '}';
    }
}
