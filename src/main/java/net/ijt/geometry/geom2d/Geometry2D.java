/**
 * 
 */
package net.ijt.geometry.geom2d;

import java.util.ArrayList;

import ij.gui.PointRoi;
import ij.gui.PolygonRoi;
import ij.gui.Roi;
import ij.process.FloatPolygon;
import net.ijt.geometry.Geometry;
import net.ijt.geometry.polygon2d.DefaultPolygon2D;

/**
 * A shape embedded into a 2-dimensional space.
 * 
 * @author dlegland
 *
 */
public interface Geometry2D extends Geometry
{
    // ===================================================================
    // Static variables
    
    /**
     * The origin of the basis, equal to (0,0).
     */
    public static final Point2D ORIGIN = new Point2D(0,0);
    
    /**
     * The minimum norm for which a vector can be assimilated to null vector.
     */
    public static final double MIN_VECTOR_NORM = 1e-12;
        
    
    // ===================================================================
    // Static methods
    
    /**
     * Converts an ImageJ ROI into an instance of Geometry2D.
     * 
     * @param roi
     *            the ROI to convert.
     * @return the corresponding Geometry2D, or null if no geometry class could
     *         be found.
     */
    public static Geometry2D fromROI(Roi roi)
    {
        // switch processing depending on ROI class
        if (roi instanceof PointRoi)
        {
            PointRoi pointRoi = (PointRoi) roi;
            int np = pointRoi.getNCoordinates();
            if (np == 1)
            {
                // single point
                FloatPolygon poly = pointRoi.getFloatPolygon();
                return new Point2D(poly.xpoints[0], poly.ypoints[0]);
            }
            else
            {
                // multi-point
                FloatPolygon poly = pointRoi.getFloatPolygon();
                ArrayList<Point2D> pts = new ArrayList<Point2D>(np);
                for (int i = 0; i < poly.npoints; i++)
                {
                    pts.add(new Point2D(poly.xpoints[i], poly.ypoints[i]));
                }
                return new MultiPoint2D(pts);
            }
        }
        else if (roi instanceof PolygonRoi)
        {
            // polygon -> use floating-point coordinates
            FloatPolygon poly = ((PolygonRoi) roi).getFloatPolygon();
            DefaultPolygon2D res = new DefaultPolygon2D(poly.npoints);
            for (int i = 0; i < poly.npoints; i++)
            {
                res.addVertex(new Point2D(poly.xpoints[i], poly.ypoints[i]));
            }
            return res;
        }
        
        return null;
    }
    
    
    // ===================================================================
    // Methods declaration

    /**
     * Checks if the geometry contains the given point, with a given precision.
     * 
     * @param point
     *            the point to test
     * @param eps
     *            the tolerance to use for distance comparison
     * @return true if the point is inside this geometry, with respect to the
     *         given tolerance
     */
    public boolean contains(Point2D point, double eps);

    /**
     * Computes the distance between this geometry and the given point.
     * 
     * @param point
     *            a point in the 2D plane
     * @return the Euclidean distance between this geometry and the specified
     *         point
     */
    public default double distance(Point2D point)
    {
        return distance(point.x(), point.y());
    }
    
    /**
     * Computes the distance between this geometry and the point given by the
     * couple of coordinates.
     * 
     * @param x
     *            the x-coordinate of the point to test
     * @param y
     *            the y-coordinate of the point to test
     * @return the Euclidean distance between this geometry and the specified
     *         point
     */
    public double distance(double x, double y);
    
    /**
     * @return the bounds of this geometry.
     */
    public Bounds2D bounds();

    /**
     * @return a dimensionality value equals to 2.
     */
    @Override
    public default int dimensionality()
    {
        return 2;
    }
}
