public class Probability implements Comparable<Probability>
{
    private Double beforeSmoothing;
    private Double afterSmoothing;

    public Double getBeforeSmoothing() { return beforeSmoothing; }

    public void setBeforeSmoothing(Double beforeSmoothing) {
        this.beforeSmoothing = beforeSmoothing;
    }

    public Double getAfterSmoothing() {
        return afterSmoothing;
    }

    public void setAfterSmoothing(Double afterSmoothing) {
        this.afterSmoothing = afterSmoothing;
    }

    @Override
    public int compareTo(Probability o) {
        return this.getAfterSmoothing().compareTo(o.getAfterSmoothing());
    }
}
