import edu.princeton.cs.algs4.StdOut;

public class DirectedEdge {

    private final int from, to;
    private final double weight;

    public DirectedEdge(int from, int to, double weight) {
        this.from = validateVertex(from);
        this.to = validateVertex(to);
        this.weight = weight;
    }

    private int validateVertex(int v) {
        if (v < 0) throw new IllegalArgumentException("negative vertex.");
        return v;
    }

    public int from() {
        return from;
    }
    public int to() {
        return to;
    }
    public double weight() {
        return weight;
    }

    public String toString() {
        return String.format("%d->%d %.5f", from, to, weight);
    }

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(12, 21, 32.23);
        StdOut.println(e);
    }
}
