/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private final WordNet net;

    public Outcast(WordNet wordnet) {
        if (wordnet == null) throw new IllegalArgumentException();
        net = wordnet;
    }         // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException();
        int dist = Integer.MIN_VALUE;
        String toRet = null;
        for (String s : nouns) {
            int tmp = 0;
            for (String k : nouns) {
                tmp += net.distance(s, k);
            }
            if (tmp > dist) {
                toRet = s;
                dist = Math.max(tmp, dist);
            }
        }
        return toRet;
    }   // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }  // see test client below
}
