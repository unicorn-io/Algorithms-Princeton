/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF mainObject;
    private WeightedQuickUnionUF fullObject;
    private boolean[][] grid;

    private int vsite1;
    private int vsite2;

    private int numberOfOpenSites = 0;
    private int size;

    public Percolation(int n) {
        if (n <= 0) { throw new IllegalArgumentException("Bad Input"); }
        else
        {
            size = n;
            mainObject = new WeightedQuickUnionUF(n*n+2);
            fullObject = new WeightedQuickUnionUF(n*n+1);
            grid = new boolean[n][n];
            vsite1 = 0;
            vsite2 = n*n+1;
        }
    }

    public void open(int row, int col) {
        if (isInBounds(row-1, col-1)) {
        if (isOpen(row, col)) {
            return;
        }
        if (row == 1) {
            mainObject.union(vsite1, coordinateConvertFrom(row, col));
            fullObject.union(coordinateConvertFrom(row, col), vsite1);
        }
        if (row == size) {
            mainObject.union(vsite2, coordinateConvertFrom(row, col));
        }
        if (isInBounds(row - 1, col) && isOpen(row, col + 1)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row, col + 1));
            fullObject.union(coordinateConvertFrom(row, col + 1), coordinateConvertFrom(row, col));
        }
        if (isInBounds(row - 1, col - 2) && isOpen(row, col - 1)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row, col - 1));
            fullObject.union(coordinateConvertFrom(row, col - 1), coordinateConvertFrom(row, col));
        }
        if (isInBounds(row, col - 1) && isOpen(row + 1, col)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row + 1, col));
            fullObject.union(coordinateConvertFrom(row + 1, col), coordinateConvertFrom(row, col));
        }
        if (isInBounds(row - 2, col - 1) && isOpen(row - 1, col)) {
            mainObject.union(coordinateConvertFrom(row, col), coordinateConvertFrom(row - 1, col));
            fullObject.union(coordinateConvertFrom(row - 1, col), coordinateConvertFrom(row, col));
        }
            grid[row - 1][col - 1] = true;
        numberOfOpenSites++;
    }
    else {
        throw new java.lang.IllegalArgumentException("Bad Coordinates");
        }

    }

    public boolean isOpen(int row, int col) {
        if (isInBounds(row-1, col-1)) {
            return grid[row-1][col-1];
        }
        else {
            throw new java.lang.IllegalArgumentException("Bad Coordinates");
        }
    }

    public boolean isFull(int row, int col) {
        if (isOpen(row, col)) {
            return fullObject.connected(vsite1, coordinateConvertFrom(row, col));
        }
        return false;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return mainObject.connected(vsite1, vsite2);
    }

    private int coordinateConvertFrom(int i, int j) {
        return size*(i-1)+(j);
    }

    private boolean isInBounds(int i, int j)
    {
        if (i < 0 || i > size-1 || j < 0 || j > size-1)
        {
            return false;
        }
        return true;
    }
}
