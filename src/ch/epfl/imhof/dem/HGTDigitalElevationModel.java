package ch.epfl.imhof.dem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ShortBuffer;
import java.nio.channels.FileChannel.MapMode;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;

/**
 * classe permettant de lire un modèle numérique du terrain depuis un fichier au format HGT
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class HGTDigitalElevationModel implements DigitalElevationModel {
    private final double latitude;
    private final double longitude;
    private ShortBuffer buffer;
    private final FileInputStream stream;
    private final int arrayLength;

    /**
     * Construit un model HGT depuis un fichier HGT   
     * 
     * @param nameFile
     *          le nom du fichier
     * 
     * @throws FileNotFoundException
     *          Exception si le fichier n'est pas trouvé
     * 
     * @throws IOException
     *          autre erreur d'entrée / sortie
     *       
     */
    
    public HGTDigitalElevationModel(File HGTFile) throws FileNotFoundException, IOException{

       // Test et affectation de arrayLength
        long fileLength = HGTFile.length();
        double arrayLengthCheck = Math.sqrt(fileLength / 2);
        if (arrayLengthCheck % 1 != 0)
            throw new IllegalArgumentException(
                    "taille en octet divisée par deux n'a pas une racine carrée entière");
        this.arrayLength = (int) arrayLengthCheck;

        
        String fileName = HGTFile.getName();
        
        
        //check la longueur du nom du fichier
        if(fileName.length()!=11)
            throw new IllegalArgumentException(
                    "Nom de fichier incorrect");


        // L'extension doit correspondre
        
        String extension = fileName.substring(7, 11);
         
        if (!(extension.equals(".hgt")))
            throw new IllegalArgumentException("fichier incorrect : extension incorrecte");
        
        
        
        // Les lattitudes et longitudes doivent correspondre
        String latitude = fileName.substring(1, 3);

        String longitude = fileName.substring(4, 7);

        try {
            this.latitude = Integer.parseInt(latitude) * signeLatitude(fileName);
            this.longitude = Integer.parseInt(longitude) * signeLongitude(fileName);
        } catch (NumberFormatException n) {
            throw new IllegalArgumentException("fichier incorrect : latitude ou longitude incorrecte");
        }
         
        //affectation du buffer
       
        try (FileInputStream s = new FileInputStream(HGTFile)) {

            buffer = s.getChannel().map(MapMode.READ_ONLY, 0, fileLength).asShortBuffer();
            stream = s;
        }
    }
    
    /**
     * Teste et retourne le signe de la longitude
     * 
     * @param fileName
     *         Le nom du fichier
     *         
     * @return
     *         Le signe correspondant au caractère de la longitude
     *          
     */
    
    private int signeLongitude(String fileName){
        
        if(fileName.charAt(3)=='W')
            return -1;
        
        else if(fileName.charAt(3)=='E')
            return 1;
        
        else
            throw new IllegalArgumentException("Signe de longitude incorrect !");
    }
    
    
    /**
     * Teste et retourne le signe de la latitude
     * 
     * @param fileName
     *         Le nom du fichier
     * 
     * @return
     *         Le signe correspondant au caractère de la latitude
     *
     */
    private int signeLatitude(String fileName){
        
        if(fileName.charAt(0)=='S')
            return -1;
        
        else if(fileName.charAt(0)=='N')
            return 1;
        
        else
            throw new IllegalArgumentException("Signe de latitude incorrect !");
    }
    

    @Override
    public void close() throws Exception {
        buffer = null;
        stream.close();
    }

    @Override
    public Vector3 normalAt(PointGeo WGS84) {
        double delta = 1.0d / (arrayLength-1);
        double s = Math.toRadians(delta) * Earth.RADIUS;
        
        double lon = Math.toDegrees(WGS84.longitude());
        double lat = Math.toDegrees(WGS84.latitude());
       

        if(!(lat >= latitude && lat <= latitude + 1))
            throw new IllegalArgumentException("point non correct : latitude");
        if( !(lon >= longitude && lon <= longitude + 1))
            throw new IllegalArgumentException("point non correct : longitude");
        
        
        
        int i = (int)Math.floor((arrayLength-1)* (lon-longitude));
        int j = (int)Math.floor((arrayLength-1)* (lat-latitude));
        
        
        // Au cas d'un effet de bord
        if(i>=arrayLength)
            i--;
        
        if(j>=arrayLength)
            j--;
        
        
        double za = altitudeAt(i+1,j) - altitudeAt(i,j);
        double zb = altitudeAt(i,j+1) - altitudeAt(i,j);
        double zc = altitudeAt(i,j+1) - altitudeAt(i+1,j+1);
        double zd = altitudeAt(i+1,j) - altitudeAt(i+1,j+1);
        
        return new Vector3(0.5*s*(zc-za), 0.5*s*(zd-zb), s*s);
        
    }
    
    /**
     * Retourne l'altitude d'un point (i,j)
     * 
     * @param i
     *      l'indice de la largeur
     *      
     * @param j
     *      l'indice de la hauteur
     *      
     * @return
     *      l'altitude correspondant au point (i,j)
     *      
     */
    
    private double altitudeAt(int i, int j){

        return buffer.get(i + arrayLength * (arrayLength - j - 1));
        
  
    }
}
