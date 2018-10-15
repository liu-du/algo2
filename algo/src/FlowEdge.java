import edu.princeton.cs.algs4.StdOut;

public class FlowEdge {

    private final int from, to;
    private final double capacity;
    private double flow = 0d;

    public FlowEdge(int from, int to, double capacity) {
        if (from < 0) throw new IllegalArgumentException("from < 0");
        if (to < 0) throw new IllegalArgumentException("to < 0");
        if (capacity < 0) throw new IllegalArgumentException("capacity is < 0");
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public FlowEdge(int from, int to, double capacity, double flow) {
        if (from < 0) throw new IllegalArgumentException("from < 0");
        if (to < 0) throw new IllegalArgumentException("to < 0");
        if (capacity < 0) throw new IllegalArgumentException("capacity is < 0");
        if (flow < 0) throw new IllegalArgumentException("flow < 0");
        if (flow > capacity) throw new IllegalArgumentException("flow > capacity");
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = flow;
    }
    public int from() { return from; }
    public int to() { return to; }
    public double capacity() { return capacity; }
    public double flow() { return flow; }

    public int other(int v) {
        validatevertex(v);
        if (v == from) return to;
        else return from;
    }

    private void validatevertex(int v) {
        if (v != from && v != to)
            throw new IllegalArgumentException("invalid endpoint: " + v + " is not " + from + " or " + to);
    }

    public double residualCapacityTo(int v) {
        validatevertex(v);
        if (v == from) return flow; // backward edge
        else return capacity - flow;  // forward edge
    }

    public void addResidualFlowTo(int v, double delta) {
       validatevertex(v);
       if (delta < 0) throw new IllegalArgumentException("delta < 0");
       if (v == from) {
           if (delta > flow)
               throw new IllegalArgumentException("delta " + delta + " is greater than flow " + flow);
           flow -= delta;
       } else {
           if (delta > capacity - flow)
               throw new IllegalArgumentException("delta " + delta + " is greater than residual flow " + (capacity - flow));
           flow += delta;
       }
    }

    public String toString() {
        return from + "->" + to + " " + flow + "/" + capacity;
    }

    public static void main(String[] args) {
        FlowEdge e = new FlowEdge(12, 23, 4.56);
        StdOut.println(e);
    }

}

