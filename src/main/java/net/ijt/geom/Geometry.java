/**
 * 
 */
package net.ijt.geom;

/**
 * A shape embedded into a N-dimensional space.
 * 
 * @author dlegland
 *
 */
public interface Geometry
{
    /**
     * @return true if this geometry can be bounded by a bounding box with
     *         finite value, or false otherwise (like for straight lines, rays,
     *         parabola...)
     */
    public boolean isBounded();
    
    /**
     * @return the bounds of this geometry
     */
    public Bounds bounds();
    
    /**
     * Returns the number of dimensions this entity is living in. For arrays,
     * this corresponds the number of dimensions of the array. For
     * connectivities or neighborhood, this corresponds to the number of
     * dimension of the array this entity can process.
     * 
     * @return the number of dimensions of this entity.
     */
    public int dimensionality();
}
