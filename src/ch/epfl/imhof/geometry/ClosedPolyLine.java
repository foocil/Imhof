package ch.epfl.imhof.geometry;

import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Point;

/**
 * Une sous classe de PolyLine : son premier et son dernier sommets sont
 * reliés par un segment
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class ClosedPolyLine extends PolyLine {

    /**
     * construit une polyline fermée avec la liste des sommets
     * 
     * @param points,
     *            la liste de sommets de la polyline
     *            
     */
    
    public ClosedPolyLine(List<Point> points) {
        super(points);
    }

    /**
     * méthode qui indique que la polyline est fermée
     * 
     * @return true
     * 
     */
    
    public boolean isClosed() {
        return true;
    }

    /**
     * méthode qui retourne l'aire (toujours positive) de la polyline fermée
     * 
     * @return aire,
     *              aire de la polyline fermée
     */

    public double area() {
        double area = 0.0;
        
        double xi = 0.0;
        double yiplus1 = 0.0;
        double yiminus1 = 0.0;

        for (int i = 0; i < points().size(); i++) {
            xi = points().get(indiceGeneralise(i)).x();
            yiplus1 = points().get(indiceGeneralise(i + 1)).y();
            yiminus1 = points().get(indiceGeneralise(i - 1)).y();

            area += xi * (yiplus1 - yiminus1);
        }
        return Math.abs(area / 2);

    }

    /**
     * méthode pour savoir si un point est à l'intérieur de la polyline. 
     * 
     * @param p
     *            un point donnée
     * 
     * @return 
     *           true: si le point est à l'intérieur de la polyline,
     *           false: si le point est à l extérieur de la polyline
     * 
     */
    
    public boolean containsPoint(Point p) {

        
        int indice = 0;
        for (int i = 0; i < points().size(); i++) {

            Point p1 = points().get(indiceGeneralise(i));
            Point p2 = points().get(indiceGeneralise(i + 1));

            if (p1.y() <= p.y()) {

                if (p2.y() > p.y() && isLeft(p, p1, p2)) {

                    indice++;
                }
            } else if (points().get(indiceGeneralise(i + 1)).y() <= p.y() && isLeft(p, p2, p1)) {
                indice--;

            }
        }
        return (indice != 0);

    }

    /**
     * méthode pour savoir si un point est à gauche d'une ligne définie par 2 points
     * 
     * @param p
     *            le point dont on veut savoir s'il est à gauche
     *            
     * @param p1
     *            premier point qui forme la ligne
     *            
     * @param p2
     *            deuxième point qui forme la ligne
     *            
     * @return  
     *             vrai: si le point est a gauche ,
     *             faux: si le point est a droite
     * 
     */
    
    private boolean isLeft(Point p, Point p1, Point p2) {

        double x = p.x();
        double y = p.y();
        double x1 = p1.x();
        double y1 = p1.y();
        double x2 = p2.x();
        double y2 = p2.y();

        return (((x1 - x) * (y2 - y) - (x2 - x) * (y1 - y)) > 0);

    }

    /**
     * Transforme l'indice généralisé en indice valide
     * 
     * @param indice généralisé
     *              
     * @return
     *          indice valide
     */
    
    private int indiceGeneralise(int indice) {
        return Math.floorMod(indice, points().size());

    }

}
