import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    /**
     *                  Sorts the array w.r.t a point and now checks for the slope minimums
     *                  to calculate the collinearity with the points.
     *  
     * @param points    The points array to be searched on.
     */
    public FastCollinearPoints(Point[] points) {
        checkException(points);
        ArrayList<LineSegment> storeSegments = new ArrayList<LineSegment>();
        Point[] copyPoints = Arrays.copyOf(points, points.length);
        for (Point point : points) {
            Arrays.sort(copyPoints, point.slopeOrder());
            double tmpSlope = point.slopeTo(copyPoints[0]);
            int count = 1;
            int i;
            for (i = 1; i < copyPoints.length; i++) {
                if (point.slopeTo(copyPoints[i]) == tmpSlope) {
                    count++;
                    continue;
                } else {
                    if (count >= 3) {
                        Arrays.sort(copyPoints, i - count, i);
                        if (point.compareTo(copyPoints[i - count]) < 0)
                            storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
                    }
                    tmpSlope = point.slopeTo(copyPoints[i]);
                    count = 1;
                }
            }
            if (count >= 3) {
                Arrays.sort(copyPoints, i - count, i);
                if (point.compareTo(copyPoints[i - count]) < 0)
                    storeSegments.add(new LineSegment(point, copyPoints[i - 1]));
            }
        }
        lineSegments = storeSegments.toArray(new LineSegment[storeSegments.size()]);
    }

    /**
     * @return    number of line segments.
     */
    public int numberOfSegments() {
        return lineSegments.length;
    }

    /**
     * @return    The array of line segments.
     */
    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    /**
     *                  Checks for data validity.
     * 
     * @param points    The points array to be checked.
     */
    private void checkException(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null)
                throw new java.lang.NullPointerException();
            for (int j = i + 1; j < points.length; j++) {
                if (points[j] == null)
                    throw new java.lang.NullPointerException();
                if (points[i].compareTo(points[j]) == 0)
                    throw new java.lang.IllegalArgumentException();
            }
        }
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
        //        StdDraw.enableDoubleBuffering();
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