import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class EagerPrimMST {

    private Queue<Edge> mst;

    public EagerPrimMST(EdgeWeightedGraph g) {

    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        double w = 0;
        for (Edge e : mst) {
            w = w + e.weight();
        }
        return w;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph g = new EdgeWeightedGraph(in);
        EagerPrimMST mst = new EagerPrimMST(g);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.2f\n", mst.weight());
    }
}
