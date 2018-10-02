import edu.princeton.cs.algs4.Stack;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Set;

// assume all vertices are connected, assume no self loop
public class EulerCycle {

    private Stack<Integer> cycle = new Stack<>();
    private Stack<Integer> stack = new Stack<>();

    public EulerCycle(Graph g) {

        HashMap<int[], Boolean> edges = new HashMap<>();

        // check degree all all even, initialize hashTable
        int v = g.V();
        for (int i = 0; i < v; i++) {
            int count = 0;
            for (int j : g.adj(i)) {
                count++;
                if (i < j) edges.put(new int[] {i, j}, false);
            }
            if (count % 2 == 1) return;
        }

//        dfs(g, 0);
    }

//    private Stack<Integer> dfs(Graph g, int s, Stack s) {
//        for (int v : g.adj(s)) {
//            if v-s not visited before and v has more than 1 out degree, mark v-s visited, push s onto the stack and visit v
//            if v-s not visited before and v has only 1 out degree (v-s), base case
//            if (v has no more available edges going out) {
//                cycle.push(s);
//            } else dfs(g, v);
//        }
//
//        while (!edges.isEmpty()) {
//
//        }
//
//    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

}
