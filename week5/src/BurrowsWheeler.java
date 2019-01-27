import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {
    public static void transform() {
        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty())
            sb.append(BinaryStdIn.readChar());
        String s = sb.toString();

        CircularSuffixArray csa = new CircularSuffixArray(s);
        for (int i = 0; i < csa.length(); i++)
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }

        int originalPosition;
        for (int i = 0; i < csa.length(); i++) {
            originalPosition = csa.index(i) - 1;
            if (originalPosition < 0) originalPosition = s.length() - 1;
            BinaryStdOut.write(s.charAt(originalPosition));
        }
        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();

        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty())
            sb.append(BinaryStdIn.readChar());
        char[] t = sb.toString().toCharArray();

        char[] firstCol = t.clone();
        Arrays.sort(firstCol);

        int[] next = new int[t.length];
        boolean[] marked = new boolean[t.length];

        for (int i = 0; i < t.length; i++)
            for (int j = 0; j < t.length; j++)
                if (firstCol[i] == t[j] && !marked[j]) {
                    next[i] = j;
                    marked[j] = true;
                    break;
                }


        for (int i = first, k = 0; k < t.length; i = next[i], k++) {
            BinaryStdOut.write(firstCol[i], 8);
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
       if (args[0].equals("-")) transform();
       else if (args[0].equals("+")) inverseTransform();
       else throw new IllegalArgumentException("must be - or +");
    }
}
