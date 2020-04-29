import java.util.Arrays;

public class Iris
{
    double[] attributes;

    public Iris(double[] attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(attributes);
    }
}
