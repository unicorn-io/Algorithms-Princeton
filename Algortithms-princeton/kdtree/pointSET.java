/* *****************************************************************************
 *  Name: Vishesh Tayal
 *  Date: 07/04/2020
 *  Description: -/-
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

public class PointSET {

    private final TreeSet<Point2D> set;

    /**
     * Construct an empty set of Points
     */
    public PointSET() {
        set = new TreeSet<>();
    }

    /**
     * @return true if set empty
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * @return number of points in the SET
     */
    public int size() {
        return set.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     *
     * @param p the point
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    /**
     * @param p the point
     * @return true if the SET contains point p
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    /**
     * Draw all the points to Standard Draw
     */
    public void draw() {
        for (Point2D point2D : set) {
            point2D.draw();
        }
    }

    /**
     * @param rect the rectangle
     * @return A Queue containing all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        for (Point2D point2D : set) {
            if (rect.contains(point2D)) queue.enqueue(point2D);
        }
        return queue;
    }

    /**
     * @param p the point
     * @return a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D tmp = null;
        double min = Double.POSITIVE_INFINITY;
        for (Point2D point2D : set) {
            double dist = p.distanceSquaredTo(point2D);
            if (min >= dist) {
                tmp = point2D;
                min = dist;
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        PointSET pointSET = new PointSET();

        System.out.println(pointSET.nearest(new Point2D(1, 1)));
    }

}
