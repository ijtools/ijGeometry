/**
 * 
 */
package net.ijt.geometry.geom2d;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 */
public class MultiPoint2D implements Geometry2D
{
    // ===================================================================
    // Members
    
    ArrayList<Point2D> points;
    
    
    // ===================================================================
    // Constructors
    
    public MultiPoint2D()
    {
        this.points = new ArrayList<>();
    }
    
    public MultiPoint2D(Collection<? extends Point2D> points)
    {
        this.points = new ArrayList<>(points.size());
        this.points.addAll(points);
    }
    
    
    // ===================================================================
    // Methods implementing Geometry2D
    
    @Override
    public boolean contains(Point2D point, double eps)
    {
        return distance(point) < eps;
    }

    @Override
    public double distance(double x, double y)
    {
        return this.points.stream()
                .mapToDouble(p -> p.distance(x, y))
                .min()
                .orElse(Double.NaN);
    }

    @Override
    public Bounds2D bounds()
    {
        double xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;
        
        for (Point2D point : points)
        {
            xmin = Math.min(xmin, point.x());
            xmax = Math.max(xmax, point.x());
            ymin = Math.min(ymin, point.y());
            ymax = Math.max(ymax, point.y());
        }
        
        return new Bounds2D(xmin, xmax, ymin, ymax);
    }

    /**
     * Returns {@code true}, as a set of points is always bounded.
     * 
     * @return true
     */ 
    @Override
    public boolean isBounded()
    {
        return true;
    }
}
