package ch.epfl.imhof.painting;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.function.Function;



import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;

/**
 * La toile sur laquelle on dessine
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class Java2DCanvas implements Canvas {
    
    private final Function<Point, Point> coordinateChange;
    private final Graphics2D ctx;
    private final BufferedImage imageFinale;
    
    /**
     * @param Pbl
     *          point en bas a gauche
     *          
     * @param Ptr
     *          point en haut a droite
     *          
     * @param width
     *          largeur de l'image
     *          
     * @param height
     *          hauteur de l'image
     *          
     * @param resolutionPic
     *          resolution de l'image
     *          
     * @param backGroundColor
     *          couleur du fond de l'image
     *          
     */
    
    public Java2DCanvas(Point BL, Point TR, int width, 
                        int height, double resolutionPic, ColorCreation backGroundColor){
        
        imageFinale = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
        ctx = imageFinale.createGraphics();
        ctx.setRenderingHint(KEY_ANTIALIASING,
                VALUE_ANTIALIAS_ON);
        
        double scale = resolutionPic/72.0d;
        
        ctx.scale(scale,scale); 
        
        //tenir compte de cette dilatation lors du calcul du changement de repère.
        double newWidth = width/scale;
        double newHeight = height/scale;
        
        ctx.setColor(backGroundColor.toAwt());             
        ctx.fillRect(0, 0, (int)Math.ceil(newWidth), (int)Math.ceil(newHeight));
        
        coordinateChange = Point.alignedCoordinateChange(BL, new Point(0, newHeight), TR, new Point(newWidth,0));
    }
    

    @Override
    public void drawPolyLine(LineStyle style, PolyLine polyline){
        
        Path2D line = new Path2D.Double();
        boolean firstPoint = true;
        for(Point point : polyline.points()){
            Point transformedPoint = coordinateChange.apply(point);
            if(firstPoint){
                line.moveTo(transformedPoint.x(),transformedPoint.y());
                firstPoint = false;
            }
            else{
                line.lineTo(transformedPoint.x(),transformedPoint.y());
            }
        }

        if(polyline.isClosed())
            line.closePath();

        BasicStroke strokeWithStyle = new BasicStroke(style.width(), style.cap().ordinal(), 
                style.join().ordinal(), 10.0f, style.alternationSequency().length == 0 ? null : style.alternationSequency(), 0f);

        ctx.setStroke(strokeWithStyle);
        ctx.setColor(style.color().toAwt());
        ctx.draw(line);
    }
    @Override
    public void drawPolygon(ColorCreation color, Polygon polygone){
        
        // Construction de l'enveloppe et transformation en area
        Path2D shell = new Path2D.Double();
        boolean firstPoint = true;
        for(Point point : polygone.shell().points()){
            Point transformedPoint = coordinateChange.apply(point);
            if(firstPoint){
                shell.moveTo(transformedPoint.x(),transformedPoint.y());
                firstPoint = false;
            }
            else{
                shell.lineTo(transformedPoint.x(),transformedPoint.y());
            }
        }
              
        //shell est forcemment closed.
        shell.closePath();
        
        Area shelly = new Area(shell);       
        
        //Construction d'une area pour chaque trou, et substract à shelly pour chaque trou
        
        for(ClosedPolyLine closedPoly : polygone.holes()){
            
            Path2D hole = new Path2D.Double();
            boolean firstPointHole = true;
            for(Point point : closedPoly.points()){
                Point transformedPoint = coordinateChange.apply(point);
                if(firstPointHole){
                    hole.moveTo(transformedPoint.x(),transformedPoint.y());
                    firstPointHole=false;
                }
                else{
                    hole.lineTo(transformedPoint.x(),transformedPoint.y());
                }
            }
            
            //hole est forcemment closed.
            hole.closePath();
            //Transformation en area :
            Area holly = new Area(hole);
            shelly.subtract(holly);
        }
        
        //Dessin de shelly: (notre polygone), avec la couleur définie
        ctx.setColor(color.toAwt());
        ctx.fill(shelly);
 
    }
    
    /**permet d'obtenir l'image de la toile, de type BufferedImage.
     * 
     * @return imageFinale
     *          l'image de la toile
     */
    
    public BufferedImage image(){
        return imageFinale;
        
    }

}
