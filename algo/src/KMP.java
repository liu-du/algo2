import edu.princeton.cs.algs4.StdOut;

public class KMP {
    int[][] dfa;
    String pat;
    int M;
    int R = 256;

    public KMP(String pat) {
        this.pat = pat;
        M = pat.length();

        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;

        for (int x = 0, c = 1; c < M; c++) {
            for (int r = 0; r < R; r++)
                dfa[r][c] = dfa[r][x];
            dfa[pat.charAt(c)][c] = c + 1;
            x = dfa[pat.charAt(c)][x];
        }
    }

    private void dfa() {
        for (int j = 0; j < R; j++) {
            StdOut.print((char) j + " ");
            for (int i = 0; i < M; i++) {
                StdOut.print(dfa[j][i]);
            }
            StdOut.println();
        }
    }

    public int search(String txt) {
        int j = 0;
        for (int i = 0; i < txt.length(); i++) {
            j = dfa[txt.charAt(i)][j];
            if (j == M) return i - M + 1;
        }
        return txt.length();
    }

    public static void main(String[] args) {

        String pat = "haha";
        String txt = "fdsafsdofsajfdaoisfjadsfhahadfdsafasfdsafdsaofjsa";
        KMP haha = new KMP(pat);

        int i = haha.search(txt);
        haha.dfa();

        StdOut.println(i);
        StdOut.println(txt.substring(i, i + pat.length()));
    }
}
