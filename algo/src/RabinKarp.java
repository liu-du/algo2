import edu.princeton.cs.algs4.StdOut;
import java.math.BigInteger;
import java.util.Random;

// Monte Carlo version, doesn't guarantee correctness, but extremely unlikely
public class RabinKarp {
    private final static int R = 256;
    private final long Q;
    private final long patHash;
    private final int M;
    private long RM; // R ^ (M - 1)

    public RabinKarp(String pat) {
        M = pat.length();
        Q = BigInteger.probablePrime(32, new Random()).longValue();
        RM = 1;
        for (int i = 1; i <= M - 1; i++) RM = (RM * R) % Q;
        patHash = hash(pat, M);
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++) {
            h = (R * h + key.charAt(j)) % Q;
        }
        return h;
    }

    public int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        for (int i = M; i < N; i++) {
            if (patHash != txtHash) txtHash = (txtHash - txt.charAt(i) * RM) * R + txt.charAt(i + M);
            else                    return i;
        }
        return N;
    }

    public static void main(String[] args) {

        String pat = "haha";
        String txt = "fdsafsdofsajfdaoisfjadsfhahadfdsafasfdsafdsaofjsa";
        RabinKarp haha = new RabinKarp(pat);

        int i = haha.search(txt);
        StdOut.println(i);
        StdOut.println(txt.substring(i, i + pat.length()));

        StdOut.println(haha.Q);
    }
}
