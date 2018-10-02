import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wn;

    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    public String outcast(String[] nouns) {

        int currentMax = -1;
        int currentIdx = -1;
        for (int i = 0; i < nouns.length; i++) {
            int sum = 0;
            for (int j = 0; j < nouns.length; j++) {
                sum = sum + wn.distance(nouns[i], nouns[j]);
            }
            if (sum > currentMax) {
                currentMax = sum;
                currentIdx = i;
            }
        }
        return nouns[currentIdx];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
