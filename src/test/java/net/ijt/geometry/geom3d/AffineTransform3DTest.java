/**
 * 
 */
package net.ijt.geometry.geom3d;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author dlegland
 *
 */
public class AffineTransform3DTest
{

    /**
     * Test method for {@link net.ijt.geometry.geom3d.AffineTransform3D#createTranslation(double, double, double)}.
     */
    @Test
    public final void testCreateTranslationDoubleDoubleDouble()
    {
        double tx = 50;
        double ty = 40;
        double tz = 30;
        
        AffineTransform3D tra = AffineTransform3D.createTranslation(tx, ty, tz);
        double[][] mat = tra.affineMatrix();
        
        assertEquals(1.0, mat[0][0], 0.01);
        assertEquals(1.0, mat[1][1], 0.01);
        assertEquals(1.0, mat[2][2], 0.01);
        assertEquals(1.0, mat[3][3], 0.01);
        assertEquals(50.0, mat[0][3], 0.01);
        assertEquals(40.0, mat[1][3], 0.01);
        assertEquals(30.0, mat[2][3], 0.01);
    }

    /**
     * Test method for {@link net.ijt.geometry.geom3d.AffineTransform3D#createScaling(double, double, double)}.
     */
    @Test
    public final void testCreateScalingDoubleDoubleDouble()
    {
        double sx = 5;
        double sy = 4;
        double sz = 3;
        
        AffineTransform3D tra = AffineTransform3D.createScaling(sx, sy, sz);
        double[][] mat = tra.affineMatrix();
        
        assertEquals(5.0, mat[0][0], 0.01);
        assertEquals(4.0, mat[1][1], 0.01);
        assertEquals(3.0, mat[2][2], 0.01);
        assertEquals(1.0, mat[3][3], 0.01);
        assertEquals(0.0, mat[0][3], 0.01);
        assertEquals(0.0, mat[1][3], 0.01);
        assertEquals(0.0, mat[2][3], 0.01);
    }

    /**
     * Test method for {@link net.ijt.geometry.geom3d.AffineTransform3D#createRotationOx(double)}.
     */
    @Test
    public final void testCreateRotationOx_30degrees()
    {
        double angle = Math.toRadians(30.0);
        AffineTransform3D tra = AffineTransform3D.createRotationOx(angle);
        double[][] mat = tra.affineMatrix();
        
        assertEquals( 1.0, mat[0][0], 0.01);
        assertEquals(Math.sqrt(3)/2, mat[1][1], 0.01);
        assertEquals(-0.5, mat[1][2], 0.01);
        assertEquals( 0.5, mat[2][1], 0.01);
        assertEquals(Math.sqrt(3)/2, mat[2][2], 0.01);
        assertEquals(1.0, mat[3][3], 0.01);
        assertEquals(0.0, mat[0][3], 0.01);
        assertEquals(0.0, mat[1][3], 0.01);
        assertEquals(0.0, mat[2][3], 0.01);
    }

    /**
     * Test method for {@link net.ijt.geometry.geom3d.AffineTransform3D#createRotationOy(double)}.
     */
    @Test
    public final void testCreateRotationOy_30degrees()
    {
        double angle = Math.toRadians(30.0);
        AffineTransform3D tra = AffineTransform3D.createRotationOy(angle);
        double[][] mat = tra.affineMatrix();
        
        assertEquals(Math.sqrt(3)/2, mat[0][0], 0.01);
        assertEquals( 0.5, mat[0][2], 0.01);
        assertEquals( 1.0, mat[1][1], 0.01);
        assertEquals(-0.5, mat[2][0], 0.01);
        assertEquals(Math.sqrt(3)/2, mat[2][2], 0.01);
        assertEquals(1.0, mat[3][3], 0.01);
        assertEquals(0.0, mat[0][3], 0.01);
        assertEquals(0.0, mat[1][3], 0.01);
        assertEquals(0.0, mat[2][3], 0.01);
    }

    /**
     * Test method for {@link net.ijt.geometry.geom3d.AffineTransform3D#createRotationOz(double)}.
     */
    @Test
    public final void testCreateRotationOz_30degrees()
    {
        double angle = Math.toRadians(30.0);
        AffineTransform3D tra = AffineTransform3D.createRotationOz(angle);
        double[][] mat = tra.affineMatrix();
        
        assertEquals(Math.sqrt(3)/2, mat[0][0], 0.01);
        assertEquals(-0.5, mat[0][1], 0.01);
        assertEquals( 0.5, mat[1][0], 0.01);
        assertEquals(Math.sqrt(3)/2, mat[1][1], 0.01);
        assertEquals( 1.0, mat[2][2], 0.01);
        assertEquals(1.0, mat[3][3], 0.01);
        assertEquals(0.0, mat[0][3], 0.01);
        assertEquals(0.0, mat[1][3], 0.01);
        assertEquals(0.0, mat[2][3], 0.01);
    }

    /**
     * Test method for {@link net.ijt.geometry.geom3d.AffineTransform3D#concatenate(net.ijt.geometry.geom3d.AffineTransform3D)}.
     */
    @Test
    public final void testConcatenate()
    {
        AffineTransform3D rotX = AffineTransform3D.createRotationOx(Math.toRadians(60.0));
        AffineTransform3D rotY = AffineTransform3D.createRotationOy(Math.toRadians(30.0));
        
        AffineTransform3D transfo = rotY.concatenate(rotX);
        double[][] mat = transfo.affineMatrix();
        
        assertEquals( 0.866, mat[0][0], 0.01);
        assertEquals( 0.433, mat[0][1], 0.01);
        assertEquals( 0.25 , mat[0][2], 0.01);
        assertEquals( 0.0  , mat[1][0], 0.01);
        assertEquals( 0.5  , mat[1][1], 0.01);
        assertEquals(-0.866, mat[1][2], 0.01);
        assertEquals(-0.5  , mat[2][0], 0.01);
        assertEquals( 0.75 , mat[2][1], 0.01);
        assertEquals( 0.433, mat[2][2], 0.01);
    }

}
