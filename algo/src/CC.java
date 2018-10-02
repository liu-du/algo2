import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class CC {
    private boolean marked[];
    private int[] id;
    private int count = 0;

    public CC(Graph g) {
        int v = g.V();
        marked = new boolean[v];
        id = new int[v];
        for (int i = 0; i < v; i++)
            if (!marked[i]) {
                dfs(g, i);
                count++;
            }
    }

    private void dfs(Graph g, int s) {
        marked[s] = true;
        id[s] = count;
        for (int v: g.adj(s))
            if (!marked[v])
                dfs(g, v);
    }

    public int count() {
        return count;
    }

    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        Graph G = new Graph(in);

        CC cc = new CC(G);
        StdOut.println(cc.count());
        Arrays.stream(cc.id).forEach(StdOut::print);
    }
}
