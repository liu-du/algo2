import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class TST<Value> {

    private class Node {
        private char c;
        private Value val;
        private Node left, middle, right;
        public Node(char c) {this.c = c;}
    }

    private Node root;

    public void put(String key, Value value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node n, String key, Value value, int d) {
        char c = key.charAt(d);
        if (n == null) n = new Node(c);

        if      (c < n.c)              n.left   = put(n.left,   key, value, d);
        else if (c > n.c)              n.right  = put(n.right,  key, value, d);
        else if (d < key.length() - 1) n.middle = put(n.middle, key, value, d+1);
        else                           n.val    = value;

        return n;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        Node n = get(root, key, 0);
        if (n == null) return null;
        return n.val;
    }

    private Node get(Node n, String key, int d) {
        if (n == null) return null;
        char c = key.charAt(d);

        if      (c < n.c)              return get(n.left,  key, d);
        else if (c > n.c)              return get(n.right, key, d);
        else if (d < key.length() - 1) return get(n.middle,key,d+1);
        else                           return n;
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node n, String key, int d) {
        if (n == null) return null;
        char c = key.charAt(d);

        if      (c < n.c)              n.left = delete(n.left, key, d);
        else if (c > n.c)              n.right = delete(n.right, key, d);
        else if (d < key.length() - 1) n.middle = delete(n.middle,key,d+1);
        else                           n.val = null;

        if (n.left == null && n.middle == null && n.right == null && n.val == null) return null;
        return n;
    }

    private void keys(Node n, Stack<String> s, String acc) {
        if (n == null) return;
        keys(n.right, s, acc);
        keys(n.middle, s, acc + n.c);
        if (n.val != null) s.push(acc + n.c);
        keys(n.left, s, acc);
    }

    public Iterable<String> keys() {
        Stack<String> s = new Stack<>();
        keys(root, s, "");
        return s;
    }

    public Iterable<String> keysWithPrefix(String prefix) {
       Node n = get(root, prefix, 0);
       Stack<String> s = new Stack<>();
       if (n == null) return s;
       if (n.val != null) s.push(prefix);
       keys(n.middle, s, prefix);
       return s;
    }

    public String longestPrefixOf(String s) {
        return tryGet(root, s, 0, 0);
    }

    private String tryGet(Node n, String key, int d, int currentLongest) {
        if (n == null) return key.substring(0, currentLongest);
        char c = key.charAt(d);

        if      (c < n.c)              return tryGet(n.left,   key, d, currentLongest);
        else if (c > n.c)              return tryGet(n.right,  key, d, currentLongest);
        else if (n.val != null)        return tryGet(n.middle, key, d+1, d + 1);
        else                           return tryGet(n.middle, key,d+1, currentLongest);
    }

    public static void main(String[] args) {

        TST<Integer> t = new TST<>();

        t.put("a", 1);
        t.put("ab", 2);
        t.put("abc", 4);
        t.put("abd", 5);

        StdOut.println(t.get("a"));
        StdOut.println(t.get("ab"));
        StdOut.println(t.get("abc"));
        StdOut.println(t.get("abd"));

        StdOut.println();

        StdOut.println(t.longestPrefixOf("abcd"));
        StdOut.println(t.longestPrefixOf("abef"));
        StdOut.println(t.longestPrefixOf("ae"));
        StdOut.println(t.longestPrefixOf("e"));

        StdOut.print("\nAll keys: ");
        for (String s : t.keys()) {
            StdOut.print(s + " ");
        }

        StdOut.print("\nkeys prefix a: ");
        for (String s : t.keysWithPrefix("a")) {
            StdOut.print(s + " ");
        }

        StdOut.print("\nkeys prefix ab: ");
        for (String s : t.keysWithPrefix("ab")) {
            StdOut.print(s + " ");
        }

        StdOut.print("\nkeys prefix abc: ");
        for (String s : t.keysWithPrefix("abc")) {
            StdOut.print(s + " ");
        }

        StdOut.print("\nkeys prefix ad: ");
        for (String s : t.keysWithPrefix("ad")) {
            StdOut.print(s + " ");
        }

        StdOut.println();
        t.delete("abc");
        StdOut.println();

        StdOut.println(t.get("a"));
        StdOut.println(t.get("ab"));
        StdOut.println(t.get("abc"));
        StdOut.println(t.get("abd"));

        for (String s : t.keys()) {
            StdOut.print(s + " ");
        }
        StdOut.println();

        t.delete("a");
        StdOut.println();

        StdOut.println(t.get("a"));
        StdOut.println(t.get("ab"));
        StdOut.println(t.get("abc"));
        StdOut.println(t.get("abd"));
        for (String s : t.keys()) {
            StdOut.print(s + " ");
        }
        StdOut.println();
    }
}
