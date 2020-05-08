import java.util.Arrays;

public class Iris
{
    double[] attributes;
    String name;

    public Iris(double[] attributes, String name)
    {
        this.attributes = attributes;
        this.name = name;
    }

    public Iris(double[] attributes)
    {
        this.attributes = attributes;
        name="";
    }

    public double[] getAttributes() {
        return attributes;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes)+" "+name;
    }
}
