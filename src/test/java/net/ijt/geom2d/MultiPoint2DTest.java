/**
 * 
 */
package net.ijt.geom2d;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

/**
 * 
 */
public class MultiPoint2DTest
{

    /**
     * Test method for {@link net.ijt.geom2d.MultiPoint2D#contains(net.ijt.geom2d.Point2D, double)}.
     */
    @Test
    public final void testContains()
    {
        MultiPoint2D mp = createMultiPoint();

        assertTrue(mp.contains(new Point2D(20, 20), 0.01));
        assertTrue(mp.contains(new Point2D(50, 20), 0.01));
        assertTrue(mp.contains(new Point2D(20, 40), 0.01));
        
        assertFalse(mp.contains(new Point2D(30, 20), 0.01));
    }

    /**
     * Test method for {@link net.ijt.geom2d.MultiPoint2D#distance(double, double)}.
     */
    @Test
    public final void testDistance()
    {
        MultiPoint2D mp = createMultiPoint();

        assertEquals(10.0, mp.distance(10, 20), 0.01);
        assertEquals(10.0, mp.distance(20, 10), 0.01);
        assertEquals( 0.0, mp.distance(20, 20), 0.01);
        
        assertEquals(10.0, mp.distance(60, 20), 0.01);
        assertEquals(10.0, mp.distance(20, 50), 0.01);
    }

    /**
     * Test method for {@link net.ijt.geom2d.MultiPoint2D#bounds()}.
     */
    @Test
    public final void testBounds()
    {
        MultiPoint2D mp = createMultiPoint();

        Bounds2D bounds = mp.bounds();
        
        Bounds2D expected = new Bounds2D(20, 50, 20, 40);
        assertTrue(expected.almostEquals(bounds, 0.01));
    }
    
    private static final MultiPoint2D createMultiPoint()
    {
        ArrayList<Point2D> points = new ArrayList<Point2D>(3);
        points.add(new Point2D(20, 20));
        points.add(new Point2D(50, 20));
        points.add(new Point2D(20, 40));
        return new MultiPoint2D(points);
    }

}
