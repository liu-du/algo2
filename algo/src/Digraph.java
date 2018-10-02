import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class Digraph {

    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public Digraph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("negative V");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("negative E");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid in.");
        }

    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " +  (V-1));
    }

    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    public String toString() {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < V; i++)
            for (int j : adj(i))
                a.append(i).append("-").append(j).append("\n");
        return a.toString();
    }


    public Digraph reverse() {
        Digraph rdg = new Digraph(V);
        for (int i = 0; i < V; i++)
            for (int j : rdg.adj(i))
                rdg.addEdge(j, i);

        return rdg;
    }


    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        StdOut.println(G.V());
        StdOut.println(G.E());
        StdOut.println(G.degree(0));
        StdOut.println(G);
    }
}
