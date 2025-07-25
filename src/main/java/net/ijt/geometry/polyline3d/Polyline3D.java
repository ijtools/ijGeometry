/**
 * 
 */
package net.ijt.geometry.polyline3d;

import java.util.Collection;
import java.util.Iterator;

import net.ijt.geometry.geom3d.AffineTransform3D;
import net.ijt.geometry.geom3d.Bounds3D;
import net.ijt.geometry.geom3d.Curve3D;
import net.ijt.geometry.geom3d.LineSegment3D;
import net.ijt.geometry.geom3d.Point3D;

/**
 * @author dlegland
 *
 */
public interface Polyline3D extends Curve3D
{
    // ===================================================================
    // Methods for managing vertices
    
    /**
     * Returns a pointer to the vertices.
     * 
     * @return a pointer to the collection of vertices
     */
    public Collection<Point3D> vertices();
    
    /**
     * Returns the number of vertices.
     * 
     * @return the number of vertices
     */
    public int vertexCount();

    public Iterator<LineSegment3D> edgeIterator();

    /**
     * Returns the polyline composed with the same vertices, but in reverse order.
     * 
     * @return the polyline with same vertices but in reverse order.
     */
    public Polyline3D reverse();
    

    // ===================================================================
    // Geoemtry methods 

    /**
     * Transforms this geometry with the specified affine transform.
     * 
     * @param trans
     *            an affine transform
     * @return the transformed geometry
     */
    public Polyline3D transform(AffineTransform3D trans);

    
    // ===================================================================
    // Implementation of the Geometry2D interface 

    @Override
    public default boolean contains(Point3D point, double eps)
    {
        // Iterate on the line segments forming the polyline
        Iterator<LineSegment3D> iter = edgeIterator();
        while(iter.hasNext())
        {
            if (iter.next().contains(point, eps))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Iterate over edges to find the minimal distance between the test point
     * and this polyline.
     * 
     * @param x
     *            the x-coordinate of the point to test
     * @param y
     *            the y-coordinate of the point to test
     * @return the distance to the polyline
     */
    public default double distance(double x, double y, double z)
    {
        double minDist = Double.POSITIVE_INFINITY;
        
        // Iterate on the line segments forming the polyline
        Iterator<LineSegment3D> iter = edgeIterator();
        while(iter.hasNext())
        {
            minDist = Math.min(minDist, iter.next().distance(x, y, z));
        }
        return minDist;
    }
    
    // ===================================================================
    // Implementation of the Geometry interface 

    /**
     * Returns true, as a linear ring is bounded by definition.
     */
    public default boolean isBounded()
    {
        return true;
    }

    public default Bounds3D bounds()
    {
        return Bounds3D.of(this.vertices());

    }
}
