/* *****************************************************************************
 *  Name: Vishesh Tayal
 *  Date: 30/03/2019
 *  Description: A Faster Algorithimic approach to find collinear points
 *  in a Given plane with points.
 *  Computer Vision: Pattern Recognition
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    ArrayList<LineSegment> allsegs = new ArrayList<LineSegment>();

    /**
     * The main objective of the following class
     * is to sort an copy of the given array
     * and sort the copy of the sorted array in a loop
     * WRT to a point, considering the point to be from the sorted array
     * so that if the points makes to form a linesegment the point that we sorted the array WRT
     * is minimum and the last point considering is maximum
     */
    public FastCollinearPoints(Point[] points) {
        Point pointsCpy[] = points.clone(); // cloning this array and sorting it.
        Arrays.sort(pointsCpy);
        int index = 0;
        for (int i = 0; i < points.length;  i++) {
            Point p = pointsCpy[i];
            Point[] pointBySlope = pointsCpy.clone();
            Arrays.sort(pointBySlope, p.slopeOrder());

            int x = 1;

            while (x < points.length) {
                LinkedList<Point> candy = new LinkedList<Point>();
                final double SLOPE_REF = p.slopeTo(pointBySlope[x]);
                do {
                    candy.add(pointBySlope[x++]);
                } while (x < points.length && p.slopeTo(pointBySlope[x]) == SLOPE_REF);

                if (candy.size() >= 3 && p.compareTo(candy.peek()) < 0) {
                    Point min = p;
                    Point max = candy.removeLast();
                    allsegs.add(new LineSegment(min, max));
                }
            }


        }

    }

    public int numberOfSegments() {

        return allsegs.size();
    }

    public LineSegment[] segments() {
        LineSegment[] ll = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment a : allsegs) {
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
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        }


}
