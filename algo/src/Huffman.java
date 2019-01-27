import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.MinPQ;

// works for ascii (8 bit encoding) files
public class Huffman {

    private static final int R = 256;

    private static class Node implements Comparable<Node> {
        private char ch;
        private int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    private static Node readTrie() {
        if (BinaryStdIn.readBoolean()) {
            char c = BinaryStdIn.readChar(8);
            return new Node(c, 0, null, null);
        }
        Node x = readTrie();
        Node y = readTrie();
        return new Node('\0', 0, x, y);
    }

    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char i = 0; i < R; i++)
            if (freq[i] > 0)
                pq.insert(new Node(i, freq[i], null, null));

        while (pq.size() > 1) {
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }

        return pq.delMin();
    }

    public static void expand() {
        Node root = readTrie();
        int N = BinaryStdIn.readInt();

        for (int i = 0; i < N; i++) {
            Node x = root;
            while(!x.isLeaf()) {
                if (BinaryStdIn.readBoolean()) x = x.right;
                else x = x.left;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    private static void buildST(Node node, ST<Character, String> st, String acc) {
        if (node.isLeaf()) {
            st.put(node.ch, acc);
        } else {
            buildST(node.left, st, acc + "0");
            buildST(node.right, st, acc + "1");
        }
    }

//    private static void printTrie(ST<Character, String> st) {
//        for (Character key : st) {
//            StdOut.print(key);
//            StdOut.print(": ");
//            StdOut.println(st.get(key));
//        }
//    }

    public static void compress() {
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            sb.append(BinaryStdIn.readChar(8));
        }
        String s = sb.toString();

        int[] freq = new int[R];
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i)]++;
        }

        // build trie
        Node root = buildTrie(freq);


        // build a symbol table
        ST<Character, String> st = new ST<>();
        buildST(root, st, "");

        // see the trie
//        printTrie(st);
//        throw new IllegalArgumentException("stop");
        // -------- transmitting --------

        // 1. transmit the trie first
        writeTrie(root);

        // 2. transmit how many chars
        BinaryStdOut.write(s.length());

        // 3. transmit encoded chars
        String code;
        for (int i = 0; i < s.length(); i++) {
            code = st.get(s.charAt(i));
            for (int j = 0; j < code.length(); j++) {
                if (code.charAt(j) == '0') BinaryStdOut.write(false);
                else                       BinaryStdOut.write(true);
            }
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
