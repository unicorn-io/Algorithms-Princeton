

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Solver {

    private int numOfMoves;
    private final Board initial;
    private ArrayList<Board> pathway;

    private class searchNode implements Comparable<searchNode> {
        public Board board;
        private int numMoves;
        public searchNode prev;
        private int manPrior;
        private int hamPrior;

        public int compareTo(searchNode o2) {
            if (this.manPrior > o2.manPrior) return 1;
            else if (this.manPrior < o2.manPrior) return -1;
            else if (this.hamPrior > o2.hamPrior) return 1;
            else if (this.hamPrior < o2.hamPrior) return -1;
            else if (this.manPrior - this.numMoves > o2.manPrior - o2.numMoves) return 1;
            else if (this.manPrior - this.numMoves < o2.manPrior - o2.numMoves) return -1;
            else if (this.hamPrior - this.numMoves > o2.hamPrior - o2.numMoves) return 1;
            else if (this.hamPrior - this.numMoves < o2.hamPrior - o2.numMoves) return -1;
            return 1;
        }

        public searchNode(Board b, int moves, searchNode node) {
            this.board = b;
            this.numMoves = moves;
            this.prev = node;
            this.manPrior = b.manhattan() + moves;
            this.hamPrior = b.hamming() + moves;
        }



    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        pathway = new ArrayList<Board>();
        numOfMoves = 0;
        MinPQ<searchNode> tree = new MinPQ<searchNode>();
        if(isSolvable()) {
            tree.insert(new searchNode(initial, numOfMoves, null));
            searchNode intil = null;
            do {
                intil = tree.delMin();
                pathway.add(intil.board);
                for (Board b : intil.board.neighbours()) {
                    if (!b.equals(intil.prev.board)) {
                        tree.insert(new searchNode(b, ++numOfMoves, intil));
                    }
                }
            } while (!intil.board.isGoal());
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        int mvs = 0;
        int mvsTwin = 0;
        int decide = 0;
        MinPQ<searchNode> tree = new MinPQ<searchNode>();
        MinPQ<searchNode> treeTwin = new MinPQ<searchNode>();
        Board intil = initial;
        Board intilTwin = initial.twin();
        if (intil.isGoal()) return true;
        tree.insert(new searchNode(intil, mvs, null));
        treeTwin.insert(new searchNode(intilTwin, mvsTwin, null));
        searchNode k1 = null;
        searchNode k2 = treeTwin.min();
        do {
            if (decide % 2 == 0) {
                k1 = tree.delMin();
                mvs++;
                for (Board b : k1.board.neighbours()) {
                    if (mvs == 1) {
                        tree.insert(new searchNode(b, mvs, k1));
                    } else if (!b.equals(k1.prev.board)) {
                            tree.insert(new searchNode(b, mvs, k1));
                    }
                }
            } else {
                k2 = treeTwin.delMin();
                mvsTwin++;
                for (Board b : k2.board.neighbours()) {
                    if (mvsTwin == 1) {
                        tree.insert(new searchNode(b, mvsTwin, k2));
                    } else if (!b.equals(k2.prev.board)) {
                        tree.insert(new searchNode(b, mvsTwin, k2));
                    }
                }
            }
            decide++;
        } while (!k1.board.isGoal() || !k2.board.isGoal());
        if (decide % 2 == 0) return true;
        return false;
    }

    // min number of moves to solve initial board
    public int moves() {
        return numOfMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return pathway;
    }

    // test client (see below)
    public static void main(String[] args) {
            // create initial board from file
            In in = new In(args[0]);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);

            // solve the puzzle
            Solver solver = new Solver(initial);

            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
    }

}
