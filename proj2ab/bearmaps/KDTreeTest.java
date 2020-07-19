package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class KDTreeTest {

    @Test
    public void sanityTest() {
        Point p1 = new Point(2.0, 3.0);
        Point p2 = new Point(4.0, 2.0);
        Point p3 = new Point(4.0, 5.0);
        Point p4 = new Point(3.0, 3.0);
        Point p5 = new Point(1.0, 5.0);
        Point p6 = new Point(4.0, 4.0);

        KDTree kd = new KDTree(List.of(p1, p2, p3, p4, p5 ,p6));
        assertEquals(1.0, kd.nearest(0.0, 7.0).getX(), 0.000001);
        assertEquals(5.0, kd.nearest(0.0, 7.0).getY(), 0.000001);
    }

    @Test
    public void randomizedTest() {
        Random rd = new Random(5201314);
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            points.add(new Point(rd.nextDouble(), rd.nextDouble()));
        }

        NaivePointSet nps = new NaivePointSet(points);
        KDTree kdt = new KDTree(points);

        for (int i = 0; i < 10000; i++) {
            double x = rd.nextDouble();
            double y = rd.nextDouble();
            Point expected = nps.nearest(x, y);
            Point actual = kdt.nearest(x, y);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void timingTest() {
        Random rd = new Random(5201314);
        Random rd1 = new Random(5201314);
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            points.add(new Point(rd.nextDouble(), rd.nextDouble()));
        }

        NaivePointSet nps = new NaivePointSet(points);
        KDTree kdt = new KDTree(points);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            double x = rd.nextDouble();
            double y = rd.nextDouble();
            Point p = nps.nearest(x, y);
        }
        long end = System.currentTimeMillis();

        long start1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            double x = rd1.nextDouble();
            double y = rd1.nextDouble();
            Point p = kdt.nearest(x, y);
        }
        long end1 = System.currentTimeMillis();

        System.out.println("Time elapsed for NaivePointSet is: " + (end - start) / 1000.0 + " second.");
        System.out.println("Time elapsed for KDTree is: " + (end1 - start1) / 1000.0 + " second.");
        assertTrue((end - start) > 10 * (end1 - start1));
    }
}
