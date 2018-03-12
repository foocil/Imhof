package ch.epfl.imhof.projection;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.geometry.Point;

/**
 * Projection d'un point selon la projection CH1903 et la méthode
 * inverse.
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class CH1903Projection implements Projection {

    /**
     * Méthode de projection CH1903
     * 
     * @param point
     *              Un point à la surface de la Terre, en coordonnées sphériques
     * 
     * @return Point, 
     *              Un point d'une carte en coordonnées cartésiennes (x,y)
     *              
     */
    @Override
    public Point project(PointGeo point) {
        double l = Math.toDegrees(point.longitude());
        double p = Math.toDegrees(point.latitude());

        double l1 = (1.0 / 10000) * (l * 3600 - 26782.5);
        double p1 = (1.0 / 10000) * (p * 3600 - 169028.66);
        
        double l1Scare = Math.pow(l1, 2);
        double p1Scare = Math.pow(p1, 2);
        
        double x = 600072.37 + 211455.93 * l1 - 10938.51 * l1 * p1 - 0.36 * l1 * p1Scare - 44.54
                * Math.pow(l1, 3);

        double y = 200147.07 + 308807.95 * p1 + 3745.25 * l1Scare + 76.63 * p1Scare - 194.56
                * l1Scare * p1 + 119.79 * Math.pow(p1, 3);
        return new Point(x, y);
    }

    /**
     * Méthode inverse de projection CH1903
     * 
     * @param Point
     *                   Un point d'une carte en coordonnées cartésiennes (x,y)
     *              
     * @return PointGeo,
     *                   Un point à la surface de la Terre, en coordonnées sphériques
     *                   
     */
    @Override
    public PointGeo inverse(Point point) {
        double x = point.x();
        double y = point.y();

        double x1 = (x - 600000) / 1000000;
        double y1 = (y - 200000) / 1000000;

        double l0 = 2.6779094 + 4.728982 * x1 + 0.791484 * x1 * y1 + 0.1306 * x1 * Math.pow(y1, 2) - 0.0436
                * Math.pow(x1, 3);

        double p0 = 16.9023892 + 3.238272 * y1 - 0.270978 * Math.pow(x1, 2) - 0.002528 * Math.pow(y1, 2) - 0.0447
                * Math.pow(x1, 2) * y1 - 0.0140 * Math.pow(y1, 3);
        double l = Math.toRadians(l0 * (100.0 / 36));
        double p = Math.toRadians(p0 * (100.0 / 36));

        return new PointGeo(l, p);

    }
}
