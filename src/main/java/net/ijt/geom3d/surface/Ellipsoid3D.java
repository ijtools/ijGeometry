/**
 * 
 */
package net.ijt.geom3d.surface;

import net.ijt.geom3d.AffineTransform3D;
import net.ijt.geom3d.Bounds3D;
import net.ijt.geom3d.Geometry3D;
import net.ijt.geom3d.Point3D;
import net.ijt.geom3d.Rotation3D;

/**
 * A 3D ellipsoid, defined by a center, three semi-axis lengths, and a 3D
 * rotation.
 * 
 * @author dlegland
 *
 */
public class Ellipsoid3D implements Geometry3D
{
    // ===================================================================
    // Class variables

    /**
     * The center of the ellipsoid.
     */
    final Point3D center;

    final double r1;
    final double r2;
    final double r3;

    /**
     * The first rotation applied to the ellipsoid, around the X-axis, in degrees.
     */
    final double eulerAngleX; 
    /**
     * The second rotation applied to the ellipsoid, around the Y-axis, in degrees.
     */
    final double eulerAngleY; 
    /**
     * The third rotation applied to the ellipsoid, around the Z-axis, in degrees.
     */
    final double eulerAngleZ;
    
    
    // ===================================================================
    // Constructors

    /**
     * Creates a new ellipsoid aligned with the main axes.
     * 
     * @param center
     *            the center of the ellipsoid
     * @param r1
     *            the length of the first ellipsoid semi-axis
     * @param r2
     *            the length of the second ellipsoid semi-axis
     * @param r3
     *            the length of the third ellipsoid semi-axis
     */
    public Ellipsoid3D(Point3D center, double r1, double r2, double r3)
    {
        this.center = center;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.eulerAngleX = 0.0;
        this.eulerAngleY = 0.0;
        this.eulerAngleZ = 0.0;
    }

    /**
     * Creates a new ellipsoid with a specific orientation given by a Rotation3D
     * instance.
     * 
     * @param center
     *            the center of the ellipsoid
     * @param r1
     *            the length of the first ellipsoid semi-axis
     * @param r2
     *            the length of the second ellipsoid semi-axis
     * @param r3
     *            the length of the third ellipsoid semi-axis
     * @param orientation
     *            the 3D orientation of the ellipsoid around its center
     */
    public Ellipsoid3D(Point3D center, double r1, double r2, double r3, Rotation3D orientation)
    {
        this.center = center;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        double[] angles = orientation.eulerAngles();
        this.eulerAngleX = Math.toDegrees(angles[0]);
        this.eulerAngleY = Math.toDegrees(angles[0]);
        this.eulerAngleZ = Math.toDegrees(angles[0]);
    }

    /**
     * Creates a new ellipsoid with a specific orientation given by three Euler
     * angles (in degrees).
     * 
     * @param centerX
     *            the x-coordinate of ellipsoid center
     * @param centerY
     *            the y-coordinate of ellipsoid center
     * @param centerZ
     *            the z-coordinate of ellipsoid center
     * @param r1
     *            the length of the first ellipsoid semi-axis
     * @param r2
     *            the length of the second ellipsoid semi-axis
     * @param r3
     *            the length of the third ellipsoid semi-axis
     * @param eulerAngleX
     *            the first rotation applied to the ellipsoid, around the
     *            X-axis, in degrees.
     * @param eulerAngleY
     *            the second rotation applied to the ellipsoid, around the
     *            Y-axis, in degrees.
     * @param eulerAngleZ
     *            the third rotation applied to the ellipsoid, around the
     *            Z-axis, in degrees.
     */
    public Ellipsoid3D(double centerX, double centerY, double centerZ, double r1, double r2, double r3, double eulerAngleX, double eulerAngleY, double eulerAngleZ)
    {
        this.center = new Point3D(centerX, centerY, centerZ);
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.eulerAngleX = eulerAngleX;
        this.eulerAngleY = eulerAngleY;
        this.eulerAngleZ = eulerAngleZ;
    }

    
    // ===================================================================
    // Methods specific to Ellipsoid3D
    
