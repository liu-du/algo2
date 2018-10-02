import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// Assumes all vertices all connected in one connected component
public class Bipartite {
    private boolean[] marked;
    private boolean[] party;
    private boolean bipartite = true;

    public Bipartite(Graph g) {
        marked = new boolean[g.V()];
        party = new boolean[g.V()];
        dfs(g, 0, true);
    }

    private void dfs(Graph g, int s, boolean party) {
        marked[s] = true;
        this.party[s] = party;
        for (int v : g.adj(s)) {
            if (!marked[v]) { dfs(g, v, !party); }
            else { if (this.party[v] == party) bipartite = false;}
        }
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        Graph G = new Graph(in);

        Bipartite bp = new Bipartite(G);
//        StdOut.println(bp.bipartite);

        for (int i = 0; i < bp.party.length; i++) {
            StdOut.println(bp.party[i]);
        }
    }
}
