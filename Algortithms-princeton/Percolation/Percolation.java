/* *****************************************************************************
 *  Name: Vishesh Tayal (unicorn-io)
 *  Description: Percolation back-end.
 *               Uses the Dynamic Connectivity (Weighted Quick Union Find) API
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF mainObject;
    // Grid to check open and closed site i.e, each time something happens this grid is updated.
    private boolean[][] grid; 

    private int vsite1;
    private int vsite2;

    private int numberOfOpenSites = 0;
    private int size; // One-dimensional input size.

    /**
     *             Constructor: Contructs a plot of size N x N.
     * 
     * @param n    The N x N size parameter.
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Bad Input");
        else {
            size = n;
            // mainObject includes the vsite1 and vsite2 hence it is 2 nos. greater.
            mainObject = new WeightedQuickUnionUF(n * n + 2);

            grid = new boolean[n][n];
            vsite1 = 0;
            vsite2 = n*n+1;
        }
    }

    /**
     *               Opens a site in the grid and let it percolate
     *               The main logic lies Here,
     *               The top site is connected to vsite1 (virtual site)
     *               The botton site is connected to vsite2 (virtual site)
     *               The problem structure: vsite1 --> grid --> vsite2
     *               if the vsite1 is connected to vsite2 then the system percolates.
     * 
     * @param row    Row value of opening site.
     * @param col    Column value of opening site.
     */
    public void open(int row, int col) {
        if (isInBounds(row-1, col-1)) {
        if (isOpen(row, col)) return;
        if (row == 1) {
            mainObject.union(vsite1, coordinateConvertFrom(row, col));
        }
        if (row == size) {
            mainObject.union(vsite2, coordinateConvertFrom(row, col));
        }
        if (isInBounds(row - 1, col) && isOpen(row, col + 1)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row, col + 1));
        }
        if (isInBounds(row - 1, col - 2) && isOpen(row, col - 1)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row, col - 1));
        }
        if (isInBounds(row, col - 1) && isOpen(row + 1, col)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row + 1, col));
        }
        if (isInBounds(row - 2, col - 1) && isOpen(row - 1, col)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row - 1, col));
        }
            grid[row - 1][col - 1] = true;
        numberOfOpenSites++;
    }
    else {
        throw new java.lang.IllegalArgumentException("Bad Coordinates");
        }

    }

    /**
     *               Checks Whether a site is open or close.
     * 
     * @param row    row value of the site.
     * @param col    col value of the site.
     * @return       True if the site is open else false.
     */
    public boolean isOpen(int row, int col) {
        if (isInBounds(row-1, col-1)) {
            return grid[row-1][col-1];
        }
        else {
            throw new java.lang.IllegalArgumentException("Bad Coordinates");
        }
    }

    /**
     *               Checks if water has reached this particular site.
     * 
     * @param row    The row value of "to be checked" site.
     * @param col    The column value of "to be checked" site.
     * @return       True if water has reached else false.
     */
    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            return mainObject.connected(vsite1, coordinateConvertFrom(row, col));
        }
        return false;
    }

    /**
     *            Gives the number of open sites.
     * 
     * @return    The number of open sites.
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     *            Checks whether two sites are connected.
     * 
     * @return    True if two sites percolate/Connected else false.  
     */
    public boolean percolates() {
        return mainObject.connected(vsite1, vsite2);
    }

    /**
     *             Converts a 2D Array Coordinate to a 1D Array Index.
     * 
     * @param i    The row value.
     * @param j    The column value.
     * @return     The converts 2D Coordinate value of the row column pair.
     */
    private int coordinateConvertFrom(int i, int j) {
        return size * (i - 1) + j;
    }

    /**
     *             Checks if the given coordinate is within the problem grid.
     * 
     * @param i    The row value.
     * @param j    The column value.
     * @return     True if the row column pair is within the problem Boundaries else false.
     */
    private boolean isInBounds(int i, int j)
    {
        if (i < 0 || i > size-1 || j < 0 || j > size-1)
        {
            return false;
        }
        return true;
    }
}
