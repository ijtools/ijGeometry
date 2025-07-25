/**
 * 
 */
package net.ijt.geometry.mesh3d.process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.ijt.geometry.geom3d.Point3D;
import net.ijt.geometry.mesh3d.Mesh3D;
import net.ijt.geometry.mesh3d.SimpleTriMesh3D;
import net.ijt.geometry.mesh3d.Mesh3D.Face;
import net.ijt.geometry.mesh3d.Mesh3D.Vertex;

/**
 * Smoothes a mesh
 * 
 * @author dlegland
 *
 */
public class Smooth
{
    /**
     * Empty contructor.
     */
    public Smooth()
    {
    }
    
    public Mesh3D process(Mesh3D mesh)
    {
        // number of elements in the input mesh
        int nv = mesh.vertexCount();
        int nf = mesh.faceCount();

        // create result mesh (assuming triangular mesh)
        SimpleTriMesh3D result = new SimpleTriMesh3D(nv, nf);

        // keep correspondence between vertices of the two meshes
        Map<Mesh3D.Vertex, Mesh3D.Vertex> vertexMap = new HashMap<>();
        
        // Iterate over vertices
        for (Mesh3D.Vertex vertex : mesh.vertices())
        {
            // initialize average with position of current vertex
            Point3D pos = vertex.position();
            double xm = pos.x(), ym = pos.y(), zm = pos.z();
            int nn = 1;
            
            // iterate over neighbors
            for (Vertex neigh : mesh.vertexNeighbors(vertex))
            {
                pos = neigh.position();
                xm += pos.x(); 
                ym += pos.y(); 
                zm += pos.z();
                nn++;
            }
            
            // divide by number of neighbors
            xm /= nn;
            ym /= nn;
            zm /= nn;
            
            // add the new vertex, and keep correspondence with initial vertex
            Vertex newVertex = result.addVertex(new Point3D(xm, ym, zm));
            vertexMap.put(vertex, newVertex);
        }
        
        // also add faces, using vertex correspondence
        for (Face face : mesh.faces())
        {
            Iterator<? extends Vertex> iter = mesh.faceVertices(face).iterator();
            Vertex v1 = vertexMap.get(iter.next());
            Vertex v2 = vertexMap.get(iter.next());
            Vertex v3 = vertexMap.get(iter.next());
            result.addFace(v1, v2, v3);
        }
        
        return result;
    }
}
