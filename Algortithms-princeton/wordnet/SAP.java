/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v >= digraph.V()) throw new IllegalArgumentException();
        if (w < 0 || w >= digraph.V()) throw new IllegalArgumentException();
        BreadthFirstDirectedPaths g1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths g2 = new BreadthFirstDirectedPaths(digraph, w);
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < digraph.V(); i++) {
            if (g1.hasPathTo(i) && g2.hasPathTo(i)) {
                result = Math.min(result, g1.distTo(i) + g2.distTo(i));
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
            if (v < 0 || v >= digraph.V()) throw new IllegalArgumentException();
            if (w < 0 || w >= digraph.V()) throw new IllegalArgumentException();
            BreadthFirstDirectedPaths g1 = new BreadthFirstDirectedPaths(digraph, v);
            BreadthFirstDirectedPaths g2 = new BreadthFirstDirectedPaths(digraph, w);
            int result = Integer.MAX_VALUE;
            int anc = -1;
            for (int i = 0; i < digraph.V(); i++) {
                if (g1.hasPathTo(i) && g2.hasPathTo(i)) {
                    int dist = g1.distTo(i) + g2.distTo(i);
                    if (dist < result) {
                        anc = i;
                        result = Math.min(result, dist);
                    }
                }
            }
            return result == Integer.MAX_VALUE ? -1 : anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        int result = Integer.MAX_VALUE;
        for (Integer a : v) {
            for (Integer b : w) {
                result = Math.min(length(a, b), result);
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null) throw new IllegalArgumentException();
        int result = Integer.MAX_VALUE;
        int anc = -1;
        for (Integer a : v) {
            for (Integer b : w) {
                int len = length(a, b);
                if (len < result) {
                    result = len;
                    anc = ancestor(a, b);
                }
            }
        }
        return result == Integer.MAX_VALUE ? -1 : anc;
    }


}
