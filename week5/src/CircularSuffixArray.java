import java.util.Arrays;

public class CircularSuffixArray {
    private final int N;
    private final String s;
    private final int[] index;

    private class Substring implements Comparable<Substring> {
        int offset;

        Substring(int offset) { this.offset = offset; }

        public int compareTo(Substring that) {
            for (int i = this.offset, j = that.offset, k = 0; k < N; i++, j++, k++) {
                if (i >= N) i %= N;
                if (j >= N) j %= N;
                if (s.charAt(i) < s.charAt(j)) return -1;
                else if (s.charAt(i) > s.charAt(j)) return 1;
            }
            return 0;
        }
    }

    public CircularSuffixArray(String s) {
        if (s == null) throw new IllegalArgumentException("String s can't be null.");

        this.N = s.length();
        this.s = s;

        Substring[] index = new Substring[N];
        for (int i = 0; i < N; i++)
            index[i] = new Substring(i);
        Arrays.sort(index);

        this.index = new int[N];
        for (int i = 0; i < N; i++)
            this.index[i] = index[i].offset;
    }

    public int length() {
        return N;
    }

    public int index(int i) {
        if (i < 0 || i >= N) throw new IllegalArgumentException("out of bounds");
        return index[i];
    }

    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray abra = new CircularSuffixArray(s);
        assert abra.length() == s.length();
        assert abra.index(0) == 11;
        assert abra.index(1) == 10;
        assert abra.index(2) == 7;
        assert abra.index(3) == 0;
        assert abra.index(4) == 3;
        assert abra.index(5) == 5;
        assert abra.index(6) == 8;
        assert abra.index(7) == 1;
        assert abra.index(8) == 4;
        assert abra.index(9) == 6;
        assert abra.index(10) == 9;
        assert abra.index(11) == 2;
    }
}
