import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SET;


public class WordNet {

    private Digraph rootedDAG;
    private RedBlackBST<String, Stack<Integer>> lookupNoun = new RedBlackBST<>();
    private RedBlackBST<Integer, String> lookupInt = new RedBlackBST<>();
    private boolean cycle = false;
    private boolean hasRoot = false;
    private int root;


    public WordNet(String synsets, String hypernyms) {
        if (synsets == null | hypernyms == null) throw new IllegalArgumentException("null arguments.");

        In synIn = new In(synsets);
        int V = 0;

        // populate lookup table
        while (synIn.hasNextLine()) {
            String[] line = synIn.readLine().split(",");
            int value = Integer.parseInt(line[0]);
            String[] keys = line[1].split(" ");

            lookupInt.put(value, line[1]);

            for (String key : keys) {
                if (lookupNoun.contains(key)) {
                    lookupNoun.get(key).push(value);
                } else {
                    Stack<Integer> tmp = new Stack<>();
                    tmp.push(value);
                    lookupNoun.put(key, tmp);
                }
            }

            // record the last value, V
            if (!synIn.hasNextLine()) V = value + 1;
        }

        // initialize empty graph
        rootedDAG = new Digraph(V);
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
        return lookupNoun.keys();
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
                else if (w != before_v && path.contains(w))
                    cycle = true;
            }
        }
        path.delete(v);
    }

    public boolean isNoun(String word) {
        return lookupNoun.contains(word);
    }

    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) | !isNoun(nounB)) throw new IllegalArgumentException("not a wordnet noun.");
        Iterable<Integer> a = lookupNoun.get(nounA);
        Iterable<Integer> b = lookupNoun.get(nounB);

        SAP sap = new SAP(rootedDAG);
        return sap.length(a, b);
    }

    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) | !isNoun(nounB)) throw new IllegalArgumentException("not a wordnet noun.");
        Iterable<Integer> a = lookupNoun.get(nounA);
        Iterable<Integer> b = lookupNoun.get(nounB);

        SAP sap = new SAP(rootedDAG);
        int ans = sap.ancestor(a, b);

        return lookupInt.get(ans);
    }

    public static void main(String[] args) {

        WordNet wn = new WordNet("src/synsets.txt", "src/hypernyms.txt");

        StdOut.println(wn.isNoun("1820s"));
        StdOut.println(wn.root);

        StdOut.println(wn.distance("white_marlin", "mileage"));
        StdOut.println(wn.distance("Black_Plague", "black_marlin"));
        StdOut.println(wn.distance("American_water_spaniel", "histology"));
        StdOut.println(wn.distance("Brown_Swiss", "barrel_roll"));
    }
}
