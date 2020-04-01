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
        switch (name)
        {
            case "Iris-setosa":
            {
                value=1;
                break;
            }
            default:
            {
                value=0;
            }
        }
    }

    public Iris(double[] attributes)
    {
        this.attributes = attributes;
        name ="Unknown";
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes)+" "+name+"\n";
    }
}
