import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;


public class WordNet {

    private Digraph rootedDAG;
    private RedBlackBST<String, Integer> lookupTable = new RedBlackBST<>();
    private boolean cycle = false;
    private boolean hasRoot = false;
    private int root;


    public WordNet(String synsets, String hypernyms) {
        if (synsets == null | hypernyms == null) throw new IllegalArgumentException("null arguments.");

//        File synfile = new File(synsets);
        In synIn = new In(synsets);

        int V = 0;
        // populate lookup table
        while (synIn.hasNextLine()) {
            String[] line = synIn.readLine().split(",");
            int value = Integer.parseInt(line[0]);
            String[] keys = line[1].split(" ");

            for (String key : keys) {
                lookupTable.put(key, value);
            }

            // record the last value, V
            if (!synIn.hasNextLine()) V = value + 1;
        }

        // initialize empty graph
        rootedDAG = new Digraph(V);

//        File hypfile = new File(hypernyms);
        In hypIn = new In(hypernyms);

        while (hypIn.hasNextLine()) {
            String[] line = hypIn.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int w = 1; w < line.length; w++)
               rootedDAG.addEdge(v, Integer.parseInt(line[w]));
        }

        if (hasCycle()) throw new IllegalArgumentException("not a DAG");
    }

    public Iterable<String> nouns() {
        return lookupTable.keys();
    }

    // this method and detectCycle is a digraph detect cycle algorithm
    private boolean hasCycle() {
        // recoded whether marked already
        boolean[] marked = new boolean[rootedDAG.V()];
        for (int i = 0; i < rootedDAG.V(); i++) {
            if (!marked[i]) {
                SET<Integer> path = new SET<>();
                detectCycle(marked, i, -1, path);
            }
        }
        return cycle;
    }

    private void detectCycle(boolean[] marked, int v, int before_v, SET<Integer> path) {
        marked[v] = true;
        path.add(v);

        // root has no leaving edges
        if (!rootedDAG.adj(v).iterator().hasNext()) {
            if (hasRoot) {
                if (root != v) throw new IllegalArgumentException("more than one root");
            } else {
                root = v;
                hasRoot = true;
            }
        } else {
            for (int w : rootedDAG.adj(v)) {
                if (!marked[w])
                    detectCycle(marked, w, v, path);
                    // if a neighbor is marked and not the immediate previous visits and in the same component, there's a cycle
                else if (w != before_v && path.contains(w)) {
                    cycle = true;
                }
            }
        }
        path.delete(v);
    }

    public boolean isNoun(String word) {
        return lookupTable.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) | !isNoun(nounB)) throw new IllegalArgumentException("not a wordnet noun.");
        int a = lookupTable.get(nounA);
        int b = lookupTable.get(nounB);

        return 0;
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) | !isNoun(nounB)) throw new IllegalArgumentException("not a wordnet noun.");

        return "hfa";
    }

    public static void main(String[] args) {

        WordNet wn = new WordNet("src/synsets.txt", "src/hypernyms.txt");

        StdOut.println(wn.isNoun("1820s"));
        StdOut.println(wn.root);

    }
}
