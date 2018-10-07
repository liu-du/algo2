import edu.princeton.cs.algs4.*;

public class DijkstraSP {
    private final int V;
    private final int s;
    private double[] distTo;
    private DirectedEdge[] edgeTo;

    public DijkstraSP(EdgeWeightedDigraph g, int s) {
        V = g.V();
        this.s = validateVertex(s);
        edgeTo = new DirectedEdge[V];
        IndexMinPQ<Double> pq = new IndexMinPQ<>(V);
        distTo = new double[V];

        for (int i = 0; i < V; i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        distTo[s] = 0;

        pq.insert(s, 0d);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : g.adj(v)) {
                relax(e, pq);
            }
        }
    }

    private void relax(DirectedEdge e, IndexMinPQ<Double> pq) {
        int from = e.from(), to = e.to();
        if (distTo[from] + e.weight() < distTo[to]) {
            distTo[to] = distTo[from] + e.weight();
            edgeTo[to] = e;
            if (pq.contains(to)) pq.decreaseKey(to, e.weight());
            else pq.insert(to, e.weight());
        }
    }

    private int validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        return v;
    }

    public double distTo(int v) {
        return distTo[validateVertex(v)];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        v = validateVertex(v);
        Stack<DirectedEdge> path = new Stack<>();
        while (v != s) {
            path.push(edgeTo[v]);
            v = edgeTo[v].from();
        }
        return path;
    }

    public boolean hasPath(int v) {
        return edgeTo[validateVertex(v)] != null;
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        StdOut.println(s);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        DijkstraSP sp = new DijkstraSP(G, s);

        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }
    }
}
