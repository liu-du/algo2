import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstPaths {

    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public DepthFirstPaths(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(g, s);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w: g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();

        for (int i = v; i != s; i = edgeTo[i])
            path.push(i);

        path.push(s);
        return path;
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        Graph G = new Graph(in);

        DepthFirstPaths dfp = new DepthFirstPaths(G, 0);
        StdOut.println(dfp.pathTo(3));

    }
}
