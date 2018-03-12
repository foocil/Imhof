package ch.epfl.imhof;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * représente une carte projetée, composée d'entités géométriques attribuées. 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class Map {
    private final List<Attributed<PolyLine>> polyLines;
    private final List<Attributed<Polygon>> polygons;
    
    
    /**
     * construit une carte à partir des listes de polylignes et polygones attribués donnés.
     * 
     * @param polyLines
     *                  Liste de PolyLines qui ont été attribuées
     *                  
     * @param polygons
     *                  Liste de polygone qui ont été attribuées
     * 
     */
    
    public Map(List<Attributed<PolyLine>> polyLines, List<Attributed<Polygon>> polygons) {
        this.polyLines = Collections.unmodifiableList(new ArrayList<Attributed<PolyLine>>(polyLines));
        this.polygons = Collections.unmodifiableList(new ArrayList<Attributed<Polygon>>(polygons));
    }
    
    /**retourne la liste des polylignes attribuées de la carte
     * 
     * @return 
     *          Liste de PolyLines qui ont été attribuées
     * 
     */

    public List<Attributed<PolyLine>> polyLines() {
        return  polyLines;
        
    }
    
    /**
     * retourne la liste des polygones attribués de la carte.
     * 
     * @return 
     *          Liste de polygons qui ont été attribuées
     * 
     */
 
    public List<Attributed<Polygon>> polygons(){
        return polygons;
    }
    
    /**
     *  sert de bâtisseur à cette dernière
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */
    
    public static final class Builder {
 
        private List<Attributed<PolyLine>> polyLines = new ArrayList<>();
        private List<Attributed<Polygon>> polygons = new ArrayList<>();
        
        //constructeur par défaut.
        
        /**
         * ajoute une polyLine attribuée à la carte en cours de construction
         * 
         * @param newPolyLine
         *          polyLine à attribuer à la carte
         * 
         */
        
        public void addPolyLine(Attributed<PolyLine> newPolyLine){
            polyLines.add(newPolyLine);
        }
        
        /**
         *  ajoute un polygone attribué à la carte en cours de construction
         * 
         * @param newPolygon
         *           polygone à attribuer à la carte
         * 
         */
        
        public void addPolygon(Attributed<Polygon> newPolygon) {
            polygons.add(newPolygon);
        }
        
        /**
         * construit une carte avec les polylignes et polygones ajoutés jusqu'à présent au bâtisseur
         * 
         * @return Map,
         *          une carte projetée, composée d'entités géométriques attribuées
         * 
         */
        
        public Map build() {
            return new Map(polyLines, polygons);
            
        }
     
    }
        
}
