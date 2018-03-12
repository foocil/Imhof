package ch.epfl.imhof;

/**
 * Classe créant un vecteur en trois dimensions
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class Vector3 {
    
    private final double x;
    private final double y;
    private final double z;
    
    /**
     * Construit un vecteur selon ses composantes x, y et z
     * 
     * @param x
     *          composante x du vecteur  
     * 
     * @param y
     *         composante y du vecteur 
     * 
     * @param z
     *         composante z du vecteur 
     *
     */
       
    public Vector3(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /** 
     * calcule et retourne la norme du vecteur
     * 
     * @return normVector
     *          la norme du Vecteur
     */
    
    public double norm(){
        return Math.sqrt(x*x + y*y+z*z);
    }
    
    /**
     * normalise le vecteur 
     * 
     * @return normalizedVector
     *          le vecteur normalisé
     */
    
    public Vector3 normalized(){
        double xp= x/norm();
        double yp = y/norm();
        double zp = z/norm();
        
        return new Vector3(xp,yp,zp);
        
    }
    
    /** 
     * effectue le produit scalaire entre le vecteur et un vecteur passé en argument
     * 
     * @param vector2
     *              second vecteur 
     * 
     * @return scalarProduct
     *          le produit scalaire entre les 2 vecteurs
     */
    
    public double scalarProduct(Vector3 vector2){
        return x*vector2.x + y*vector2.y + z*vector2.z;
        
    }
}
