/**
 * 
 */
package net.ijt.geometry.geom2d.curve;

import java.util.ArrayList;
import java.util.Collection;

import net.ijt.geometry.geom2d.AffineTransform2D;
import net.ijt.geometry.geom2d.Bounds2D;
import net.ijt.geometry.geom2d.Curve2D;
import net.ijt.geometry.geom2d.CurveShape2D;
import net.ijt.geometry.geom2d.Point2D;

/**
 * A collection of curves that implements the CurveShape2D interface.
 * 
 * @author dlegland
 *
 */
public class CurveSet2D implements CurveShape2D
{
    // ===================================================================
    // Class variables
    
    ArrayList<Curve2D> curves;

    
    // ===================================================================
    // Constructors
    
    public CurveSet2D(Collection<? extends Curve2D> curves)
    {
        this.curves = new ArrayList<Curve2D>(curves.size());
        this.curves.addAll(curves);
    }
    
    public CurveSet2D(Curve2D... curves)
    {
        this.curves = new ArrayList<Curve2D>(curves.length);
        for (Curve2D c : curves)
            this.curves.add(c);
    }
    
    
    // ===================================================================
    // Implementation of CurveShape2D interface
    
    @Override
    public Collection<Curve2D> curves()
    {
        return this.curves;
    }
    
    /**
     * Returns the result of the given transformation applied to this curve shape.
     * 
     * @param trans
     *            the transformation to apply
     * @return the transformed geometry
     */
    public CurveSet2D transform(AffineTransform2D trans)
    {
        ArrayList<Curve2D> newCurves = new ArrayList<Curve2D>(this.curves.size());
        for (Curve2D curve : this.curves)
        {
            newCurves.add(curve.transform(trans));
        }
        return new CurveSet2D(newCurves);
    }

    
    // ===================================================================
    // Geometry2D interface
    
    @Override
    public boolean contains(Point2D point, double eps)
    {
        for (Curve2D curve : this.curves)
        {
            if (!curve.contains(point, eps))
                return true;
        }
        return false;
    }

    @Override
    public double distance(double x, double y)
    {
        double minDist = Double.POSITIVE_INFINITY;
        
        for (Curve2D curve : this.curves)
        {
            minDist = Math.min(minDist,  curve.distance(x, y));
        }
        return minDist;
    }

    @Override
    public Bounds2D bounds()
    {
        // initialize extreme values
        Bounds2D bounds = new Bounds2D(
                Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 
                Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
        
        for (Curve2D curve : curves)
        {
            bounds = bounds.union(curve.bounds());
        }
        
        return bounds;
    }

    @Override
    public boolean isBounded()
    {
        for (Curve2D curve : this.curves)
        {
            if (!curve.isBounded())
                return false;
        }
        return true;
    }
}
