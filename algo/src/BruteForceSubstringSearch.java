import edu.princeton.cs.algs4.StdOut;

public class BruteForceSubstringSearch {
    public static int search(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();

        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j))
                    break;
            }
            if (j == M) return i;
        }
        return N;
    }

    public static int search2(String pat, String txt) {
        int i, N = txt.length();
        int j, M = pat.length();

        for (i = 0, j = 0; i < N && j < M; i++) {
            if (txt.charAt(i) == pat.charAt(j)) j++;
            else {i -= j; j = 0;}
        }

        if (j == M) return i - M;
        else return M;
    }

    public static void main(String[] args) {
        String pat = "haha";
        String txt = "fdsafsdofsajfdaoisfjadsfhahadfdsafasfdsafdsaofjsa";

        int i = BruteForceSubstringSearch.search(pat, txt);
        StdOut.println(txt.substring(i, i + pat.length()));

        int i2 = BruteForceSubstringSearch.search2(pat, txt);
        StdOut.println(txt.substring(i2, i2 + pat.length()));
    }
}
