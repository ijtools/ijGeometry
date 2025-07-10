/**
 * 
 */
package net.ijt.geometry.geom2d;

/**
 * The generic interface for geometric transforms in the plane.
 * 
 * @see AffineTransform2D
 * 
 * @author dlegland
 */
public interface Transform2D
{
	public Point2D transform(Point2D point);
}
