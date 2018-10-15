import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class FordFulkerson {

    private boolean marked[];
    private FlowEdge edgeTo[];
    private double value = 0d;

    public FordFulkerson(FlowNetwork g, int s, int t) {
        while (hasAugmentingPath(g, s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            for (int i = t; i != s; i = edgeTo[i].other(i)) {
                if (edgeTo[i].residualCapacityTo(i) < bottle)
                    bottle = edgeTo[i].residualCapacityTo(i);
            }
            for (int i = t; i != s; i = edgeTo[i].other(i)) {
                edgeTo[i].addResidualFlowTo(i, bottle);
            }
            value += bottle;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork g, int s, int t) {
        marked = new boolean[g.V()];
        edgeTo = new FlowEdge[g.V()];
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        marked[s] = true;

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e: g.adj(v)) {
                int w = e.other(v);
                if (!marked[w] && e.residualCapacityTo(w) > 0) {
                    q.enqueue(w);
                    marked[w] = true;
                    edgeTo[w] = e;
                    if (w == t) return true;
                }
            }
        }
        return false;
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    public static void main(String[] args) {
        FlowNetwork g = new FlowNetwork(new In("flownetwork_demo.txt"));
        StdOut.println("Flow network before");
        StdOut.println(g);

        FordFulkerson ff = new FordFulkerson(g, 0, 7);

        StdOut.println("max flow: " + ff.value());
        StdOut.print("min cut: ");

        for (int i = 0; i < g.V(); i++) {
            if (ff.inCut(i)) StdOut.print(i + " ");
        }

        StdOut.println("\n");
        StdOut.println("Flow network after");
        StdOut.println(g);
    }
}
