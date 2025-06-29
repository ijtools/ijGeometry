/**
 * 
 */
package net.ijt.geom2d.curve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.ijt.geom2d.AffineTransform2D;
import net.ijt.geom2d.Boundary2D;
import net.ijt.geom2d.Bounds2D;
import net.ijt.geom2d.Contour2D;
import net.ijt.geom2d.Point2D;

/**
 * An implementation of <code>Boundary2D</code> based on a collection of 2D
 * contours.
 */
public class MultiContourBoundary2D implements Boundary2D
{
    // ===================================================================
    // Static factories
    
    public static final MultiContourBoundary2D from(Contour2D... contours)
    {
        return new MultiContourBoundary2D(Stream.of(contours).collect(Collectors.toList()));
    }
    
    
    // ===================================================================
    // Members
    
    ArrayList<Contour2D> contours;
    

    // ===================================================================
    // Constructors
    
    public MultiContourBoundary2D()
    {
        this.contours = new ArrayList<>();
    }
    
    public MultiContourBoundary2D(Collection<? extends Contour2D> contours)
    {
        this.contours = new ArrayList<>(contours.size());
        this.contours.addAll(contours);
    }
    
    @Override
    public Collection<? extends Contour2D> curves()
    {
        return Collections.unmodifiableList(this.contours);
    }

    @Override
    public double signedDistance(Point2D point)
    {
        return signedDistance(point.x(), point.y());
    }

    @Override
    public double signedDistance(double x, double y)
    {
        double minDist = Double.POSITIVE_INFINITY;
        for (Contour2D contour : contours)
        {
            double dist = contour.signedDistance(x, y);
            if (Math.abs(dist) < Math.abs(minDist))
            {
                minDist = dist;
            }
        }
        
        return minDist;
    }

    @Override
    public boolean isInside(Point2D point)
    {
        return isInside(point.x(), point.y());
    }

    @Override
    public boolean isInside(double x, double y)
    {
        return signedDistance(x, y) <= 0;
    }
    

    // ===================================================================
    // Implementation of the Geometry2D interface
    
    @Override
    public boolean contains(Point2D point, double eps)
    {
        return distance(point) < eps;
    }

    @Override
    public double distance(double x, double y)
    {
        return this.contours.stream()
                .mapToDouble(c -> c.distance(x, y))
                .min()
                .orElse(Double.NaN);
    }


    // ===================================================================
    // Implementation of the Geometry interface
    
    @Override
    public Bounds2D bounds()
    {
        double xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;
        
        for (Contour2D contour : contours)
        {
            Bounds2D bounds = contour.bounds();
            xmin = Math.min(xmin, bounds.minX());
            xmax = Math.max(xmax, bounds.maxX());
            ymin = Math.min(ymin, bounds.minY());
            ymax = Math.max(ymax, bounds.maxY());
        }
        
        return new Bounds2D(xmin, xmax, ymin, ymax);
    }

    @Override
    public boolean isBounded()
    {
        return true;
    }

    @Override
    public Boundary2D transform(AffineTransform2D trans)
    {
        return new MultiContourBoundary2D(contours.stream()
                .map(c -> c.transform(trans))
                .collect(Collectors.toList()));
    }
}
