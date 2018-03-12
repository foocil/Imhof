package ch.epfl.imhof.geometry;

import java.util.List;

import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;




/**
 * Sous-classe de PolyLine. Une PolyLine est ouverte si son premier et son dernier sommets ne sont
 * pas reliés par un segment
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class OpenPolyLine extends PolyLine {

    /**
     * construit la polyline ouverte avec une liste de sommets
     * 
     * @param points,
     *            liste des sommets
     */
    
    public OpenPolyLine(List<Point> points) {
        super(points);
    }

    /**
     * méthode qui indique que la polyline est ouverte
     * 
     * @return false 
     *              
     */

    public boolean isClosed() {
        return false;
    }

}
