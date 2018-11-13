import edu.princeton.cs.algs4.StdOut;

public class KeyIndexCounting {
    public static void count(int[] a, int R) {
       int N = a.length;
       int[] count = new int[R + 1];
       int[] aux = new int[N];

       for (int i = 0; i < N; i++)
           count[a[i] + 1]++;

       for (int r = 0; r < R; r++)
           count[r + 1] += count[r];

       for (int i = 0; i < N; i++)
           aux[count[a[i]]++] = a[i];

       for (int i = 0; i < N; i++)
           a[i] = aux[i];

    }

    public static void main(String[] args) {

        int[] a = new int[] {4,0,3,5,1};
        KeyIndexCounting.count(a, 6);
        for (int i = 0; i < a.length; i++)
            StdOut.print(a[i]);

        StdOut.println();

        int i = 3;
        StdOut.println(a[i++]); // a++ take the value of a first then increment a

        int j = 3;
        StdOut.println(a[++j]); // ++a increment a first then take the value of a

    }
}
