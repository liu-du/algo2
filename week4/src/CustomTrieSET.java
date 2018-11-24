//public class CustomTrieSET {
//
//    public static final int R = 26;
//    public Node root;
//
//    public class Node {
//        public char c;
//        public boolean in;
//        public Node[] next = new Node[R];
//
//        public boolean hasLinks() {
//            for (int i = 0; i < R; i++)
//                if (next[i] != null)
//                    return true;
//            return false;
//        }
//    }
//
//    public CustomTrieSET() {
//        root = new Node();
//    }
//
//    public void add(String s) {
//        add(root, s, 0);
//    }
//
//    private Node add(Node n, String s, int d) {
//
//        char c = s.charAt(d);
//        int i = c - 65;
//
//        if (n.next[i] == null) {
//            Node newNode = new Node();
//            newNode.c = c;
//            n.next[i] = newNode;
//        }
//
//        if (d == s.length() - 1) {
//            n.next[i].in = true;
//        } else {
//            n.next[i] = add(n.next[i], s, d + 1);
//        }
//
//        return n;
//    }
//
////    public boolean contains(String s) {
////        Node n = contains(root, s, 0);
////        if (n == null) return false;
////        return n.in;
////    }
////
////    public Node contains(Node n, String s, int d) {
////        char c = s.charAt(d);
////        int i = c - 65;
////        if (d == s.length() - 1) return n.next[i];
////        if (n.next[i] == null) return null;
////        else return contains(n.next[i], s, d + 1);
////    }
//
////    public static void main(String[] args) {
////
////        CustomTrieSET t = new CustomTrieSET();
////
////        t.add("A");
////        assert t.contains("A");
////        t.add("AB");
////        assert t.contains("AB");
////        t.add("ABC");
////        assert t.contains("ABC");
////        t.add("ABD");
////        assert t.contains("ABD");
////
////        assert !t.contains("ABE");
////        assert !t.contains("BE");
////        assert !t.contains("D");
////
////        assert t.contains("A");
////        assert t.contains("AB");
////        assert t.contains("ABC");
////        assert t.contains("ABD");
////
////        t.add("AB");
////        assert t.contains("AB");
////        t.add("ABC");
////        assert t.contains("ABC");
////        t.add("ABD");
////        assert t.contains("ABD");
////
////        assert !t.contains("ABE");
////        assert !t.contains("BE");
////        assert !t.contains("D");
////
////        t.add("JKL");
////        t.add("JK");
////        t.add("J");
////
////        assert t.contains("J");
////        assert t.contains("JK");
////        assert t.contains("JKL");
////    }
//}
