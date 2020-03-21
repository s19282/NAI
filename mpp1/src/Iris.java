import java.util.Arrays;

public class Iris
{
    double[] attributes;
    String name;

    public Iris(double[] attributes, String decision)
    {
        this.attributes = attributes;
        this.name = decision;
    }

    public Iris(double[] attributes)
    {
        this.attributes = attributes;
        name ="Unknown";
    }
    public int calculateDistance(Iris iris,int howManyAttrs)
    {
        int value=0;
        for(int i=0; i<howManyAttrs; i++)
        {
            value+=Math.pow((iris.attributes[i]-this.attributes[i]),2);
        }
        return value;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes)+" "+name+"\n";
    }
}
