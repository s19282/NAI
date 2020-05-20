public class SingleObject
{
    private final int number;
    private final int size;
    private final int value;

    public SingleObject(int number, int weight, int value) {
        this.number = number;
        this.size = weight;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return  "number= " + number +
                ", size= " + size +
                ", value= " + value;
    }
}
