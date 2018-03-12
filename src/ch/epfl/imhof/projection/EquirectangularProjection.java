package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * Projection équirectangulaire d'un Pointgeo et la méthode inverse.
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class EquirectangularProjection implements Projection {

    /**
     * Méthode de projection équirectangulaire
     * 
     * @param PointGeo
     *              Un point à la surface de la Terre, en coordonnées sphériques
     * 
     * @return Point, 
     *              Un point d'une carte en coordonnées cartésiennes (x,y). 
     *              
     */

    public Point project(PointGeo point) {
       
        return new Point(point.longitude(), point.latitude());
    }

    /**
     * Méthode inverse de projection équirectangulaire
     * 
     * @param Point
     *              Un point d'une carte en coordonnées cartésiennes (x,y).
     * 
     * @return PointGeo, 
     *               Un point à la surface de la Terre, en coordonnées sphériques
     *               
     */
    
    public PointGeo inverse(Point point) {
        return new PointGeo(point.x(), point.y());
    }
}
