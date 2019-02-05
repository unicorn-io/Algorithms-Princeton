import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> allSegments = new ArrayList<LineSegment>();
    private LineSegment a;


    public BruteCollinearPoints(Point[] points)  {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("Bad Input!");
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("Bad Array Of Values");
            }
            try {
                if (points[i].compareTo(points[i + 1]) == 0) {
                    throw new java.lang.IllegalArgumentException("Nah!");
                }
            } catch (java.lang.IndexOutOfBoundsException en) {}
        }
        Arrays.sort(points);
        int n = points.length;
        for (int i = 0; i < n-3; i++)
            for (int j = i + 1; j < n-2; j++)
                for (int k = j + 1; k < n-1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k]))
                        continue;
                    for (int l = k + 1; l < n; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) &&
                                points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))
                            allSegments.add(new LineSegment(points[i], points[l]));
                    }
                }
    }   // finds all line segments containing 4 points


    public int numberOfSegments() {
        return allSegments.size();
    }       // the number of line segments


    public LineSegment[] segments() {
        LineSegment[] returner = allSegments.toArray(new LineSegment[allSegments.size()]);
        return returner;
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
