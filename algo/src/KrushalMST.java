import edu.princeton.cs.algs4.*;

public class KrushalMST {

    private Queue<Edge> mst;

    public KrushalMST(EdgeWeightedGraph g) {

        MinPQ<Edge> edges = new MinPQ<>();
        for (Edge e : g.adj()) {
            edges.insert(e);
        }

        UF uf = new UF(edges.size());
        mst = new Queue<>();

        while (!edges.isEmpty() && mst.size() < g.V() - 1) {
            Edge e = edges.delMin();
            int v = e.either(), w = e.other(v);
            if (!uf.connected(v, w)) {
                mst.enqueue(e);
                uf.union(v, w);
            }
        }
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
        KrushalMST mst = new KrushalMST(g);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.2f\n", mst.weight());
    }
}
