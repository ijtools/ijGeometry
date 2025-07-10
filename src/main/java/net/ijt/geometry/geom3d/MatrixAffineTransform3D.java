/**
 * 
 */
package net.ijt.geometry.geom3d;

import java.util.Locale;

/**
 * Concrete implementation of a 3D affine transform, that stores the twelve
 * coefficients.
 * 
 * @author dlegland
 *
 */
public class MatrixAffineTransform3D implements AffineTransform3D
{
	// ===================================================================
	// Class members

	// coefficients for x coordinate.
	protected double m00, m01, m02, m03;

	// coefficients for y coordinate.
	protected double m10, m11, m12, m13;

    // coefficients for y coordinate.
    protected double m20, m21, m22, m23;
	
    
	// ===================================================================
	// Constructors

	/**
	 * Empty constructor, that creates an instance of the identity transform.
	 */
	public MatrixAffineTransform3D()
	{
		m00 = 1;
		m01 = 0;
        m02 = 0;
        m03 = 0;
        m10 = 0;
        m11 = 1;
        m12 = 0;
        m13 = 0;
        m20 = 0;
        m21 = 0;
        m22 = 1;
        m23 = 0;
	}

	public MatrixAffineTransform3D(
            double xx, double yx, double zx, double tx, 
            double xy, double yy, double zy, double ty, 
            double xz, double yz, double zz, double tz)
	{
		m00 = xx;
		m01 = yx;
        m02 = zx;
        m03 = tx;
        m10 = xy;
        m11 = yy;
        m12 = zy;
        m13 = ty;
        m20 = xz;
        m21 = yz;
        m22 = zz;
        m23 = tz;
	}


	// ===================================================================
	// general methods

//	/**
//	 * Returns the affine transform created by applying first the affine
//	 * transform given by <code>that</code>, then this affine transform.
//	 * 
//	 * @param that
//	 *            the transform to apply first
//	 * @return the composition this * that
//	 */
//	public MatrixAffineTransform2d concatenate(MatrixAffineTransform2d that)
//	{
//		double n00 = this.m00 * that.m00 + this.m01 * that.m10;
//		double n01 = this.m00 * that.m01 + this.m01 * that.m11;
//		double n02 = this.m00 * that.m02 + this.m01 * that.m12 + this.m02;
//		double n10 = this.m10 * that.m00 + this.m11 * that.m10;
//		double n11 = this.m10 * that.m01 + this.m11 * that.m11;
//		double n12 = this.m10 * that.m02 + this.m11 * that.m12 + this.m12;
//		return new MatrixAffineTransform2d(n00, n01, n02, n10, n11, n12);
//	}
//
//	/**
//	 * Return the affine transform created by applying first this affine
//	 * transform, then the affine transform given by <code>that</code>.
//	 * 
//	 * @param that
//	 *            the transform to apply in a second step
//	 * @return the composition that * this
//	 */
//	public MatrixAffineTransform2d preConcatenate(MatrixAffineTransform2d that)
//	{
//		return new MatrixAffineTransform2d(that.m00 * this.m00 + that.m01 * this.m10,
//				that.m00 * this.m01 + that.m01 * this.m11, that.m00 * this.m02
//						+ that.m01 * this.m12 + that.m02, that.m10 * this.m00
//						+ that.m11 * this.m10, that.m10 * this.m01 + that.m11
//						* this.m11, that.m10 * this.m02 + that.m11 * this.m12
//						+ that.m12);
//	}

	public Point3D transform(Point3D p)
	{
		double x = p.x();
        double y = p.y();
        double z = p.z();
		return new Point3D(
                x * m00 + y * m01 + z * m02 + m03, 
                x * m10 + y * m11 + z * m12 + m13, 
                x * m20 + y * m21 + z * m22 + m23);
	}

	/**
	 * Transforms a vector, by using only the linear part of this transform.
	 * 
	 * @param v
	 *            the vector to transform
	 * @return the transformed vector
	 */
	public Vector3D transform(Vector3D v)
	{
		double vx = v.x();
		double vy = v.y();
		double vz = v.z();
		return new Vector3D(
				vx * m00 + vy * m01 + vz * m02, 
				vx * m10 + vy * m11 + vz * m12, 
				vx * m20 + vy * m21 + vz * m22);
	}

	/**
	 * Returns the inverse transform. If the transform is not invertible, throws
	 * a new RuntimeException.
	 * 
	 * @return the inverse of this transform
	 * @throws a RuntimeException
	 */
	public MatrixAffineTransform3D inverse()
	{
        double det = this.determinant();

        // check invertibility
        if (Math.abs(det) < 1e-12)
            throw new RuntimeException("Non-invertible matrix");
        
        return new MatrixAffineTransform3D(
                (m11 * m22 - m21 * m12) / det,
                (m21 * m02 - m01 * m22) / det,
                (m01 * m12 - m11 * m02) / det,
                (m01 * (m22 * m13 - m12 * m23) + m02 * (m11 * m23 - m21 * m13) 
                        - m03 * (m11 * m22 - m21 * m12)) / det, 
                (m20 * m12 - m10 * m22) / det, 
                (m00 * m22 - m20 * m02) / det, 
                (m10 * m02 - m00 * m12) / det, 
                (m00 * (m12 * m23 - m22 * m13) - m02 * (m10 * m23 - m20 * m13) 
                        + m03 * (m10 * m22 - m20 * m12)) / det, 
                (m10 * m21 - m20 * m11) / det, 
                (m20 * m01 - m00 * m21) / det,
                (m00 * m11 - m10 * m01) / det, 
                (m00 * (m21 * m13 - m11 * m23) + m01 * (m10 * m23 - m20 * m13) 
                        - m03 * (m10 * m21 - m20 * m11))    / det);
	}


    /**
     * Computes the determinant of this affine transform. Can be zero.
     * 
     * @return the determinant of the transform.
     */
    private double determinant()
    {
        return m00 * (m11 * m22 - m12 * m21) - m01 * (m10 * m22 - m20 * m12)
                + m02 * (m10 * m21 - m20 * m11);
    }

    @Override
    public double[][] affineMatrix()
    {
        return new double[][] {
                { this.m00, this.m01, this.m02, this.m03 },
                { this.m10, this.m11, this.m12, this.m13 },
                { this.m20, this.m21, this.m22, this.m23 },
                { 0, 0, 0, 1 } };
    }
    
    
    @Override
    public String toString()
    {
        String pattern = "DefaultAffineTransform3D(%5.3f, %5.3f, %5.3f, %7.2f,  %5.3f, %5.3f, %5.3f, %7.2f,  %5.3f, %5.3f, %5.3f, %7.2f,  0, 0, 0, 1)";
        return String.format(Locale.ENGLISH, pattern, m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23);
    }

}
