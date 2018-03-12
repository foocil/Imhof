package ch.epfl.imhof.dem;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.function.Function;

import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.painting.ColorCreation;
import ch.epfl.imhof.projection.Projection;

/**
 * classe permettant de dessiner un relief ombré coloré
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */


public final class ReliefShader {

    private final HGTDigitalElevationModel numericModel;
    private final Projection projection;
    private final Vector3 lightVector;
    

    /**
     * Construit un relief ombré coloré
     * 
     * @param projection
     *          méthode de projection sur une carte
     * 
     * @param modelNumeric
     *          modèle numérique du terrain
     * 
     * @param vectorLight
     *          le Vecteur de la lumière
     * 
     */

    
    public ReliefShader (Projection projection, HGTDigitalElevationModel numericModel, Vector3 lightVector){
        
        
        this.numericModel = numericModel;
        this.projection = projection;
        this.lightVector= lightVector;
        
    }
    
    /**
     * Méthode permettant de flouter le relief 
     * 
     * @param BL
     *          Point en bas à gauche
     * 
     * @param TR
     *          Point en haut à droite
     * 
     * @param widthPixel
     *          largeur en pixel de l'image
     * 
     * @param heightPixel
     *          hauteur en pixel de l'image
     * 
     * @param rayonF
     *          le rayon de floutage
     * 
     * @return
     *          le relief flouté
     *          
     */

    
    public BufferedImage shadedRelief(Point BL, Point TR, int widthPixel, int heightPixel, int rayonF){
        
        if(rayonF==0){
            Function<Point, Point> pixelInCartesian = Point.alignedCoordinateChange(new Point(0,heightPixel), BL, new Point(widthPixel,0), TR);
            return shadeCreation(widthPixel, heightPixel,pixelInCartesian); 
        }
        
        else{
            float [] blurArray = gaussianBlur(rayonF);
        
            int n = blurArray.length;

            Kernel kH = new Kernel(n, 1, blurArray);
            Kernel kV = new Kernel(1, n, blurArray);
            int tampon = (int)Math.floor(n/2.0d)    ; 
            
            Function <Point,Point>  pixelInCartesian= Point.alignedCoordinateChange(new Point(tampon, heightPixel+tampon), BL, 
                    new Point(widthPixel+tampon, tampon),TR);
            
            BufferedImage biggerShade = shadeCreation(widthPixel+n, heightPixel+n,pixelInCartesian); 
            ConvolveOp cH = new ConvolveOp(kH, ConvolveOp.EDGE_NO_OP, null);
            ConvolveOp cV = new ConvolveOp(kV, ConvolveOp.EDGE_NO_OP, null);
            return BluredImage(biggerShade, cH, cV, tampon, widthPixel, heightPixel);
        }      
    }
    

    /**
     * méthode permettant de flouter les images
     * 
     * @param shadedImage
     *          le relief de l'image non flouté
     * 
     * @param cH
     *          ConvolveOp horizontal
     * 
     * @param cV
     *          ConvolveOp vertical
     * 
     * @param width
     *          largeur en pixel
     * 
     * @param height
     *          hauteur en pixel
     * 
     * @return
     *          une image d'un relief flouté
     * 
     */

    private BufferedImage BluredImage(BufferedImage shadedImage, ConvolveOp cH, ConvolveOp cV, int tampon, int width, int height){       
        return cH.filter(cV.filter(shadedImage,null),null).getSubimage(tampon, tampon, width, height);  
   }
    
    /**
     * méthode permettant de calculer le noyau du flou gaussien (unidimensionnel)
     * 
     * @param rayon
     *          rayon de floutage 
     * 
     * @return
     *          un tableau unidimensionnel de floutage
     * 
     */

    private float[] gaussianBlur(int rayon){
        
        double sigma = rayon/3.0d;
        int n = (int)(2*Math.ceil(rayon)+1);
        float [] matrix = new float[n];
        
        //indices du centre de la matrice :
        int i=(n-1)/2;
        float somme=0;
        
        for(int e=0;e<n;e++){
                matrix[e]= (float) Math.pow(Math.E, -(Math.pow(i-e,2))/(2*Math.pow(sigma,2)));
                somme+=matrix[e];
            
        }
        return normalizedMatrix(matrix, somme);
    }
    
    /**
     * méthode permettant de normaliser une matrice unidimensionnelle
     * 
     * @param matrix
     *          tableau unidimensionnel représentant une matrice
     *          
     * @param somme
     *          la somme pour normaliser la matrice
     * 
     * @return
     *          la matrice normalisée 
     *
     */
    
    
    private float[] normalizedMatrix(float[] matrix, float somme){
        
        int matrixLength = matrix.length;
        
        float[] normMatrix = new float[matrixLength];
        float[] finalMatrix = new float[matrixLength];
        
        for(int i =0 ;i<matrixLength; i++){
                finalMatrix[i]=(matrix[i]/somme);
                normMatrix[i]=finalMatrix[i];     
        }
        
        return normMatrix; 
    }
    
    
    /**
     * méthode permettant de créer une image sans relief ni floutage
     * 
     * @param width
     *          largeur de l'image
     * 
     * @param height
     *          hauteur de l'image
     * 
     * @param pixelInCartesian
     *          fonction passant des pixels de la carte en coordonnées cartésiennes du plan
     * 
     * @return
     *          image non floutée et sans relief
     * 
     */
    
    private BufferedImage shadeCreation(int width, int height, Function<Point, Point> pixelInCartesian){
        
        BufferedImage finalImage = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i =0; i<height ; i++){
            for(int j = 0; j<width; j++){
                Vector3 normalVector = numericModel.normalAt(projection.inverse(pixelInCartesian.apply(new Point(j,i))));
                double cosinus = (normalVector.scalarProduct(lightVector))/(normalVector.norm()*lightVector.norm());
                double r = 0.5*(cosinus+1);
                double g = 0.5*(cosinus+1);
                double b = 0.5*(0.7*cosinus+1);
                finalImage.setRGB(j,i,ColorCreation.rgb(r,g,b).toAwt().getRGB());
            }   
        }
        
        return finalImage;
    }
}
