public class Iris
{
    int[] attributes;
    String decision;

    public Iris(int[] attributes, String decision)
    {
        this.attributes = attributes;
        this.decision = decision;
    }

    public Iris(int[] attributes)
    {
        this.attributes = attributes;
        decision="Unknown";
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

}