    /**
     * Returns the center of this ellipsoid as a Point3D.
     * 
     * @return the center of the ellipsoid.
     */
    public Point3D center()
    {
        return this.center;
    }
    
    public double[] radiusList()
    {
        return new double[] {r1, r2, r3};
    }
    
    /**
     * Returns the orientation of this ellipsoid, as a Rotation3D object. 
     * 
     * @return the orientation of the ellipsoid.
     */
    public Rotation3D orientation()
    {
        return Rotation3D.fromEulerAngles(Math.toRadians(eulerAngleX), Math.toRadians(eulerAngleY), Math.toRadians(eulerAngleZ));
    }
    
    /**
     * Returns the three Euler angles that define the orientation of this
     * ellipsoid, in an array with three elements. Angles are given in degrees,
     * in XYZ order.
     * 
     * @return the three Euler angles defining the orientation, in degrees in
     *         XYZ order.
     */
    public double[] eulerAngles()
    {
        return new double[] {eulerAngleX, eulerAngleY, eulerAngleZ};
    }
    


    // ===================================================================
    // Methods that mimics the Boundary2D interface

    /**
     * Returns the signed distance of the point to this boundary.
     * 
     * Let <em>dist</em> be the distance of the point to the curve. The signed
     * distance is defined by:
     * <ul>
     * <li>-dist if the point is inside the region defined by the boundary</li>
     * <li>+dist if the point is outside the region.</li>
     * </ul>
     * 
     * @param point
     *            a point in the plane
     * @return the signed distance of the point to the boundary
     */
    public double signedDistance(Point3D point)
    {
        return distance(point) * (isInside(point) ? -1 : +1);
    }
    
    /**
     * Returns the signed distance of the point to this boundary.
     * 
     * Let <em>dist</em> be the distance of the point to the curve. The signed
     * distance is defined by:
     * <ul>
     * <li>-dist if the point is inside the region defined by the boundary</li>
     * <li>+dist if the point is outside the region.</li>
     * </ul>
     * 
     * @param x
     *            the x-coordinate of the query point
     * @param y
     *            the y-coordinate of the query point
     * @return the signed distance of the point to the boundary
     */
    public double signedDistance(double x, double y, double z)
    {
        return distance(x, y, z) * (isInside(x, y, z) ? -1 : +1);
    }

    /**
     * Checks if the specified point is contained within the domain bounded by
     * this boundary.
     * 
     * @param point
     *            the point to test
     * @return true is the point is within the domain corresponding to this
     *         boundary.
     */
    public boolean isInside(Point3D point)
    {
        return globalToLocalTransform().transform(point).distance(0, 0, 0) <= 1;
    }

    /**
     * Checks if the specified point is contained within the domain bounded by
     * this boundary.
     * 
     * @param x
     *            the x-coordinate of the point to test
     * @param y
     *            the y-coordinate of the point to test
     * @return true is the point is within the domain corresponding to this
     *         boundary.
     */
    public boolean isInside(double x, double y, double z)
    {
        return globalToLocalTransform().transform(new Point3D(x, y, z)).distance(0, 0, 0) <= 1;
    }
    
    
    // ===================================================================
    // Implementation of the Geometry3D interface

    @Override
    public double distance(double x, double y, double z)
    {
        // use a discrete approximation of the ellipsoid
        Point3D[][] verts = surfaceVertices(240, 120);
        
        // initialize distance
        double dist = Double.POSITIVE_INFINITY;
        
        // iterate over vertices
        for (int i = 0; i < verts.length; i++)
        {
            for (int j = 0; j < verts[i].length; j++)
            {
                dist = Math.min(dist, verts[i][j].distance(x, y, z));
            }
        }
        
        // concatenate into a new Bounds3D object
        return dist;
    }

