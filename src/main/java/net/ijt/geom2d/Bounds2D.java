/**
 * 
 */
package net.ijt.geom2d;

import static java.lang.Double.isInfinite;

import net.ijt.geom.Bounds;
import net.ijt.geom2d.polygon.Polygon2D;

/**
 * Contains the bounds of a planar geometry.
 * 
 * @author dlegland
 *
 */
public class Bounds2D implements Bounds
{
    // ===================================================================
    // Static factories
    
    public static final Bounds2D of(Iterable<Point2D> points)
    {
        // initialize with extreme values
        double xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;
        
        // compute min/max for each coordinate
        for (Point2D p : points)
        {
            double x = p.x();
            double y = p.y();
            xmin = Math.min(xmin, x);
            xmax = Math.max(xmax, x);
            ymin = Math.min(ymin, y);
            ymax = Math.max(ymax, y);
        }
        
        // return new Bounding Bounds
        return new Bounds2D(xmin, xmax, ymin, ymax);        
    }
    
    
    // ===================================================================
    // class variables

    double xmin;
    double ymin;
    double xmax;
    double ymax;
    
    
    // ===================================================================
    // constructors

    /** Empty constructor (size and position zero) */
    public Bounds2D()
    {
        this(0, 0, 0, 0);
    }
    
    /**
	 * Main constructor, given bounds for x coordinate, then bounds for y
	 * coordinate.
	 * 
	 * @param xmin
	 *            the minimum value along the first dimension
	 * @param xmax
	 *            the maximum value along the first dimension
	 * @param ymin
	 *            the minimum value along the second dimension
	 * @param ymax
	 *            the maximum value along the second dimension
	 */
    public Bounds2D(double xmin, double xmax, double ymin, double ymax)
    {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }
    
    /**
	 * Constructor from 2 points, giving extreme coordinates of the box.
	 * 
	 * @param p1
	 *            a point corresponding to a corner of the box
	 * @param p2
	 *            a point corresponding to a corner of the box, opposite of p1
	 */
    public Bounds2D(Point2D p1, Point2D p2)
    {
        double x1 = p1.x();
        double y1 = p1.y();
        double x2 = p2.x();
        double y2 = p2.y();
        this.xmin = Math.min(x1, x2);
        this.xmax = Math.max(x1, x2);
        this.ymin = Math.min(y1, y2);
        this.ymax = Math.max(y1, y2);
    }
    
    
    // ===================================================================
    // General methods
    
    /**
	 * Converts this bounding box to a rectangular polygon.
	 * 
	 * @return the polygon corresponding to this bounds
	 */
    public Polygon2D getRectangle()
    {
    	Point2D p1 = new Point2D(this.xmin, this.ymin);
    	Point2D p2 = new Point2D(this.xmax, this.ymin);
    	Point2D p3 = new Point2D(this.xmax, this.ymax);
    	Point2D p4 = new Point2D(this.xmin, this.ymax);
    	Polygon2D poly = Polygon2D.create(p1, p2, p3, p4);
    	return poly;
    }
    
    public Bounds2D union(Bounds2D bounds)
    {
        double xmin = Math.min(this.xmin, bounds.xmin);
        double xmax = Math.max(this.xmax, bounds.xmax);
        double ymin = Math.min(this.ymin, bounds.ymin);
        double ymax = Math.max(this.ymax, bounds.ymax);
        return new Bounds2D(xmin, xmax, ymin, ymax);
    }

    
    // ===================================================================
    // Tests of inclusion
    
    /**
	 * Checks if this box contains the given point.
	 * 
	 * @param point
	 *            the test point
	 * @return true if the point is inside the bounding box
	 */
    public boolean contains(Point2D point)
    {
        double x = point.x();
        double y = point.y();
        if (x < xmin)
            return false;
        if (y < ymin)
            return false;
        if (x > xmax)
            return false;
        if (y > ymax)
            return false;
        return true;
    }
    
    /**
     * Checks if this box contains the point defined by the given coordinates.
	 * @param x
	 *            the x-coordinate of the test point
	 * @param y
	 *            the y-coordinate of the test point
	 * @return true if the point is inside the bounding box
     */
    public boolean contains(double x, double y)
    {
        if (x < xmin)
            return false;
        if (y < ymin)
            return false;
        if (x > xmax)
            return false;
        if (y > ymax)
            return false;
        return true;
    }

    // ===================================================================
    // Accessors to Bounds2D fields
    
    public double minX()
    {
        return xmin;
    }
    
    public double maxX()
    {
        return xmax;
    }
    
    public double minY()
    {
        return ymin;
    }
    
    public double maxY()
    {
        return ymax;
    }
    
    public double sizeX()
    {
        return xmax - xmin;
    }
    
    public double sizeY()
    {
        return ymax - ymin;
    }
    
    // ===================================================================
    // generic accessors
    
    public double size(int d)
    {
        switch(d)
        {
        case 0: return this.xmax - this.xmin;
        case 1: return this.ymax - this.ymin;
        default: throw new IllegalArgumentException("Dimension index must be either 0 or 1, not " + d);
        }
    }

    public double getMin(int d)
    {
        switch(d)
        {
        case 0: return this.xmin;
        case 1: return this.ymin;
        default: throw new IllegalArgumentException("Dimension index must be either 0 or 1, not " + d);
        }
    }
    
    public double getMax(int d)
    {
        switch(d)
        {
        case 0: return this.xmax;
        case 1: return this.ymax;
        default: throw new IllegalArgumentException("Dimension index must be either 0 or 1, not " + d);
        }
    }
    
    
    
    
    // ===================================================================
    // Methods mimicking the Geometry2D interface
    
    /** @return true if all bounds are finite. */
    public boolean isBounded()
    {
        if (isInfinite(xmin))
            return false;
        if (isInfinite(ymin))
            return false;
        if (isInfinite(xmax))
            return false;
        if (isInfinite(ymax))
            return false;
        return true;
    }

    @Override
    public int dimensionality()
    {
        return 2;
    }

    // ===================================================================
    // Methods mimicking the Geometry2D interface
    
    public boolean almostEquals(Bounds2D box, double eps)
    {
        if (Math.abs(box.xmin - xmin) > eps) return false;
        if (Math.abs(box.xmax - xmax) > eps) return false;
        if (Math.abs(box.ymin - ymin) > eps) return false;
        if (Math.abs(box.ymax - ymax) > eps) return false;
        return true;
    }
}
