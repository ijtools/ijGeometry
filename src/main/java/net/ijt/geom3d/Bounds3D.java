/**
 * 
 */
package net.ijt.geom3d;

import static java.lang.Double.isInfinite;

import net.ijt.geom.Bounds;

/**
 * Contains the bounds of a planar geometry.
 * 
 * @author dlegland
 *
 */
public class Bounds3D implements Bounds
{
    // ===================================================================
    // class variables

    double xmin;
    double xmax;
    double ymin;
    double ymax;
    double zmin;
    double zmax;
    
    
    // ===================================================================
    // constructors

    /** Empty constructor (size and position zero) */
    public Bounds3D()
    {
        this(0, 0, 0, 0, 0, 0);
    }
    
    /**
     * Main constructor, given bounds for x coord, then bounds for y coord, then
     * bounds for the z coords.
     * 
     * @param xmin
     *            the minimum value of the x coordinate of the box
     * @param xmax
     *            the maximum value of the x coordinate of the box
     * @param ymin
     *            the minimum value of the y coordinate of the box
     * @param ymax
     *            the maximum value of the y coordinate of the box
     * @param zmin
     *            the minimum value of the z coordinate of the box
     * @param zmax
     *            the maximum value of the z coordinate of the box
     */
    public Bounds3D(double xmin, double xmax, double ymin, double ymax, double zmin, double zmax)
    {
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
        this.zmin = zmin;
        this.zmax = zmax;
    }
    
    /**
     * Constructor from 2 points, giving extreme coordinates of the box.
     * 
     * @param p1
     *            first corner of the box
     * @param p2
     *            the corner of the box opposite to the first corner
     */
    public Bounds3D(Point3D p1, Point3D p2)
    {
        double x1 = p1.x();
        double y1 = p1.y();
        double z1 = p1.z();
        double x2 = p2.x();
        double y2 = p2.y();
        double z2 = p2.z();
        this.xmin = Math.min(x1, x2);
        this.xmax = Math.max(x1, x2);
        this.ymin = Math.min(y1, y2);
        this.ymax = Math.max(y1, y2);
        this.zmin = Math.min(z1, z2);
        this.zmax = Math.max(z1, z2);
    }

    
    // ===================================================================
    // Specific methods
    
    public Bounds3D union(Bounds3D bounds)
    {
        double xmin = Math.min(this.xmin, bounds.xmin);
        double xmax = Math.max(this.xmax, bounds.xmax);
        double ymin = Math.min(this.ymin, bounds.ymin);
        double ymax = Math.max(this.ymax, bounds.ymax);
        double zmin = Math.min(this.zmin, bounds.zmin);
        double zmax = Math.max(this.zmax, bounds.zmax);
        return new Bounds3D(xmin, xmax, ymin, ymax, zmin, zmax);
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
    
    public double minZ()
    {
        return zmin;
    }
    
    public double maxZ()
    {
        return zmax;
    }
    
    public double sizeX()
    {
        return xmax - xmin;
    }
    
    public double sizeY()
    {
        return ymax - ymin;
    }
    
    public double sizeZ()
    {
        return zmax - zmin;
    }
    
    public double size(int d)
    {
        switch(d)
        {
        case 0: return this.xmax - this.xmin;
        case 1: return this.ymax - this.ymin;
        case 2: return this.zmax - this.zmin;
        default: throw new IllegalArgumentException("Dimension index must be between 0 and 2, not " + d);
        }
    }

    // ===================================================================
    // generic accessors
    
    public double getMin(int d)
    {
        switch(d)
        {
        case 0: return this.xmin;
        case 1: return this.ymin;
        case 2: return this.zmin;
        default: throw new IllegalArgumentException("Dimension index must be between 0 and 2, not " + d);
        }
    }
    
    public double getMax(int d)
    {
        switch(d)
        {
        case 0: return this.xmax;
        case 1: return this.ymax;
        case 2: return this.zmax;
        default: throw new IllegalArgumentException("Dimension index must be between 0 and 2, not " + d);
        }
    }
    

    // ===================================================================
    // tests of inclusion
    
    /**
     * Checks if this box contains the given point.
     * 
     * @param point
     *            the point to evaluate
     * @return true if the 3D point is within this 3D box
     */
    public boolean contains(Point3D point)
    {
        double x = point.x();
        double y = point.y();
        double z = point.z();
        
        if (x < xmin)
            return false;
        if (y < ymin)
            return false;
        if (z < zmin)
            return false;
        if (x > xmax)
            return false;
        if (y > ymax)
            return false;
        if (z > zmax)
            return false;
        return true;
    }
    

    // ===================================================================
    // Methods mimicking the Geometry3D interface
    
    /** 
     * Returns true if all bounds are finite. 
     *
     * @return true is the box is bounded
     */
    public boolean isBounded()
    {
        if (isInfinite(xmin))
            return false;
        if (isInfinite(ymin))
            return false;
        if (isInfinite(zmin))
            return false;
        if (isInfinite(xmax))
            return false;
        if (isInfinite(ymax))
            return false;
        if (isInfinite(zmax))
            return false;
        return true;
    }

    @Override
    public int dimensionality()
    {
        return 3;
    }
}
