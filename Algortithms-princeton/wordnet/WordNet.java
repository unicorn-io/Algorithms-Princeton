/* *****************************************************************************
 *  Name: Vishesh Tayal
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class WordNet {

    private final HashMap<Integer, String> map;
    private final HashMap<String, Set<Integer>> ultMap;
    private final Digraph digraph;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();
        map = new HashMap<>();
        ultMap = new HashMap<>();
        In scn = new In(synsets);
        In hyp = new In(hypernyms);
        while (scn.hasNextLine()) {
            String[] line = scn.readLine().split(",");
            String[] netArr = line[1].split(" ");
            int id = Integer.parseInt(line[0]);
            //  there may be less number of words than expeceted add a checker
            map.put(id, line[1]);
            for (String s : netArr) {
                if (!ultMap.containsKey(s)) ultMap.put(s, new HashSet<>());
                ultMap.get(s).add(id);
            }
        }

        this.digraph = new Digraph(map.size());

        while (hyp.hasNextLine()) {
            String[] line = hyp.readLine().split(",");
            for (int i = 1; i < line.length; i++) {
                digraph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
            }
        }

        boolean isDAG = false;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) isDAG = true;
        }
        if(!isDAG) throw new IllegalArgumentException();
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return ultMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return ultMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        Iterable<Integer> ita = ultMap.get(nounA);
        Iterable<Integer> itb = ultMap.get(nounB);
        BreadthFirstDirectedPaths g1, g2;
        g1 = new BreadthFirstDirectedPaths(digraph, ita);
        g2 = new BreadthFirstDirectedPaths(digraph, itb);

        int result = Integer.MAX_VALUE;
        for (int a : map.keySet()) {
            if (g1.hasPathTo(a) && g2.hasPathTo(a)) {
                int dist = g1.distTo(a) + g2.distTo(a);
                result = Math.min(result, dist);
            }
        }
        return result;
    }

    // @return a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

        Iterable<Integer> ita = ultMap.get(nounA);
        Iterable<Integer> itb = ultMap.get(nounB);
        BreadthFirstDirectedPaths g1, g2;
        g1 = new BreadthFirstDirectedPaths(digraph, ita);
        g2 = new BreadthFirstDirectedPaths(digraph, itb);

        int result = Integer.MAX_VALUE;
        int index = -1;
        for (int a : map.keySet()) {
            if (g1.hasPathTo(a) && g2.hasPathTo(a)) {
                int dist = g1.distTo(a) + g2.distTo(a);
                if (dist < result) {
                    result = dist;
                    index = a;
                }
            }
        }
        return map.get(index);

    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        //        1633ms is consumed
        WordNet word = new WordNet("synsets.txt", "hypernyms300K.txt");
        long cur = System.currentTimeMillis();
        StdOut.println("Total time used is: "+(cur-begin));
        StdOut.println(word.sap("two-grain_spelt", "Old_World_chat"));
        StdOut.println(word.distance("two-grain_spelt", "Old_World_chat"));
        //        StdOut.println(word.distance("municipality", "region"));
        //        StdOut.println(word.distance("Black_Plague", "black_marlin"));
        //        StdOut.println(word.distance("American_water_spaniel", "histology"));
        //        StdOut.println(word.distance("Brown_Swiss", "barrel_roll"));
        //        StdOut.println(word.sap("Brown_Swiss", "barrel_roll"));
        //        StdOut.println(word.sap("European_magpie", "brace_wrench"));
        //        5ms is useds
        StdOut.println("Total time used is: "
                               + (System.currentTimeMillis()-cur));
        return;
    }
}
