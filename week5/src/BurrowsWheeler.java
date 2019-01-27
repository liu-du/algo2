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

    private static class Comp implements Comparable<Comp> {
        int i;
        char ch;

        public Comp (char ch, int i) {
            this.i = i;
            this.ch = ch;
        }

        public int compareTo(Comp that) {
            if (this.ch < that.ch) return -1;
            else if (this.ch > that.ch) return 1;
            else return 0;
        }
    }

    public static void inverseTransform() {

        int first = BinaryStdIn.readInt();

        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty())
            sb.append(BinaryStdIn.readChar());

        int N = sb.length();
        Comp[] next = new Comp[N];
        for (int i = 0; i < N; i++)
            next[i] = new Comp(sb.charAt(i), i);

        Arrays.sort(next);

        for (int i = first, k = 0; k < N; i = next[i].i, k++)
            BinaryStdOut.write(next[i].ch, 8);
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
       if (args[0].equals("-")) transform();
       else if (args[0].equals("+")) inverseTransform();
       else throw new IllegalArgumentException("must be - or +");
    }
}
