import java.util.Arrays;

public class Iris
{
    double[] attributes;
    String name;
    Double distance;

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

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
    public void calculateDistance(Iris iris,int howManyAttrs)
    {
        double value=0;
        for(int i=0; i<howManyAttrs; i++)
        {
            value+=Math.pow((iris.attributes[i]-this.attributes[i]),2);
        }
        distance=value;
    }

    @Override
    public String toString() {
        return Arrays.toString(attributes)+" "+name+"\n";
    }
}
