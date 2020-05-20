public class SingleObject
{
    private int number;
    private int weight;
    private int value;

    public SingleObject(int weight, int value, int number)
    {
        this.weight = weight;
        this.value = value;
        this.number = number;
    }

    @Override
    public String toString() {
        return  "number= " + number +
                ", weight= " + weight +
                ", value= " + value;
    }
}
