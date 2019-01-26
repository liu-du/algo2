import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLength {

    private final static int R = 256;
    private final static int lgR = 8;

    public static void compress() {
        boolean bit = false;
        char count = 0;
        while (!BinaryStdIn.isEmpty()) {
            while (!BinaryStdIn.isEmpty() && bit == BinaryStdIn.readBoolean() && count < R - 1) {
                count++;
            }
            BinaryStdOut.write(count);
            bit = !bit;
            count = 1;
        }
        BinaryStdOut.close();
    }

    public static void expand() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(lgR);
            for (int i = 0; i < run; i++) {
                BinaryStdOut.write(bit);
            }
            bit = !bit;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
