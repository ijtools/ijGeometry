package net.ijt.geometry.geom3d;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;


public class Sphere3DTest
{
    
    @Test
    public final void testVolume()
    {
        double r = 5;
        Sphere3D sphere = new Sphere3D(new Point3D(2, 3, 4), r);
        
        double exp = r * r * r * 4 * Math.PI / 3;
        double vol = sphere.volume();
        assertEquals(exp, vol, .01);
    }
    
    @Test
    public final void testSurfaceArea()
    {
        double r = 5;
        Sphere3D sphere = new Sphere3D(new Point3D(2, 3, 4), r);
        
        double exp = r * r * 4 * Math.PI;
        double surf = sphere.surfaceArea();
        assertEquals(exp, surf, .01);
    }
    
    @Test
    public final void testIntersections_StraightLine3D_Ox()
    {

        Point3D center = new Point3D(5, 4, 3);
        double radius = 6;
        Sphere3D sphere = new Sphere3D(center, radius);
        StraightLine3D line = new StraightLine3D(center, new Vector3D(2, 0, 0));
        
        Collection<Point3D> inters = sphere.intersections(line);
        
        assertEquals(2, inters.size());
        
        Point3D p1 = new Point3D(center.x()-radius, center.y(), center.z());
        Point3D p2 = new Point3D(center.x()+radius, center.y(), center.z());
        assertContainsPoint(inters, p1, .001);
        assertContainsPoint(inters, p2, .001);
    }

    private void assertContainsPoint(Collection<Point3D> coll, Point3D p, double tol)
    {
        for (Point3D point : coll)
        {
            if (point.almostEquals(p, tol))
            {
                return;
            }
        }
        
        Assert.fail("Could not find the point in the collection");
    }
}
