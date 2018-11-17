import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class LRS {

    // longest common prefix
    private static int lcp(String a, String b) {
        int len = a.length();
        if (len > b.length())
            len = b.length();

        for (int i = 0; i < len; i++)
            if (a.charAt(i) != b.charAt(i))
                return i;
        return len;
    }

    public static String lrs(String s) {
        int N = s.length();

        String[] suffixes = new String[N];
        for (int i = 0; i < N; i++) {
            if (i % 100 == 0) StdOut.println(i);

            suffixes[i] = s.substring(i, N);
        }

        ThreeWayRadixQuickSort.sort(suffixes);

        String lrs = "";
        for (int i = 0; i < N-1; i++) {
            int len = lcp(suffixes[i], suffixes[i + 1]);
            if (len > lrs.length())
                lrs = suffixes[i].substring(0, len);
        }
        return lrs;
    }

    public static void main(String[] args) {
        In in = new In("mobydick.txt");
        String s = in.readAll();
        String lrs = LRS.lrs(s);
        StdOut.println(lrs);
    }
}
