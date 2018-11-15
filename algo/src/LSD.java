import edu.princeton.cs.algs4.StdOut;

// Least Significant Digit first radix sort
public class LSD {
    public static void sort(String[] a, int W) {
        int R = 256;
        int N = a.length;
        String aux[] = new String[N];

        for (int d = W - 1; d >=0; d--) {
            int[] count = new int[R + 1];
            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            for (int i = 0; i < N; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {

        // automatically cast 'a' to ascii code value
        StdOut.println('a' + 1);

        // can index through ascii code number
        for (char c = 'a'; c <= 'z'; c++) {
            StdOut.println(c);
        }

        // cast char to int gives ascii code number
        StdOut.println((int) '1');
        StdOut.println((int) 'a');

        // Character.getNumericValue gives 0 to 9 for '0' to '9', 10 to 35 for 'a' to 'Z'
        // -1 for other chars? not sure
        StdOut.println(Character.getNumericValue('0'));
        StdOut.println(Character.getNumericValue('9'));
        StdOut.println(Character.getNumericValue('a'));
        StdOut.println(Character.getNumericValue('Z'));
        StdOut.println(Character.getNumericValue('@'));

        // LSD
        String[] a = new String[] {"dab", "cab", "cds", "acs", "bac", "dds", "abd", "bcb", "cde", "cbe"};
        LSD.sort(a, 3);
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
}
