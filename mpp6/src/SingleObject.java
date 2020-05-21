public class SingleObject
{
    private final int n;
    private final int weight;
    private final int value;

    public SingleObject(int n, int weight, int value)
    {
        this.n = n;
        this.weight = weight;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "n=" + n + ", weight=" + weight + ", value=" + value;
    }
}
