public class KosarajuSharirSCC {

    private boolean[] marked;
    private int[] id;
    private int count = 0;

    public KosarajuSharirSCC(Digraph g) {

        marked = new boolean[g.V()];
        DepthFirstOrder dfo = new DepthFirstOrder(g.reverse());

        for (int i : dfo.reversePost()) {
            if (!marked[i]) {
                dfs(g, i);
                count++;
            }
        }
    }

    public void dfs(Digraph g, int i) {
        marked[i] = true;
        id[i] = count;
        for (int v : g.adj(i))
            if (!marked[v])
                dfs(g, v);
    }
}
