package ch.epfl.imhof.geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.Point;


/**
 * Classe abstraite modélisant une ligne formée en reliant ses sommets
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public abstract class PolyLine {

    private final List<Point> sommets;

    /**
     * Construit une polyline avec la liste des sommets
     * 
     * @param points
     *            la liste composée de sommets de la polyline
     * 
     * @throws IllegalArgumentException
     *             exception si la liste des sommets est vide
     * 
     */

    public PolyLine(List<Point> points) {
        
        if(points.isEmpty())
            throw new IllegalArgumentException("Liste vide...");
        else{
            List<Point> listeTemp = new ArrayList<>(points);
            sommets = Collections.unmodifiableList(listeTemp);
        }
    }

    /**
     * méthode abstraite pour savoir si le polyline est fermée ou ouverte
     * 
     * @return boolean,
     *          pour savoir si la polyLine est ouvert ou fermé
     *          
     */

    public abstract boolean isClosed();

    /**
     * getter de la liste de sommets
     * 
     * @return sommets, 
     *              la liste composée de sommets de la polyline
     *              
     */
    
    public List<Point> points() {
        return sommets;
    }

    /**
     * getter retournant le premier point de la liste
     * 
     * @return firstPoint, 
     *          le premier point de la liste composée des sommets de la polyline
     *          
     */
    
    public Point firstPoint() {
        return sommets.get(0);
    }

    /**
     * Builder pour construire une polyline
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */

    public final static class Builder {
        
        
        final private List<Point> sommets = new ArrayList<>();

        /**
         * méthode qui ajoute le point donné à la (fin de la) liste des sommets
         * de la polyligne
         * 
         * @param newPoint
         *            un nouveau point (x,y) à ajouter à la liste
         *            
         */
        
        public void addPoint(Point newPoint) {
            sommets.add(newPoint);

        }

        /**
         * méthode qui construit et retourne une polyligne ouverte avec les
         * points ajoutés jusqu'à présent.
         * 
         * @return Openpolyline,
         *                      une polyline ouverte
         *                      
         */

        public OpenPolyLine buildOpen() {
            return new OpenPolyLine(sommets);

        }

        /**
         * méthode qui construit et retourne une polyligne fermée avec les
         * points ajoutés jusqu'à présent.
         * 
         * @return Closedpolyline, 
         *                      une polyline fermée
         *                      
         */
        
        public ClosedPolyLine buildClosed() {
            return new ClosedPolyLine(sommets);

        }

    }
}
