import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class PrimMST {
    private Queue<Edge> mst = new Queue<>();

    public PrimMST(EdgeWeightedGraph g) {
        boolean[] marked = new boolean[g.V()];
        marked[0] = true;
        MinPQ<Edge> pq = new MinPQ<>();
        for (Edge e : g.adj(0)) {
            pq.insert(e);
        }
        while (mst.size() < g.V() - 1 && !pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either(), w = e.other(v);
            if (marked[v] ^ marked[w]) {
                mst.enqueue(e);

                int new_vertex;
                if (marked[v]) new_vertex = w;
                else new_vertex = v;
                marked[new_vertex] = true;

                for (Edge d : g.adj(new_vertex)) {
                    int other = d.other(new_vertex);
                    if (marked[new_vertex] ^ marked[other]) pq.insert(d);
                }
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
        PrimMST mst = new PrimMST(g);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.2f\n", mst.weight());
    }
}
