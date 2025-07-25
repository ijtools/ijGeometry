/**
 * 
 */
package net.ijt.geometry.geom2d;

/**
 * A domain determined by a boundary curve.
 * 
 * @author dlegland
 *
 * @see Boundary2D
 */
public class GenericDomain2D implements Domain2D
{
    /**
     * The boundary curve that encloses this domain.
     */
	Boundary2D boundary;
	
	/**
	 * Creates a new domain determined by the specified boundary. 
	 * 
	 * @param boundary the boundary of the new domain.
	 */
	public GenericDomain2D(Boundary2D boundary)
	{
		this.boundary = boundary;
	}

	@Override
	public boolean contains(Point2D point, double eps)
	{
		return this.boundary.signedDistance(point) <= eps;
	}

	@Override
	public double distance(double x, double y)
	{
		return Math.max(this.boundary.signedDistance(x, y), 0);
	}

	@Override
	public boolean isBounded()
	{
		// TODO should also manage unbounded domains
		return this.boundary.isBounded();
	}

	/**
	 * Simply returns the boundary curve referenced by this domain.
	 * 
	 * @return the inner boundary curve
	 */
	@Override
	public Boundary2D boundary()
	{
		return this.boundary;
	}

	
    // ===================================================================
    // Methods implementing the Geometry2D interface
    
	@Override
	public boolean isInside(Point2D point)
	{
		return this.boundary.isInside(point);
	}

	@Override
	public boolean isInside(double x, double y)
	{
		return this.boundary.isInside(x, y);
	}

	@Override
	public Bounds2D bounds()
	{
		// TODO should also manage unbounded domains
		return boundary.bounds();
	}
}
