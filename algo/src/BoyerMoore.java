import edu.princeton.cs.algs4.StdOut;

public class BoyerMoore {
    private int[] right;
    private final int R = 256;
    private final int M;
    private final String pat;

    public BoyerMoore(String pat) {
        this.pat = pat;
        M = pat.length();
        right = new int[R];
        for (int i = 0; i < R; i++) {
            right[i] = -1;
        }
        for (int j = 0; j < M; j++) {
            right[pat.charAt(j)] = j;
        }
    }

    public int search(String txt) {

        int N = txt.length();
        int skip;
        for (int i = 0; i < N - M; i+=skip) {
            skip = 0;
            for (int j = M-1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0) return i;
        }

        return N;
    }

    public static void main(String[] args) {

        String pat = "haha";
        String txt = "fdsafsdofsajfdaoisfjadsfhahadfdsafasfdsafdsaofjsa";
        BoyerMoore haha = new BoyerMoore(pat);

        int i = haha.search(txt);
        StdOut.println(i);
        StdOut.println(txt.substring(i, i + pat.length()));
    }

}
