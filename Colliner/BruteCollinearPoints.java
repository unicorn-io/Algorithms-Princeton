/* *****************************************************************************
 *  Name: Vishesh Tayal
 *  Date: 29/03/2019
 *  Description: Computer Vision: Pattern Recognition,
 *  Brute force Method For finding the collinear points in a given plane of
 *  points.
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    ArrayList<LineSegment> allSegs = new ArrayList<LineSegment>();

    public BruteCollinearPoints(Point[] points) {

        Arrays.sort(points); // Sorts the data so that repetitive lines i.e, reverse lines are not counted.
        for (int i = 0; i < points.length - 3; i++) { // chooses an point from the sorted array
            for (int j = i; j < points.length - 2; j++) {
                Double testSlope = points[i].slopeTo(points[j]); // This is to compare the choosen point slope with a point and with others to collinearity
                    for (int k = j + 1; k < points.length - 1; k++) {
                        if (testSlope == points[i].slopeTo(points[k])    // This checks the slope Further and also wheather the new point is above the choosen point
                                && points[i].compareTo(points[k]) < 0) {
                            for (int l = k + 1; l < points.length; l++) {
                                if (testSlope == points[i].slopeTo(points[l])
                                        && points[i].compareTo(points[l]) < 0) { // This checks the slope Further and also wheather the new point is above the choosen point
                                    allSegs.add(new LineSegment(points[i], points[l]));
                                    // If the above condition is true just add the point
                                }
                            }
                        }
                    }
            }
        }

    }

   /*
    * Returns The number of Line Segments in The array list.
    */
    public int numberOfSegments() {
        return allSegs.size();
    }

    /**
     * Takes In A Array List if Line Segments and converts them to An Array
     * return an LineSegment Array
     */
    public LineSegment[] segments() {
        LineSegment[] ll = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment a : allSegs) {
            ll[i] = a;
            i++;
        }
        return ll;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
