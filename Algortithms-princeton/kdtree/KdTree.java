/* *****************************************************************************
 *  Name: Vishesh Tayal
 *  Date: 04/07/2020
 *  Description: -/-
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private class Node {
        public final Point2D key;
        public Node left;
        public Node right;
        public boolean isHorizontal = false;

        public Node(Point2D key, boolean isEven) {
            this.key = key;
            this.isHorizontal = isEven;
        }
    }

    private Node root;
    private int size;
    private final RectHV board;

    /**
     * Construct an empty Tree
     */
    public KdTree() {
        size = 0;
        root = null;
        board = new RectHV(0, 0, 1.0, 1.0);
    }

    /**
     * @return true if tree is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return number of points in the tree
     */
    public int size() {
        return size;
    }

    /**
     * add the point to the tree (if it is not already in the tree)
     *
     * @param p the point
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (contains(p)) return;
        root = insert(p, root, false);
    }

    private Node insert(Point2D p, Node x, boolean isHorizontal) {
        if (x == null) {
            size++;
            return new Node(p, isHorizontal);
        }
        if (p.compareTo(x.key) == 0) {
            return x;
        }
        else if ((x.isHorizontal && p.y() <= x.key.y()) || (!x.isHorizontal && p.x() <= x.key.x())) {
            x.left = insert(p, x.left, !isHorizontal);
        } else {
            x.right = insert(p, x.right, !isHorizontal);
        }
        return x;
    }

    /**
     * @param p the point
     * @return true if the tree contains point p
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Node tmp = root;
        while (tmp != null) {
            if (p.compareTo(tmp.key) == 0) return true;
            else if (tmp.isHorizontal && p.y() <= tmp.key.y() || !tmp.isHorizontal && p.x() <= tmp.key.x()) tmp = tmp.left;
            else tmp = tmp.right;
        }
        return false;
    }

    /**
     * Draw all the points to Standard Draw
     */
    public void draw() {
        StdDraw.setScale(0, 1);
        draw(root, board);
    }

    private void draw(Node x, RectHV rect) {
        if (x != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            new Point2D(x.key.x(), x.key.y()).draw();
            StdDraw.setPenRadius();

            if (!x.isHorizontal) {
                StdDraw.setPenColor(StdDraw.RED);
                new Point2D(x.key.x(), rect.ymin()).drawTo(new Point2D(x.key.x(), rect.ymax()));
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                new Point2D(rect.xmin(), x.key.y()).drawTo(new Point2D(rect.xmax(), x.key.y()));
            }
            draw(x.left, leftDownRect(rect, x));
            draw(x.right, rightUpRect(rect, x));
        }
    }

    private RectHV leftDownRect(RectHV rect, Node x) {
        if (!x.isHorizontal) {
            return new RectHV(rect.xmin(), rect.ymin(), x.key.x(), rect.ymax());
        } else return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), x.key.y());
    }

    private RectHV rightUpRect(RectHV rect,  Node x) {
        if (!x.isHorizontal) {
            return new RectHV(x.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else return new RectHV(rect.xmin(), x.key.y(), rect.xmax(), rect.ymax());
    }

    /**
     * @param rect the rectangle
     * @return A Queue containing all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> pointsIn = new Queue<>();
        get(root, board, rect, pointsIn);
        return pointsIn;
    }

    private Point2D nearestNeigh(Node node, RectHV rect, Point2D point, Point2D nearPoint) {
        Point2D nearestPoint = nearPoint;
        if (node != null) {
            if (nearestPoint == null || nearestPoint.distanceSquaredTo(point) > rect.distanceSquaredTo(point)) {
                if (nearestPoint == null) {
                    nearestPoint = node.key;
                } else {
                    if (node.key.distanceSquaredTo(point) < nearestPoint.distanceSquaredTo(point)) {
                        nearestPoint = node.key;
                    }
                }

                if (!node.isHorizontal) {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                    RectHV rightRect = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
                    if (point.x() <= node.key.x()) {
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                    }
                } else {
                    RectHV leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                    RectHV rightRect = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
                    if (point.y() <= node.key.y()) {
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                    } else {
                        nearestPoint = nearestNeigh(node.right, rightRect, point, nearestPoint);
                        nearestPoint = nearestNeigh(node.left, leftRect, point, nearestPoint);
                    }
                }
            }
        }
        return nearestPoint;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return nearestNeigh(root, board, p, null);
    }

    private void get(Node x, RectHV nodeRect, RectHV rect, Queue<Point2D> q) {
        if (x != null) {
            if (rect.intersects(nodeRect)) {
                if (rect.contains(x.key)) q.enqueue(x.key);
            }
            get(x.left, leftDownRect(nodeRect, x), rect, q);
            get(x.right, rightUpRect(nodeRect, x), rect, q);
        }
    }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        In in = new In("input10.txt");
        for (int i = 0; i < 10; i++) {
            tree.insert(new Point2D(in.readDouble(), in.readDouble()));
        }
        System.out.println(tree.range(new RectHV(0.378, 0.41, 0.895, 0.615)));
    }
}
