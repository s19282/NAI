import java.util.List;

public class Set implements Comparable<Set>
{
    int number;
    List<Integer> sizes;
    List<Integer> values;

    public  Set(){}

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
