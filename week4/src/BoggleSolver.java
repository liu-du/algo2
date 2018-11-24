import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

import java.util.HashSet;

public class BoggleSolver {
    private int col;
    private int row;
    private CustomTrieSET trie;
    private static final int R = 26;

    private class Node {
        private char c;
        private boolean in;
        private Node[] next = new Node[R];

        private boolean hasLinks() {
            for (int i = 0; i < R; i++)
                if (next[i] != null)
                    return true;
            return false;
        }
    }


    private class CustomTrieSET {
        private Node root;

        private CustomTrieSET() { root = new Node(); }

        private void add(String s) { add(root, s, 0); }

        private Node add(Node n, String s, int d) {
            char c = s.charAt(d);
            int i = c - 65;
            if (n.next[i] == null) {
                Node newNode = new Node();
                newNode.c = c;
                n.next[i] = newNode;
            }
            if (d == s.length() - 1) n.next[i].in = true;
            else n.next[i] = add(n.next[i], s, d + 1);
            return n;
        }
        private boolean contains(String s) {
            Node n = contains(root, s, 0);
            if (n == null) return false;
            return n.in;
        }
        private Node contains(Node n, String s, int d) {
            char c = s.charAt(d);
            int i = c - 65;
            if (d == s.length() - 1) return n.next[i];
            if (n.next[i] == null) return null;
            else return contains(n.next[i], s, d + 1);
        }
    }

    public BoggleSolver(String[] dictionary) {
        // build a trie or TST
        trie = new CustomTrieSET();
        for (int i = 0; i < dictionary.length; i++)
            trie.add(dictionary[i]);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        col = board.cols();
        row = board.rows();

        HashSet<String> s = new HashSet<>();
        boolean[][] marked = new boolean[row][col];

        // depth first search
        for (int c = 0; c < col; c++) {
            for (int r = 0; r < row; r++) {
                findWordWithPrefix(r, c, "", trie.root, s, marked, board);
                marked[r][c] = false;
            }
        }
        return s;
    }

    private void findWordWithPrefix(int r, int c, String prefix, Node n, HashSet<String> s, boolean[][] marked, BoggleBoard board) {
        // mark current position
        marked[r][c] = true;

        Character ch = board.getLetter(r, c);
        Node nextNode = n.next[ch - 65];
        if (nextNode == null) return;

        if (ch == 'Q') {
            if (nextNode.next['U' - 65] == null) return;
            nextNode = nextNode.next['U' - 65];
            prefix = prefix + "QU";
        } else { prefix = prefix + ch; }

        // add if find a match
        if (nextNode.in && prefix.length() > 2) s.add(prefix);

        // recursively search all unvisited neighbors
        if (nextNode.hasLinks())
            for (int rr = r - 1; rr <= r + 1; rr++)
                for (int cc = c - 1; cc <= c + 1; cc++)
                    if (isOnBoarad(rr, cc) && !marked[rr][cc]) {
                        findWordWithPrefix(rr, cc, prefix, nextNode, s, marked, board);
                        // recursive call will mark (rr,cc) as visited, unmark it once it finishes
                        marked[rr][cc] = false;
                    }
    }

    private boolean isOnBoarad(int r, int c) {
        if (r < 0 || r >= row || c < 0 || c >= col) return false;
        return true;
    }

    public int scoreOf(String word) {
        int score = 0;
        if (trie.contains(word)) {
            switch (word.length()) {
                case 0: case 1: case 2:
                    break;
                case 3: case 4:
                    score = 1; break;
                case 5:
                    score = 2; break;
                case 6:
                    score = 3; break;
                case 7:
                    score = 5; break;
                default:
                    score = 11;
            }
        }
        return score;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

