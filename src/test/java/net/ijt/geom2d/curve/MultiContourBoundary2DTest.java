/**
 * 
 */
package net.ijt.geom2d.curve;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import net.ijt.geometry.geom2d.Boundary2D;
import net.ijt.geometry.geom2d.Bounds2D;
import net.ijt.geometry.geom2d.Point2D;
import net.ijt.geometry.geom2d.curve.Circle2D;
import net.ijt.geometry.geom2d.curve.MultiContourBoundary2D;

/**
 * 
 */
public class MultiContourBoundary2DTest
{
    /**
     * Test method for {@link net.ijt.geometry.geom2d.curve.MultiContourBoundary2D#isInside(net.ijt.geometry.geom2d.Point2D)}.
     */
    @Test
    public final void testIsInsidePoint2D()
    {
        Boundary2D boundary = threeDiscsBoundary();
        
        assertFalse(boundary.isInside(new Point2D(9, 20)));
        assertTrue(boundary.isInside(new Point2D(11, 20)));
        assertFalse(boundary.isInside(new Point2D(61, 20)));
        assertTrue(boundary.isInside(new Point2D(59, 20)));
        assertFalse(boundary.isInside(new Point2D(30, 51)));
        assertTrue(boundary.isInside(new Point2D(30, 49)));
    }

    /**
     * Test method for {@link net.ijt.geometry.geom2d.curve.MultiContourBoundary2D#contains(net.ijt.geometry.geom2d.Point2D, double)}.
     */
    @Test
    public final void testContains()
    {
        Boundary2D boundary = threeDiscsBoundary();
        
        assertTrue(boundary.contains(new Point2D(10, 20), 0.01));
        assertTrue(boundary.contains(new Point2D(20, 10), 0.01));
        assertTrue(boundary.contains(new Point2D(20, 30), 0.01));
        assertTrue(boundary.contains(new Point2D(30, 20), 0.01));
        
        assertTrue(boundary.contains(new Point2D(40, 20), 0.01));
        assertTrue(boundary.contains(new Point2D(50, 10), 0.01));
        assertTrue(boundary.contains(new Point2D(50, 30), 0.01));
        assertTrue(boundary.contains(new Point2D(60, 20), 0.01));
        
        assertTrue(boundary.contains(new Point2D(20, 40), 0.01));
        assertTrue(boundary.contains(new Point2D(30, 30), 0.01));
        assertTrue(boundary.contains(new Point2D(30, 50), 0.01));
        assertTrue(boundary.contains(new Point2D(40, 40), 0.01));
    }

    /**
     * Test method for {@link net.ijt.geometry.geom2d.curve.MultiContourBoundary2D#bounds()}.
     */
    @Test
    public final void testBounds()
    {
        Boundary2D boundary = threeDiscsBoundary();
        Bounds2D bounds = boundary.bounds();
        
        assertEquals(10.0, bounds.minX(), 0.01);
        assertEquals(60.0, bounds.maxX(), 0.01);
        assertEquals(10.0, bounds.minY(), 0.01);
        assertEquals(50.0, bounds.maxY(), 0.01);
    }
    
    private Boundary2D threeDiscsBoundary()
    {
        Circle2D circ1 = new Circle2D(20, 20, 10);
        Circle2D circ2 = new Circle2D(50, 20, 10);
        Circle2D circ3 = new Circle2D(30, 40, 10);
        return MultiContourBoundary2D.from(circ1, circ2, circ3);
    }

}
