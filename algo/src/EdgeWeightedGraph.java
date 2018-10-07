import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class EdgeWeightedGraph {

    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("negative endpoint");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("negative number of edges");
        for (int i = 0; i < E; i++) {
            int v = validateVertex(in.readInt());
            int w = validateVertex(in.readInt());
            double weight = in.readDouble();
            this.addEdge(new Edge(v, w, weight));
        }

    }

    private int validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        return v;
    }

    public void addEdge(Edge e) {
        int v = validateVertex(e.either());
        int w = validateVertex(e.other(v));
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
       return adj[validateVertex(v)];
    }

    public Iterable<Edge> adj() {
        Bag<Edge> all_edges = new Bag<>();
        for (int i = 0; i < V; i++) {
            int selfLoops = 0;
            for (Edge j : adj[i]) {
                int w = j.other(i);
                if (i < w) {
                    all_edges.add(j);
                } else if (j.other(i) == i) {
                    if (selfLoops % 2 == 0) all_edges.add(j);
                    selfLoops++;
                }
            }
        }
        return all_edges;
    }

    public int V() {
        return this.V;
    }

    public int E() {
        return this.E;
    }

    public int degree(int v) {
        return adj[validateVertex(v)].size();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" ").append(E).append("\n");
        for (int i = 0; i < V; i++) {
            s.append(i).append(": ");
            for (Edge e : adj[i]) {
                s.append(e).append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph g = new EdgeWeightedGraph(in);
        StdOut.println(g);
    }
}
