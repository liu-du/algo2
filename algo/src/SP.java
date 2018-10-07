import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SP {
    private final int V;
    private final int s;
    private double[] distTo;
    private DirectedEdge[] edgeTo;


    public SP(EdgeWeightedDigraph g, int s) {
        V = g.V();
        this.s = validateVertex(s);
        distTo = new double[V];
        edgeTo = new DirectedEdge[V];
        s = validateVertex(s);
        dfs(g, 0, new DirectedEdge(s, s, 0));
    }

    private void dfs(EdgeWeightedDigraph g, double dist, DirectedEdge edge) {
        int to = edge.to();
        if (edgeTo[to] == null || distTo[to] > dist) {
            distTo[to] = dist;
            edgeTo[to] = edge;
            for (DirectedEdge e : g.adj(to))
                if (e.from() != e.to())
                    dfs(g, dist + e.weight(), e);
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
        SP sp = new SP(G, s);

        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%d to %d (%.2f): ", s, v, sp.distTo(v));
            for (DirectedEdge e : sp.pathTo(v)) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }
    }
}
