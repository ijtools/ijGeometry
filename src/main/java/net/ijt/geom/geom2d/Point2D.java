/**
 * 
 */
package net.ijt.geom.geom2d;

import java.util.Locale;

import net.ijt.geom.Point;

/**
 * @author dlegland
 *
 */
public class Point2D implements Geometry2D, Point
{
    // ===================================================================
    // Static methods
    
    public static final Point2D centroid(Point2D... points)
    {
        double xc = 0;
        double yc = 0;
        int np = points.length;
        for (Point2D p : points)
        {
            xc += p.x;
            yc += p.y;
        }
        
        return new Point2D(xc / np, yc / np);
    }

    // ===================================================================
    // class variables

	/** x coordinate of the point */
	final double x;

	/** y coordinate of the point */
	final double y;

	
	// ===================================================================
	// Constructors

	/** Empty constructor, similar to Point(0,0) */
	public Point2D()
	{
		this(0, 0);
	}

	/** 
	 * New point given by its coordinates 
	 * 
	 * @param x the x coordinate of the new point
	 * @param y the y coordinate of the new point
	 */
	public Point2D(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	// ===================================================================
	// Specific methods

	/**
     * Returns the result of the given transformation applied to this point.
     * 
     * @param trans
     *            the transformation to apply
     * @return the transformed point
     */
	public Point2D transform(AffineTransform2D trans)
	{
	    return trans.transform(this);
	}
	
	/**
	 * Adds the specified vector to the point, and returns the result.
	 * 
	 * @param v
	 *            the vector to add
	 * @return the result of the addition of<code>v</code> to this point
	 */
	public Point2D plus(Vector2D v)
	{
		return new Point2D(this.x + v.getX(), this.y + v.getY());
	}

	/**
	 * Subtracts the specified vector from the point, and returns the result.
	 * 
	 * @param v
	 *            the vector to subtract
	 * @return the result of the subtraction of<code>v</code> to this point
	 */
	public Point2D minus(Vector2D v)
	{
		return new Point2D(this.x - v.getX(), this.y - v.getY());
	}

	public boolean almostEquals(Point2D point, double eps)
	{
        if (Math.abs(point.x - x) > eps) return false;
        if (Math.abs(point.y - y) > eps) return false;
        return true;
	}
	
	
    // ===================================================================
    // accessors

    /**
     * @return the x coordinate of this point
     */
    public double getX()
    {
        return x;
    }
    
    /**
     * @return the y coordinate of this point
     */
    public double getY()
    {
        return y;
    }
    
    
	
	// ===================================================================
    // Implementation of the Point interface

    @Override
    public double get(int dim)
    {
        switch(dim)
        {
        case 0: return this.x;
        case 1: return this.y;
        default:
            throw new IllegalArgumentException("Dimension should be comprised between 0 and 1");
        }
    }

    
    // ===================================================================
    // Implementation of the Geometry2D interface

    @Override
    public boolean contains(Point2D point, double eps)
    {
        if (Math.abs(this.x - point.x) > eps) return false;
        if (Math.abs(this.y - point.y) > eps) return false;
        return true;
    }

    /**
     * Computes the distance between this point and the point
     * <code>point</code>.
     *
     * @param point
     *            another point
     * @return the distance between the two points
     */
    /* 
     * Overrides the default implementation in Geometry2D interface to directly
     * compare point coordinates.
     */ 
    @Override
    public double distance(Point2D point)
    {
        return distance(point.x, point.y);
    }

    /**
     * Computes the distance between current point and point with coordinate
     * <code>(x,y)</code>. Uses the <code>Math.hypot()</code> function for
     * better robustness than simple square root.
     * 
     * @param x the x-coordinate of the other point
     * @param y the y-coordinate of the other point
     * @return the distance between the two points
     */
    public double distance(double x, double y)
    {
        return Math.hypot(this.x - x, this.y - y);
    }


    // ===================================================================
    // Implementation of the Geometry interface

    /**
     * Returns true, as a point is bounded by definition.
     */
    public boolean isBounded()
    {
        return true;
    }

    @Override
    public Bounds2D bounds()
    {
        return new Bounds2D(this.x, this.x, this.y, this.y);
    }


//	public Point2d transform(Transform2d trans) 
//	{
//		return trans.transform(this);
//	}
    // ===================================================================
    // Override Object interface

    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH, "Point2D(%g,%g)", this.x, this.y);
    }

}
