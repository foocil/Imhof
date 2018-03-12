package ch.epfl.imhof.geometry;

import static org.junit.Assert.assertEquals;

import java.util.function.Function;

import org.junit.Test;

public class PointTest {
    private static final double DELTA = 0.000001;

    @Test
    public void xGetterReturnsX() {
        for (double x = -100; x <= 100; x += 12.32)
            assertEquals(x, new Point(x, 0).x(), DELTA);
    }

    @Test
    public void yGetterReturnsY() {
        for (double y = -100; y <= 100; y += 12.32)
            assertEquals(y, new Point(0, y).y(), DELTA);
    }

    @Test
    public void translationTest() {
        Function<Point, Point> oneToTwo =
                Point.alignedCoordinateChange(new Point(1, -1),
                                              new Point(5, 4),
                                              new Point(-1.5, 1),
                                              new Point(0, 0));
            Point point1 = oneToTwo.apply(new Point(0, 0));
        
        Function<Point, Point> threeToFour =
                    Point.alignedCoordinateChange(new Point(4, 5),
                                                  new Point(4, 1),
                                                  new Point(2, 1),
                                                  new Point(0, -1));
                Point point2 = threeToFour.apply(new Point(5, 4));
        
        assertEquals(3,point1.x(), DELTA );
        assertEquals(2,point1.y(), DELTA);
        assertEquals(6,point2.x(), DELTA );
        assertEquals(0.5,point2.y(), DELTA);
        
    }
}
