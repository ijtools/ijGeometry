/**
 * 
 */
package net.ijt.geom2d.polygon;

import java.util.ArrayList;
import java.util.Collections;

import net.ijt.geom2d.AffineTransform2D;
import net.ijt.geom2d.Bounds2D;
import net.ijt.geom2d.Point2D;

/**
 * @author dlegland
 *
 */
public class OrientedBox2D implements Polygon2D
{
    // ===================================================================
    // Class variables
    
    /** X-coordinate of the center. */
    protected final double xc;

    /** Y-coordinate of the center. */
    protected final double yc;

    /** Length of first size. Must be positive. */
    protected final double size1;
    
    /** Length of second size. Must be positive. Usually smaller than first side. */
    protected final double size2;

	/**
	 * Orientation of major semi-axis, in degrees, counter-clockwise. Usually
	 * restrained to the range between -90 and +90.
	 */
    protected final double theta;
    
    // buffer the coordinates of boundary vertices
    private LinearRing2D boundary;
    

    
    // ===================================================================
    // Constructors
    
    public OrientedBox2D(double xc, double yc, double size1, double size2, double orientInDegrees)
    {
    	this.xc = xc;
    	this.yc = yc;
    	this.size1 = size1;
    	this.size2 = size2;
    	this.theta = orientInDegrees;
    	
    	computeBoundary();
    }
    
    /**
     * Pre-computes the boundary of the box, used to compute vertex coordinates,
     * bounds, distances...
     */
    private void computeBoundary()
    {
    	// create the "local to global" affine transform
    	AffineTransform2D rot = AffineTransform2D.createRotation(Math.toRadians(this.theta));
    	AffineTransform2D tra = AffineTransform2D.createTranslation(xc, yc);
    	AffineTransform2D transfo = tra.concatenate(rot);
    	
    	// create vertex array
    	ArrayList<Point2D> vertices = new ArrayList<>(4);
    	vertices.add(new Point2D(-size1/2, -size2/2).transform(transfo));
    	vertices.add(new Point2D( size1/2, -size2/2).transform(transfo));
    	vertices.add(new Point2D( size1/2,  size2/2).transform(transfo));
    	vertices.add(new Point2D(-size1/2,  size2/2).transform(transfo));
    	
        // create boundary
        this.boundary = new LinearRing2D(vertices);
    }
    
    
    // ===================================================================
    // Implementation of Polygon2D interface
    
    @Override
    public double signedArea()
    {
        return size1 * size2;
    }

    @Override
    public int vertexCount()
    {
        return 4;
    }

    @Override
    public Iterable<Point2D> vertexPositions()
    {
        // return pre-computed boundary vertices
        return this.boundary.vertexPositions();
    }

    @Override
    public void addVertex(Point2D vertexPosition)
    {
        throw new RuntimeException("Can not add vertex to this geometry");
    }
    
    @Override
    public Iterable<LinearRing2D> rings()
    {
        return Collections.singleton(this.boundary);
    }

    
    // ===================================================================
    // Implementation of the PolygonalDomain2D interface
    
    @Override
    public Polygon2D complement()
    {
        throw new RuntimeException("Unimplemented operation");
    }

    @Override
    public LinearRing2D boundary()
    {
        return this.boundary;
    }

    
    // ===================================================================
    // Implementation of the Geometry interface
    
    @Override
    public double distance(double x, double y)
    {
        double dist = this.boundary.signedDistance(x, y);
        return dist > 0 ? dist : 0;
    }

    @Override
    public boolean isInside(Point2D point)
    {
    	return this.boundary.signedDistance(point) <= 0;
    }

    @Override
    public boolean isInside(double x, double y)
    {
		return this.boundary.signedDistance(x, y) <= 0;
	}

    @Override
    public boolean contains(Point2D point, double eps)
    {
    	return this.boundary.signedDistance(point) <= eps;
    }

    @Override
    public Bounds2D bounds()
    {
        return this.boundary.bounds();
    }

    @Override
    public boolean isBounded()
    {
        return true;
    }
}
