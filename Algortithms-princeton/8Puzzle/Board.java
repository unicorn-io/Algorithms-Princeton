import java.util.Arrays;
import java.util.ArrayList;

/**
 * The type Board.
 */
public class Board {


    private int[][] board;
    private int hamming;
    private int manhattan;
    private int[][] boardR;
    private int[][] boardL;
    private int[][] boardU;
    private int[][] boardB;
    private int[][] twinB;
    private int zerPosX;
    private int zerPosY;

    /**
     * Creates a Board from an n by n array of tiles,
     * While tiles[row][col] = tile at (row, col)
     *
     * @param tiles The board Tiles
     */
    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles.length];
        boardR = new int[tiles.length][tiles.length];
        boardL = new int[tiles.length][tiles.length];
        boardU = new int[tiles.length][tiles.length];
        boardB = new int[tiles.length][tiles.length];
        twinB = new int[tiles.length][tiles.length];
        int pos = 1;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                board[i][j] = tiles[i][j];
                boardR[i][j] = tiles[i][j];
                boardL[i][j] = tiles[i][j];
                boardU[i][j] = tiles[i][j];
                boardB[i][j] = tiles[i][j];
                twinB[i][j] = tiles[i][j];
                if (tiles[i][j] != pos++ && tiles[i][j] != 0) {
                    hamming++;
                    manhattan = Math.abs(i - toRow(board[i][j]));
                    manhattan += Math.abs(j - toCol(board[i][j]));
                }
                if (tiles[i][j] == 0) {
                    zerPosX = i;
                    zerPosY = j;
                }
            }
        }

    }

    /**
     * @return String Representation of the Board
     */
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(dimension());
        boardString.append("\n");
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                boardString.append(" ");
                boardString.append(board[i][j]);
                boardString.append(" ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    /**
     * @return Dimensions of the board.
     */
    public int dimension() {
        return board.length;
    }

    /**
     * Hamming distance is number of blocks in wrong place.
     *
     * @return Hamming distance of the board.
     */
    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.equals(this.board, that.board)) return false;
        return true;
    }

    public Iterable<Board> neighbours() {
        ArrayList neighs = new ArrayList<Board>();
        try {
            exch(zerPosX, zerPosY + 1, boardR);
            neighs.add(new Board(boardR));
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) { }
        try {
            exch(zerPosX, zerPosY - 1, boardL);
            neighs.add(new Board(boardL));
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) { }
        try {
            exch(zerPosX - 1, zerPosY, boardU);
            neighs.add(new Board(boardU));
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) { }
        try {
            exch(zerPosX + 1, zerPosY, boardB);
            neighs.add(new Board(boardB));
        } catch (java.lang.ArrayIndexOutOfBoundsException ex) { }
        return neighs;
    }


    public Board twin() {
        int tmp = twinB[0][0];
        twinB[0][0] = twinB[0][1];
        twinB[0][1] = tmp;
        return new Board(twinB);
    }

    private int toRow(int indx) {
        return (int) Math.floor(indx/dimension());
    }

    private int toCol(int indx) {
        return indx % dimension();
    }

    private void exch(int a, int b, int[][] neigh) {
        int temp = neigh[a][b];
        neigh[a][b] = neigh[zerPosX][zerPosY];
        neigh[zerPosX][zerPosY] = temp;
    }

    public static void main(String[] args) {
        int[][] k = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board test = new Board(k);
        System.out.println(test.toString());
        for (Board b : test.neighbours()) {
            System.out.println(b.toString());
        }
        System.out.println(test.isGoal());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
    }
}
