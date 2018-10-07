import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class EdgeWeightedDigraph {
    private int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    private int[] indegree;

    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("negative number of vertices.");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        this.adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++)
            adj[i] = new Bag<>();
    }

    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("negative number of edges.");
        for (int i = 0; i < E; i++) {
            int v = validateVertex(in.readInt());
            int w = validateVertex(in.readInt());
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    private int validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " vs not between 0 and " + (V - 1));
        return v;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[validateVertex(v)];
    }

    public void addEdge(DirectedEdge e) {
        int from = validateVertex(e.from());
        int to = validateVertex(e.to());
        adj[from].add(e);
        E++;
        indegree[to]++;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int indegree(int v) {
        return indegree[validateVertex(v)];
    }

    public int outdegree(int v) {
       return adj[validateVertex(v)].size();
    }

    public Iterable<DirectedEdge> edges() {
       Bag<DirectedEdge> edges = new Bag<>();
       for (int i = 0; i < V; i++) {
           for (DirectedEdge e : adj[i]) {
               edges.add(e);
           }
       }
       return edges;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" ").append(E).append("\n");
        for (int i = 0; i < V; i++) {
            s.append(i).append(": ");
            for (DirectedEdge e : adj[i]) {
                s.append(e).append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        StdOut.println(G);
    }

}
