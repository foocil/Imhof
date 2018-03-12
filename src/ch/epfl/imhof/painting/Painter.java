package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 * représente un peintre, dessine sur une toile
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public interface Painter {
    
    void drawMap(Map map, Java2DCanvas canvas);

    /**
     * retourne un peintre dessinant l'intérieur de tous les polygones de la carte qu'il reçoit avec cette couleur
     * 
     * @param color
     *          couleur pour dessiner l'intérieur de tous les polygones
     *          
     * @return  Painter
     *          peintre pour dessiner l'intérieur de tous les polygones avec la couleur
     */
    
    public static Painter polygon(ColorCreation color) {      
        return (map, canvas) -> {
           
            map.polygons().stream().forEach(polygon -> canvas.drawPolygon(color, polygon.value()));
            
        };
    }
    


    /**
     * retourne un peintre dessinant toutes les lignes de la carte qu'on lui fournit avec ce style
     * 
     * @param style
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     * 
     * @return Painter
     *          peintre pour dessiner toutes les lignes de la carte avec le style
     */
    
    public static Painter line(LineStyle style){
        return(map, canvas) -> {
           
            map.polyLines().stream().forEach(polyLine -> canvas.drawPolyLine(style, polyLine.value()));
            
        };
    }

    /**
     * retourne un peintre dessinant toutes les lignes de la carte qu'on lui fournit avec le style correspondant
     * 
     * @param width
     *          largeur du trait
     *          
     * @param color
     *          couleur du trait
     *          
     * @param cap
     *          terminaison des lignes
     * 
     * @param join
     *          jointure des segments
     *          
     * @param alternationSequency
     *          alternance des sections opaques et transparentes
     * 
     * @return
     *          peintre pour dessiner toutes les lignes de la carte
     *
     */
    
    public static Painter line(float width, ColorCreation color,
             LineCap cap, LineJoin join, float ... alternationSequency) {
        LineStyle style = new LineStyle(width, color, cap, join, alternationSequency);
        return line(style);
    }
    
    /**
     * retourne un peintre dessinant toutes les lignes de la carte qu'on lui fournit avec ce style
     * 
     * @param width
     *          largeur du trait
     *          
     * @param color
     *          couleur du trait
     * 
     * @return Painter
     *           peintre pour dessiner toutes les lignes de la carte avec le style
     * 
     */
    
    public static Painter line(float width, ColorCreation color) {
        LineStyle style = new LineStyle(width, color);
        return line(style);
    }

    /**
     * dessine les pourtours de l'enveloppe et des trous de tous les polygones de la carte qu'on lui fournit
     * 
     * @param style
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     * 
     * @return Painter
     *          peintre dessinant les pourtous de l'enveloppe et les trous des polygones
     *
     *
     */
    
    public static Painter outline(LineStyle style){
     //nous n'avons pas utilisé le stream ici par souci de clarté
        return (map, canvas) -> {
     
            for (Attributed<Polygon> polygone : map.polygons()) {
                canvas.drawPolyLine(style, polygone.value().shell());
                for (ClosedPolyLine closedPoly : polygone.value().holes()) {
                    canvas.drawPolyLine(style, closedPoly);
                }
            }
        };
        
    }
    
    
    /**
     * dessine les pourtours de l'enveloppe et des trous de tous les polygones de la carte qu'on lui fournit
     * 
     * @param width
     *          largeur du trait
     *          
     * @param color
     *          couleur du trait         
     * 
     * @param cap
     *          terminaison des lignes
     * 
     * @param join
     *          jointure des segments
     *  
     * @param alternationSequency
     *          alternance des sections opaques et transparentes
     *  
     * @return Painter
     *          peintre dessinant les pourtous de l'enveloppe et les trous des polygones
     *          
     */
    
    public static Painter outline(float width, ColorCreation color, LineCap cap, LineJoin join,
            float... alternationSequency) {
        LineStyle style = new LineStyle(width, color, cap, join, alternationSequency);
       return outline(style);
    }
    

    /**
     *dessine les pourtours de l'enveloppe et des trous de tous les polygones de la carte qu'on lui fournit
     * 
     * @param width
     *          largeur du trait
     *          
     * @param color
     *          couleur du trait
     * 
     * @return Painter
     *           peintre dessinant les pourtous de l'enveloppe et les trous des polygones
     * 
     */
    
    public static Painter outline(float width, ColorCreation color) {
        LineStyle style = new LineStyle(width, color);
        return outline(style);
    }
    
    /**
     * retournant un peintre se comportant comme celui auquel on l'applique, 
     * si ce n'est qu'il ne considère que les éléments de la carte satisfaisant le prédicat
     * 
     * @param predicat
     *          predicate confirmant si l'attribut est possédé ou non
     * 
     * @return Painter
     *          peintre se comportant comme celui auquel on l'applique
     */
    
    public default Painter when(Predicate<Attributed<?>> predicat) {
        return (map, canvas) -> {
            Map.Builder mappy = new Map.Builder(); 

              map.polygons().stream().filter(polygon -> predicat.test(polygon)).forEach(polygon -> mappy.addPolygon(polygon));
              
              map.polyLines().stream().filter(polyline -> predicat.test(polyline)).forEach(polyline -> mappy.addPolyLine(polyline));

             
            drawMap(mappy.build(), canvas); 
       };
    }
       
    /**
     * retournant un peintre dessinant d'abord la carte produite par ce second peintre puis, par dessus, la carte produite par le premier peintre
     * 
     * @param secondPainter
     *          second peintre 
     * 
     * @return Painter
     *          peintre composé des deux peintres
     *
     */
    
    public default Painter above (Painter secondPainter){
        return (map, canvas) -> {
            secondPainter.drawMap(map, canvas);
            drawMap(map, canvas);
        };  
    }
    
    /**
     * retournant un peintre utilisant l'attribut layer attaché aux entités de la carte pour la dessiner couche par couche ( de -5 à +5) 
     * 
     * @return Painter
     *          peintre dessinant couche par couche
     * 
     */
    
    public default Painter layered(){ 
            Painter p = when(Filters.onLayer(-5));
            for(int i=-4;i<=5;i++)
               p = when(Filters.onLayer(i)).above(p);
            return p;
            
    }
}
