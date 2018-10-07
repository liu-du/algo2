import edu.princeton.cs.algs4.StdOut;

public class Edge implements Comparable<Edge> {

    private int v;
    private int w;
    private double weight;

    public Edge (int v, int w, double weight) {

        if (v < 0 || w < 0) throw new IllegalArgumentException("negative endpoint");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");

        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int v) {
        if (this.v == v) return this.w;
        else if (this.w == v) return this.v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public double weight() {
       return this.weight;
    }

    public String toString() {
       return String.format("%d-%d %.5f", v, w, weight);
    }

    public static void main(String[] args) {
        Edge e = new Edge(12, 21, 32.23);
        StdOut.println(e);
    }
}
