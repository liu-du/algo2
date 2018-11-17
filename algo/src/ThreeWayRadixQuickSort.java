import edu.princeton.cs.algs4.StdOut;

public class ThreeWayRadixQuickSort {

    private static int charAt(String s, int d) {
        if (d >= s.length()) return -1;
        else return s.charAt(d);
    }

    private static void exch(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void sort(String[] a) {
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi <= lo) return;
        int lt = lo, gt = hi;
        int v = charAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(a[i], d);
            if      (t < v) exch(a, lt++, i++);
            else if (t > v) exch(a, i, gt--);
            else            i++;
        }

        sort(a, lo, lt - 1, d);
        if (v >= 0) sort(a, lt, gt, d + 1);
        sort(a, gt + 1, hi, d);
    }

    public static void main(String[] args) {
        String[] a = new String[] {"dab", "cab", "cds", "acs", "bac", "dds", "abd", "bcb", "cde", "cbe"};
        ThreeWayRadixQuickSort.sort(a);
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

}
