import edu.princeton.cs.algs4.StdOut;

// Most Significant Digit first radix sort
public class MSD {

    public static int charAt(String s, int d) {
        if (d >= s.length()) return -1;
        else return s.charAt(d);
    }

    public static void sort(String[] a) {
        String[] aux = new String[a.length];
        sort(a, aux, 0, a.length - 1, 0);
    }

    public static void sort(String[] a, String[] aux, int lo, int hi, int d) {
        if (hi <= lo) return;

        int R = 256;
        int[] count = new int[R + 2];

        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;
        for (int i = 0; i < R + 1; i++)
            count[i + 1] += count[i];
        for (int i = lo; i <= hi; i++)
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        for (int i = 0; i < R; i++) {
            sort(a, aux, lo + count[i], lo + count[i + 1] - 1, d + 1);
        }
    }

    public static void main(String[] args) {
        String[] a = new String[] {"dab", "cab", "cds", "acs", "bac", "dds", "abd", "bcb", "cde", "cbe"};
        MSD.sort(a);
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
