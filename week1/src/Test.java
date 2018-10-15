import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        Integer[] a = {1,2,null,3};

        Iterable<Integer> b = Arrays.asList(a);
        for (Integer i : b) {// for (int i : b) gives NullPointerException
            StdOut.println(i);
        }
    }
}
