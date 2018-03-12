package ch.epfl.imhof.painting;

/**
 * regroupe tous les paramètres de style utiles au dessin d'une ligne
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class LineStyle {
    
    private final float width;
    private final ColorCreation color;
    private final float[] alternationSequency;
    private final LineCap cap;
    private final LineJoin join;


    /**
     * construit une lineStyle 
     * 
     * @param width
     *          largeur du trait
     * 
     * @param color
     *          couleur du trait
     * 
     * @param alternationSequency
     *          alternance des sections opaques et transparentes
     * 
     * @param cap
     *          terminaison des lignes
     * 
     * @param join
     *           jointure des segments
     * 
     * @throws IllegalArgumentException
     *         si la largeur du trait est négative ou si l'un des éléments de la séquence d'alternance des segments est négatif ou nul
     */
    
    public LineStyle(float width, ColorCreation color, LineCap cap, LineJoin join,
            float... alternationSequency) {
        if (width < 0)
            throw new IllegalArgumentException("La largeur est négative");

        for (int i = 0; i < alternationSequency.length; i++) {
            if (alternationSequency[i] < 0)
                throw new IllegalArgumentException("Sequence invalide");
        }
        this.width = width;
        this.color = color;
        this.alternationSequency = alternationSequency.clone();
        this.cap = cap;
        this.join = join;
    }

    /**
     * construit une linestyle avec la largeur et la couleur
     * 
     * @param width 
     *          largeur du trait
     * 
     * @param color
     *          couleur du trait
     *          
     */
    
    public LineStyle(float width, ColorCreation color) {
        this(width,color,LineCap.BUTT,LineJoin.MITER, new float[0]);
    
    }

    /**
     * énumère les trois types de terminaison des lignes qu'une LineStyle peut avoir
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     */
    
    public enum LineCap {
        BUTT, ROUND, SQUARE
    };

    /**
     * énumère les trois types de jointure des segments qu'une LineStyle peut avoir
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     */
    
    public enum LineJoin {
        MITER, ROUND, BEVEL
    };

    /**
     * méthode retournant la largeur du trait
     * 
     * @return width
     *          largeur du trait
     * 
     */
    public float width(){ return width; }
    
    /**
     * méthode retournant la couleur du trait
     * 
     * @return color
     *          couleur du trait
     *          
     */
    public ColorCreation color() { return color; }
    
    /**
     * méthode retournant l'alternance des sections opaques et transparentes
     * 
     * @return alternationSequency
     *          alternance des sections opaques et transparentes
     */
    public float[] alternationSequency() { 
        return alternationSequency.clone(); 
   }
    
    /**
     * méthode retournant les terminaison des traits
     * 
     * @return cap
     *          terminaison des traits
     */
    public LineCap cap() { return cap; }
    
    /**
     * méthode retournant les jointures des traits
     * 
     * @return join
     *          jointures des traits
     */
    public LineJoin join() {return join;}
    
    
    
    /**
     * méthode permettant d'obtenir un style identique à celui auquel on l'applique, sauf pour la largeur du trait
     * 
     * @param width 
     *          largeur du trait
     * 
     * @return LineStyle
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne  
     *          
     */
    public LineStyle withWidth(float newWidth){
        return new LineStyle(newWidth, color, cap, join, alternationSequency);
        
    }
    
    /**
     * méthode permettant d'obtenir un style identique à celui auquel on l'applique, sauf pour la couleur du trait
     * 
     * @param color
     *          couleur du trait
     *          
     * @return LineStyle
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     *          
     */
    public LineStyle withColor(ColorCreation newColor){
        return new LineStyle(width, newColor, cap, join, alternationSequency);
        
    }
    
    /**
     * méthode permettant d'obtenir un style identique à celui auquel on l'applique, sauf pour l'alternance des sections opaques et transparentes
     * 
     * @param alternationSequency
     *          l'alternance des sections opaques et transparentes
     * 
     * @return LineStyle
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     */
    public LineStyle withalternationSequency(float... newAlternationSequency){
        return new LineStyle(width, color, cap, join, newAlternationSequency);
        
    }
    
    /**
     * méthode permettant d'obtenir un style identique à celui auquel on l'applique, sauf pour la terminaison des traits
     * 
     * @param cap
     *          terminaison des traits
     * 
     * @return LineStyle
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     */
    public LineStyle withCap(LineCap newCap){
        return new LineStyle(width, color, newCap, join, alternationSequency);
        
    }
    
    /**
     * méthode permettant d'obtenir un style identique à celui auquel on l'applique, sauf pour les jointures des traits
     * 
     * @param join
     *          jointure des traits
     *          
     * @return LineStyle
     *          regroupe tous les paramètres de style utiles au dessin d'une ligne
     */
    public LineStyle withJoin(LineJoin newJoin){
        return new LineStyle(width, color, cap, newJoin, alternationSequency);
        
    }
    
}
