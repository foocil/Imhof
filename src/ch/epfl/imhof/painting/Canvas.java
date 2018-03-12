package ch.epfl.imhof.painting;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * classe abstraite représentant une toile
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */
public interface Canvas {

    /**
     * permettant de dessiner sur la toile une polyligne donnée avec un style de ligne donné
     * 
     * @param style
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     * 
     * @param polyline
     *          une ligne formée en reliant ses sommets
     * 
     */
    void drawPolyLine(LineStyle style, PolyLine polyline);

    /**
     * permettant de dessiner sur la toile un polygone donné avec une couleur donnée 
     * 
     * @param color
     *          une couleur décrite par ses 3 composantes
     * 
     * @param polygone
     *          PolyLine pouvant contenir plusieurs trous
     * 
     */
    void drawPolygon(ColorCreation color, Polygon polygone);
}
