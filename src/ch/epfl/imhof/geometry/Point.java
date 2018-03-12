package ch.epfl.imhof.geometry;

import java.util.function.Function;

/**
 * Un point d'une carte en coordonnées cartésiennes (x,y).
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class Point {
    
    private final double x;
    private final double y;
    
     /**
     * Construit un point sur une carte 
     * avec ses coordonnées cartésiennes x et y.
     *
     * @param x
     *            coordonnée de l'axe x du point
     * @param y
     *            coordonnée de l'axe y du point
     *            
     */
    
    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * getteur de la coordonnée x
     * 
     * @return x,
     *           coordonnée de l'axe x du point
     *          
     */
    public double x (){
        return x;
    }
    /**
     * getteur de la coordonnée y
     * 
     * @return y,
     *          coordonée y du point
     */
    public double y() {
        return y;
    }
    
    /**
     * fonction qui calcule un changement de repères alignés d'un point vers un autre
     * 
     * @param pA1
     *          Point A dans le repère 1
     * 
     * @param pA2
     *          Point A dans le repère 2
     * 
     * @param pB1
     *          Point B dans le repère 1
     * 
     * @param pB2
     *          Point B dans le repère 2
     * 
     * @throws IllegalArgumentException
     *          si les deux points sont situés sur une même ligne horizontale ou verticale
     * 
     * @return
     *          Function du point C du repère 1 au repère 2
     * 
     */
    public static Function<Point, Point> alignedCoordinateChange(Point pA1, Point pA2, Point pB1, Point pB2){
        double xA1 = pA1.x;
        double yA1 = pA1.y;
        
        double xA2 = pA2.x;
        double yA2 = pA2.y;
        
        double xB1 = pB1.x;
        double yB1 = pB1.y;
        
        double xB2 = pB2.x;
        double yB2 = pB2.y;
        
        if(xB1==xA1 || yB1==yA1)
            throw new IllegalArgumentException("Points en paramètre impossibles");
        
        double translationX = xA2-(((xB2 - xA2)/(xB1 - xA1))*xA1);
        double translationY = yA2-(((yB2 - yA2)/(yB1 - yA1))*yA1);
        
        double dilatationX = (xB2 - xA2)/(xB1 - xA1);
        double dilatationY = (yB2 - yA2)/(yB1 - yA1);
        
        return p -> new Point((dilatationX * p.x) + translationX, (dilatationY * p.y) + translationY );
    }
}
