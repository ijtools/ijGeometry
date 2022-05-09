/**
 * 
 */
package net.ijt.geom3d.mesh.io;

import java.io.IOException;

import net.ijt.geom3d.mesh.Mesh3D;

/**
 * Interface for reading a mesh from a file.
 * 
 * @author dlegland
 *
 */
public interface MeshReader
{
    /**
     * Reads a mesh.
     * 
     * @return a new Mesh3D instance
     * @throws IOException
     *             if there was a problem during mesh reading.
     */
	public Mesh3D readMesh() throws IOException;
}
