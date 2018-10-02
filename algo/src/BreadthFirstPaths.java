import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BreadthFirstPaths {

    private int s;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BreadthFirstPaths(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        bfs(g, s);
    }

    private void bfs(Graph g, int s) {

        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int w = q.dequeue();
            for (int i : g.adj(w)) {
                if (!marked[i]) {
                    q.enqueue(i);
                    marked[i] = true;
                    edgeTo[i] = w;
                    distTo[i] = distTo[w] + 1;
                }
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

        BreadthFirstPaths dfp = new BreadthFirstPaths(G, 0);
        StdOut.println(dfp.pathTo(3));
    }
}
