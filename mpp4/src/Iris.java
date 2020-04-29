import java.util.Arrays;

public class Iris
{
    double[] attributes;
    String name;
    int group;

    public Iris(double[] attributes, String name)
    {
        this.attributes = attributes;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return Arrays.toString(attributes) + " " + name;
    }

    public double[] getAttributes()
    {
        return attributes;
    }
    public double calculateDistance(double[] centroid)
    {
        double value=0;
        for(int i=0; i<centroid.length; i++)
        {
            value+=Math.pow((centroid[i]-this.attributes[i]),2);
        }
        return value;
    }

    public int getGroup() {
        return group;
    }
}