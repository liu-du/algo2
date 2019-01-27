import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront {

    private static final int R = 256;

    public static void encode() {
        char[] seq = new char[R];
        for (int i = 0; i < R; i++)
            seq[i] = (char) i;

        char ch;
        while (!BinaryStdIn.isEmpty()) {
            ch = BinaryStdIn.readChar();
            int i = 0;
            while (seq[i] != ch) {
                i++;
            }
            BinaryStdOut.write((char) i);
            if (i > 0) {
                for (int j = i; j > 0; j--)
                    seq[j] = seq[j - 1];
                seq[0] = ch;
            }
        }
        BinaryStdOut.close();
    }

    public static void decode() {
        char[] seq = new char[R];
        for (int i = 0; i < R; i++)
            seq[i] = (char) i;

        int i;
        char swap;
        while (!BinaryStdIn.isEmpty()) {
            i = BinaryStdIn.readChar();
            BinaryStdOut.write(seq[i]);
            if (i > 0) {
                swap = seq[i];
                for (int j = i; j > 0; j--)
                    seq[j] = seq[j - 1];
                seq[0] = swap;
            }
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
       if (args[0].equals("-")) encode();
       else if (args[0].equals("+")) decode();
       else throw new IllegalArgumentException("must be - or +.");
    }
}
