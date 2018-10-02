import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// assume one component
public class Cycle {
    private boolean[] marked;
    private boolean hasCycle = false;

    public Cycle(Graph g) {
        marked = new boolean[g.V()];
        dfs(g, 0, -1);
    }

    private void dfs(Graph g, int s, int before_s) {
        marked[s] = true;
        for (int v : g.adj(s)) {
            if (!marked[v]) dfs(g, v, s);
            else {
                if (v != before_s) hasCycle = true;
            }
        }
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        Graph G = new Graph(in);

        Cycle c = new Cycle(G);
        StdOut.println(c.hasCycle);
    }
}
