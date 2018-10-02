import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;


// assume it's a DAG
public class DepthFirstOrder {

    private boolean marked[];
    private Stack<Integer> reversPostOrder;

    public DepthFirstOrder(Digraph g) {

        reversPostOrder = new Stack<>();
        marked = new boolean[g.V()];
        for (int i = 0; i < g.V(); i++)
            if (!marked[i]) dfs(g, i);
    }

    public void dfs(Digraph g, int s) {
        marked[s] = true;
        for (int v : g.adj(s))
            if (!marked[v]) dfs(g, v);
        reversPostOrder.push(s);
    }

    public Iterable<Integer> reversePost() {
        return reversPostOrder;
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DepthFirstOrder dfo = new DepthFirstOrder(G);

        for (int i : dfo.reversPostOrder) {
            StdOut.println(i);
        }
    }
}
