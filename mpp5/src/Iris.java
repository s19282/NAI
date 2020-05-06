import java.util.Arrays;

public class Iris
{
    double[] attributes;
    String name;
    int value;
    public Iris(double[] attributes, String decision)
    {
        this.attributes = attributes;
        this.name = decision;
    }

    public double[] getAttributes() {
        return attributes;
    }

    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Iris(double[] attributes)
    {
        this.attributes = attributes;
        name ="Unknown";
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes)+" "+name;
    }
}