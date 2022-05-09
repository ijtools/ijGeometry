/**
 * 
 */
package net.ijt.geom;

/**
 * Used to define the bounding box of geometries.
 * 
 * @author dlegland
 *
 */
public interface Bounds
{
    /**
     * @param d the dimension index
     * @return Returns the lowest coordinate of the geometry in the given dimension.
     */
    double getMin(int d);

    /**
     * @param d the dimension index
     * @return Returns the highest coordinate of the geometry in the given dimension.
     */
    double getMax(int d);
    
    /**
     * Returns the dimensionality of the bounds.
     * 
     * @return the number of dimensions of this entity.
     */
   public int dimensionality();
}
