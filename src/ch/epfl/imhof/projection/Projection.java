package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * Interface avec méthode de projection sur une carte (de pointGeo à point) et
 * une méthode inverse (de point à pointGeo)
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public interface Projection {
    
    /**
     * méthode abstract de projection
     * 
     * @param PointGeo
     *              Un point à la surface de la Terre, en coordonnées sphériques
     *              
     * @return Point, 
     *              Un point d'une carte en coordonnées cartésiennes (x,y).
     *              
     */
    
    Point project(PointGeo point);

    /**
     * méthode abstract de projection inverse
     * 
     * @param Point
     *              Un point d'une carte en coordonnées cartésiennes (x,y).
     *              
     * @return PointGeo, 
     *              Point correspondant à la surface de la terre
     */
    
    PointGeo inverse(Point point);
}
