import edu.princeton.cs.algs4.StdOut;

public class TrieST<Value> {

    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    private static final int R = 256;
    private Node root = new Node();

    public TrieST() {

    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node n, String key, Value val, int d) {
        if (n == null) n = new Node();
        if (d == key.length()) {
            n.value = val;
        } else {
            int i = key.charAt(d);
            n.next[i] = put(n.next[i], key, val, d + 1);
        }
        return n;
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        else return (Value) x.value;
    }

    private Node get(Node n, String key, int d) {
        if (n == null || d == key.length()) return n;
        else return get(n.next[key.charAt(d)], key, d + 1);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node n, String key, int d) {
        if (n == null) return n;

        if (d == key.length()) {
            n.value = null;
        } else {
            int i = key.charAt(d);
            n.next[i] = delete(n.next[i], key, d + 1);
        }
        if (n.value != null) return n;
        for (int i = 0; i < R; i++) {
            if (n.next[i] != null) return n;
        }
        return null;
    }

    public static void main(String[] args) {

        TrieST<Integer> t = new TrieST<>();

        t.put("a", 1);
        t.put("ab", 2);
        t.put("abc", 4);
        t.put("abd", 5);

        StdOut.println(t.get("a"));
        StdOut.println(t.get("ab"));
        StdOut.println(t.get("abc"));
        StdOut.println(t.get("abd"));

        t.delete("abc");
        StdOut.println();

        StdOut.println(t.get("a"));
        StdOut.println(t.get("ab"));
        StdOut.println(t.get("abc"));
        StdOut.println(t.get("abd"));

        t.delete("a");
        StdOut.println();

        StdOut.println(t.get("a"));
        StdOut.println(t.get("ab"));
        StdOut.println(t.get("abc"));
        StdOut.println(t.get("abd"));
    }
}
