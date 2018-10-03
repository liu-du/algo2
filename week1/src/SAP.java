import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class SAP {

    private Digraph g;
    private boolean[] markedV;
    private boolean[] markedW;
    private int[] distToV;
    private int[] distToW;
    private int[] edgeToV;
    private int[] edgeToW;
    private int countV;
    private int countW;
    private int bestSoFar;
    private int foundOne;
    private Queue<Integer> qV;
    private Queue<Integer> qW;


    public SAP(Digraph g) {
        if (g == null) throw new IllegalArgumentException("null graph");
        this.g = new Digraph(g);
        init();
    }

    // initialize instance variables
    private void init() {
        markedV = new boolean[g.V()];
        markedW = new boolean[g.V()];
        distToV = new int[g.V()];
        distToW = new int[g.V()];
        edgeToV = new int[g.V()];
        edgeToW = new int[g.V()];
        bestSoFar = Integer.MAX_VALUE;
        foundOne = -1;
    }

    public int length(int v, int w) {
        return length(Arrays.asList(v), Arrays.asList(w));
    }

    public int ancestor(int v, int w) {
        return ancestor(Arrays.asList(v), Arrays.asList(w));
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("null v or w");
        int ans = ancestor(v, w);
        if (ans == -1) return -1;
        return distToV[ans] + distToW[ans];
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException("null v or w");

        // reset instance variable
        init();

        // initialize V
        countV = 0;
        qV = new Queue<>();
        for (int vv : v) qV.enqueue(vv);

        // initialize W
        countW = 0;
        qW = new Queue<>();
        for (int ww : w) qW.enqueue(ww);

        // lockstep bfs
        bfsV();
        return foundOne;
    }

    private void bfsV() {
        int size = qV.size();
        // if both queue has no element, there's no ancestor
        if (size == 0) {
            if (qW.size() != 0) bfsW();
        } else {
            for (int i = 0; i < size; i++) {
                int v = qV.dequeue();
                if (!markedV[v]) {
                    markedV[v] = true;
                    distToV[v] = countV;
                    // if v is marked in markedW, found SAP ancestor
                    if (markedW[v]) {
                        if (foundOne == -1) {
                            foundOne = v;
                            bestSoFar = distToV[v] + distToW[v];
                        } else if (distToW[v] + distToV[v] < bestSoFar) {
                            foundOne = v;
                            return; // return early
                        }
                    }
                    for (int w : g.adj(v)) {
                        qV.enqueue(w);
                        edgeToV[w] = v;
                    }
                }
            }
            countV++;
            bfsW();
        }
    }

    private void bfsW() {
        int size = qW.size();
        // if both queue has no element, there's no ancestor
        if (size == 0) {
            if (qV.size() != 0) bfsV();
        } else {
            for (int i = 0; i < size; i++) {
                int v = qW.dequeue();
                if (!markedW[v]) {
                    markedW[v] = true;
                    distToW[v] = countW;
                    // if v is marked in markedV, found SAP ancestor
                    if (markedV[v]) {
                        if (foundOne == -1) {
                            foundOne = v;
                            bestSoFar = distToV[v] + distToW[v];
                        } else if (distToW[v] + distToV[v] < bestSoFar) {
                            foundOne = v;
                            return;
                        }
                    }
                    for (int w : g.adj(v)) {
                        qW.enqueue(w);
                        edgeToW[w] = v;
                    }
                }
            }
            countW++;
            bfsV();
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//            StdOut.printf("ancestor = %d\n", ancestor);
        }
    }
}
