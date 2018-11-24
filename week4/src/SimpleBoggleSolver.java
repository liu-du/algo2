import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.TrieSET;

public class SimpleBoggleSolver {

    private final TrieSET trie;
    private int col;
    private int row;

    public SimpleBoggleSolver(String[] dictionary) {
        // build a trie or TST
        trie = new TrieSET();
        for (int i = 0; i < dictionary.length; i++) {
            trie.add(dictionary[i]);
        }
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        col = board.cols();
        row = board.rows();

        TrieSET s = new TrieSET();
        boolean[][] marked = new boolean[row][col];

        // depth first search
        for (int c = 0; c < col; c++) {
            for (int r = 0; r < row; r++) {
                findWordWithPrefix(r, c, "", s, marked, board);
                marked[r][c] = false;
            }
        }
        return s;
    }

    private void findWordWithPrefix(int r, int c, String prefix, TrieSET s, boolean[][] marked, BoggleBoard board) {

        // mark current position
        marked[r][c] = true;
        Character ch = board.getLetter(r, c);

        String inc;
        if (ch == 'Q') inc = "QU";
        else inc = Character.toString(ch);
        prefix = prefix + inc;

        // stop searching if the current prefix is not in the dictionary
        if (!trie.keysWithPrefix(prefix).iterator().hasNext()) return;

        // add if find a match
        if (trie.contains(prefix) && prefix.length() > 2)
            s.add(prefix);

        // recursively search all unvisited neighbors
        for (int rr = r - 1; rr <= r + 1; rr++) {
            for (int cc = c - 1; cc <= c + 1; cc++) {
                if (isOnBoarad(rr, cc) && !marked[rr][cc]) {
                    findWordWithPrefix(rr, cc, prefix, s, marked, board);
                    // recursive call will mark (rr,cc) as visited, unmark it once it finishes
                    marked[rr][cc] = false;
                }
            }
        }
    }

    private boolean isOnBoarad(int r, int c) {
        if (r < 0 || r >= row || c < 0 || c >= col) return false;
        return true;
    }

    public int scoreOf(String word) {
        int score;
        switch (word.length()) {
            case 0: case 1: case 2:
                score = 0; break;
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
        return score;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        SimpleBoggleSolver solver = new SimpleBoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
