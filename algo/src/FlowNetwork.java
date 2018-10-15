import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FlowNetwork {

    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        if (V <= 0) throw new IllegalArgumentException("V must be positive");
        this.V = V;
        this.E = 0;
        this.adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new Bag<>();
        }
    }

    public FlowNetwork(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("E must be positive");

        for (int i = 0; i < E; i++) {
            int from = in.readInt();
            int to = in.readInt();
            double capacity = in.readDouble();
            FlowEdge e = new FlowEdge(from, to, capacity);
            addEdge(e);
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException(v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(FlowEdge e) {
        int from = e.from();
        int to = e.to();
        validateVertex(from);
        validateVertex(to);
        adj[from].add(e);
        adj[to].add(e);
        E++;
    }

    public Iterable<FlowEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> q = new Bag<>();
        for (int i = 0; i < V; i++) {
            for (FlowEdge e : adj[i]) {
                if (e.from() == i) q.add(e);
            }
        }
        return q;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(V).append(" ").append(E).append("\n");

        for (int v = 0; v < V; v++) {
            s.append(v).append(": ");
            for (FlowEdge e : adj[v]) {
                if (e.from() == v) s.append(e).append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        FlowNetwork G = new FlowNetwork(in);
        StdOut.println(G);
    }
}