    @Override
    public Bounds3D bounds()
    {
        // use a discrete approximation of the ellipsoid
        Point3D[][] verts = surfaceVertices(240, 120);
        
        // initialize bounds
        double xmin = Double.POSITIVE_INFINITY;
        double xmax = Double.NEGATIVE_INFINITY;
        double ymin = Double.POSITIVE_INFINITY;
        double ymax = Double.NEGATIVE_INFINITY;
        double zmin = Double.POSITIVE_INFINITY;
        double zmax = Double.NEGATIVE_INFINITY;
        
        // iterate over vertices
        for (int i = 0; i < verts.length; i++)
        {
            for (int j = 0; j < verts[i].length; j++)
            {
                Point3D p = verts[i][j];
                xmin = Math.min(xmin, p.getX());
                xmax = Math.max(xmax, p.getX());
                ymin = Math.min(ymin, p.getY());
                ymax = Math.max(ymax, p.getY());
                zmin = Math.min(zmin, p.getZ());
                zmax = Math.max(zmax, p.getZ());
            }
        }
        
        // concatenate into a new Bounds3D object
        return new Bounds3D(xmin, xmax, ymin, ymax, zmin, zmax);
    }

    @Override
    public boolean contains(Point3D point, double eps)
    {
        Point3D point2 = globalToLocalTransform().transform(point);
        return Math.abs(point2.distance(0, 0, 0) - 1.0) < eps;
    }
    
    
    // ===================================================================
    // Implementation of the Geometry interface

    /**
     * Returns true, as an ellipsoid is always bounded.
     * 
     * @return true.
     */
    @Override
    public boolean isBounded()
    {
        return true;
    }
    
    
    // ===================================================================
    // Private computation methods
    
    private Point3D[][] surfaceVertices(int nPhi, int nTheta)
    {
        // pre-compute angle values for phi
        double[] phiList = new double[nPhi+1];
        for (int i = 0; i <= nPhi; i++)
        {
            phiList[i] = i * 2.0 * Math.PI / (double) nPhi;
        }
        
        // pre-compute sin and cos values for theta
        double[] sinTheta = new double[nTheta+1];
        double[] cosTheta = new double[nTheta+1];
        for (int i = 0; i <= nTheta; i++)
        {
            double theta = (double) i * Math.PI / (double) nTheta;
            sinTheta[i] = Math.sin(theta);
            cosTheta[i] = Math.cos(theta);
        }
        
        // retrieve coordinates transform
        AffineTransform3D transfo = localToGlobalTransform();
        
        // allocate memory
        Point3D[][] res = new Point3D[nTheta+1][nPhi+1];
        
        // iterate over pairs of spherical coordinates
        for (int iPhi = 0; iPhi <= nPhi; iPhi++)
        {
            // pre-compute trigonometric projections of phi
            double cosPhi = Math.cos(phiList[iPhi]);
            double sinPhi = Math.sin(phiList[iPhi]);
            
            // process current "meridian"
            for (int iTheta = 0; iTheta <= nTheta; iTheta++)
            {
                double sit = sinTheta[iTheta];
                double cot = cosTheta[iTheta];
                res[iTheta][iPhi] = transfo.transform(new Point3D(cosPhi * sit, sinPhi * sit, cot));
            }
        }
        
        return res;
    }
    
    private AffineTransform3D localToGlobalTransform()
    {
        AffineTransform3D sca = AffineTransform3D.createScaling(r1, r2, r3);
        AffineTransform3D rot = AffineTransform3D.fromMatrix(orientation().affineMatrix());
        AffineTransform3D tra = AffineTransform3D.createTranslation(center);
        return tra.concatenate(rot).concatenate(sca);
    }
    
    private AffineTransform3D globalToLocalTransform()
    {
        AffineTransform3D tra = AffineTransform3D.createTranslation(-center.getX(), -center.getY(), -center.getZ());
        AffineTransform3D rot = AffineTransform3D.fromMatrix(orientation().inverse().affineMatrix());
        AffineTransform3D sca = AffineTransform3D.createScaling(1.0 / r1, 1.0 / r2, 1.0 / r3);
        return sca.concatenate(rot).concatenate(tra);
    }

}
