/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFINDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceHi;
    private final double confidenceLo;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        Percolation mainObject;
        final double[] openSitesPerTrial;
        final double size;
        final double t;
        openSitesPerTrial = new double[trials];
        int x, y, i = 0;
        size = n;
        t = trials;
        while (i < trials) {
            mainObject =  new Percolation(n);
            while (true) {
                x = StdRandom.uniform(n)+1;
                y = StdRandom.uniform(n)+1;
                if (mainObject.isOpen(x, y)) {
                    continue;
                }
                mainObject.open(x, y);
                if (mainObject.percolates()) {
                    openSitesPerTrial[i] = (double) (mainObject.numberOfOpenSites());
                    i++;
                    break;
                }
            }
        }
        mean = StdStats.mean(openSitesPerTrial)/(size*size);
        stddev = StdStats.stddev(openSitesPerTrial)/(size*size);
        confidenceLo = (mean - (CONFINDENCE_95 * stddev/Math.sqrt(t)));
        confidenceHi = (mean + (CONFINDENCE_95 * stddev/Math.sqrt(t)));
    }
    public double mean() {
        return mean;
    }
    public double stddev() {
        return stddev;
    }
    public double confidenceLo() {
        return confidenceLo;
    }
    public double confidenceHi()
    {
        return confidenceHi;
    }

    public static void main(String[] args) {
        if (args[0].length() > 10 || args[1].length() > 10) {
            throw new java.lang.IllegalArgumentException("cannot exceed limit of an integer");
        }
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        if (args.length != 2) {
            throw new java.lang.IllegalArgumentException("privide only two arguments n and t");
        }
        else {
            PercolationStats testObj = new PercolationStats(a, b);
            System.out.println("mean                    = " + testObj.mean() + "\n" +
                                       "stddev                  = " + testObj.stddev() + "\n" +
                                       "95% confidence interval = [" + testObj.confidenceLo()
                                       + ", " + testObj.confidenceHi() + "]");
        }
    }
}
