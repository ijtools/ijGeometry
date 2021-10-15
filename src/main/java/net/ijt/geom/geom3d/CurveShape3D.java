/**
 * 
 */
package net.ijt.geom.geom3d;

import java.util.Collection;

import net.ijt.geom.Curve;

/**
 * A geometry that is composed of one or more continuous curves in the 3D space.
 * 
 * @author dlegland
 *
 */
public interface CurveShape3D extends Curve, Geometry3D
{
    /**
     * @return the collection of continuous curves that forms this curve shape.
     */
    public Collection<? extends Curve3D> curves();
    
    /**
     * Returns the result of the given transformation applied to this curve shape.
     * 
     * @param trans
     *            the transformation to apply
     * @return the transformed geometry
     */
    public CurveShape3D transform(AffineTransform3D trans);
}
