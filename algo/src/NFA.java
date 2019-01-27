import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Digraph;

public class NFA {

    private char[] re;  // match transition
    private Digraph G;  // epsilon transition digraph
    private int M;      // number of states


    public NFA(String regexp) {
        M = regexp.length();
        re = regexp.toCharArray();
        G = buildEpsilonTransitionDigraph();
    }

    public boolean recognizes(String txt) {

        int N = txt.length();

        // initial program count, pc, a bag of reachable states and digraph
        Bag<Integer> pc = new Bag<>();

        // initial reachable state from 0 before scanning any char
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int i = 0; i < M; i++)
            if (dfs.marked(i)) pc.add(i);

        // scan next char, recompute reachable states
        for (int i = 0; i < N; i++) {
            // scan next char, initialize empty pc
            char nc = txt.charAt(i);
            pc = new Bag<>();

            // add reachable state after reading in nc
            for (int j = 0; j < M; j++)
                if (dfs.marked(j) && (re[j] == nc || re[j] == '.')) pc.add(j + 1);

            // if pc is empty, doesn't match finish early
            if (pc.isEmpty()) return false;

            // compute more reachable state by epsilon transition
            dfs = new DirectedDFS(G, pc);

            pc = new Bag<>();
            for (int j = 0; j < M + 1; j++)
                if (dfs.marked(j)) pc.add(j);
        }

        for (int i : pc)
            if (i == M) return true;

        return false;
    }

    public Digraph buildEpsilonTransitionDigraph() {
        Digraph g = new Digraph(M + 1);
        Stack<Integer> peranStack = new Stack<>();

        for (int i = 0; i < M; i++) {
            if (re[i] == '(' || re[i] == '|') {
                peranStack.push(i);
            } else if (re[i] == ')') {
                g.addEdge(i, i + 1);
                Bag<Integer> ors = new Bag<>();

                int lp = peranStack.pop();
                while (re[lp] == '|') {
                    g.addEdge(lp, i);
                    ors.add(lp);
                    lp = peranStack.pop();
                }
                g.addEdge(lp, lp + 1);

                for (int or : ors) { g.addEdge(lp, or + 1); }

                // special case if '*' follows ')'
                if (i < M - 1 && re[i + 1] == '*') {
                    g.addEdge(lp, i + 1);
                    g.addEdge(i + 1, lp);
                    g.addEdge(i + 1, i + 2);
                    i++;
                }
            } else if (re[i] == '*') {
                g.addEdge(i, i - 1);
                g.addEdge(i - 1, i);
                g.addEdge(i, i + 1);
            }
        }

        // accept state epsilon transition
        g.addEdge(M - 1, M);

        return g;
    }

    public static void main(String[] args) {


        NFA nfa = new NFA("(A*D)");

        assert( nfa.recognizes("D") );
        assert( nfa.recognizes("AD") );
        assert( nfa.recognizes("AAAAD") );
        assert(!nfa.recognizes("fdsa") );


        nfa = new NFA("(A*D|C)");

        assert( nfa.recognizes("C") );
        assert( nfa.recognizes("D") );
        assert( nfa.recognizes("AD") );
        assert( nfa.recognizes("AAAAD") );
        assert(!nfa.recognizes("ADD") );

        nfa = new NFA("(A*D|C|E)");

        assert( nfa.recognizes("C") );
        assert( nfa.recognizes("D") );
        assert( nfa.recognizes("E") );
        assert( nfa.recognizes("AD") );
        assert( nfa.recognizes("AAAAD") );
        assert(!nfa.recognizes("ADD") );


        nfa = new NFA("((A*D|C|E)*B)");
        //                    012345678910

        assert( nfa.recognizes("CB") );
        assert( nfa.recognizes("CCB") );
        assert( nfa.recognizes("B") );
        assert(!nfa.recognizes("C") );
        assert(!nfa.recognizes("AD") );
        assert(!nfa.recognizes("AAAD") );
        assert(!nfa.recognizes("CE") );

        assert( nfa.recognizes("DB") );
        assert( nfa.recognizes("DDDDDB") );
        assert( nfa.recognizes("EEB") );
        assert( nfa.recognizes("ADADB") );
        assert(!nfa.recognizes("AAAAD") );
        assert(!nfa.recognizes("ADD") );


        nfa = new NFA("(.*(B|C))");
        //                    012345678

        assert( nfa.recognizes("B") );
        assert( nfa.recognizes("BBBBBBBBB") );
        assert( nfa.recognizes("C") );
        assert( nfa.recognizes("CC") );
        assert( nfa.recognizes("AB") );
        assert( nfa.recognizes("AAAC") );
        assert( nfa.recognizes("dfadB") );
        assert( nfa.recognizes("dfadC") );
        assert(!nfa.recognizes("dd") );
        assert(!nfa.recognizes("fdasfsafdsaf") );


    }
}
