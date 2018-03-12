package ch.epfl.imhof.dem;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * Interface représenant un modèle numérique
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public interface DigitalElevationModel extends AutoCloseable {
    
    /**
     * Méthode retournant un vecteur normal à la Terre du pointGeo passé en argument
     * 
     * @param WGS84
     *          Un point à la surface de la Terre
     * 
     * @return Vector3
     *          Un vecteur en 3 dimensions
     * 
     * @throws IllegalArgumentException
     *          Exception si le nom du fichier n'est pas correcte
     * 
     */
    
    Vector3 normalAt(PointGeo WGS84) throws IllegalArgumentException;
}
