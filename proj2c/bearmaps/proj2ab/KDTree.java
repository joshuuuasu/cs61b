package bearmaps.proj2ab;

import java.util.List;

public class KDTree implements PointSet {

    private Node root;

    private class Node {
        private Point point;
        private boolean horizontal;
        private Node leftChild, rightChild;

        Node(Point p, boolean b) {
            point = p;
            horizontal = b;
            leftChild = null;
            rightChild = null;
        }

        public double compareTo(Node n) {
            if (horizontal) {
                return point.getX() - n.point.getX();
            } else {
                return point.getY() - n.point.getY();
            }
        }
    }

    private void put(Node n, Point p) {
        Node temp = new Node(p, !n.horizontal);
        double cmp = n.compareTo(temp);
        if (cmp > 0) {
            if (n.leftChild == null) {
                n.leftChild = temp;
            } else {
                put(n.leftChild, p);
            }
        } else {
            if (n.rightChild == null) {
                n.rightChild = temp;
            } else {
                put(n.rightChild, p);
            }
        }
    }

    public KDTree(List<Point> points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        root = new Node(points.get(0), true);
        for (int i = 1; i < points.size(); i++) {
            put(root, points.get(i));
        }
    }

    private Node nearest(Node n, Point goal, Node best) {
        if (n == null) {
            return best;
        }
        if (Point.distance(n.point, goal) < Point.distance(best.point, goal)) {
            best = n;
        }
        Node temp = new Node(goal, !n.horizontal);
        Node good, bad;
        if (n.compareTo(temp) > 0) {
            good = n.leftChild;
            bad = n.rightChild;
        } else {
            good = n.rightChild;
            bad = n.leftChild;
        }
        best = nearest(good, goal, best);
        double disToBad = n.compareTo(temp);
        if (disToBad * disToBad < Point.distance(best.point, goal)) {
            best = nearest(bad, goal, best);
        }
        return best;
    }

    @Override
    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearest(root, goal, root).point;
    }
}
